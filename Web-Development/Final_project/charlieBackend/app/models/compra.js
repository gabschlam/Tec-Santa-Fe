var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var ComprasSchema = new Schema({
    idUser: {
        type: Number,
        required: true,
    },
    status: {
        type: String,
        required: true,
    },
    products: {
        type: Array,
        required: true,
    },
    address: {
        type: Array,
        required: true,
    },
    validation: Boolean,
    comment: String
}, { versionKey: false });

module.exports = mongoose.model("Compra", ComprasSchema, 'compras');
