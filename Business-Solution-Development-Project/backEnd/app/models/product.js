var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var ProductsSchema = new Schema({
    _id: {
        type: Number,
        required: true,
    },
    name: {
        type: String,
        required: true,
    },
    description: {
        type: String,
        required: true,
    },
    size: {
        type: String,
        required: true,
    },
    presentation: {
        type: String,
        required: true,
    },
    price: {
        type: Number,
        required: true,
    },
    quantity: Array,
    url: {
        type: String,
        required: true,
    },
}, { versionKey: false });

module.exports = mongoose.model("Product", ProductsSchema, 'products');
