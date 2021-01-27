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

        $scope.stationByIdResult = null;
        $scope.vehicleByIdResult = null;
        $scope.tableByIdResult = null;

        $scope.enableVehicleInput = null;
        $scope.enableStationInput = null;
        $scope.enableTableInput = null;

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
                obj = node['transport-station'];
                $scope.stationByIdResult = node['transport-station'];
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
                $scope.vehicleByIdResult = node['vehicle'];
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
                $scope.tableByIdResult = node['timetable'];
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
            
            // break;
        }
        return result

    }







    function get(url) {
        return $http.get(url);
    }
  







});