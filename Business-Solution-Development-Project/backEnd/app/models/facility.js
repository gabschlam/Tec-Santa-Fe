var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var FacilitySchema = new Schema({
    _id: {
        type: Number,
        required: true,
    },
    name: {
        type: String,
        required: true,
    },
    address: {
        type: Array,
        required: true,
    },
    opening_hour: {
        type: String,
        required: true,
    },
    closing_hour: {
        type: String,
        required: true,
    }
}, { versionKey: false });

module.exports = mongoose.model("Facility", FacilitySchema, 'facilities');
