app.service('userService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resource/user/";

    this.getUserData = function() {
        var url = baseUrl+"getUserData";
        $http.get(url).then(function (response) {
            return response.data;
        }).catch(function (response) {
            //TODO error handling
        })
    }

    this.setUserData = function(userInfo) {
        var url = baseUrl+"setUserData";
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