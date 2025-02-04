import xml.etree.ElementTree as ET

tree = ET.parse('../data/statii-ratt-oldformat.xml')
root = tree.getroot()

for tag in root.findall('TransportStations/TransportStation'):
    station_id = tag.find('StationID')
    tag.remove(station_id)
    tag.set('id', station_id.text)

with open('../data/statii-ratt.xml', 'wb') as f:
    tree.write(f)
