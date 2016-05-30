app.controller('loginController', ['$scope', '$state', 'authService', function ($scope, $state, authService) {

    $scope.login = function() {
        if ($scope.loginForm.$valid) {
            authService.login($scope.user).then(function (response) {
                authService.setAuthData(response.data);
                $state.go('main');
            }).catch(function (response) {
                //TODO: error Handling
            })
        }
    }

    $scope.loginWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            $scope.messageLog = response.data;
        })
    }

}]);
