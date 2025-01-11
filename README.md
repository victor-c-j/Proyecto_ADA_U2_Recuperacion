# Proyecto-ADA-tema-2-Recuperacion
Segunda entrega proyecto ada tema 2, MySQL Olimpiadas Colorado Jiménez Víctor
Esta vez, he modificado todas las clases pertinentes a los atletas, esta vez partimos de una consulta
mucho más avanzada, que reúne información de varias tablas al mismo tiempo, para mostrar información 
más relevante de los atletas, por tanto, también se ha modificado todos los métodos de AtletaDAO, para que siempre que sea
posible, trabajen o devuelvan con objetos de Atletas. 

Ahora los Atletas tienen todos los atributos que se mostrarán en la vista, no solo 
los atributos correspondientes a la tabla Atletas, la idea no es representar solo una tabla, 
sino trabajar de forma cómoda con toda la información que queremos mostrar en la vista, es mucho más 
cómodo que tener que trabajar sabiendo las posiciones exactas de un array de Strings, aquí cada atributo de Atleta
tiene un nombre significativo, para que sepas a que dato estás accediendo, al principio no le vi mucha utilidad, 
pero debo admitir que es una gran mejora.

La Ventana Vista Atletas solo tiene métodos referentes a la actualización de la información que se va a mostrar en la pantalla,
para la vista ahora es invisible el modelo y el controlador, en cambio, es ahora el controlador el que gestiona y llama a los métodos de AtletaDAO y a los métodos de la ventana, es quien pasa ya los datos finales para mostrar a la vista, teniendo así
esta última solo que cargar los nuevos datos en pantalla.

Se han cambiado los dos triggers que tenemos en nuestra base de datos, por unos más significativos,
ahora sirven como restricciones reales y con sentido sobre nuestra base de datos, definimos que la  edad mínima a insertar para todos los atletas sea de 16 años, y por otro lado obligamos a que un juez sólo pueda arbitrar un mismo evento en los juegos olímpicos.

También hacemos uso de una clase Tabla Abstracta, la cual usaremos para representar las tablas que tendrá nuestra aplicación, independientemente de cúantas columnas tenga y lo que contengan, esta se adaptará al contenido en el momento en el que la construyamos.

