app.controller('editProfileController', ['$scope', '$state', 'userService', 'authService', function ($scope, $state, userService, authService) {
    var currentEmail = null;

    var setFields = function() {
        userService.getUserDataByAuth().then(function(response) {
            $scope.user = response.data;
            currentEmail = $scope.user.email;
            $scope.messageLog = $scope.user;

            var tempString = $scope.user.name.split(" ");
            $scope.name = tempString.slice(0, -1).join(" ");
            $scope.surname = tempString[tempString.length-1];
        });
    }

    setFields();

    var updateProfile = false;

    $scope.delete = function() {
        var answer = prompt("Įveskite slaptažodį", false);
        answer = { "password" : answer };
        if(answer) {
            userService.checkPassword(answer).then(function() {
                userService.delete().then(function (response) {
                    authService.logout();
                }).catch(function (response) {
                    //TODO: error handling
                });
            }).catch(function (response) {
                //TODO: error handling
            });
        }
    }

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
                    //TODO: error handling
                })
            }
        }
    }

}]);
