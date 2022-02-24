var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var UsersSchema = new Schema({
  _id: {
    type: Number,
    required: true,
  },
  profile_pic: {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  lname: {
    type: String,
    required: true,
  },
  role:{
    type:String
  },
  dBirth: {
    type: String,
    required: true,
  },
  addresses: {
    type: Array,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  password: {
    type: String,
    required: true,
  },
}, { versionKey: false });

module.exports = mongoose.model("User", UsersSchema, 'users');
