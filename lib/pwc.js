//defining pwc functions
var pwc = require('PayWithCapture');
var cfg = require('../pwc_config');
var async = require('async');
var pwc_env = "staging";

var client = new pwc(cfg.client_id, cfg.client_secret, pwc_env);

var auth = function () {
	async.waterfall(
		function(callback) {
			var Authentication = client.getAuthentication();
			Authentication.authenticate()
			.then(function(resp) {
				return callback(null, resp.access_token)
			})
			.catch(function(err) {
				console.log(err);
				return callback(true, "something went wrong");
			});
		},
		function(err, data) {
			if (err) {
				return null;
			}
			return data;
		});
}


module.exports = {
	otp: function(phone) {
		console.log('got here');
		var otpClient = client.getOtp();

		async.waterfall(
			[async.apply(
				function(phone, callback) {
					otpClient.sendSmsOtp(phone)
						.then(function(resp) {
							console.log(resp)
							return callback(null, resp);
						})
						.catch(function(err) {
							console.log(err);
							return callback(true, true);
						});
				}, 
			phone)],
			function(err, data) {
				if (err) {
					return null;
				}
				return data;
			}
		)
	
	}

	/*

	otp_validation: function(code) {
		otpValClient = client.getOtp();
		otpValClient.send
	}
	*/


}

