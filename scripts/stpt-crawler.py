from unidecode import unidecode

import requests
from xml.etree.ElementTree import Element, tostring
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


# ToDo: create an XML containing all the vehicle types and id's from the mapping up there
def crawl_for_data():
    all_vehicles = [available_busses, available_trams, available_trolleys]
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

                for line, arrival in grouped(filtered_lines, 2):
                    station = Element('station')
                    # ToDo: have some mapping for stations here and append the proper StationID
                    # station.set('station_id', stations[name])
                    station.text = line.text
                    arr_time = Element("arrival")
                    arr_time.text = arrival.text

                    timetable_entry.append(station)
                    timetable_entry.append(arr_time)

            else:
                print(f'Could not get data for line {vehicle_name}, ID: {vehicle_id}')

            xml_document.append(timetable_entry)
            break


        print(tostring(xml_document, encoding='utf8', method='xml'))
        break



crawl_for_data()
