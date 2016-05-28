app.controller('mainController', ['$scope', '$http', function ($scope, $http) {

    $http.post("/frontend/services/resources/members/byauth", $scope.authHeader)
        .then(function(response){
            $scope.testas = response.data;
        });



}]);