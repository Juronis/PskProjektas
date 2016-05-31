app.service('summerHouseService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/summerhouses/";

    this.getSummerHouse = function (id) {
        var url = baseUrl + "byid/"+id;
        return $http.get(url);
    }

    this.getSummerHouses = function () {
        var url = baseUrl + "all";
        return $http.get(url);
    }

    this.editSummerHouse = function (data) {
        var url = baseUrl + "edit";
        return $http.post(url, data);
    }

    this.addSummerHouse = function (data) {
        var url = baseUrl + "add";
        return $http.post(url, data);
    }

    this.addImage = function (file) {
        var url = baseUrl + "upload";
        var fd = new FormData();
        fd.append('file', file);
        return $http.post(url, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    }

    this.addServices = function (id, data) {
        var url = baseUrl + "addServices/"+id;
        $http.post(url, data);

    }

    this.deleteSummerHouse = function (id) {
        var url = baseUrl + "delete/"+id;
        return $http.get(url);
    }

    this.getDisabledDates = function (id) {
        var url = baseUrl + "disabledDates/"+id;
        return $http.get(url);
    }

}]);