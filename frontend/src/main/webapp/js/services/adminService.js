app.service('adminService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/parameters/";

    this.getSettings = function(data) {
        var url = baseUrl + "get/"+data;
        return $http.get(url);
    }

    this.updateSettings = function(data) {
        var url = baseUrl + "update";
        return $http.post(url, data);
    }

}]);