app.controller('mainController', ['$scope', '$http', function ($scope, $http) {

    $http.get("/frontend/services/resources/members/byemail")
        .then(function(response){
            $scope.testas = response.data;
        });



}]);