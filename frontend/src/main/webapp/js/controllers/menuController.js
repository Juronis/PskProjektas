app.controller('menuController', ['$scope', '$rootScope', 'authService', 'userService', function ($scope, $rootScope, authService, userService) {

    var handleAuth = function() {
        if(!$scope.isAuthenticated) {
            $scope.isAuthenticated = authService.isAuthenticated();
        }
        else {
            $scope.isAuthenticated = 0;
        }

        userService.getCandidatesTotal().then(function (response) {
            $scope.candidatesTotalNumber = response.data.total;
        })
    }

    handleAuth();

    $scope.$on('authChanged', function () {
        handleAuth();
    });


    $scope.logout = function () {
        if (authService.isAuthenticated()) {
            authService.logout();
        }
    }
}]);