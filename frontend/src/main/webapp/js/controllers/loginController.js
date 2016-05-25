app.controller('loginController', ['$scope', '$state', 'authService', function ($scope, $state, authService) {

    $scope.login = function() {
        if ($scope.loginForm.$valid) {
            authService.login($scope.user).then(function (response) {
                authService.setAuthData(response.data);
                $state.go('main');
            }).catch(function (response) {
                if(response.status == 500) {
                    console.log('testas');
                    $scope.messageLog = "Neteisingi prisijungimo duomenys";
                }
                if(response.status == 401) {
                    $scope.messageLog = "Unautorizer prisijungimo duomenys";
                }
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
