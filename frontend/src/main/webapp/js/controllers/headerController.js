app.controller('headerController', ['$scope', '$rootScope', 'authService', function ($scope, $rootScope, authService) {

    var handleAuth = function() {
        if(!$scope.isAuthenticated) {
            $scope.isAuthenticated = authService.isAuthenticated();
        }
        else {
            $scope.isAuthenticated = 0;
        }
    }

    handleAuth();

    $scope.$on('authChanged', function () {
        handleAuth();
    });
}]);