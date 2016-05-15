app.controller('registerController', ['$scope', '$state', 'authService', function ($scope, $state, authService) {
    $scope.signupType = "pw";
    
    $scope.register = function () {
        if ($scope.signupType === "pw") {
            registerWithUsername();
        } else {
            registerWithFB();
        }
    };
    
    var registerWithUsername = function () {
        authService.register($scope.newUser).then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            //TODO error handling
            alert("Couldn't register" + response.data);
        })
    };

    var registerWithFB = function () {
        authService.authenticateFB().then(function (response) {
            authService.setAuthData(response.data);
            $state.go('main');
        }).catch(function (response) {
            alert("Couldn't register" + response.data);
        })
    }
}]);
