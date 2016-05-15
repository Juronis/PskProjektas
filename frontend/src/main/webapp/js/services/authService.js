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
        var userAuthData = {
            email: authData.email,
            name: authData.name,
            surname: authData.surname,
            role: authData.role
        };

        localStorage.setItem('userAuthData', JSON.stringify(userAuthData));
        $auth.setToken(authData.token);
        $rootScope.$broadcast('authChanged');
    };
    
    this.getAuthData = function () {
        return JSON.parse(localStorage.getItem('userAuthData'));
    };

    this.isAuthenticated = function () {
        return $auth.getToken() && this.getAuthData();
    };

    this.isMember = function () {
        return this.getAuthData().role === 'MEMBER';
    };

    this.isCandidate = function () {
        return this.getAuthData().role === 'CANDIDATE';
    };

    this.isAdmin = function () {
        return this.getAuthData().role === 'ADMIN';
    };

    this.logout = function () {
        $auth.logout();
        localStorage.removeItem('userAuthData');
        $rootScope.$broadcast('authChanged');
    }

}]);
