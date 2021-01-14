import xml.etree.ElementTree as ET

tree = ET.parse('../data/statii-ratt.xml')
root = tree.getroot()

for tag in root.findall('TransportStations/TransportStation'):
    station_id = tag.find('StationID')
    tag.remove(station_id)
    tag.set('id', station_id.text)

with open('../data/statii-ratt-format.xml', 'wb') as f:
    tree.write(f)
