const router = require('express').Router();
const accountServices = require('../services/accountServices');

const authCheck = (req, res, next) => {
    if(!req.user){
        res.redirect('/auth/login');
    } else {
        next();
    }
};

router.get('/', authCheck, (req, res) => {
    userId = req.user.id;
    user = accountServices.getDetailsByAccountID(userId,function(error,currentUser){
      res.render('profile', { user: currentUser });
    });
});

router.get('/edit', authCheck, (req, res) => {
    console.log("printing from the router");
    userId = req.user.id;
    user = accountServices.getDetailsByAccountID(userId,function(error,currentUser){
      res.render('profile-update', { user: currentUser });
    });
});

router.post('/update-account', authCheck, (req, res) => {
    userId = req.user.id;
    var updatedProfile ={
        id: userId,
        //userName: ,
        firstName: req.body.firstName,
        lastName:req.body.lastName,
        //googleId:,
        streetAddress:req.body.streetAddress,
        city:req.body.city,
        state:req.body.state,
        country:req.body.country,
        zipcode:req.body.zipcode,
        //email: ,
    };
    accountServices.updateAccount(updatedProfile,function(){
        user = accountServices.getDetailsByAccountID(userId,function(error,currentUser){
            res.render('profile', { user: currentUser });
        });

    });


});



module.exports = router;
