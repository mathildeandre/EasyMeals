/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.factory("restFactory", function ($http, $q, $log) {

        return {
            txtPost: txtPost,
            insertObjTest:insertObjTest,
            getCourses: getCourses,
            getTest: getTest,
            getProjects: getProjects,
            getProjectById: getProjectById,
            updateProject: updateProject,
            createProject: createProject,
            deleteProject: deleteProject
        };


/*
    function createCourse(course) {
        $log.debug("[restBack-service.js -- getCourses() method:POST] ENVOI DATA TO SERVER----------------------------------------- -----");
        return $http({
            method: 'POST',
            url: '/rest/course',
            data: JSON.stringify(course)
        })
            .then(function (response) {
                $log.debug("[restBack-service.js -- getCourses() method:POST - on recoit response du serber] RETOUR RESPONSE FROM SERVER ----------------------------------------- " + response.data)
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response);
            })
    };
*/
    function insertObjTest() {
        $log.debug("[restBack-service.js -- getCourses() V3 method:POST] ENVOI DATA TO SERVER----------------------------------------- -----");
        return $http({
            method: 'POST',
            url: '/rest/testObj',
            data: {"name" : "myNameISCOUCOU"}
        })
            .then(function (response) {
                $log.debug("[restBack-service.js -- getCourses() method:POST - on recoit response du serber V3] RETOUR RESPONSE FROM SERVER ----------------------------------------- " + response.data)

                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response);
            })
    };


    function txtPost(txt) {
        $log.debug("[restBack-service.js -- getTxt() method:POST 25-04] ENVOI DATA txt TO SERVER----------------------------------------- -----");
        return $http({
            method: 'POST',
            url: '/rest/txt',
            data: txt
        })
            .then(function (response) {
                $log.debug("[restBack-service.js -- getTxt() method:POST - on recoit response du server 25-04] RETOUR RESPONSE FROM SERVER " + response.data)
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response);
            })
    };




    function getTest() {
        return $http({
            method: 'GET',
            url: '/rest/word'
        }).then(function (response) {
                if (response.status == 200) {
                    $log.debug("HELLO 3 ----------------------------------------- still here ::: " + response.data)
                    return response.data; //'{"name":"helloo74"}';
                }
                $log.debug("HELLO xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx ")
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };



    function getCourses() {
        return $http({
            method: 'GET',
            url: '/rest/recipes/course'
        })
            .then(function (response) {
                $log.debug("[restBack-service.js -- getCourses() method:GET] ARRIVEE DES RESPONSE FROM SERVER-----------------------------------------" + response.data)
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };

        function getProjects() {
            return $http({
                method: 'GET',
                url: '/api/projects'
            })
                .then(function (response) {
                    if (response.status == 200) {
                        return response.data;
                    }
                    return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
                })
        };

        function getProjectById(idProject) {
            return $http({
                method: 'GET',
                url: '/api/projects/' + idProject
            })
                .then(function (response) {
                    if (response.status == 200) {
                        return response.data;
                    }
                    return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
                })
        };

        function updateProject(project) {
            return $http({
                method: 'PUT',
                url: '/api/projects/' + project._id,
                data: project
            })
                .then(function (response) {
                    if (response.status == 200) {
                        return response.data;
                    }
                    return $q.reject(response);
                })
        };

        function createProject(project) {
            return $http({
                method: 'POST',
                url: '/api/projects/',
                data: project
            })
                .then(function (response) {
                    if (response.status == 200) {
                        return response.data;
                    }
                    return $q.reject(response);
                })
        };

        function deleteProject(project) {
            return $http({
                method: 'DELETE',
                url: '/api/projects/' + project._id,
            })
                .then(function (response) {
                    if (response.status == 200) {
                        return response.data;
                    }
                    return $q.reject(response);
                })
        }



    });
