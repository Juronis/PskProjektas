app.controller('mainController', ['$scope', '$http', function ($scope, $http) {

    $http.get("/frontend/services/resources/members/total/candidates")
        .then(function(response){
            $scope.testas = response.data;
        });



}]);