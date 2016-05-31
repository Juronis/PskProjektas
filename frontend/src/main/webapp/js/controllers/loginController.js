app.controller('loginController', ['$scope', '$state', 'authService', 'errorService', function ($scope, $state, authService, errorService) {

    $scope.login = function() {
        if ($scope.loginForm.$valid) {
            authService.login($scope.user).then(function (response) {
                authService.setAuthData(response.data);
                $state.go('main');
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'loginController');
            })
        }
    }

    $scope.loginWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'loginController');
        })
    }

}]);
