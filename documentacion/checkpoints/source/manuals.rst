*********
MatchApp

Entrega
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
Changelog
============================================
- Shared Server
    - Setup de la aplicación Heroku
    - Conexión con Postgres
    - Webapp en node.js con listado y Alta, baja, vista de usuarios, edit de usuarios
    - Listado de intereses
    - Alta de intereses en categorias.
- Application Server
    - CMake
    - Conexion appServer con SharedServer
    - Conexión con mongoose-cpp a RocksDB
    - Endpoints signup/login/like/dislike
    - Logica de Matches
    - Unit tests
    - Logs
    - Ci-travis
    - Integration tests
    - Medir code coverage de unit tests
    - Docker
    - Retornar access token encriptado en login
- Cliente
    - Pantalla de Login
    - Pantalla de Registro
    - Pantalla de candidatos
    - Funcionalidad de like/dislike
    - Integración con Appserver mediante Volley
    - Manejo de sessiones con el auth token
	- Menu lateral
- Documentacion en Sphinx

============================================
Division de tareas
============================================

- Cliente: Gisela
- AppServer: Federico y Nicolas
- SharedServer: Pablo

Trello Board para ver tareas y responsables:
https://trello.com/b/MKvp61Xg/taller2-matchapp 

============================================
Links útiles
============================================

Repositorios
^^^^^^^^^^^^^
- SharedServer: https://github.com/PabloFederico/SharedServer
- ApplicationSever: https://github.com/nicolas-vazquez/tp75521c
- Cliente: https://github.com/gisedaye/taller2android/ 

Aplicación web
^^^^^^^^^^^^^
https://tallerdeprogramacionii-1c2016.herokuapp.com/

Funcionalidades:
 - List GET /users
 - Add POST /users
 - View GET /users/{userId}
 - Delete DELETE /users/{userId}
 - List GET /interests
 - Add POST /interests



