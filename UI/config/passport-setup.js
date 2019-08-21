const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth20').Strategy;
const keys = require('./keys');
const accountServices = require('../services/accountServices');

passport.serializeUser((user, done) => {
    done(null, user.id);
});

passport.deserializeUser((id, done) => {
  accountServices.getDetailsByAccountID(id,function(err,user){
      done(null,user);
    });
});

passport.use(
    new GoogleStrategy({
        clientID: keys.google.clientID,
        clientSecret: keys.google.clientSecret,
        callbackURL: '/auth/google/redirect'
    }, (accessToken, refreshToken, profile, done) => {
        accountServices.getDetailsByGoogleID(profile.id,function(err,user){
          if(user){
              done(null,user);
          }else{            
            var user ={
              googleId: profile.id,
              userName: profile.displayName
            }
            accountServices.createAccount(user,function(err,user){
              done(null,user);
            })
          }
        });
      })
);
