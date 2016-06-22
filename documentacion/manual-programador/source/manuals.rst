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
A continuacion se dara una breve descripcion de los distintos endpoints del appserver y como se manejan para obtener la informacion.

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

Virtualizacion
""""""""""""""""""

Para correr en contenedor de docker ejecutar en la consola

> sudo docker run -t -i -p 127.0.0.1:8083:8083 appserver/ubuntu:14.04v3 /bin/bash

> ./appServer

Correr Unit Tests
""""""""""""""""""

En la consola desde la carpeta build ejecutar el comando

 > ctest

Correr Coverage
""""""""""""""""""

En la raiz del proyecto correr

 > sudo ./coverage.sh

Se abrira una ventana del navegador con los resultados de tests cubiertos

.. image:: Screenshots/coverage.png

Correr Tests funcionales
""""""""""""""""""

Instalar python

> sudo apt-get install python2.7

Instalar pip

> wget https://bootstrap.pypa.io/get-pip.py
> sudo python get-pip.py

Instalar el modulo requests

> sudo pip install requests

Para correr los tests funcionales ir al directorio functionalTests y correr los tests

> cd functionalTests/
> python python restTester.py

Saldran los resultados del tests en la consola


Testear Endpoints Manualmente
""""""""""""""""""
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

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``

Candidates
"""""""""

``GET http://127.0.0.1:8083/api/matches/candidates/``

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``

Ver mensajes
"""""""""

``GET http://127.0.0.1:8083/api/matches/1/messages/``

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``

Enviar mensaje
"""""""""

``PUT http://127.0.0.1:8083/api/matches/1/message/``

``{
"message": "Hola!"
}``

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``


Like
"""""""""
``PUT http://127.0.0.1:8083/api/accounts/1/like/``

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``


Disike
"""""""""

``PUT http://127.0.0.1:8083/api/accounts/3/dislike/``

Agregar el header con el token que recibio del endpoint de login

``Authorization: <token>``


Shared Server
--------------------------------------------

Testear manualmente
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Login
"""""""""

``POST http://127.0.0.1:8083/api/accounts/login``

En la tab body, seleccionar el radiobutton raw y agregar el siguiente texto

``{
"username": "user",
"password": "P4ssw0rd"
}``

Agregar headers

``Authorization: <token>``
``Content-Type: application/json``

Listado de  usuarios
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users``


Vista de un usuario
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/5``

Vista de perfil de usuario
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/5/profile``

Agregar headers

``Authorization: <token>``
``Content-Type: application/json``

Vista de candidatos de usuario
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/nico/candidates``

Agregar headers

``Authorization: <token>``
``Content-Type: application/json``


Alta de usuario
"""""""""

``POST https://tallerdeprogramacionii-1c2016.herokuapp.com/users``

``{
"name":"Name",
"Alias":"aliiaass",
"email":"mail@mail.com", 
"latitude":"21222", 
"Longitude":"22322"
}``

Edit de usuario
"""""""""

``PUT https://tallerdeprogramacionii-1c2016.herokuapp.com/users/1``

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

``DELETE https://tallerdeprogramacionii-1c2016.herokuapp.com/users/20``

Listado de  intereses
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/interests``

Alta de interes
"""""""""

``POST https://tallerdeprogramacionii-1c2016.herokuapp.com/interests``

``{
"category":"Music",
"value":"One Direction"
}``

Baja de interes
"""""""""

``DELETE https://tallerdeprogramacionii-1c2016.herokuapp.com/interests/2``

============================================
Instalacion
============================================

Application Server
--------------------------------------------

Pasos para correr la aplicación

- Bajar archivos e instalar paquetes requeridos: En una consola copiar y pegar la siguiente linea
	- git clone ghttps://github.com/nicolas-vazquez/tp75521c.git
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

Para correr con docker

 - Buildear contenedor de Docker
 	- sudo docker build -t appserver/ubuntu:14.04 .
 - Correr contenedor de Docker
 	- sudo docker run -t -i -p 127.0.0.1:8083:8083 appserver/ubuntu:14.04v3 /bin/bash
 	- ./appServer

Cliente
--------------------------------------------

- Setear ip de computadora a 192.168.1.33
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

- Para ver la app ingresar a https://tallerdeprogramacionii-1c2016.herokuapp.com/

