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

    this.editSummerHouse = function (id, data) {
        var url = baseUrl + "edit/"+id;
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

    this.getDisabledDates = function (id) {
        var url = baseUrl + "disabledDates/"+id;
        return $http.get(url);
    }

    this.reserve = function(id, dateFrom, dateTo, data) {
        var url = baseUrl + "reserve/" + id + "/" + dateFrom + "/" + dateTo;
        $http.post(url, data);
    }

    this.deleteById = function(id, data) {
        var url = baseUrl + "delete/"+id;
        $http.post(url, data);
    }

}]);