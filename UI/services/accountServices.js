const request = require('request');

module.exports = {
  getDetailsByGoogleID: function (googleID,callBack){
    request('https://localhost:8086/auctionwebapp/accounts/google/'+googleID, { json: true, rejectUnauthorized:false, requestCert:true }, (err, res, body) => {
      if(body){
        var user = {
          username: body.userName,
          googleId: body.googleId,
          id: body.id
        }
        callBack(null,user);
      }
      else{
        callBack(err,null);
      }
    })
  },
  getDetailsByAccountID: function (accountID,callBack){
    request('https://localhost:8086/auctionwebapp/accounts/'+accountID, { json: true, rejectUnauthorized:false, requestCert:true }, (err, res, body) => {
      if(body){
        var user = {
          username: body.userName,
          googleId: body.googleId,
          firstName: body.firstName,
          lastName: body.lastName,
          streetAddress: body.streetAddress,
          city: body.city,
          state: body.state,
          country: body.country,
          zipcode: body.zipcode,
          id: body.id
        }
        callBack(null,user);
      }
      else{
        callBack(err,null);
      }
    });
  },
  createAccount: function(user,callBack){
      var headersOpt = {
      "content-type": "application/json"
      };
      request(
            {
            method:'post',
            url:'https://localhost:8086/auctionwebapp/accounts/create',
            body: user,
            headers: headersOpt,
            rejectUnauthorized: false,
            requestCert: true,
            json: true,
        }, function (error, response, body) {
              var newUser = {
                id: body.id,
                googleId: body.googleId,
                userName: body.userName
              }
              callBack(null,newUser);
        });
  },


  updateAccount: function(updatedProfile,callBack){
    var headersOpt = {
    "content-type": "application/json"
    };
    request(
          {
          method:'post',
          url:'https://localhost:8086/auctionwebapp/accounts/create',
          body: updatedProfile,
          headers: headersOpt,
          rejectUnauthorized: false,
          requestCert: true,
          json: true,
      }, function (error, response, body) {
            var newUser = {
              id: body.id,
              googleId: body.googleId,
              userName: body.userName
            }
            callBack(null,newUser);
      });
}

};

//getDetailsByGoogleID('111902420642001097513',callBack);
//getDetailsByAccountID(1,callBack);
