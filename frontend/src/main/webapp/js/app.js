var app = angular.module('webApp', ['ui.router', 'satellizer']);

app.config(['$locationProvider', '$stateProvider', '$urlRouterProvider', '$httpProvider', '$authProvider',
    function ($locationProvider, $stateProvider, $urlRouterProvider, $httpProvider, $authProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('main', {
                url: "/",
                controller: 'mainController',
                templateUrl: 'partials/mainView.html'
            })
            .state('login', {
                url: "/login",
                controller: 'loginController',
                templateUrl: 'partials/loginView.html'
            })
            .state('register', {
                url: "/register",
                controller: 'registerController',
                templateUrl: 'partials/registerView.html'
            })
            .state('usersList', {
                url: "/usersList",
                controller: 'usersListController',
                templateUrl: 'partials/usersListView.html'
            })
            .state('summerHousesList', {
                url: "/summerHousesList",
                controller: 'summerHousesListController',
                templateUrl: 'partials/summerHousesListView.html'
            });

        $locationProvider.html5Mode({
            enabled: true
        });

        $authProvider.loginUrl = '/frontend/services/resources/auth/login';
        $authProvider.signupUrl = '/frontend/services/resources/auth/signup';
        //$authProvider.baseUrl = $('base').attr('href');
        //$authProvider.tokenPrefix='labanoro_draugai';
        $authProvider.facebook({
            clientId: '488291451363760',
            url: '/frontend/services/resources/auth/facebook'
        });

    }]);