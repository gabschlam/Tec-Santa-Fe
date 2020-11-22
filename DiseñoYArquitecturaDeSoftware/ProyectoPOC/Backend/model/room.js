const mongoose = require('mongoose');
const Schema = mongoose.Schema;
 
// create a schema
const roomSchema = new Schema({
  roomId: { type: String, required: true, unique: true },
  campusCode: { type: String, required: true },
  building: { type: String, required: true },
  roomNumber: { type: Number, required: true },
  capacity: { type: Number, required: true }
}, { collection : 'rooms' });
 
const Room = mongoose.model('Room', roomSchema);
 
module.exports = Room;