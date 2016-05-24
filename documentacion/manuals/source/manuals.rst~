*********
MatchApp
*********
**Grupo 10**

**Ayudante asignado: Christian Calonico**

**Integrantes:**

+-------------------------------------+--------------------------------------+
|       Apellido y Nombre             |              Padrón                  |
+=====================================+======================================+
|       Daye, Gisela Denise           |              87602                   |
+-------------------------------------+--------------------------------------+
|       Federico, Pablo               |              90280                   |
+-------------------------------------+--------------------------------------+
|       Farina, Federico              |              90177                   |
+-------------------------------------+--------------------------------------+
|       Vazquez, Nicolás              |              89172                   |
+-------------------------------------+--------------------------------------+

============================================
Manual de usuario
============================================

Cliente
--------------------------------------

- Instalar apk en el telefono celular
https://drive.google.com/open?id=0B96FtE1h2ukFNHdob042a3ZQU1k

- Ejecutar el Application server 

- Abrir aplicacion

- Se encontrara con la pantalla de login

.. image:: Screenshots/login.png

- Si ya posee un usuario registrado, ingrese los campos requeridos de usuario y clave y luego haga tap en el boton de entrar.
- Si los datos son los correctos se lo redireccionara a la pantalla de busqueda de matchs
- Si los datos son incorrectos aparecera un mensaje de error y debera volver a intentar loguearse

- Si no posee un usuario debera crear uno haciendo tap en el texto “Registrate en Match”
- Se redireccionara a la pantalla de registro
.. image:: Screenshots/registro.png

- Llene los datos requeridos para crear el usuario y haga tap en registrarse
- Si los datos son correctos se lo redireccionara a la pantalla de login para que pueda ingresar a la aplicacion
- Si los datos son erroneos aparecera un mensaje de error y debera reintentar el registro.

Shared Server
--------------------------------------

- Ingrese a https://sharedserver.herokuapp.com/

Para dar de alta un usuario haga click en “Alta usuario”

Se redireccionara a la pantalla de alta de usuario
.. image:: Screenshots/user.png

Ingrese lo datos requeridos y haga click en el boton “Alta Usuario”

Si el usuario se creo correctamente se mostrara el texto “Created”

Para ver los usuarios registrados ingrese haga click en “Ver usuarios”

Se redireccionara a la pantalla de listado de usuarios
.. image:: Screenshots/users.png

============================================
Manual de programador
============================================

Appserver
--------------------------------------------

Correr el appserver (Ver manual de instalacion)

Correr Unit Tests
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
En la consola desde la carpeta build ejecutar el comando

 > ctest

Testear Endpoints Manualmente
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

SignUp
"""""""""

``POST http://127.0.0.1:8083/api/accounts/signup``

En la tab body, seleccionar el radiobutton raw y agregar el siguiente texto

``{
"username": "user",
"password": "P4ssw0rd"
}``



Login
"""""""""

``POST http://127.0.0.1:8083/api/accounts/login``

En la tab body, seleccionar el radiobutton raw y agregar el siguiente texto

``{
"username": "user",
"password": "P4ssw0rd"
}``


Matches
"""""""""

``GET http://127.0.0.1:8083/api/matches/``

Agregar el header

``Authorization: <token>``


Like
"""""""""
``PUT http://127.0.0.1:8083/api/accounts/1/like/``

Agregar el header

``Authorization: <token>``


Disike
"""""""""

``PUT http://127.0.0.1:8083/api/accounts/3/dislike/``

Agregar el header

``Authorization: <token>``


Shared Server
--------------------------------------------

Testear manualmente
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Listado de  usuarios
"""""""""

``GET https://sharedserver.herokuapp.com/users``


Vista de un usuario
"""""""""

``GET https://sharedserver.herokuapp.com/users/5``


Alta de usuario
"""""""""

``POST https://sharedserver.herokuapp.com/users``

``{
"name":"Name",
"Alias":"aliiaass",
"email":"mail@mail.com", 
"latitude":"21222", 
"Longitude":"22322"
}``


Baja de usuario
"""""""""

``DELETE https://sharedserver.herokuapp.com/users/20``


============================================
Manual de instalacion
============================================

Application Server
--------------------------------------------

Pasos para correr la aplicación

- Bajar archivos e instalar paquetes requeridos: En una consola copiar y pegar la siguiente linea
	- git clone git@bitbucket.org:fjfarina/tp75521c.git 
- Ejecutar los siguientes pasos tambien en la consola:
	- cd tp75521c/AppServer
	- sudo ./install.sh
- Buildear aplicación para crear ejecutable
	- mkdir build
	- cd build
	- cmake ..
	- sudo make
- Correr la aplicacion:
	- ./appServer

Cliente
--------------------------------------------

- Setear ip de computadora desde la consola ingrese:
	- Ifconfig 192.168.0.106
- Correr el Appserver 
- Bajar apk de https://drive.google.com/open?id=0B96FtE1h2ukFNHdob042a3ZQU1k desde el celular donde se ejecutara la aplicacion
- Abrir MatchApp desde el icono de la aplicación

Shared Server
--------------------------------------------

Ingresar a https://sharedserver.herokuapp.com/


============================================
Entrega
============================================

Changelog
--------------------------------------------
- Shared Server
    - Setup de la aplicación Heroku
    - Conexión con Postgres
    - Webapp en node.js con listado y Alta, baja, vista de usuarios, edit de usuarios
- Application Server
    - CMake
    - Conexión con mongoose-cpp a RocksDB
    - Endpoints signup/login/like/dislike
    - Unit tests
    - Logs
    - Ci-travis
    - Integration tests
- Cliente
    - Pantalla de Login
    - Pantalla de Registro
    - Pantalla de match
    - Pantalla de chat	
    - Integración con Appserver mediante Volley
- Documentacion en Sphinx

Division de tareas
--------------------------------------------
- Cliente: Gisela
- AppServer: Federico y Nicolas
- SharedServer: Pablo

Trello Board para ver tareas y responsables:
https://trello.com/b/MKvp61Xg/taller2-matchapp 

Links útiles
--------------------------------------------
Repositorios
^^^^^^^^^^^^^
- SharedServer: https://github.com/PabloFederico/SharedServer
- ApplicationSever: https://bitbucket.org/fjfarina/tp75521c 
- Cliente: https://github.com/gisedaye/taller2android/ 

Aplicación web
^^^^^^^^^^^^^
https://sharedserver.herokuapp.com/

Funcionalidades:
 - List GET /users
 - Add POST /users
 - View GET /users/{userId}
 - Delete DELETE /users/{userId}



