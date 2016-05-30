app.controller('usersListController', ['$scope', 'userService', function ($scope, userService) {

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

    /* $http.get("/frontend/services/resource/persons/4")
     .then(function(response){ $scope.testas = response.data; }) */

}]);
