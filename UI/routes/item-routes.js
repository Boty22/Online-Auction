const router = require('express').Router();
const itemServices = require('../services/itemServices');

const authCheck = (req, res, next) => {
    if(!req.user){
        res.redirect('/auth/login');
    } else {
        next();
    }
};

router.get('/', authCheck, (req, res) => {
    user = req.user;
    items = itemServices.getAllItemsForAccount(user.id,function(error,currentItems){
      res.render('items', { items: currentItems });
    });
});

router.get('/item/:itemId', authCheck, (req, res) => {
    itemId = req.params.itemId;
    item = itemServices.getItemById(itemId,function(error,currentItem){
      itemBids = itemServices.getBidsForItem(itemId, function(error, currItemBids){
        res.render('item', { item: currentItem, itemBids: currItemBids });
      });
    });
});

router.post('/item/:itemId/postBid', authCheck, (req, res) => {
    user = req.user;
    curItemId = req.params.itemId;
    curBidPrice = req.body.biddingPrice;
    bid = {
      accountId:user.id,
      itemId:curItemId,
      biddingPrice:curBidPrice
    };
    itemServices.postBid(bid,function(){
      item = itemServices.getItemById(curItemId,function(error,currentItem){
        res.render('item', { item: currentItem });
      });
    });
});

router.get('/createItem', authCheck, (req, res) => {
    user = req.user;
    res.render('createItem', {});
});

router.post('/createItem/create', authCheck, (req, res) => {
    user = req.user;
    var item = {
      name: req.body.name,
      description: req.body.description,
      availability: true,
      startingPrice: req.body.startingPrice,
      priceSold: null,
      image: null,
      date: req.body.auctionDate
    };
    itemServices.createItem(item,user.id,function(){
      items = itemServices.getAllItemsForAccount(user.id,function(error,currentItems){
        res.render('items', { items: currentItems });
      });
    });
});

router.get('/biddingHistory/:itemId', authCheck, (req, res) => {
    user = req.user;
    itemId = req.params.itemId;
    itemBids = itemServices.getBidsForItem(itemId, function(error, currItemBids){
      res.render('biddingHistory', { itemBids: currItemBids });
    });
});

module.exports = router;
