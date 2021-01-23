import pickle
import requests
from unidecode import unidecode

from fuzzywuzzy import fuzz

from xml.etree.ElementTree import ElementTree, Element, tostring
from bs4 import BeautifulSoup


STPT_RAIDFLEET_URL = 'http://86.125.113.218:61978/html/timpi/trasee.php'

available_trams = {'1': 1106, '2': 1126, '4': 1266, '6a': 2686,
                   '6b': 2706, '7': 2846, '8': 158, '9': 2406}

available_trolleys = {'11': 990, 'M11': 2786, '14': 1006, 'M14': 2766,
                      '15': 989, '16': 1206, '17': 1086, '18': 1166}

available_busses = {'3': 1207, '5a': 2426, '5b': 2446, '5c': 3446,
                    '13': 1066, '21': 1146, '28': 1226, '32': 1546,
                    '33': 1046, '40': 886, '46': 1406,
                    'E1': 1550, 'E2': 1551, 'E3': 1552, 'E4': 1926,
                    'E6': 1928, 'E7': 2026, 'E8': 1547,
                    'M22': 2906, 'M29': 3086, 'M30': 1746, 'M35': 1986,
                    'M36': 2006, 'M41': 3306, 'M42': 3307, 'M43': 2646, 'M44': 2506,
                    'M45': 2606, 'M46': 3326, 'M47': 3560, 'M48': 3406, 'M49': 3426,
                    'M50': 3486, 'M51': 3466, 'M52': 3546}

# iterate over a list two elements at a time
def grouped(iterable, n):
    return zip(*[iter(iterable)]*n)


# Filter for entries like Sosire, Statie or Linie
def filter_others(x):
    if 'Statia' in unidecode(x.text):
        return False
    if 'Linia' in x.text:
        return False
    if 'Sosire' in x.text:
        return False
    return True


def crawl_for_data():
    all_vehicles = [available_busses, available_trams, available_trolleys]
    # mapping of type StationShortName: id -> Ex: 'Ghe. Ranetti': '3595'
    with open('../data/station_with_ids.pkl', 'rb') as fh:
        station_ids = pickle.load(fh)

    xml_document = Element('timetables')

    for vehicle_type in all_vehicles:
        for vehicle_name, vehicle_id in vehicle_type.items():
            timetable_entry = Element('timetable')
            timetable_entry.set('vehicle_id', str(vehicle_id))

            resp = requests.get(f'{STPT_RAIDFLEET_URL}?param1={vehicle_id}')
            print(f'{STPT_RAIDFLEET_URL}?param1={vehicle_id}')

            if resp.status_code == 200:
                soup = BeautifulSoup(resp.text, 'html.parser')
                lines = soup.find_all('b')
                filtered_lines = filter(filter_others, lines)
                last_line = None
                line_changed = 0
                direction_1 = Element('direction1')
                direction_2 = Element('direction2')
                direction = direction_1

                for line, arrival in grouped(filtered_lines, 2):
                    entry = Element('arrival')
                    station = Element('station')

                    for key, value in station_ids.items():
                        if (fuzz.token_set_ratio(line.text, key) > 60):
                            station.set('station_id', station_ids[key])
                            # print(f"{line.text} - {key} - {fuzz.token_set_ratio(line.text, key)}")
                            pass

                    station.text = unidecode(line.text)
                    arr_time = Element("time")
                    arr_time.text = arrival.text

                    entry.append(station)
                    entry.append(arr_time)

                    if not(line.text == last_line) and line_changed == 0:
                        direction = direction_1
                    else:
                        line_changed = 1
                        direction = direction_2

                    direction.append(entry)
                    last_line = line.text

                timetable_entry.append(direction_1)
                timetable_entry.append(direction_2)
            else:
                print(f'Could not get data for line {vehicle_name}, ID: {vehicle_id}')

            xml_document.append(timetable_entry)

        # print(tostring(xml_document, encoding='utf8', method='xml'))
    ElementTree(xml_document).write('timetables.xml')


crawl_for_data()
