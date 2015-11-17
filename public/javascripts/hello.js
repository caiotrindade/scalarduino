var app = angular.module('app', [])
app.controller("ArduinoCtrl", function($scope, $http) {
  $scope.arduino = {};

  $scope.changeLed = function changeLed() {
    if($scope.arduino.ledIsOn)
      $http.post('/leds/on')
    else
      $http.post('/leds/off')
  };
});