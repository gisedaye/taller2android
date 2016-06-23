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
    - Conexión con base de datos PostgreSQL
    - Webapp en Node.js con Listado, Alta, Baja y Modificación de perfiles de usuarios
    - Listado de intereses
    - Alta de intereses.
    - Consulta de candidatos correspondientes a un usuario
- Application Server
    - CMake
    - Conexión de App Server con Shared Server
    - Conexión con mongoose-cpp y RocksDB
    - Endpoints signup/login/like/dislike
    - Retornar Access Token en Login
    - Filtrado de candidatos
    - Lógica de Matches
    - Unit tests
    - Logs
    - CI-Travis
    - Tests de integración
    - Medir code coverage de tests unitarios
    - Docker
- Cliente
    - Pantalla de Login
    - Pantalla de Registro
    - Pantalla de Candidatos
    - Funcionalidad de like/dislike
    - Listado de Matches
    - Integración con App Server mediante Volley
    - Manejo de sesiones con el Authorization Token
    - Menú lateral
- Documentación en Sphinx

============================================
Entregas
============================================
Checkpoint 1
----------------------------------------

Changelog
"""""""""
- Shared Server
    - Setup de la aplicación Heroku
    - Conexión con base de datos Postgres SQL
    - Webapp en Node.js con Listado, Alta, Baja y Modificación de perfiles de usuarios
- Application Server
    - CMake
    - Conexión con mongoose-cpp a RocksDB
    - Endpoints signup/login/like/dislike
    - Logs
    - Retornar Access Token en Login
- Cliente
    - Pantalla de Login
    - Pantalla de Registro
    - Integración con Appserver mediante Volley
- Documentación

División de tareas
""""""""""""""""""

- Cliente: Gisela
- AppServer: Federico y Nicolás
- SharedServer: Pablo

Trello Board para ver tareas y responsables:
https://trello.com/b/MKvp61Xg/taller2-matchapp 

Links útiles
"""""""""""""

Repositorios
^^^^^^^^^^^^^
- SharedServer: https://github.com/PabloFederico/SharedServer
- ApplicationSever: https://github.com/nicolas-vazquez/tp75521c
- Cliente: https://github.com/gisedaye/taller2android/ 

Aplicación web
^^^^^^^^^^^^^
https://tallerdeprogramacionii-1c2016.herokuapp.com/


Checkpoint 2
----------------------------------------

Changelog
"""""""""""""

- Shared Server
    - Webapp en Node.js con Listado, Alta, Baja y Modificación de perfiles de usuarios
    - Listado de intereses
    - Alta de intereses
    - Docker en Shared server
- Application Server
    - Conexión de App Server con Shared Server
    - Lógica de Matches
    - Unit tests
    - CI-Travis
    - Docker en App Server
- Cliente
    - Pantalla de candidatos
    - Funcionalidad de like/dislike
    - Menú lateral
- Documentación en Sphinx

División de tareas
"""""""""""""
- Cliente: Gisela
- AppServer: Federico y Nicolás
- SharedServer: Pablo y Nicolás

Trello Board para ver tareas y responsables:
https://trello.com/b/MKvp61Xg/taller2-matchapp 

Links útiles
"""""""""""""

Repositorios
^^^^^^^^^^^^^
- SharedServer: https://github.com/PabloFederico/SharedServer
- ApplicationSever: https://github.com/nicolas-vazquez/tp75521c
- Cliente: https://github.com/gisedaye/taller2android/ 

Aplicación web
^^^^^^^^^^^^^
https://tallerdeprogramacionii-1c2016.herokuapp.com/


Checkpoint 3
----------------------------------------

Changelog
"""""""""""""

- Shared Server
    - Webapp en Node.js 
    - Layout responsive con Materialize
    - Alta de intereses en usuarios
    - Alta de imagen en usuario
    - Localización de usuario
    - Endpoints login/interests/candidates/profile
- Application Server
    - Endpoints matches/candidates/messages/message/
    - Unit tests
    - Functional tests
    - Medir code coverage de unit tests
- Cliente
    - Pantalla de candidatos
    - Funcionalidad de like/dislike
    - Menú lateral
    - Pantalla de matches
    - Chat
    - Profile
    
- Documentación en Sphinx

División de tareas
"""""""""""""
- Cliente: Federico
- AppServer:  Federico y Nicolás
- SharedServer: Federico y Nicolás
- Shared server redesign: Gisela
- Tests unitarios y funcionales: Gisela
- Documentación: Gisela y Pablo

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

