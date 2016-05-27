app.service('userService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/members/";

    this.getUserDataByAuth = function() {
        var url = baseUrl+"byAuth";
        return $http.get(url);
    }

    this.getUserDataById = function(id) {
        var url = baseUrl+"byId/"+id;
        return $http.get(url);
    }

    this.setUserData = function(userInfo) {
        var url = baseUrl+"update";
        return $http.post(url, userInfo);
    }

    this.sendEmailRequest = function(email) {
        var url = baseUrl+"sendEmailRequest";
        return $http.post(url, email);
    }

    this.isMember = function () {
        return this.getUserData.role === 'MEMBER';
    };

    this.isCandidate = function () {
        return this.getUserData.role === 'CANDIDATE';
    };

    this.isAdmin = function () {
        return this.getUserData.role === 'ADMIN';
    };

}]);