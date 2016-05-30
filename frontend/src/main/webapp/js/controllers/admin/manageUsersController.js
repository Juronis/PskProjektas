app.controller('manageUsersController', ['$scope', 'userService', function ($scope, userService) {

    var loadLists = function() {
        userService.getAllCandidates().then(function (response) {
            $scope.candidates = response.data;
        }).catch(function (response) {
            //TODO: error handling
        });

        userService.getAllUsers().then(function (response) {
            $scope.users = response.data;
        }).catch(function (response) {
            //TODO: error handling
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
                //TODO: error handling
            });
        }
    }

}]);
