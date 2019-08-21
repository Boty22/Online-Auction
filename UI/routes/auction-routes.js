const router = require('express').Router();
const scheduleServices = require('../services/scheduleServices');

const authCheck = (req, res, next) => {
    if(!req.user){
        res.redirect('/auth/login');
    } else {
        next();
    }
};

router.get('/', authCheck, (req, res) => {
    user = req.user;
    res.render('auctionSearch', {});
});

router.post('/search', authCheck, (req, res) =>{
    startDate = req.body.auctionDate;
    endDate = req.body.auctionEndDate;

    var s = {
      "startDate": startDate,
      "endDate" : endDate
    };

    items = scheduleServices.getSchedulesBetweenDates(s,function(error,currentItems){
      res.render('auctionResults', { items: currentItems });
    });
});

module.exports = router;
