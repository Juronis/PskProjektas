app.controller('loginController', ['$scope', function ($scope) {

    $scope.login = function() {
        if ($scope.loginForm.$valid) {
            authService.login($scope.user).then(function (response) {
                authService.setAuthData(response.data);
                $state.go('main');
            }).catch(function (response) {
                //TODO error handling
                errorList = response.data;
            })
        }
    }

    $scope.loginWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            //TODO
            errorList = response.data;
        })
    }

}]);
