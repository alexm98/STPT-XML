from lxml import etree


class XMLPruning:
    def __init__(self, xml_document_location):
        self.xml_document_location = xml_document_location
        self.root = self.get_root()

    def get_root(self) -> etree.Element:
        tree = etree.parse(self.xml_document_location)
        return tree.getroot()

    def remove_duplicates_by_id(self, element_type):
        visited = set()

        for element in self.root.iter(element_type):
            if 'id' in element.attrib:
                self.check_visited_id(element, visited)

        self.write_to_file()
        return visited

    def check_visited_id(self, element, visited):
        current = element.get('id')
        if current in visited:
            print("Removing element with id " + current)
            element.getparent().remove(element)
        else:
            visited.add(current)

    def write_to_file(self):
        with open("new-" + self.xml_document_location, 'wb') as doc:
            doc.write(etree.tostring(self.root, pretty_print=True))


if __name__ == "__main__":
    # usage example
    xmlP = XMLPruning("statii-ratt-format.xml")
    print(len(xmlP.remove_duplicates_by_id("TransportStation")))
