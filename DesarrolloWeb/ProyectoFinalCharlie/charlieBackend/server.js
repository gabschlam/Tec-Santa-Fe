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

var uri = "mongodb+srv://web_user:admin123@cluster0-f9acl.gcp.mongodb.net/charlie?retryWrites=true&w=majority";

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
var productUser = require("./app/models/productUser");
var Carrito = require("./app/models/carrito");
var Compra = require("./app/models/compra");
var newsFeed = require("./app/models/newsFeed");

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
          res.status(200).send({ message: "Login success", _id: usuarioDB._id, token: token});
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
  .route("/productsUsers")
  .post(verifyToken, async function (req, res) {
    console.log(req.body)
    if (req.body.idUser && req.body.name && req.body.condition && req.body.description && req.body.price && req.body.url) {
      var idProd
      await productUser.aggregate([{ $unwind: '$products' }, { $sort: {'products.idProd': -1}},{$limit: 1}], function (err, idProduct) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        else {
          console.log("idProd: " + idProduct[0].products.idProd)
          idProd = parseInt(idProduct[0].products.idProd)+1;
        }
      })

      var producto = {
        idProd: idProd,
        name: req.body.name,
        condition: req.body.condition,
        description: req.body.description,
        quantity: 1, 
        price: parseInt(req.body.price), 
        url: req.body.url,
      }

      productUser.findOneAndUpdate({idUser: req.body.idUser}, {$push: {products: producto}}, async function (error, result) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        if (result == null) {
          var product = new productUser();

          product.idUser = req.body.idUser;
          product.products = producto;
          try {
            await product.save(function (err) {
              if (err) {
                console.log(err);
                if (err.name == "ValidationError")
                  res.status(400).send({ error: err.message });
              }
            });
            res.status(200).send({ message: "Producto agregado" })
          } catch (error) {
            res.status(500).send({ error: error });
          }
        }
        else {
          res.status(200).send({ message: "Producto agregado" })
        }
      });
      
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
    
  })

router
  .route("/allProducts/:page")
  .get(verifyToken, async function (req, res) {
    console.log(req.userId)
    const resPerPage = 3;
    const page = req.params.page;

    await productUser.find({ }).skip((resPerPage * page)-resPerPage).limit(resPerPage).sort({idUser: 1}).exec(async function (err, products) {
      console.log(products)
      if (err) {
        console.log(err)
        res.status(500).send(err);
      }
      else {
        await productUser.count({}, function (err, count) {
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
    productUser.aggregate([{ $unwind: '$products' }, { $match: {'products.idProd': parseInt(req.params.id_product)}}], function (error, product) {
      if (error) {
        res.status(500).send(error);
        return;
      }
      if (product == "") {
        res.status(404).send({ product: "not found" });
        return;
      }
      res.status(200).send(product);
    });
  })
  .put(verifyToken, function (req, res) {
    if (req.body.name && req.body.condition && req.body.description && req.body.price && req.body.url) {
      var idProd = parseInt(req.params.id_product);
    
      var producto = {
        idProd: idProd,
        name: req.body.name,
        condition: req.body.condition,
        description: req.body.description,
        quantity: 1, 
        price: parseInt(req.body.price), 
        url: req.body.url,
      }

      productUser.findOneAndUpdate({"products.idProd": idProd}, {$set: {"products.$": producto}}, function (error, result) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        if (result == null) {
          res.status(404).send({ result: "not found" });
          return;
        }
        res.status(200).send({ message: "Producto actualizado" })
      });
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })
  .delete(verifyToken, function (req, res) {
    var idProd = parseInt(req.params.id_product);

    productUser.updateOne({"products.idProd": idProd}, {$pull: {products: {"idProd": idProd}}}, function (error, result) {
      if (error) {
        console.log(error)
        res.status(500).send(error);
        return;
      }
      if (result == null) {
        res.status(404).send({ result: "not found" });
        return;
      }
      res.status(200).send({ message: "Producto eliminado" })
    });
  });

// GET PRODUCTOS DEL USUARIO
router
  .route("/productsUsers/:id_user")
  .get(verifyToken, function (req, res) {
    productUser.aggregate([
      {$match: {'idUser': parseInt(req.params.id_user)} },
      {$lookup: 
        {from: 'users',
        localField: 'idUser',
        foreignField: '_id',
        as: 'user'}
      },
      {$unwind: '$user' },
      {$project: 
        {"idUser":1,
        'products':1,
        'user.name': 1,
        'user.lname': 1,
        'user.profile_pic': 1}
      }
    ],function (error, result) {
      console.log("result" + result)
      if (error) {
        res.status(500).send(error);
        return;
      }
      else if (result == null) {
        res.status(404).send({ result: "not found" });
        return;
      }
      else if (result == "") {
        User.findById(req.params.id_user, function (error, usuario) {
          if (error) {
            res.status(500).send(err);
            return;
          }
          else if (usuario == null) {
            res.status(404).send({ usuario: "not found" });
            return;
          }
          else {
            res.status(200).send({user: usuario});
          }
        }).select('idUser name lname profile_pic');
      }
      else {
        res.status(200).send(result[0]);
      }
    });
  })


// NEWS FEED
router
  .route("/newsFeed")
  .post(verifyToken, async function (req, res) {
    var post = new newsFeed();
    if (req.body.idUser && req.body.message) { 
      post.idUser = req.body.idUser;
      post.message = req.body.message;
     
      try {
        await post.save(function (err) {
          if (err) {
            console.log(err);
            if (err.name == "ValidationError")
              res.status(400).send({ error: err.message });
              return;
          }
        });
        res.json({ mensaje: "Post creado" });
      } catch (error) {
        res.status(500).send({ error: error });
      }  
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
  })
  .get(verifyToken, function (req, res) {
    newsFeed.aggregate([
                      {$lookup: 
                        {from: 'users',localField: 'idUser',
                        foreignField: '_id',
                        as: 'user'}
                      },
                      {$unwind: '$user' },
                      {$addFields : {
                        "time": { $dateToString: { format: "%d-%m-%Y %H:%M", date: "$_id", timezone: "America/Mexico_City"}}
                      }},
                      {$sort: {'_id': -1}},
                      {$project: 
                        {"time":1,
                        'user.name': 1,
                        'user.lname': 1,
                        'user.profile_pic': 1,
                        'message': 1}
                      }
                    ],function (err, result) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      res.status(200).send(result);
    })
  });


// USERS
router
  .route("/users")
  .post(async function (req, res) {
    if (req.body.profile_pic && req.body.name && req.body.lname && req.body.dBirth && req.body.country && req.body.email && req.body.password) {
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
              user.country = req.body.country;
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
      var product;
      Carrito.find({idUser: req.params.id_user, "products.idProd": req.body.idProd}, async function (err, producto) {
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
          await productUser.aggregate([{ $unwind: '$products' }, { $match: {'products.idProd': parseInt(req.body.idProd)}}, {'$limit': 1}, {$project: {'products':1, _id:0}}], function (err, producto) {
            if (err) {
              res.status(500).send(err);
              return;
            }
            else {
              console.log("producto[0].products" + producto[0].products);
              product = producto[0].products
            
              console.log("product: " + product);

              Carrito.findOneAndUpdate({idUser: req.params.id_user}, {$push: {products: product}}, async function (error, result) {
                if (error) {
                  res.status(500).send(error);
                  return;
                }
                if (result == null) {
                  var carrito = new Carrito();

                  carrito.idUser = req.params.id_user;
                  carrito.products = product;
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

    Carrito.updateOne({idUser: req.params.id_user}, {$pull: {products: {"idProd": idProd}}}, { "multi": false }, function (error, result) {
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

// COMPRA
router
  .route("/compra")
  .get(verifyToken, function (req, res) {
    Compra.find({ }, function (err, carrito) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      res.status(200).send(carrito);
    })
  });

router
  .route("/compra/:id_user")
  .get(verifyToken, function (req, res) {
    Compra.find({idUser: req.params.id_user}, function (error, compra) {
      if (error) {
        res.status(500).send(error);
        return;
      }
      else if (compra == null) {
        res.status(404).send({ compra: "not found" });
        return;
      }
      res.status(200).send(compra);
    }).sort('-_id');
  })
  .post(verifyToken, async function (req, res) {
    var productos;
    var compra = new Compra();
    await Carrito.find({idUser: req.params.id_user}, async function (err, carrito) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      else {
      console.log(carrito)
      productos = carrito[0].products;

      productos.forEach(element => {
        var idProd = parseInt(element.idProd);

        productUser.updateOne({"products.idProd": idProd}, {$pull: {products: {"idProd": idProd}}}, async function (error, result) {
          if (error) {
            console.log(error)
            res.status(500).send(error);
            return;
          }
          if (result == null) {
            res.status(404).send({ result: "not found" });
            return;
          }
        });
      });
          compra.products = productos;
     
          compra.idUser = req.params.id_user;
          compra.address = req.body.address;
          compra.status = "comprado";

        try {
          await compra.save(function (err) {
            if (err) {
              console.log(err);
              if (err.name == "ValidationError")
                res.status(400).send({ error: err.message });
            }
            else {
              Carrito.remove({idUser: req.params.id_user},
                function (err) {
                  if (err) {
                    res.status(500).send(err);
                    return;
                  }
                  res.status(200).send({ mensaje: "Compra creada con exito" });
                }
              );
            }
          });
        } catch (error) {
          res.status(500).send({ error: error });
        }  
    }
  })
});

router
  .route("/validarCompra/:id_user")
  .put(verifyToken, function (req, res) {
    if (req.body.validation) {
      Compra.findOne({idUser: req.params.id_user}, function (err, compra) {
        if (err) {
          res.status(500).send(err);
          return;
        }
        compra.validation = req.body.validation;
        if (req.body.comment != null) {
          compra.comment = req.body.comment;
        }

        compra.save(function (err) {
          if (err) {
            res.status(500).send(err);
            return;
          }
          res.status(200).send({ message: "Validacion de compra agregada" });
        });
      }).sort('-_id');
    }
    else {
      res.status(400).send({error: "missing fields"})
    }
    
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
//Show products in Main Admin
router
  .route("/compras")
  .get(verifyToken, function (req, res) {
    Compra.aggregate([{$unwind: '$products'},{
      $group: {
          _id: "$products"
      }
  }], function (error, product) {
      if (error) {
        res.status(500).send(error);
        return;
      }
      if (product == "") {
        res.status(404).send({ product: "not found" });
        return;
      }
      
      res.status(200).send(product);
    });
  })

  //Update Status
  router
    .route('/compras/:id_product')
    .get(verifyToken, function (req, res) {
      Compra.findOne({"products.idProd" : {$eq:parseInt(req.params.id_product)} }, async function (error, product) {
        if (error) {
          res.status(500).send(error);
          return;
        }
        if (product == "") {
          res.status(404).send({ product: "not found" });
          return;
        }
        
        res.status(200).send(product);
      });
    })
    .put(verifyToken, function(req,res){
      console.log("req.params.id_product" + req.params.id_product)
    Compra.findOneAndUpdate({"products.idProd" : {$eq:parseInt(req.params.id_product)} }, { $set: { "status": req.body.status }}, async function (error, result) {
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
      res.status(200).send({mensage : "Status cambiado" });
    }
    )
    });



app.use("/api", router); //url base de nuestro api que tiene las rutas en el routerglobal.fetch = require('node-fetch');
router.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')
  next();
});

app.listen(port); //abre el puerto de escucha

console.log("sevidor arriba");
