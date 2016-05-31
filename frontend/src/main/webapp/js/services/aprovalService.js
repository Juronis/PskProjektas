app.service('aprovalService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/approvals/";

    this.askRecommendation = function(data) {
        var url = baseUrl + "ask";
        return $http.post(url, data);
    }

    this.aprove = function(data) {
        var url = baseUrl + "approver/approve";
        return $http.post(url, data);
    }

    this.getCandidatesList = function() {
        var url = baseUrl + "approver/candidates";
        return $http.get(url);
    }

    this.sendInvite = function(data) {
        var url = baseUrl + "invite";
        return $http.post(url, data);
    }


}]);