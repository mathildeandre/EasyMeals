/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restUserService", function ($http, $q, $log, $localStorage, restGlobalService) {

    function connexion(pseudo, password, callback){
        $http.post('/rest/connexionUser/'+pseudo+"/"+password).success(function (response) {
            // login successful if there's a token in the response
            if (response.isValidToken) {
                $log.debug("[restUserService] - connexion() - Token VALID :) : "+response.token);
                $log.debug("[restUserService] - connexion() - Token VALID :) : "+response.user.pseudo);
                $log.debug("[restUserService] - connexion() - Token VALID :) : "+response.user.isAdmin);
                $log.debug("[restUserService] - connexion() - Token VALID :) : "+response.user.id);

                /*
                $http.post('/rest/testVerifyToken/2117', response).success(function(response){
                    $log.debug("BOOM ca a MARCHEEEEE :):) :) :) :) :) :) :) :) - (response.name :"+response.name);
                })
                */


                // store username and token in local storage to keep user logged in between page refreshes
                $localStorage.userConnected = response.user; //response.user = { id: 21117, pseudo: 'mathou', email: '', isAdmin: 0, colorThemeRecipe: 'grey'}
                $localStorage.token = response.token ;

                // add jwt token to auth header for all requests made by the $http service
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;


                //return
                restGlobalService.initGlobalLoadData_afterConnexion(response.user.id).then(function(){
                   $log.warn("[restUserService] - connexion() xx YOUPI : initialized all data for idUser "+response.idUser)
                });


                // execute callback with true to indicate successful login
                callback(true);
            } else {
                // execute callback with false to indicate failed login
                callback(false, response.msg);
            }
        });
    }
    function registration(pseudo, password, callback){
        $http.post('/rest/registrationUser/'+pseudo+"/"+password).success(function (response) {
            // login successful if there's a token in the response
            if (response.isValidToken) {
                $log.debug("[restUserService] - registration() - Token VALID :) : "+response.token);

                /*
                $http.post('/rest/testVerifyToken/2117', response).success(function(response){
                    $log.debug("BOOM ca a MARCHEEEEE :):) :) :) :) :) :) :) :) - (response.name :"+response.name);
                })
                */

                // store username and token in local storage to keep user logged in between page refreshes
                $localStorage.currentUser = { pseudo: response.pseudo, idUser: response.idUser, token: response.token };

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







    function login(username, password, callback) {

        $log.info("ON VA TEST LE TOKEN ")
        $http.post('/rest/testAuthenticate/2117')
            .success(function (response) {
                // login successful if there's a token in the response
                $log.warn("TOKENNNNNNNNNNNNNNNNNNNNNNN ?? -- ")
                $log.info("response.token : "+response.token);
                if (response.token) {

                    $http.post('/rest/testVerifyToken/2117', response).success(function(response){
                        $log.debug("BOOM ca a MARCHEEEEE :):) :) :) :) :) :) :) :) - (response.name :"+response.name);
                    })


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



        /*
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
            */
    }

    function logout() {
        // remove user from local storage and clear http auth header
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    }

    return {
        login: login,
        logout: logout,
        connexion: connexion,
        registration: registration
    };
});
