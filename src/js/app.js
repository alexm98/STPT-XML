var app = angular.module("xml", []);

app.controller("xmlCtrl", function ($scope, $http) {


    var basedUrl = '';
    var x2js = new X2JS();
    var response = '';
    var element = ''
    // $scope.enableStationInput = false;

    $scope.clear = function () {
        $scope.stationsResult = null;
        $scope.vehiclesResult = null;
        $scope.tableByIdResult = null;

        $scope.stationByIdResult = null;
        $scope.vehicleByIdResult = null;
        $scope.tableByIdResult = null;

        $scope.enableVehicleInput = null;
        $scope.enableStationInput = null;
        $scope.enableTableInput = null;

        $scope.enableStationDeleteInput = null;
        $scope.enableVehicleDeleteInput = null;
        $scope.enableTableDeleteInput = null;


        $scope.enableTableUpdateInput = null;
        $scope.enableVehicleUpdateInput = null;
        $scope.enableStationUpdateInput = null;

        $scope.enableStationAddInput = null;
        $scope.enableVehicleAddInput = null;
        $scope.enableTableAddInput = null;
        


        $scope.putStationID = null;
        $scope.putLineID = null;
        $scope.putLineName = null;
        $scope.putRowName = null;
        $scope.putFriendlyName = null;
        $scope.putshortName = null;
        $scope.putLat = null;
        $scope.putLong = null;
        $scope.putInvalid = null;
        $scope.putVerficationData = null;
        $scope.putGmaps = null;
        $scope.putInfoComments = null;

        
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

    $scope.showUpdateInput = function (type) {
        $scope.clear();
        if (type == 'station') {
            $scope.enableStationUpdateInput = true;
        } else if (type == 'vehicle') {
            $scope.enableVehicleUpdateInput = true;
        } else if (type == 'table') {
            $scope.enableTableUpdateInput = true;
        }

    }


    $scope.showAddInputs = function (type) {
        $scope.clear();
        if (type == 'station') {
            $scope.enableStationAddInput = true;
        } else if (type == 'vehicle') {
            $scope.enableVehicleAddInput = true;
        } else if (type == 'table') {
            $scope.enableTableAddInput = true;
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



    $scope.addStation = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/station';
        

        var params = {}
        params['transport-station'] = {}

        params['transport-station']['_id'] = $scope.putStationID
        params['transport-station']['line-id'] = $scope.putLineID
        params['transport-station']['line-name'] = $scope.putLineName

        params['transport-station']['raw-station-name'] = $scope.putRowName
        params['transport-station']['friendly-station-name'] = $scope.putFriendlyName
        params['transport-station']['short-station-name'] = $scope.putshortName
        
        params['transport-station']['lat'] = $scope.putLat
        params['transport-station']['long'] = $scope.putLong
        params['transport-station']['invalid'] = $scope.putInvalid

        params['transport-station']['verified'] = $scope.putVerified
        params['transport-station']['verification-date'] = $scope.putVerficationData
        params['transport-station']['gmaps-link'] = $scope.putGmaps
        params['transport-station']['info-comments'] = $scope.putInfoComments



        var xmlBodyRequet = x2js.json2xml_str(params);

        put(basedUrl, xmlBodyRequet).then(
            function (success) {
                $scope.getStations();
                
            },
            function (error) {
                console.log("error: ", error);
            }
        );

    }




    $scope.updateStation = function () { 
        $scope.clear();
        basedUrl = 'http://localhost:9091/api/station/' + $scope.putReplaceStation;
        

        var params = {}
        params['transport-station'] = {}

        params['transport-station']['_id'] = $scope.putStationID
        params['transport-station']['line-id'] = $scope.putLineID
        params['transport-station']['line-name'] = $scope.putLineName

        params['transport-station']['raw-station-name'] = $scope.putRowName
        params['transport-station']['friendly-station-name'] = $scope.putFriendlyName
        params['transport-station']['short-station-name'] = $scope.putshortName
        
        params['transport-station']['lat'] = $scope.putLat
        params['transport-station']['long'] = $scope.putLong
        params['transport-station']['invalid'] = $scope.putInvalid

        params['transport-station']['verified'] = $scope.putVerified
        params['transport-station']['verification-date'] = $scope.putVerficationData
        params['transport-station']['gmaps-link'] = $scope.putGmaps
        params['transport-station']['info-comments'] = $scope.putInfoComments



        var xmlBodyRequet = x2js.json2xml_str(params);

        post(basedUrl, xmlBodyRequet).then(
            function (success) {
                $scope.getStations();
                
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
        return $http.post(url, params);
        // return $http.post(url, JSON.stringify(params));
    }
    function put(url, params) {
        return $http.put(url, params);
    }








});