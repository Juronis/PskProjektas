app.service('summerHouseService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/summerhouses/";

    this.getSummerHouse = function (id) {
        var url = baseUrl + "id/"+id;
        return $http.get(url);
    }

    this.getSummerHouses = function () {
        var url = baseUrl + "all";
        return $http.get(url);
    }

    this.editSummerHouse = function (data) {
        var url = baseUrl + "edit";
        $http.post(url, data);
    }

    this.addSummerHouse = function (data) {
        var url = baseUrl + "add";
        $http.post(url, data);
    }

    this.addImage = function (data) {
        var url = baseUrl + "addImage";
        $http.post(url, data);
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