app.controller('registerController', ['$scope', '$state', 'authService', function ($scope, $state, authService) {
    
    $scope.register = function () {
        if ($scope.registerForm.$valid) {
            if($scope.user.password === $scope.user.password2) {
                registerWithForm();
            } else {
                $scope.errorList = "Nesutampa slaptažodiai";
            }
        }
        else {
            console.log('testas');
        }
    };
    
    var registerWithForm = function () {
        authService.register($scope.user).then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            $scope.errorList = response.data;
        })
    };

    $scope.registerWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            $scope.errorList = response.data;
        })
    }
}]);
