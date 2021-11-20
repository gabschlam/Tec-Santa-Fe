var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var ProductsSchema = new Schema({
    idProd: {
        type: Number,
        required: true,
    },
    name: {
        type: String,
        required: true,
    },
    condition: {
        type: String,
        required: true,
    },
    description: {
        type: String,
        required: true,
    },
    quantity: {
        type: Number,
        required: true,
    },
    price: {
        type: Number,
        required: true,
    },
    url: {
        type: String,
        required: true,
    },
}, { versionKey: false });

module.exports = mongoose.model("Product", ProductsSchema);
