app.controller('headerController', ['$scope', '$rootScope', 'authService', 'userService', 'aprovalService', function ($scope, $rootScope, authService, userService, aprovalService) {

    var handleAuth = function() {
        if(!$scope.isAuthenticated) {
            $scope.isAuthenticated = authService.isAuthenticated();

            userService.getUserDataByAuth().then(function (response) {
                $scope.amount = response.data.credits;
                $scope.isCandidate = userService.isCandidate(response.data.role);
                $scope.isMember = userService.isMember(response.data.role);
                $scope.isAdmin = userService.isAdmin(response.data.role);
            });
        }
        else {
            $scope.isAuthenticated = false;
        }
    }

    var handleAuth2 = function() {
        $scope.isAuthenticated = authService.isAuthenticated();
        if ($scope.isAuthenticated) {
            userService.getUserDataByAuth().then(function (response) {
                $scope.amount = response.data.creditAmount;
                $scope.membership = response.data.membership;
                $scope.isCandidate = userService.isCandidate(response.data.role);
                $scope.isMember = userService.isMember(response.data.role);
                $scope.isAdmin = userService.isAdmin(response.data.role);
            });
        }
    }

    handleAuth2();

    $scope.$on('authChanged', function () {
        handleAuth();
    });

    $scope.$on('transferCompleted', function () {
       handleAuth2();
    });


    function isValidEmailAddress(emailAddress) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test(emailAddress);
    }

    $scope.sendRequest = function () {
        if(isValidEmailAddress($scope.email)) {
            var data = { "email" : $scope.email };
            aprovalService.sendInvite(data).then(function(response) {

            }).catch(function(response) {
                //TODO: error handling
            });
        }
    }

    $scope.askRecommendation = function () {
        if(isValidEmailAddress($scope.email)) {
            var data = { "email" : $scope.email };
            aprovalService.askRecommendation(data).then(function(response) {
                $scope.successMessage = "Jūs sėkmingai paprašėte <b>"+data.email+"</b> rekomendacijos";
            }).catch(function(response) {
                //TODO: error handling
            });
        }
    }

    $scope.payMembership = function () {
        userService.payMembership().then(function (response) {
            $scope.successMessage = "Apmokėjote metinį nario mokestį";
            $rootScope.$broadcast('transferCompleted');
        }).catch(function (response) {
           //TODO: error handling
        });
    }

}]);