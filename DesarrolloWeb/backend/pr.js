var passwordHash = require('password-hash');

var hashedPassword = passwordHash.generate('charlie123');

console.log(hashedPassword);