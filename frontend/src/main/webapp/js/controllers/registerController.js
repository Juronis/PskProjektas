app.controller('registerController', ['$scope', '$state', 'authService', 'adminService', 'errorService', function ($scope, $state, authService, adminService, errorService) {

    adminService.getSettings('birthday_required').then(function (response) {
        $scope.birthdayRequired = response.data.pvalue;
    });
    $('#birthdayField').datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1
    });


    $scope.register = function () {
        if ($scope.registerForm.$valid) {
            if($scope.user.password === $scope.password2) {
                $scope.user.name = $scope.name + " " + $scope.surname;
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
            $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'registerController');
        })
    };

    $scope.registerWithFacebook = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'registerController');
        })
    };
}]);
