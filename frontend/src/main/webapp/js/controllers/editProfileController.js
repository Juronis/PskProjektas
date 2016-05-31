app.controller('editProfileController', ['$scope', '$state', 'userService', 'authService', 'errorService', function ($scope, $state, userService, authService, errorService) {
    var currentEmail = null;

    $('#birthdayField').datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1
    });

    var setFields = function() {
        userService.getUserDataByAuth().then(function(response) {
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
                userService.setUserData($scope.user).then(function(response) {
                    $state.go('main');
                }).catch(function (response) {
                    $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'editProfileController');
                })
            }
        }
    }

    $scope.delete = function() {
        var answer = prompt("Įveskite slaptažodį", "");
        if(answer) {
            var data = { "password" : answer };
            userService.delete(data).then(function (response) {
                authService.logout();
                $state.go("main");
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'editProfileController');
            });
        }
    }

}]);
