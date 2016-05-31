app.controller('editUserController', ['$scope', '$state', '$stateParams', 'userService', 'authService', 'errorService', function ($scope, $state, $stateParams, userService, authService, errorService) {
    var currentEmail = null;

    var setFields = function() {
        userService.getUserDataById($stateParams.userId).then(function(response) {
            $scope.user = response.data;
            currentEmail = $scope.user.email;

            var tempString = $scope.user.name.split(" ");
            $scope.name = tempString.slice(0, -1).join(" ");
            $scope.surname = tempString[tempString.length-1];
        });
    }

    setFields();

    var updateProfile = false;

    $scope.edit = function() {
        if ($scope.editForm.$valid) {
            if($scope.password == null) {
                $scope.user.password = null;
                updateProfile = false;
            }
            if(!updateProfile && $scope.password == $scope.password2) {
                $scope.user.password = $scope.password;
                updateProfile = true;
            } else {
                updateProfile = false;
                $scope.messageLog = "Slaptažodžiai nesutampa";
            }
            if($scope.user.email != currentEmail) {
                updateProfile = true;
            }

            if(updateProfile) {
                userService.setUserDataById($stateParams.userId, $scope.user).then(function(response) {
                    $scope.successMessage = "Nario profilis sėkmingai atnaujintas";
                }).catch(function (response) {
                    $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'editUserController');
                })
            }
        }
    }

}]);
