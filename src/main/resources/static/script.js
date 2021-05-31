var app = angular.module('rps', ['ngRoute']);
app.constant('config', {
    baseUrl: "http://localhost:8080/api/games"
});

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/game', {
            templateUrl: 'game.html',
            controller: 'GameController',
            option: ''
        }).
        when('/admin', {
            templateUrl: 'admin.html',
            controller: 'GameController',
            option: '?AllUsers=true'
        }).
        otherwise({
            redirectTo: '/'
        });
    }
]);

app.controller('GameController', function($scope, $route, $http, config) {
  const option = $route.current ? $route.current.option : ''

  $scope.loadData = function() {
    $http.get(config.baseUrl+'/all'+option).then(
        function (response) {
            $scope.games = response.data;
        }
    );
  }

  $scope.loadData();

  $scope.play = function() {
    $http.post(config.baseUrl+'/new').then($scope.loadData())
  };

  $scope.clear = function() {
    $http.delete(config.baseUrl+'/all'+option).then($scope.loadData())
  };
});

app.controller('NavController', function($scope, $location) {
  $scope.navList = [
    { url: 'game', title: 'Game', state: ''},
    { url: 'admin', title: 'Admin', state: ''}
  ];

  $scope.$on('$routeChangeSuccess', function(){
    angular.forEach($scope.navList, function(item) {
      item.state = $location.path().match(new RegExp(item.url)) ? 'active' : '';
    });
  });
});