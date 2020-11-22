var express = require("express"); //importar express
const cors = require('cors');
var app = express();
app.use(cors());
var bodyParser = require("body-parser");
var morgan = require("morgan");

app.use(morgan("dev"));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 3000; ///puerto disponible

var uri = 'mongodb+srv://arquitectura_user:admin123@cluster0.f9acl.gcp.mongodb.net/softwareArchitecture?retryWrites=true&w=majority';

var mongoose = require("mongoose");
mongoose.set('useFindAndModify', false);
mongoose.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true });

var db = mongoose.connection;
db.on("error", console.error.bind(console, "error de conexion"));
db.once("openUri", function () {
  console.log("Me conecte a mongodb");
});

//middleware
var router = express.Router();

router.use(function (req, res, next) {
  next();
}); //funcion habilita el middleware

router.get("/", function (req, res) {
  res.json({
    mensaje: "keep alive",
  });
});

// declarar los modelos
var User = require('./model/user');
var Booking = require("./model/booking");
var Room = require("./model/room");

router
  .route('/login')
  .post(function (req, res) { 
        User.find({
            username : (req.body.username.toUpperCase()), password : req.body.password
        }, function(err, user){
            if(err) throw err;
            if(user.length === 1){  
                return res.status(200).json({
                    status: 'success',
                    data: user
                })
            } else {
                return res.status(200).json({
                    status: 'fail',
                    message: 'Login Failed'
                })
            }
             
        })
    });

router
  .route("/reservar")
  .post(async function (req, res) {
    var reserva = new Booking();
    if (req.body.userId && req.body.roomId && req.body.date && req.body.timeStart && req.body.timeEnd && req.body.concept) { 
      reserva.userId = req.body.userId;
      reserva.roomId = req.body.roomId;
      reserva.date = req.body.date;
      reserva.timeStart = req.body.timeStart;
      reserva.timeEnd = req.body.timeEnd;
      reserva.concept = req.body.concept;
     
      try {
        await reserva.save(function (err) {
          if (err) {
            console.log(err);
            if (err.name == "ValidationError")
              res.status(400).send({ error: err.message });
              return;
          }
        });
        res.json({ mensaje: "Reserva creado" });
      } catch (error) {
        res.status(500).send({ error: error });
      }  
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })

router
  .route("/reservas")
  .get(function (req, res) {
    console.log(req.query);
    Booking.find({userId : req.query.idUser }, function (err, reservas) {
      if (err) {
        res.send(err);
        return;
      }
      res.status(200).send(reservas);
    }).sort({ date: 1, timeStart: 1 });
  })
  .delete(function (req, res) {
    console.log(req.body);
    Booking.findById(req.body.bookingId, function (error, result) {
      if (error) {
        console.log(error)
        res.status(404).send({ message: "not found" });
        return;
      }
      if (result == null) {
        res.status(404).send({ result: "not found" });
        return;
      }
      else {
        Booking.remove({_id: req.body.bookingId},
          function (err) {
            if (err) {
              res.send(err);
              return;
            }
            res.json({ mensaje: "Reserva eliminada con exito" });
          }
        );
      }
  });
});

router
  .route("/reserva")
  .get(function (req, res) {
    console.log(req.query);
    Booking.findById(req.query.bookingId, function (err, reserva) {
      if (err) {
        res.send(err);
        return;
      }
      res.status(200).send(reserva);
    })
  })

router
  .route("/salones")
  .get(function (req, res) {
    Room.find({ }, function (err, salones) {
      if (err) {
        res.send(err);
        return;
      }
      res.status(200).send(salones);
    }).sort({ roomId: 1 });
  })
 
app.use("/api", router); //url base de nuestro api que tiene las rutas en el routerglobal.fetch = require('node-fetch');
router.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')
  next();
});

app.listen(port); //abre el puerto de escucha

console.log("sevidor arriba");