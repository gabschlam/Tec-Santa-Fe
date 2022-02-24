var express = require("express"); //importar express

const cors = require('cors');
var app = express();
app.use(cors());
var bodyParser = require("body-parser");
var morgan = require("morgan");
var passwordHash = require('password-hash');
var port = process.env.PORT || 8080; ///puerto disponible

const jwt = require('jsonwebtoken');

app.use(morgan("dev"));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var uri = "mongodb+srv://proyecto_user:integrador123@cluster0-f9acl.gcp.mongodb.net/proyectoIntegrador?retryWrites=true&w=majority";

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
var Product = require("./app/models/product");
var User = require("./app/models/user");
var Facility = require("./app/models/facility");
var Carrito = require("./app/models/carrito");

router
  .route("/login")
  .post(function (req, res) {
    if (req.body.email && req.body.password) {
      User.findOne({email: req.body.email}, function (error, usuarioDB) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        if (!usuarioDB) {
          res.status(401).send({ message: "Usuario o password incorrectos" });
          return;
        }

        if (!passwordHash.verify(req.body.password, usuarioDB.password)) {
          res.status(401).send({ message: "Usuario o password incorrectos" });
          return;
        }
        else {
          const token = jwt.sign({_id: usuarioDB._id}, 'secretKey', {expiresIn: "1d"})
          res.status(200).send({ message: "Login success", _id: usuarioDB._id, token: token, role: usuarioDB.role});
        }
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  });

router
  .route("/logout")
  .get(function (req, res) {
    res.status(200).send({ message: "Logout success"});
  });

  router
  .route("/products")
  .post(verifyToken, async function (req, res) {
    console.log(req.body)
    if (req.body.name && req.body.description && req.body.size && req.body.presentation && req.body.price && req.body.url && req.body.quantity) {
      var product = new Product();
      var idProd;
      
      await Product.findOne(async function (err, result) {
        if (err) {
          res.status(500).send(err);
          console.log("first error")
          return;
        }
        else {
          idProd = parseInt(result._id)+1;
          product._id = idProd;

          product.name = req.body.name;
          product.description= req.body.description;
          product.size= req.body.size;
          product.presentation= req.body.presentation;
          product.price= parseInt(req.body.price);
          product.url= req.body.url;
          product.quantity = req.body.quantity;
          try {
            await product.save(function (err) {
              if (err) {
                console.log(err);
                if (err.name == "ValidationError")
                  res.status(400).send({ error: err.message });
              }
              else{
                res.status(200).send({ mensaje: "Producto creado" });
              }
            });
            
          } catch (error) {
            res.status(500).send({ error: error });
          }
        }
      }).sort('-_id')
    }
  else {
    res.status(400).send({error: "missing fields"})
  }
});   

router
  .route("/allProducts/:page")
  .get(verifyToken, async function (req, res) {
    const resPerPage = 6;
    const page = req.params.page;
    var cantidadTotal = 0;

    await Product.find({ }).skip((resPerPage * page)-resPerPage).limit(resPerPage).sort({_id: 1}).exec(async function (err, products) {
      //console.log(products)
      if (err) {
        console.log(err)
        res.status(500).send(err);
      }
      else {
        await Product.count({}, function (err, count) {
          if (err) {
            res.status(500).send(err);
          }
          else {
            
            res.status(200).send({products, currentPage: parseInt(page), pages: Math.ceil(count / resPerPage)});

          }
        })
      }
    })
  });

router
  .route("/products/:id_product")
  .get(verifyToken, function (req, res) {
    Product.findById(req.params.id_product, function (error, producto) {
      if (error) {
        res.status(500).send(error);
        return;
      }
      if (producto == null) {
        res.status(404).send({ producto: "not found" });
        return;
      }
      res.status(200).send(producto);
    });
  })
  .put(verifyToken, function (req, res) {
    if (req.body.name && req.body.description && req.body.size && req.body.presentation && req.body.price && req.body.url && req.body.quantity) {
      Product.findById(req.params.id_product, function (err, product) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        product.name = req.body.name;
        product.description= req.body.description;
        product.size= req.body.size;
        product.presentation= req.body.presentation;
        product.price= parseInt(req.body.price);
        product.url= req.body.url;
        product.quantity = req.body.quantity;

        product.save(function (err) {
          if (err) {
            res.status(500).send(err);
            return;
          }
          res.status(200).send({ message: "producto actualizado" });
        });
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })
  .delete(verifyToken, function (req, res) {
    Product.remove({_id: req.params.id_product},
      function (err) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        res.status(200).send({ message: "Producto borrado con exito" });
      }
    );
  });

// USERS
router
  .route("/users")
  .post(async function (req, res) {
    if (req.body.profile_pic && req.body.name && req.body.lname && req.body.dBirth && req.body.addresses && req.body.email && req.body.password) {
      var idUser;
      console.log(req.body)
      var user = new User();
      await User.findOne(async function (err, result) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        else {
          idUser = parseInt(result._id)+1;
          user._id = idUser;

          await User.findOne({email: req.body.email}, async function (err, resp) {
            if (resp) {
              res.status(400).send({error: "Email already exists"})
            }
            else if (err) {
              res.status(500).send(err);
            }
            else {
              user.profile_pic = req.body.profile_pic;
              user.name = req.body.name;
              user.lname = req.body.lname;
              user.dBirth = req.body.dBirth
              user.addresses = req.body.addresses;
              user.email = req.body.email;
              user.password =  passwordHash.generate(req.body.password);
              
              try {
                await user.save(function (err) {
                  if (err) {
                    console.log(err);
                    if (err.name == "ValidationError")
                      res.status(400).send({ error: err.message });
                  }
                  else{
                    res.status(200).send({ mensaje: "Usuario creado" });
                  }
                });
                
              } catch (error) {
                res.status(500).send({ error: error });
              }
            }
          })
        }
      }).sort('-_id')
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  
  })
  .get(verifyToken, function (req, res) {
    User.find({ }, function (err, usuarios) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      res.status(200).send(usuarios);
    }).select('-password').sort({ _id: 1 })
  });

// USERS
router
  .route("/address/:id_user")
  .post(verifyToken, async function (req, res) {
    if (req.body.address) {
      console.log(req.body)
      
      User.findOneAndUpdate({_id: req.params.id_user}, {$push: {addresses: req.body.address}}, async function (error, result) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        res.status(200).send({ message: "direccion agregada" });
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })
  .put(verifyToken, function (req, res) {
    if (req.body.address) {
      console.log(req.body)
      
      User.findOneAndUpdate({_id: req.params.id_user}, {addresses: req.body.address}, async function (error, result) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        res.status(200).send({ message: "direcciones actualizadas" });
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })

router
  .route("/users/:id_user")
  .get(verifyToken, function (req, res) {
    User.findById(req.params.id_user, function (error, usuario) {
      if (error) {
        res.status(500).send(error);
        return;
      }
      if (usuario == null) {
        res.status(404).send({ usuario: "not found" });
        return;
      }
      res.status(200).send(usuario);
    }).select('-password');
  })
  .put(verifyToken, function (req, res) {
    if (req.body.profile_pic && req.body.name && req.body.lname && req.body.dBirth && req.body.country && req.body.email) {
      User.findById(req.params.id_user, function (err, user) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        user.profile_pic = req.body.profile_pic;
        user.name = req.body.name;
        user.lname = req.body.lname;
        user.dBirth = req.body.dBirth
        user.country = req.body.country;
        user.email = req.body.email;

        user.save(function (err) {
          if (err) {
            res.status(500).send(err);
            return;
          }
          res.status(200).send({ message: "usuario actualizado" });
        });
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })
  .delete(verifyToken, async function (req, res) {
    try {
      await productUser.deleteOne( { idUser: req.params.id_user}, function (err) {
        if (err) {
          console.log(err);
          if (err.name == "ValidationError")
            res.status(400).send({ error: err.message });
        }
        else {
          User.remove({_id: req.params.id_user},
            function (err) {
              if (err) {
                res.status(500).send(err);
                return;
              }
              res.status(200).send({ message: "Usuario borrado con exito" });
            }
          );
        }
      });
    } catch (error) {
      res.status(500).send({ error: error });
    }
  });

// FACILITIES
router
.route("/facility")
.get(verifyToken, function (req, res) {
  Facility.find({ }, function (err, facility) {
    if (err) {
      res.status(500).send(err);
      return;
    }
    res.status(200).send(facility);
  }).sort('_id')
});

// CARRITO
router
  .route("/carrito")
  .get(verifyToken, function (req, res) {
    Carrito.find({ }, function (err, carrito) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      res.status(200).send(carrito);
    })
  });

router
  .route("/carrito/:id_user")
  .get(verifyToken, function (req, res) {
    Carrito.find({idUser: req.params.id_user}, function (error, carrito) {
      if (error) {
        res.status(500).send(err);
        return;
      }
      if (carrito == null) {
        res.status(404).send({ carrito: "not found" });
        return;
      }
      res.status(200).send(carrito);
    });
  })
  .post(verifyToken, async function (req, res) {
    console.log(req.body);
    if (req.body) {
      Carrito.find({idUser: req.params.id_user, "products._id": req.body.idProd}, async function (err, producto) {
        console.log(producto)
        if (err) {
          res.status(500).send(err);
          return;
        }
        else if (producto != "") {
          res.status(400).send({message: "Producto ya en el carrito"})
          return;
        }
        else {
          await Product.findById(req.body.idProd, function (err, producto) {
            console.log(producto)
            if (err) {
              res.status(500).send(err);
              return;
            }
            else {

              Carrito.findOneAndUpdate({idUser: req.params.id_user}, {$push: {products: producto}}, async function (error, result) {
                if (error) {
                  res.status(500).send(error);
                  return;
                }
                if (result == null) {
                  var carrito = new Carrito();

                  carrito.idUser = req.params.id_user;
                  carrito.products = producto;
                  try {
                    await carrito.save(function (err) {
                      if (err) {
                        console.log(err);
                        if (err.name == "ValidationError")
                          res.status(400).send({ error: err.message });
                      }
                      else{
                        res.status(200).send({ mensaje: "Producto agregado al carrito" });

                      }
                    });
                  } catch (error) {
                    res.status(500).send({ error: error });
                  }
                }
                else {
                  res.status(200).send({ mensaje: "Producto agregado al carrito" })
                }
              });
            }
              
          })
        }
      });
    }

    else {
      res.status(400).send({error: "missing fields"})
    }
    
  })
  .delete(verifyToken, function (req, res) {
    var idProd = parseInt(req.body.idProd);

    Carrito.updateOne({idUser: req.params.id_user}, {$pull: {products: {"_id": idProd}}}, { "multi": false }, function (error, result) {
      console.log(result)
      if (error) {
        console.log(error)
        res.status(500).send(error);
        return;
      }
      if (result == null) {
        res.status(404).send({ result: "not found" });
        return;
      }
      else{
        res.status(200).send({ mensaje: "Producto eliminado del carrito" })
      }
    });
  });

function verifyToken(req, res, next) {
  if (!req.headers.authorization) {
    return res.status(401).send({error: "Unauthorized request"})
  }
  
  const token = req.headers.authorization.split(' ')[1];

  if (token === 'null') {
    return res.status(401).send({error: "Unauthorized request"})
  }

  const payload = jwt.verify(token, 'secretKey');
  req.userId = payload._id;
  next();

}

app.use("/api", router); //url base de nuestro api que tiene las rutas en el routerglobal.fetch = require('node-fetch');
router.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')
  next();
});

app.listen(port); //abre el puerto de escucha

console.log("sevidor arriba");
