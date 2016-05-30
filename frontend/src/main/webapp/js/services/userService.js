app.service('userService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/members/";

    this.getUserDataByAuth = function() {
        var url = baseUrl+"byauth";
        return $http.get(url);
    }

    this.getUserDataById = function(id) {
        var url = baseUrl+"byid/"+id;
        return $http.get(url);
    }

    this.setUserData = function(userInfo) {
        var url = baseUrl+"update";
        return $http.post(url, userInfo);
    }

    this.setUserDataById = function(id,userInfo) {
        var url = baseUrl+"update/"+id;
        return $http.post(url, userInfo);
    }

    this.delete = function(data) {
        var url = baseUrl+"delete";
        return $http.post(url, data);
    }

    this.sendEmailRequest = function(email) {
        var url = baseUrl+"sendEmailRequest";
        return $http.post(url, email);
    }

    this.sendRecommendation = function(email) {
        var url = baseUrl+"sendRecommendation";
        return $http.post(url, email);
    }

    this.isMember = function (role) {
        return this.isAdmin(role) || role === 'FULLMEMBER';
    };

    this.isAdmin = function (role) {
        return role === 'ADMIN'
    };

    this.isCandidate = function (role) {
        return role === 'CANDIDATE';
    };

    this.getUserByEmail = function(data) {
        var url = baseUrl + "byemail";
        return $http.post(url, data);
    }

    this.addCredits = function(data) {
        var url = baseUrl + "addcredit";
        return $http.post(url, data);
    }

    this.getCandidatesTotal = function () {
        var url = baseUrl + "total/candidates";
        return $http.get(url);
    }

    this.getAllCandidates = function () {
        var url = baseUrl + "all/candidates";
        return $http.get(url);
    }

    this.getAllUsers = function () {
        var url = baseUrl + "all/adminsmembers";
        return $http.get(url);
    }

    this.payMembership = function () {
        var url = baseUrl + "membership";
        return $http.post(url);
    }

}]);