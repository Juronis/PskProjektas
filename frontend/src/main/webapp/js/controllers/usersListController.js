app.controller('usersListController', ['$scope', 'userService', 'aprovalService', 'errorService', function ($scope, userService, aprovalService, errorService) {

var loadLists = function() {
    aprovalService.getCandidatesList().then(function (response) {
        $scope.candidates = response.data;
    });

    userService.getAllMembers().then(function (response) {
        $scope.users = response.data;
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
            $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'usersListController');
        });
    }

}]);
