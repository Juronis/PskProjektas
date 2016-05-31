app.controller('userInfoController', ['$scope', '$stateParams', 'userService', 'errorService', function ($scope, $stateParams, userService, errorService) {

    $scope.userId = $stateParams.userId;

    var loadUser = function() {

        userService.getUserDataById($stateParams.userId).then(function (response) {
            $scope.user = response.data;
            $scope.userId = $scope.user.id;
        }).catch(function (response) {
            //TODO: error handling
        });
    }

    loadUser();

    userService.getUserDataByAuth().then(function(response) {
        $scope.isCandidate = userService.isCandidate(response.data.role);
        $scope.isMember = userService.isMember(response.data.role);
        $scope.isAdmin = userService.isAdmin(response.data.role);
    });

}]);
