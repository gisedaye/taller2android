*********
MatchApp

Manual de programador
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
Arquitectura
============================================

.. image:: Screenshots/architecture.png

Appserver
--------------------------------------------
A continuacion se dara una breve descripcion de los distintos endpoits del appserver y como se manejan para obtener la informacion.

Signup
"""""""""

- Desde el cliente request a AppServer con los datos del profile y intereses completos.
- Request a SharedServer para crear un usuario con los datos restantes (profile y intereses).
- Guardo la información del profile en SharedServer.
- El AppServer guarda username y password.
- Vuelvo con un Successful SignUp all cliente.

Login
"""""""""

- Desde el cliente request a AppServer con username/password
- Busco el usuario en el AppServer:
- En el caso que exista devuelvo accessToken
- Si no existe hago un request a SharedServer, puede ser que el usuario haya sido creado desde el backoffice.
- Busco el usuario en el SharedServer y devuelvo username/password
- Guardo username/password y accesstoken/username en el AppServer
- Vuelvo al cliente con el access token

Like/Dislike
"""""""""

- Desde el cliente request a AppServer con el id
- Guardamos el id en el array de keptAccounts
- Si hay un match lo guardamos

GetCandidates
"""""""""

- Desde el cliente request a AppServer  GET /candidates con location.
- Busco el usuario en el AppServer, obtengo el username
- Request a SharedServer con username
- SharedServer determina los candidatos de acuerdo a los intereses del usuario 
- Devuelvo los candidatos al AppServer y filtro por likes/dislikes
- Devuelvo los candidatos filtrados al cliente


GetMatches
"""""""""

- Desde el cliente request al AppServer GET /matches
- Obtenemos la lista de matches
- Request a SharedServer con la lista
- Obtenemos los perfiles asociados
- Devuelvo los matches al cliente


Sharedserver
--------------------------------------------
Listado de usuarios
""""""""""""""""""
- Request GET /users/
- Busca a todos los usuarios del sharedserver
- Devuelve array de usuarios con: id, name, alias, email, sex, age, photo_profile, array de interests, location, metadata

Alta de usuario
""""""""""""""""""
- Request POST /users/ con parametros: name, alias, email, sex, age, interests, location 
- Crea al usuario  en el sharedserver

Consulta perfil de usuario
""""""""""""""""""
- Request GET /users/+id
- Busca en el shared server usuario con ese id
- Devuelve un objeto user con: id, name, alias, email, sex, age, photo_profile, array de interests, location, metadata

Edicion de usuario
""""""""""""""""""
- Request PUT /users/+id con parametros: id, name, alias, email, sex, age, photo_profile, interests, location 
- Modifica al usuario  en el sharedserver

Actualización foto perfil
""""""""""""""""""
- Request PUT /users/+id/photo con parametro: photo en base64
- Agrega una foto de perfil al usuario con id +id.

Baja de usuario
""""""""""""""""""
- Request DELETE /users/+id
- Elimina al usuario del sharedserver

Listado de intereses
""""""""""""""""""
- Request GET /interests/
- Busca a todos los intereses del sharedserver
- Devuelve array de intereses con: category, value

Alta de interes
""""""""""""""""""
- Request POST /interests/ con parametros: category, value
- Crea al interes en el sharedserver en esa categoria

Client
--------------------------------------------
- Consume los endpoints del appserver para Login, Registro, Candidatos, Matches, Like, Dislike
- Vistas:
	- LoginActivity	
	- RegisterActivity
	- MatchActivity
	- MenuFragments (items del menu)
	- MatchFragments (candidatos para match)

============================================
Testing
============================================

Appserver
--------------------------------------------

Correr Unit Tests
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
En la consola desde la carpeta build ejecutar el comando

 > ctest

Testear Endpoints Manualmente
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Correr el appserver (Ver manual de instalacion)

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

Edit de usuario
"""""""""

``PUT https://sharedserver.herokuapp.com/users/1``

``{
"id": 1,
"name":"Name",
"Alias":"aliiaass",
"email":"mail@mail.com", 
"latitude":"21222", 
"Longitude":"22322"
}``


Baja de usuario
"""""""""

``DELETE https://sharedserver.herokuapp.com/users/20``

Listado de  intereses
"""""""""

``GET https://sharedserver.herokuapp.com/interests``

Alta de interes
"""""""""

``POST https://sharedserver.herokuapp.com/interests``

``{
"category":"Music",
"value":"One Direction"
}``


============================================
Instalacion
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
- Para correr con docker:
	- En la consola ejecutar el comando ./install-docker.sh para instalar dependencias.
	- Luego ejecutar los siguientes comandos:
		- sudo service mongodb stop
		- sudo docker-compose up
	- Para detener la aplicacion correr en la consola:
		- sudo docker-compose stop

- Para ver la app ingresar a https://sharedserver.herokuapp.com/

