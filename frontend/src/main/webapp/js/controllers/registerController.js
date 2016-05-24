app.controller('registerController', ['$scope', '$state', 'authService', function ($scope, $state, authService) {
    
    $scope.register = function () {
        if ($scope.registerForm.$valid) {
            if($scope.user.password === $scope.user.password2) {
                registerWithForm();
            } else {
                $scope.messageLog = "Nesutampa slaptažodiai";
            }
        }
    };
    
    var registerWithForm = function () {
        authService.register($scope.user).then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            if(response.status == 404) {
                $scope.messageLog = "Puslapis nerastas";
            }
        })
    };

    $scope.registerWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            if(response.status == 404) {
                $scope.messageLog = "Puslapis nerastas";
            }
        })
    }
}]);
