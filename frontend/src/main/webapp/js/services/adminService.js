app.service('adminService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/admin/";

    this.getUsersSettings = function() {
        var url = baseUrl + "membersSettings";
        return $http.get(url);
    }

    this.setUsersSettings = function(data) {
        var url = baseUrl + "setMembersSettings";
        return $http.post(url, data);
    }

    this.addCredits = function(data) {
        var url = baseUrl + "addCredits";
        return $http.post(url, data);
    }

}]);