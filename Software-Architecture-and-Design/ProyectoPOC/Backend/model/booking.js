const mongoose = require('mongoose');
const Schema = mongoose.Schema;
 
// create a schema
const bookingSchema = new Schema({
  userId: { type: String, required: true },
  roomId: { type: String, required: true },
  date: { type: String, required: true },
  timeStart: { type: String, required: true },
  timeEnd: { type: String, required: true },
  concept: { type: String, required: true }
}, { versionKey: false });
 
const Booking = mongoose.model('Booking', bookingSchema, 'bookings');
 
module.exports = Booking;