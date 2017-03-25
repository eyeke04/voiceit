//index.js
var path = require('path');
var http = require('http');
var bodyParser = require('body-parser');
var pwc = require('PayWithCapture');
var pwc_lib = require('./lib/pwc');

var app = require('express')();
//var otp = pwc_lib.otp("+2348064006070");

var routes = require('./routes/index');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/api', routes);

var port = process.env.PORT || '3000';
app.set('port', port);

var server = http.createServer(app);
server.listen(port, function() {
	console.log('Api listen on port ' + port);
});