app.controller('addCreditsController', ['$scope', 'userService', function ($scope, userService) {

    $scope.successMessage = "";
    $scope.messageLog = "";

    function isValidEmailAddress(emailAddress) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test(emailAddress);
    }

    $scope.check = function() {
        if(isValidEmailAddress($scope.email)) {
            var data = {"email" : $scope.email };
            userService.getUserByEmail(data).then(function(response) {
                $('#email').addClass('has-success');
            }).catch(function (response) {
                $('#email').addClass('has-error');
            });
        }
    }

    $scope.add = function() {
        if($scope.creditsForm.$valid) {
            if (isValidEmailAddress($scope.email)) {
                var data =
                    {
                        "email" : $scope.email,
                        "amount" : $scope.amount.toString()
                    };
                userService.addCredits(data).then(function(response) {
                    $scope.successMessage = "Jūs sėkmingai nariui "+$scope.email+" pridėjote "+$scope.credits+" kreditų";
                }).catch(function(response) {
                    //TODO: error handling
                });
            }
        }
    }

}]);