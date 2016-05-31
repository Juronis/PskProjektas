app.controller('menuController', ['$scope', '$rootScope', 'authService', 'userService', 'errorService', function ($scope, $rootScope, authService, userService, errorService) {

    var handleAuth = function() {
        if(!$scope.isAuthenticated) {
            $scope.isAuthenticated = authService.isAuthenticated();

            userService.getUserDataByAuth().then(function (response) {
                $scope.amount = response.data.credits;
                $scope.isCandidate = userService.isCandidate(response.data.role);
                $scope.isMember = userService.isMember(response.data.role);
                $scope.isAdmin = userService.isAdmin(response.data.role);
            });
        }
        else {
            $scope.isAuthenticated = false;
        }
    }

    var handleAuth2 = function() {
        $scope.isAuthenticated = authService.isAuthenticated();
        if ($scope.isAuthenticated) {
            userService.getUserDataByAuth().then(function (response) {
                $scope.amount = response.data.credits;
                $scope.isCandidate = userService.isCandidate(response.data.role);
                $scope.isMember = userService.isMember(response.data.role);
                $scope.isAdmin = userService.isAdmin(response.data.role);
            });
        }
    }

    handleAuth2();

    $scope.$on('authChanged', function () {
        handleAuth();
    });


    $scope.logout = function () {
        if (authService.isAuthenticated()) {
            authService.logout();
        }
    }
}]);