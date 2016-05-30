app.controller('registerController', ['$scope', '$state', 'authService', 'adminService', function ($scope, $state, authService, adminService) {

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
            //if(response.status == 500) {
                $scope.messageLog = response.status;
            //}
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
    };
}]);
