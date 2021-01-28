var app = angular.module("xml", []);

app.controller("xmlCtrl", function ($scope, $http) {


    var basedUrl = '';
    var x2js = new X2JS();
    var response = '';
    var element = ''

    $scope.clear = function () {
        $scope.stationsResult = null;
        $scope.vehiclesResult = null;
        $scope.tablesResult = null;
        $scope.allArrivalsResult = null;
        $scope.allDeparturesResult = null;

        $scope.stationByIdResult = null;
        $scope.vehicleByIdResult = null;
        $scope.tableByIdResult = null;

        $scope.enableVehicleInput = null;
        $scope.enableStationInput = null;
        $scope.enableTableInput = null;

        $scope.latitudeLongitudeInputs = null;
        $scope.lastArrivalInput = null;
        $scope.lastDepartureInput = null;
        $scope.allArrivalsInput = null;
        $scope.allDeparturesInput = null;

    }

    $scope.showSearchInput = function (type) {
        $scope.clear();
        if (type == 'station') {
            $scope.enableStationInput = true;
        } else if (type == 'vehicle') {
            $scope.enableVehicleInput = true;
        } else if (type == 'table') {
            $scope.enableTableInput = true;
        }

    }
    
    $scope.enableClosestStationInputs = function () { 
        $scope.clear();
        $scope.latitudeLongitudeInputs = true;
    }

    $scope.enableLastArrivalInputs = function () { 
        $scope.clear();
        $scope.lastArrivalInput = true;
    }

    $scope.enableLastDepartureInputs = function () { 
        $scope.clear();
        $scope.lastDepartureInput = true;
    }

    $scope.enableAllArrivalsInputs = function () { 
        $scope.clear();
        $scope.allArrivalsInput = true;
    }

    $scope.enableAllDepaturesInputs = function () { 
        $scope.clear();
        $scope.allDeparturesInput = true;
    }

    $scope.getStations = function () {
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/stations/';
        get(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</transport_station>'
                $scope.stationsResult = xmlToJson(response, element);
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getVehicles = function () {
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/vehicles/';
        get(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</vehicle>'
                $scope.vehiclesResult = xmlToJson(response, element);
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getTimeTables = function () {
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/timetables';
        get(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</timetable>'
                $scope.tablesResult = xmlToJson(response, element);
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }


    $scope.getStationById = function () {
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/station/' + $scope.inputStaion;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.stationByIdResult = node ? node['transport-station'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }


    $scope.getVehicleByID = function () {
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/vehicle/' + $scope.inputVehicle;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.vehicleByIdResult = node ? node['vehicle'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }


    $scope.getTableByID = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/timetable/' + $scope.inputTable;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.tableByIdResult = node ? node['timetable'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }

    $scope.getClosest = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:8080/closestStation/?latitude=' + $scope.latitude + '&longitude=' + $scope.longitude;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.stationByIdResult = node ? node['transport-station'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getLastArrival = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:8080/lastArrival/' + $scope.lastArrivalID;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.vehicleByIdResult = node ? node['vehicle'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getLastDeparture = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:8080/lastDeparture/' + $scope.lastDepartureID;
        get(basedUrl).then(
            function (success) {
                node = x2js.xml_str2json(success.data);
                $scope.vehicleByIdResult = node ? node['vehicle'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getAllArrivals = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:8080/allArrivals/' + $scope.allArrivalsID;
        console.log(basedUrl);
        get(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                node = x2js.xml_str2json(response);
                
                $scope.allArrivalsResult = node ? node['vehicle_arrivals']['vehicle_arrival'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    $scope.getAllDepartures = function () { 
        $scope.clear();
        $scope.allDepaturesID = '5841'
        basedUrl = 'http://localhost:8080/allDepartures/' + $scope.allDepaturesID;
        get(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                node = x2js.xml_str2json(response);
                
                $scope.allDeparturesResult = node ? node['vehicle_departures']['vehicle_departure'] : null;
            },
            function (error) {
                console.log("error: ", error);
            }
        );
    }

    function xmlToJson(xml, element) {
        var xmlArr = xml.split(element);
        var node = {}
        result = [];
        for (i = 0; i < xmlArr.length; i++) {
            node = x2js.xml_str2json(xmlArr[i] + element);
            if (node) { 
                result.push(node)
            }
        }
        return result
    }

    function get(url) {
        return $http.get(url);
    }
  
});