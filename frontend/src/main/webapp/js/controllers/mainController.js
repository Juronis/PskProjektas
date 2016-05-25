app.controller('mainController', ['$scope', '$http', function ($scope, $http) {

    $http.get("/frontend/services/resources/auth/4")
        .then(function(response){
            $scope.testas = response.data;
        });



}]);