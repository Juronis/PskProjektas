app.controller('headerController', ['$scope', '$rootScope', 'authService', 'userService', function ($scope, $rootScope, authService, userService) {

    var handleAuth = function() {
        if(!$scope.isAuthenticated) {
            $scope.isAuthenticated = authService.isAuthenticated();

            $scope.amount = userService.userCredits();

            $scope.isCandidate = userService.isCandidate();
            $scope.isMember = userService.isMember();
            $scope.isAdmin = userService.isAdmin();
        }
        else {
            $scope.isAuthenticated = 0;
        }
    }

    var handleAuth2 = function() {

        if ($scope.isAuthenticated) {
            $scope.amount = userService.userCredits();

            $scope.isCandidate = userService.isCandidate();
            $scope.isMember = userService.isMember();
            $scope.isAdmin = userService.isAdmin();

        }
    }

    handleAuth();
    handleAuth2();

    $scope.$on('authChanged', function () {
        handleAuth();
    });

    $scope.$on('transferChanged', function () {
       $scope.credits = userService.userCredits();
    });

    function isValidEmailAddress(emailAddress) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test(emailAddress);
    }

    $scope.sendRequest = function () {
        if(isValidEmailAddress($scope.email)) {
            var data = { "email" : $scope.email };
            userService.sendEmailRequest(data).then(function(response) {

            }).catch(function(response) {
                //TODO: error handling
            });
        }
    }

    $scope.sendRecommendation = function () {
        if(isValidEmailAddress($scope.email)) {
            var data = { "email" : $scope.email };
            userService.sendRecommendation(data).then(function(response) {

            }).catch(function(response) {
                //TODO: error handling
            });
        }
    }

}]);