app.service('aprovalService', ['$http', function ($http) {

    var baseUrl = "/frontend/services/resources/approvals/";

    this.askRecommendation = function(data) {
        var url = baseUrl + "send";
        return $http.post(url, data);
    }

    this.aprove = function(email) {
        var url = baseUrl + "approver/approve/"+email;
        return $http.post(url);
    }

    this.getCandidatesList = function() {
        var url = baseUrl + "approver/candidates";
        return $http.get(url);
    }

}]);