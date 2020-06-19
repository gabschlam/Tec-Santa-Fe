var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var productsUsersSchema = new Schema({
    idUser: {
        type: Number,
        required: true,
    },
    products: Array
}, { versionKey: false });

module.exports = mongoose.model("ProductUser", productsUsersSchema, 'productsUser');
