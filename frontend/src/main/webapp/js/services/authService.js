app.service('authService', ['$http', '$rootScope', '$auth', function ($http, $rootScope, $auth) {

    this.login = function(loginInfo) {
        return $auth.login(loginInfo);
    };
    
    this.register = function (userInfo) {
        return $auth.signup(userInfo);
    };

    this.authenticateFB = function () {
        return $auth.authenticate('facebook');
    };
    
    this.setAuthData = function (authData) {
        $auth.setToken(authData.token);
        $rootScope.$broadcast('authChanged');
    };

    this.isAuthenticated = function () {
        return $auth.getToken();
    };

    this.logout = function () {
        $auth.logout();
        $rootScope.$broadcast('authChanged');
    }

}]);
