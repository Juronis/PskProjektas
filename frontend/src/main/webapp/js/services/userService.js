app.service('userService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/members/";

    this.getUserDataByAuth = function() {
        var url = baseUrl+"byauth";
        return $http.get(url);
    }

    this.getRole = function() {
        this.getUserDataByAuth().then(function(response) {
            return response.data.role;
        });
    }

    var role = this.getRole();

    this.getUserDataById = function(id) {
        var url = baseUrl+"byId/"+id;
        return $http.get(url);
    }

    this.setUserData = function(userInfo) {
        var url = baseUrl+"update";
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

    this.isMember = function () {
        return role === 'FULLMEMBER';
    };

    this.isAdmin = function () {
        return role === 'ADMIN'
    };

    this.isCandidate = function () {
        /* this.getUserDataByAuth().then(function(response){
            return response.data.role === 'CANDIDATE';
        }).catch(function(response) {
            return false;
        }); */
        return role === 'CANDIDATE';
    };

    this.userCredits = function() {
        this.getUserDataByAuth().then(function(response) {
            return response.data.amount;
        });
        return null;
    }

    this.getUserByEmail = function(data) {
        var url = baseUrl + "byemail";
        return $http.post(url, data);
    }

    this.addCredits = function(data) {
        var url = baseUrl + "addcredits";
        return $http.post(url, data);
    }

    this.getCandidatesTotal = function () {
        var url = baseUrl + "candidates/total";
        return $http.get(url);
    }

    this.getAllCandidates = function () {
        var url = baseUrl + "candidates/all";
        return $http.get(url);
    }

    this.getAllUsers = function () {
        var url = baseUrl + "members/all";
        return $http.get(url);
    }

}]);