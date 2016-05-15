app.controller('loginController', ['$scope', function ($scope) {

    $scope.login = function() {
        if ($scope.loginForm.$valid) {
            console.log('sending request to server');
        }
    }

}]);
