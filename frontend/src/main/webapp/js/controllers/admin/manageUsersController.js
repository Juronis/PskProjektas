app.controller('manageUsersController', ['$scope', 'userService', 'errorService', function ($scope, userService, errorService) {

    var loadLists = function() {
        userService.getAllCandidates().then(function (response) {
            $scope.candidates = response.data;
        });

        userService.getAllUsers().then(function (response) {
            $scope.users = response.data;
        });
    }

    loadLists();

    $scope.delete = function(id) {
        var answer = prompt("Įveskite slaptažodį", "");
        if(answer) {
            var data = { "password" : answer };
            userService.deleteUser(id, data).then(function (response) {
                //update list
                $scope.users = $scope.users.filter(function (el) {
                   return el.id !== id;
                });
                $scope.successMessage = "Narys sėkmingai ištrintas";
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'manageUsersController');
            });
        }
    }

}]);
