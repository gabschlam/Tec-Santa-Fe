var express = require("express"); //importar express
const jwt = require("express-jwt");
const jwksRsa = require("jwks-rsa");
var app = express();
var bodyParser = require("body-parser");
var morgan = require("morgan");
const cors = require("cors");
var corsOptions = {
  origin: "*",
  optionsSuccessStatus: 200,
};

// Set up Auth0 configuration
const authConfig = {
  domain: "dev-x97wd7s0.auth0.com",
  audience: "http://localhost:8080/api/"
};

app.use(cors(corsOptions));
app.options("*", cors());
app.use(morgan("dev"))

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080; ///puerto disponible

var uri = "mongodb+srv://test:test123@cluster0-f9acl.gcp.mongodb.net/test?retryWrites=true&w=majority";

var mongoose = require("mongoose");
mongoose.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true });

var db = mongoose.connection;
db.on("error", console.error.bind(console, "error de conexion"));
db.once("openUri", function () {
  console.log("Me conecte a mongodb");
});

// Define middleware that validates incoming bearer tokens
// using JWKS from dev-x97wd7s0.auth0.com
const checkJwt = jwt({
  secret: jwksRsa.expressJwtSecret({
    cache: true,
    rateLimit: true,
    jwksRequestsPerMinute: 5,
    jwksUri: `https://${authConfig.domain}/.well-known/jwks.json`
  }),

  audience: authConfig.audience,
  issuer: `https://${authConfig.domain}/`,
  algorithm: ["RS256"]
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

//declarar los modelos
var Alumno = require("./app/models/alumno");
var Clase = require("./app/models/clase");

router
  .route("/alumnos")
  .post(async function (req, res) {
    var alumno = new Alumno();
    alumno.nombre = req.body.nombre;
    alumno.apellido = req.body.apellido;
    alumno.matricula = req.body.matricula;
    alumno.carrera = req.body.carrera;
    alumno.email = req.body.email;
    try {
      //alumno.validate();
      await alumno.save(function (err) {
        if (err) {
          console.log(err);
          if (err.name == "ValidationError")
            res.status(400).send({ error: err.message });
        }
      });
      res.json({ mensaje: "Alumno creado" });
    } catch (error) {
      res.status(500).send({ error: error });
    }
  })
  .get(function (req, res) {
    //limite = parseInt(req.body.limite);
    //matricula = req.body.matricula;

    Alumno.find({ }, function (err, alumnos) {
      if (err) {
        res.send(err);
      }
      res.status(200).send(alumnos);
    })//.limit(limite);
  });

router
  .route("/alumnos/:id_alumno")
  .get(function (req, res) {
    Alumno.findById(req.params.id_alumno, function (error, alumno) {
      if (error) {
        res.status(404).send({ message: "not found" });
        return;
      }
      if (alumno == null) {
        res.status(404).send({ alumno: "not found" });
        return;
      }
      res.status(200).send(alumno);
    });
  })
  .put(function (req, res) {
    Alumno.findById(req.params.id_alumno, function (err, alumno) {
      if (err) {
        res.send(err);
      }
      alumno.nombre = req.body.nombre;
      alumno.save(function (err) {
        if (err) {
          res.send(err);
        }
        res.json({ message: "alumno actualizado" });
      });
    });
  })
  .delete(function (req, res) {
    Alumno.remove(
      {
        _id: req.params.id_alumno,
      },
      function (err, alumno) {
        if (err) {
          res.send(err);
        }
        res.json({ mensaje: "borrado con exito" });
      }
    );
  });

router.route("/alumnosv2/:matricula").get(function (req, res) {
  Alumno.find({ matricula: req.params.matricula }, function (error, alumno) {
    if (error) {
      res.status(404).send({ message: "not found" });
      return;
    }
    if (alumno == null) {
      res.status(404).send({ alumno: "not found" });
      return;
    }
    res.status(200).send(alumno);
  }).sort({ nombre: 1 });
});

router.route("/clases").post(async function (req, res) {
  var clase = new Clase();
  clase.nombre = req.body.nombre;
  clase.semestre = req.body.semestre;
  try {
    //alumno.validate();
    await clase.save(function (err) {
      if (err) {
        console.log(err);
        if (err.name == "ValidationError")
          res.status(400).send({ error: err.message });
      }
    });
    res.json({ mensaje: "Clase creado" });
  } catch (error) {
    res.status(500).send({ error: error });
  }
});

app.use("/api", router); //url base de nuestro api que tiene las rutas en el routerglobal.fetch = require('node-fetch');

// Define an endpoint that must be called with an access token
app.get("/api/external", checkJwt, (req, res) => {
  res.send({
    msg: "Your Access Token was successfully validated!"
  });
});

app.listen(port); //abre el puerto de escucha

console.log("sevidor arriba");
