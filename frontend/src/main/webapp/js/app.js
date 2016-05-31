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
                templateUrl: 'partials/loginView.html',
            })
            .state('register', {
                url: "/register",
                controller: 'registerController',
                templateUrl: 'partials/registerView.html',
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
            })
            .state('editProfile', {
                url: "/editProfile",
                controller: 'editProfileController',
                templateUrl: 'partials/editProfileView.html'
            })
            .state('addSummerHouse', {
                url: "/admin/addSummerHouse",
                controller: 'addSummerHouseController',
                templateUrl: 'partials/admin/addSummerHouseView.html'
            })
            .state('addCredits', {
                url: "/admin/addCredits",
                controller: 'addCreditsController',
                templateUrl: 'partials/admin/addCreditsView.html'
            })
            .state('usersSettings', {
                url: "/admin/usersSettings",
                controller: 'usersSettingsController',
                templateUrl: 'partials/admin/usersSettingsView.html'
            })
            .state('registrationForm', {
                url: "/admin/registrationForm",
                controller: 'registrationFormController',
                templateUrl: 'partials/admin/registrationFormView.html'
            })
            .state('userInfo', {
                url: "/user/:userId",
                controller: 'userInfoController',
                templateUrl: 'partials/userInfoView.html'
            })
            .state('editUser', {
                url: "/admin/editUser/:userId",
                controller: 'editUserController',
                templateUrl: 'partials/admin/editUserView.html'
            })
            .state('manageUsers', {
                url: "/admin/manageUsers",
                controller: 'manageUsersController',
                templateUrl: 'partials/admin/manageUsersView.html'
            })
            .state('summerHouse', {
                url: "/summerHouse/:id",
                controller: 'summerHouseController',
                templateUrl: 'partials/summerHouseView.html'
            })
            .state('manageSummerHouses', {
                url: "/admin/manageSummerHouses",
                controller: 'manageSummerHousesController',
                templateUrl: 'partials/admin/manageSummerHousesView.html'
            })
            .state('editSummerHouse', {
                url: "/admin/editSummerHouse/:id",
                controller: 'editSummerHouseController',
                templateUrl: 'partials/admin/editSummerHouseView.html'
            });

        $locationProvider.html5Mode({
            enabled: true
        });

        $authProvider.loginUrl = '/frontend/services/resources/auth/login';
        $authProvider.signupUrl = '/frontend/services/resources/auth/signup';
        $authProvider.facebook({
            clientId: '488291451363760',
            url: '/frontend/services/resources/auth/facebook'
        });

    }]);