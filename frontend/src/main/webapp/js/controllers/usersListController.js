app.controller('usersListController', ['$scope', 'userService', 'aprovalService', function ($scope, userService, aprovalService) {

var loadLists = function() {
    aprovalService.getCandidatesList().then(function (response) {
        $scope.candidates = response.data;
    }).catch(function (response) {
        //TODO: error handling
    });

    userService.getAllMembers().then(function (response) {
        $scope.users = response.data;
    }).catch(function (response) {
        //TODO: error handling
    });
}

loadLists();

    $scope.aproveCandidate = function(email) {
        var data = {
            "email" : email
        };
        aprovalService.aprove(data).then(function(response) {
            $scope.successMessage = "Jūs patvirtinote kandidatūrą";
        }).catch(function(response){
            //TODO: error handling
        });
    }

}]);
