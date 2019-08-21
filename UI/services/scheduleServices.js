const request = require('request');

module.exports = {
  getSchedulesForDate: function(schedule,callBack){
      var headersOpt = {
      "content-type": "application/json"
      };
      request(
            {
            method:'post',
            url:'https://localhost:8086/auctionwebapp/bidding/getSchedules',
            body: schedule,
            headers: headersOpt,
            rejectUnauthorized: false,
            requestCert: true,
            json: true,
        }, function (error, response, body) {
              callBack(null,body);
        });
  },
  getSchedulesBetweenDates: function(schedule,callBack){
      var headersOpt = {
      "content-type": "application/json"
      };
      request(
            {
            method:'post',
            url:'https://localhost:8086/auctionwebapp/bidding/getSchedulesBetweenDates',
            body: schedule,
            headers: headersOpt,
            rejectUnauthorized: false,
            requestCert: true,
            json: true,
        }, function (error, response, body) {
              callBack(null,body);
        });
  }
};
