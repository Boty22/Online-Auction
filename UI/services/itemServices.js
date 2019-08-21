const request = require('request');

module.exports = {
  getAllItemsForAccount: function(accountID, callBack){
    request('https://localhost:8086/auctionwebapp/accounts/'+accountID, { json: true, rejectUnauthorized: false, requestCert: true }, (err, res, body) => {
      if(body){
        var items = body.items;
        callBack(null,items);
      }
      else{
        callBack(err,null);
      }
    });
  },
  getItemById: function(itemId, callBack){
    request('https://localhost:8086/auctionwebapp/items/'+itemId, { json: true, rejectUnauthorized: false, requestCert: true }, (err, res, body) => {
      if(body){
        var item = body;
        callBack(null,item);
      }
      else{
        callBack(err,null);
      }
    });
  },
  createItem: function(item,userId,callBack){
      var headersOpt = {
      "content-type": "application/json"
      };
      request(
            {
            method:'post',
            url:'https://localhost:8086/auctionwebapp/items/create/'+userId,
            body: item,
            headers: headersOpt,
            rejectUnauthorized: false,
            requestCert: true,
            json: true,
        }, function (error, response, body) {
              callBack(null,body);
        });
  },
  postBid: function(bid,callBack){
      var headersOpt = {
      "content-type": "application/json"
      };
      request(
            {
            method:'post',
            url:'https://localhost:8086/auctionwebapp/itemBid/create/',
            body: bid,
            headers: headersOpt,
            rejectUnauthorized: false,
            requestCert: true,
            json: true,
        }, function (error, response, body) {
              callBack(null,body);
        });
  },
  getBidsForItem: function(itemId, callBack){
    request('https://localhost:8086/auctionwebapp/itemBid/item/'+itemId, { json: true, rejectUnauthorized: false, requestCert: true }, (err, res, body) => {
      if(body){
        var itemBid = body;
        callBack(null,itemBid);
      }
      else{
        callBack(err,null);
      }
    });
  },
}
