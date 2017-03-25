//routes index.js
var router = require('express').Router();

router.get('/balance/:acc', function(req, res, next) {
	res.send({ status: 'success', msg: 'Account balance for '+ req.params.acc, data: "1000.00" });
});

router.get('/otp/:phone',function(req, res, next) {
	res.send({ status: "success", msg:"Otp has been sent to " + req.params.phone,  data: ''});
});

router.get('/validateotp/:otp', function(req, res, next) {
	res.send({ status: 'success', msg: "OTP Validation successful" });
})

module.exports = router;