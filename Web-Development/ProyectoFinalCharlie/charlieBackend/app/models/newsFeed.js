var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var NewsFeedSchema = new Schema({
    idUser: {
        type: Number,
        required: true,
    },
    message: {
        type: String,
        required: true,
    },
}, { versionKey: false });

module.exports = mongoose.model("NewsFeed", NewsFeedSchema, 'newsFeed');
