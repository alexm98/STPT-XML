var app = angular.module("xml", []);

app.controller("xmlCtrl", function ($scope, $http) {


    var basedUrl = '';
    var x2js = new X2JS();
    var response = '';
    var element = ''
    // $scope.enableStationInput = false;

    $scope.clear = function () {
        $scope.stationsResult = null;
        $scope.stationByIdResult = null;
        $scope.vehiclesResult = null;
        $scope.vehicleByIdResult = null;
        $scope.tablesResult = null;
        $scope.tableByIdResult = null;
        $scope.enableVehicleInput = null;
        $scope.enableStationInput = null;
        $scope.enableTableInput = null;
        $scope.enableStationDeleteInput = null;
        $scope.enableVehicleDeleteInput = null;
        $scope.enableTableDeleteInput = null;
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
    $scope.showDeleteInput = function (type) {
        $scope.clear();
        if (type == 'station') {
            $scope.enableStationDeleteInput = true;
        } else if (type == 'vehicle') {
            $scope.enableVehicleDeleteInput = true;
        } else if (type == 'table') {
            $scope.enableTableDeleteInput = true;
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



    $scope.deleteStaion = function () { 
        basedUrl = 'http://localhost:9091/api/station/' + $scope.deleteStationID;
        del(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</transport_station>'
                $scope.stationsResult = xmlToJson(response, element);
                $scope.enableStationDeleteInput = false;
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }

    $scope.deleteVehicle = function () { 
        basedUrl = 'http://localhost:9091/api/vehicle/' + $scope.deleteVehicleID;
        del(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</vehicle>'
                $scope.vehiclesResult = xmlToJson(response, element);
                $scope.enableVehicleDeleteInput = false;
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }

    $scope.deleteTable = function () { 
        basedUrl = 'http://localhost:9091/api/timetable/' + $scope.deleteTableID;
        del(basedUrl).then(
            function (success) {
                response = success.data.replaceAll("-", "_");
                element = '</timetable>'
                $scope.tablesResult = xmlToJson(response, element);
                $scope.enableTableDeleteInput = false;
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
    function del(url) {
        return $http.delete(url);
    }

    function post(url, params) {
        return $http.post(url, JSON.stringify(params));
    }








});