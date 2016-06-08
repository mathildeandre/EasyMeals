/**
 * Created by fabien on 13/03/2016.
 */
var myModule = angular.module('controllers');



myModule.controller('CarouselDemoCtrl', function ($scope) {
    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    var currIndex = 0;
    $scope.slidesMoment = [{
        image: '/images/course/crepes.jpg',//'http://lorempixel.com/607/300',
        text: 'Crepes',
        id: currIndex++
    },{
        image: '/images/course/escalopeViennoise.jpg',//'http://lorempixel.com/608/300',
        text: 'Escalope Viennoise',
        id: currIndex++
    },{
        image: '/images/course/aubergineFour.jpg',//'http://lorempixel.com/608/300',
        text: 'Aubergines au four',
        id: currIndex++
    },{
        image: '/images/course/burritos.jpg',//'http://lorempixel.com/607/300',
        text: 'burritos',
        id: currIndex++
    },{
        image: '/images/course/lasagne.jpg',//'http://lorempixel.com/608/300',
        text: 'lasagne',
        id: currIndex++
    }];

    $scope.addSlide = function() {
        var newWidth = 600 + $scope.slides.length + 1;
        $scope.slides.push({
            image: 'http://lorempixel.com/' + newWidth + '/300',
            text: ['Nice image','Awesome photograph','That is so cool','I love that'][$scope.slides.length % 4],
            id: currIndex++
        });
    };

    /*
    for (var i = 0; i < 4; i++) {
        $scope.addSlide();
    }
    $scope.randomize = function() {
        var indexes = generateIndexesArray();
        assignNewIndexesToSlides(indexes);
    };


    // Randomize logic below

    function assignNewIndexesToSlides(indexes) {
        for (var i = 0, l = $scope.slides.length; i < l; i++) {
            $scope.slides[i].id = indexes.pop();
        }
    }

    function generateIndexesArray() {
        var indexes = [];
        for (var i = 0; i < currIndex; ++i) {
            indexes[i] = i;
        }
        return shuffle(indexes);
    }

    // http://stackoverflow.com/questions/962802#962890
    function shuffle(array) {
        var tmp, current, top = array.length;

        if (top) {
            while (--top) {
                current = Math.floor(Math.random() * (top + 1));
                tmp = array[current];
                array[current] = array[top];
                array[top] = tmp;
            }
        }

        return array;
    }
     */
});