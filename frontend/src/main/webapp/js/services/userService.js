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

    this.sendRecommendation = function(email) {
        var url = baseUrl+"sendRecommendation";
        return $http.post(url, email);
    }

    this.isMember = function () {
        this.getUserDataByAuth().then(function(response){
            return response.data.role === 'MEMBER';
        });
        return false;
    };

    this.isAdmin = function () {
        this.getUserDataByAuth().then(function(response){
            return response.data.role === 'ADMIN';
        });
        return false;
    };

    this.isCandidate = function () {
        this.getUserDataByAuth().then(function(response){
            return response.data.role === 'CANDIDATE';
        });
        return false;
    };

    this.userCredits = function() {
        this.getUserDataByAuth().then(function(response) {
            return response.data.amount;
        });
        return null;
    }

    this.getUserByEmail = function(data) {
        var url = baseUrl + "byEmail";
        return $http.post(url, data);
    }

}]);