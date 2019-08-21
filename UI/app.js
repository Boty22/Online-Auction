const express = require('express');
const cookieSession = require('cookie-session');
const passport = require('passport');
const authRoutes = require('./routes/auth-routes');
const profileRoutes = require('./routes/profile-routes');
const itemRoutes = require('./routes/item-routes');
const auctionRoutes = require('./routes/auction-routes');
const passportSetup = require('./config/passport-setup');
const keys = require('./config/keys');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require('fs');
const https = require('https');
const compression = require('compression');

const app = express();

// set view engine
app.set('view engine', 'ejs');

// set up session cookies
app.use(cookieSession({
    maxAge: 5 * 60 * 1000,
    keys: [keys.session.cookieKey]
}));

//Compress response to browser
app.use(compression());

//parse application/json, parse urlencoded
app.use(bodyParser.urlencoded({extended : false}))
app.use(bodyParser.json())

//cors for cross origin requests
app.use(cors({
  'allowedHeaders': ['sessionId', 'Content-Type'],
  'exposedHeaders': ['sessionId'],
  'origin': '*',
  'methods': 'GET,HEAD,PUT,PATCH,POST,DELETE',
  'preflightContinue': false
}));

// initialize passport
app.use(passport.initialize());
app.use(passport.session());

// set up routes
app.use('/auth', authRoutes);
app.use('/profile', profileRoutes);
app.use('/items', itemRoutes);
app.use('/auction', auctionRoutes);

// create home route
app.get('/', (req, res) => {
    res.render('home', { user: req.user });
});

app.get("*", (req,res)=>{
    res.status(404).render('unknown', {});
});

//set up https and start server
const httpsOptions = {
    key: fs.readFileSync('../ssl/key.pem', 'utf-8'),
    cert: fs.readFileSync('../ssl/server.crt', 'utf-8'),
    passphrase:'utdbuilders',
    rejectUnauthorized:false,
    requestCert:true,
    agent:false
};

var server = https.createServer(httpsOptions, app).listen(3000, () =>{
  console.log('app now listening for requests on port 3000');
})
