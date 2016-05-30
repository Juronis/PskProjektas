app.controller('mainController', ['$scope', '$http', function ($scope, $http) {

    $http.post("/frontend/services/resources/parameters/update")
        .then(function(response){
            $scope.testas = response.data;
        });



}]);