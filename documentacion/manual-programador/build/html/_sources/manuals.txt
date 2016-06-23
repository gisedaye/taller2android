*********
MatchApp

Manual de programador - Documentación Técnica
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
Tecnologías utilizadas
============================================

Cliente
----------------------------------------

- Android SDK compatible hasta v23
- Volley
- Material Design para templates


Application Server
----------------------------------------
- Cmake
- CI-Travis
- Mongoose-cpp
- Jsoncpp
- Casablanca
- RocksDB (v4.4.1)
- Docker
- Code coverage con gcov
- Unit tests (cppunit)
- Tests de endpoints con Postman 
- Tests funcionales con Python (pip y requests)


Shared Server
----------------------------------------
- Utilización de Heroku, para hostear la base de datos y la aplicación web.
.. image:: Screenshots/heroku.png

- Utilización de Express + Node.js en sus versiones 4.13 y 0.12.7 respectivamente.

- Utilización de una base de datos PostgreSQL para almacenamiento de la información de Match App.
.. image:: Screenshots/postgresql.png

- Aplicación Web utilizando CSS, HTML, Boostrap + Materialize, JQuery y Ajax.
- Test  de endpoints con Postman
- Docker


Proyecto
----------------------------------------
-- Documentación en Sphinx
- Utilización de 3 repositorios en GitHub. (Cliente, App Server y Shared Server)
- Shared Server: https://github.com/PabloFederico/SharedServer
- App Server: https://github.com/nicolas-vazquez/tp75521c
- Cliente y Documentación: https://github.com/gisedaye/taller2android

============================================
Arquitectura
============================================

.. image:: Screenshots/architecture.png

Appserver
--------------------------------------------

Esquemas
""""""""""""""""""
Paquetes del appServer

.. image:: Screenshots/paquetesApp.png

Modelos y Controladores básicos del sistema.

.. image:: Screenshots/classesapp.png

Esquemas de la base de datos de rocksDB.

.. image:: Screenshots/rocksdb.png

Diagrama de secuencia

..image:: Screenshots/secuenciaAppMain.png


Endpoints
""""""""""""""""""
A continuación se brindará una breve descripción de los distintos endpoints del App Server y como se manejan para obtener la información.

Signup
"""""""""

- Desde el cliente se realiza un request a App Server con los datos del profile e intereses completos.
- Se almacena username y password en el App Server.
- Se realiza un request a Shared Server para crear un usuario con los datos restantes (profile e intereses).
- Se almacena la información del profile en Shared Server.
- Se retorna la respuesta con un mensaje de error en caso de fallo o con un mensaje exitoso en caso contrario.

Login
"""""""""

- Desde el cliente se realiza un request a App Server con username/password.
- Se corrobora si existe el usuario en la base de datos de App Server.
- En el caso que exista se realiza un request a Shared Server para obtener el profile del usuario que luego de retorna junto con el Access Token.
- Si no existe se realiza un request a Shared Server, ya que el usuario puede haber sido creado desde el backoffice.
- Se busca el usuario en el Shared Server y si existe se devuelve el profile. En caso que no exista se devuelve un error al App Server.
- Si hubo error se devuelve un mensaje de error a la aplicación Cliente. En caso contrario se guarda username/password en el AppServer.
- Se retorna la respuesta con el Access Token y el profile del usuario.

Like/Dislike
"""""""""

- Desde el cliente se realiza un request a App Server con el id del usuario elegido como parámetro.
- Se almacena el id en el array de keptAccounts.
- En caso de producirse un match se almacena el mismo.

Get Candidates
"""""""""

- Desde el cliente se realiza un request a App Server con los datos de la localización geográfica del usuario.
- Se corrobora si existe el usuario en la base de datos de App Server.
- Se realiza un request a SharedServer con el alias del usuario para obtener los candidatos que compartan algún interés con el usuario y se encuentren dentro del radio de localización correspondiente.
- Shared Server determina los candidatos de acuerdo a los intereses del usuario.
- Se retornan la respuesta con los perfiles de los candidatos al App Server y se filtra por likes/dislikes.
- Se devuelve un candidato que cumpla con los requisitos de compartir un interés con el usuario, encontrarse dentro del radio de localización correspondiente y poseer una cantidad de matches menor al 1% del total de matches por usuario promedio.


Get Matches
"""""""""

- Desde el cliente se realiza un request al App Server.
- Se obtiene la lista de matches asociados al usuario.
- Se realiza un request a Shared Server con el listado de matches para obtener los profiles asociados a los mismos.
- Se retorna la respuesta a la aplicación Cliente con los datos de los usuarios que poseen un match con el usuario.


Sharedserver
--------------------------------------------

Esquemas
""""""""""""""""""

- A continuación se mostrará un esquema de funcionamiento del Shared Sever, para poder explicar el flujo en la utilización de la API
.. image:: Screenshots/esquemaShared.png

- En el siguiente Diagrama de Componentes podemos visualizar la interacción entre los diferentes módulos de Shared Server. Por un lado, todas las dependencias necesarias para el funcionamiento se encuentran dentro de node_modules. En la parte central se encuentra el servidor, que tiene interacción con las dependencias de la aplicación, con las vistas y con la API. En el caso de la API, interactúa con las vistas y con el servidor para enviar la respuesta a las solicitudes del mismo.

.. image:: Screenshots/componentes3Shared.png 

- Se crearon tres tablas en la base de datos de PostgreSQL para almacenar la información de los usuarios y sus intereses. EL esquema de tablas utilizados es el siguiente
.. image:: Screenshots/tablasShared.png

- El siguiente es un Diagrama Entidad-Relación de la base de datos:

.. image:: Screenshots/derShared.png

Listado de usuarios
""""""""""""""""""
- Request GET /users
- Devuelve un listado de usuarios con: id, name, alias, email, sex, age, photo_profile, interests, location, metadata

Alta de usuario
""""""""""""""""""
- Request POST /users con parámetros: name, alias, email, sex, age, interests, location, photo_profile
- Crea el usuario en el Shared Server

Consulta perfil de usuario
""""""""""""""""""
- Request GET /users/:id
- Busca en el Shared Server un usuario con el id correspondiente a la solicitud
- Devuelve un error en caso que no exista o en caso contrario un usuario con las siguientes propiedades: id, name, alias, email, sex, age, photo_profile, array de interests, location, metadata

Edición de usuario
""""""""""""""""""
- Request PUT /users/:id con parámetros: id, name, alias, email, sex, age, photo_profile, interests, location 
- Modifica el perfil del usuario en el Shared Server y retorna un error en caso que haya ocurrido un fallo o un mensaje exitoso en caso contrario.

Consulta foto de perfil
""""""""""""""""""
- Request GET /users/:id/photo con parámetro: id del usuario
- Obtiene la foto de perfil del usuario con el id correspondiente a la solicitud o un error en caso que no exista el usuario.

Baja de usuario
""""""""""""""""""
- Request DELETE /users/:id
- Elimina al usuario del Shared Server y retorna un error en caso que haya ocurrido un fallo o un mensaje exitoso en caso contrario.

Listado de intereses
""""""""""""""""""
- Request GET /interests
- Obtiene un listado con los intereses globales del Shared Server con las propiedades category y value o un error en caso que haya ocurrido un fallo.

Alta de interés
""""""""""""""""""
- Request POST /interests con parametros: category, value
- Crea un nuevo interés en el Shared Server para la categoría y valor correspondientes.

Client
--------------------------------------------
- Consume los servicios del App Server para Login, Registro, Búsqueda de Candidatos, Matches, Like, Dislike y Chat
- Maneja una sesión con el Authorization Token provisto previamente por el Login
- Vistas:
	- LoginActivity	
	- RegisterActivity
	- MainActivity
	- SplashActivity

============================================
Testing
============================================

Appserver
--------------------------------------------

Unit Tests
""""""""""""""""""

En la consola desde la carpeta build ejecutar el comando

 > ctest

Coverage
""""""""""""""""""

En el directorio raíz del proyecto ejecutar el siguiente comando:

 > sudo ./coverage.sh

Se abrirá una ventana del navegador con los resultados del test coverage

.. image:: Screenshots/coverage.png

Tests funcionales
""""""""""""""""""

Instalar python

> sudo apt-get install python2.7

Instalar pip

> wget https://bootstrap.pypa.io/get-pip.py
> sudo python get-pip.py

Instalar el módulo requests

> sudo pip install requests

Para correr los tests funcionales dirigirse al directorio functionalTests y ejecutar los siguientes comandos:

> cd functionalTests/
> python python restTester.py

Se mostrarán los resultados de los tests en la consola


Testear Endpoints Manualmente
""""""""""""""""""
Ejecutar el App Server (Ver Manual de instalación)
Ejecutar el cliente Postman

SignUp
"""""""""

``POST http://127.0.0.1:8083/api/accounts/signup``

En la pestaña body, seleccionar el radiobutton raw y agregar el siguiente texto:

``{
"username": "user",
"password": "P4ssw0rd"
}``



Login
"""""""""

``POST http://127.0.0.1:8083/api/accounts/login``

En la pestaña body, seleccionar el radiobutton raw y agregar el siguiente texto:

``{
"username": "user",
"password": "P4ssw0rd"
}``


Matches
"""""""""

``GET http://127.0.0.1:8083/api/matches/``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``

Candidates
"""""""""

``GET http://127.0.0.1:8083/api/matches/candidates``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``

Ver mensajes
"""""""""

``GET http://127.0.0.1:8083/api/matches/:id/messages``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``

Enviar mensaje
"""""""""

``PUT http://127.0.0.1:8083/api/matches/:id/message``

``{
"message": "Hola!"
}``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``


Like
"""""""""
``PUT http://127.0.0.1:8083/api/accounts/:id/like``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``


Disike
"""""""""

``PUT http://127.0.0.1:8083/api/accounts/:id/dislike``

Setear el header Authorization con el token que se recibió en la solicitud de Login

``Authorization: <token>``


Shared Server
--------------------------------------------

Testear manualmente
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Login
"""""""""

``POST http://127.0.0.1:8083/api/accounts/login``

En la pestaña body, seleccionar el radiobutton raw y agregar el siguiente texto

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

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/:id``

Consulta de perfil de usuario
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/:id/profile``

Agregar headers

``Authorization: <token>``
``Content-Type: application/json``

Consulta de candidatos para un usuario
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/users/:user/candidates``

Agregar headers

``Authorization: <token>``
``Content-Type: application/json``


Alta de usuario
"""""""""

``POST https://tallerdeprogramacionii-1c2016.herokuapp.com/users``

``{
"name":"Name",
"Alias":"aliiaass",
"age":23,
"gender":"M",
"email":"mail@mail.com", 
"latitude":"-34.58", 
"longitude":"-58.60",
"photo_profile": "base_64"
}``

Modificación de usuario
"""""""""

``PUT https://tallerdeprogramacionii-1c2016.herokuapp.com/users/:id``

``{
"id": 1,
"name":"Name",
"Alias":"aliiaass",
"age":23,
"gender":"M",
"email":"mail@mail.com", 
"latitude":"-34.58", 
"longitude":"-58.60",
"photo_profile": "base_64"
}``


Baja de usuario
"""""""""

``DELETE https://tallerdeprogramacionii-1c2016.herokuapp.com/users/:id``

Listado de intereses
"""""""""

``GET https://tallerdeprogramacionii-1c2016.herokuapp.com/interests``

Alta de interés
"""""""""

``POST https://tallerdeprogramacionii-1c2016.herokuapp.com/interests``

``{
"category":"Music",
"value":"One Direction"
}``

Baja de interés
"""""""""

``DELETE https://tallerdeprogramacionii-1c2016.herokuapp.com/interests/:id``

