var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var CarritosSchema = new Schema({
    idUser: {
        type: Number,
        required: true,
    },
    products: Array
}, { versionKey: false });

module.exports = mongoose.model("Carrito", CarritosSchema, 'carritos');
