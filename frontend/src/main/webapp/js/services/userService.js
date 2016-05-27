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

    this.delete = function() {
        var url = baseUrl+"delete";
        return $http.get(url);
    }

    this.checkPassword = function(data) {
        var url = baseUrl+"checkPassword";
        return $http.get(url, data);
    }

    this.sendEmailRequest = function(email) {
        var url = baseUrl+"sendEmailRequest";
        return $http.post(url, email);
    }

    this.isMember = function () {
        this.getUserDataByAuth().then(function(response){
            return response.role === 'MEMBER';
        });
        return false;
    };

    this.isAdmin = function () {
        this.getUserDataByAuth().then(function(response){
            return response.role === 'ADMIN';
        });
        return false;
    };

    this.isCandidate = function () {
        this.getUserDataByAuth().then(function(response){
            return response.role === 'CANDIDATE';
        });
        return false;
    };

}]);