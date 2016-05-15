var app = angular.module('webApp', ['ui.router', 'satellizer']);

app.config(['$locationProvider', '$stateProvider', '$urlRouterProvider', '$httpProvider', '$authProvider',
    function ($locationProvider, $stateProvider, $urlRouterProvider, $httpProvider, $authProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('register', {
                url: "/",
                controller: 'registerController',
                templateUrl: 'partials/registerView.html'
            })
            .state('login', {
                url: "/login",
                controller: 'loginController',
                templateUrl: 'partials/loginView.html'
            });

        $locationProvider.html5Mode({
            enabled: true
        });
/*
        cfpLoadingBarProvider.includeSpinner = false;
        $authProvider.loginUrl = 'rest/login';
        $authProvider.signupUrl = 'rest/register';
        $authProvider.baseUrl = $('base').attr('href');
        $authProvider.tokenPrefix='labanoro_draugai';
        $authProvider.facebook({
            clientId: '1538319626473322',
            url: 'rest/register/facebook'
        });
*/
    }]);