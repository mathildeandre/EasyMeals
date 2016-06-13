/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restUserService", function ($http, $q, $log, $localStorage, restGlobalService) {


    function login(username, password, callback) {

        $log.debug("[[restUserService]]  .login. : username;pwd : "+username+";"+password);
        $http.post('/rest/authenticate', { pseudo: username, email: '', password: password })
            .success(function (response) {
                // login successful if there's a token in the response
                $log.warn("TOKENNNNNNNNNNNNNNNNNNNNNNN ?? -- ")
                $log.info("response.name : "+response.name);
                    if (response.token) {
                    // store username and token in local storage to keep user logged in between page refreshes
                    $localStorage.currentUser = { username: username, token: response.token };

                    // add jwt token to auth header for all requests made by the $http service
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;

                    // execute callback with true to indicate successful login
                    callback(true);
                } else {
                    // execute callback with false to indicate failed login
                    callback(false);
                }
            });
    }

    function logout() {
        // remove user from local storage and clear http auth header
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    }

    return {
        login: login,
        logout: logout
    };
});
