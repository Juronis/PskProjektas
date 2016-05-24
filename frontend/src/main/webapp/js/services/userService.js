app.service('userService', ['$http', '$rootScope', function ($http, $rootScope) {

    var baseUrl = "/frontend/services/resource/user/";

    this.getUserData = function() {
        var url = baseUrl+"getUserData";
        return $http.get(url);
    }

    this.setUserData = function(userInfo) {
        var url = baseUrl+"setUserData";
        return $http.post(url, userInfo);
    }

    this.sendEmailRequest = function(email) {
        var url = baseUrl+"sendEmailRequest";
        return $http.post(url, email);
    }

}]);