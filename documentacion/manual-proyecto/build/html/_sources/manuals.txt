*********
MatchApp

Manual de proyecto
*********
**Grupo 10**

**Ayudante: Christian Calonico**

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
Organizacion de tareas
============================================
Gestion
----------------------------------------
- Utilización de la herramienta Trello para seguimiento de tareas y manejo de tickets
.. image:: Screenshots/trello.png

https://trello.com/b/MKvp61Xg/taller2-matchapp

- Respositorios taggeados en cada checkpoint
	- Shared Server: https://github.com/PabloFederico/SharedServer
	- App Server: https://github.com/nicolas-vazquez/tp75521c
	- Cliente y Documentación: https://github.com/gisedaye/taller2android

Division
----------------------------------------
Primeros 2 checkpoints

- AppServer: Nicolas y Federico
- Shared Server: Pablo y Nicolas
- Cliente: Gisela

Ultimo Checkpoint

- Shared Server: Nicolas y Federico y Pablo
- Layout responsive: Gisela
- Cliente: Federico
- App Server: Nicolas
- Tests unitarios y funcionales: Gisela
- Documentacion: Gisela y Pablo

============================================
Entregas
============================================
Checkpoint 1
----------------------------------------

Changelog
"""""""""
- Shared Server
    - Setup de la aplicación Heroku
    - Conexión con Postgres
    - Webapp en node.js con listado y Alta, baja, vista de usuarios
- Application Server
    - CMake
    - Conexión con mongoose-cpp a RocksDB
    - Endpoints signup/login/like/dislike
    - Logs
    - Retornar access token encriptado en login
- Cliente
    - Pantalla de Login
    - Pantalla de Registro
    - Integración con Appserver mediante Volley
- Documentacion en Google Docs

Division de tareas
""""""""""""""""""

- Cliente: Gisela
- AppServer: Federico y Nicolas
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
https://sharedserver.herokuapp.com/


Checkpoint 2
----------------------------------------

Changelog
"""""""""""""

- Shared Server
    - Webapp en node.js con listado y Alta, baja, vista de usuarios, edit de usuarios
    - Listado de intereses
    - Alta de intereses en categorias.
    - Docker en Shared server
- Application Server
    - Conexion appServer con SharedServer
    - Logica de Matches
    - Unit tests
    - Ci-travis
    - Docker
- Cliente
    - Pantalla de candidatos
    - Funcionalidad de like/dislike
    - Menu lateral
- Documentacion en Sphinx

Division de tareas
"""""""""""""
- Cliente: Gisela
- AppServer: Federico y Nicolas
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


Checkpoint 3
----------------------------------------

Changelog
"""""""""""""

- Shared Server
    - Webapp en node.js 
    - Layout responsive con materialize
    - Alta de intereses en usuarios
    - Alta de imagen en usuario
    - Localizacion de usuario
    - Endpoints login/interests/candidates/profile
- Application Server
    - Endpoints matches/candidates/messages/message/
    - Unit tests
    - Ci-travis
    - Functional tests
    - Medir code coverage de unit tests
    - Docker
- Cliente
    - Pantalla de candidatos
    - Funcionalidad de like/dislike
    - Menu lateral
    - Pantalla de matches
    - Chat
    - Profile
    
- Documentacion en Sphinx

Division de tareas
"""""""""""""
- Cliente: Federico
- AppServer:  Nicolas
- SharedServer: Federico y Nicolas y Pablo
- Shared server redesign: Gisela
- Tests unitarios y funcionales: Gisela
- Documentacion: Gisela y Pablo

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

============================================
Hipótesis y Supuestos
============================================

- El usuario tendrá conocimientos en la utilización de algun sistema Operativo, uso de Internet y contará con un dispositivo mobile con conexión para el correcto funcionamiento del aplicativo.
- La documentación del sistema (manuales de usuario, proyecto, administración y programador) será escrita en Español.
- La interfaz de usuario, tanto en el dispositivo mobile y la Web, será en Español.
- EL usuario final tendrá conocimientos en idioma Español.

============================================
Lecciones Aprendidas
============================================

- Decidimos separar las tareas sin tener en cuenta las tecnologias en las que trabajamos y eso nos hizo atrasarnos en los primeros checkpoints. 
- Importancia de llevar un backlog de Tareas, a fín de organizar el proyecto y el trabajo en grupo. 
- Estimación de tareas. Control de Tickets.
- Integración en un solo aplicativo de varias tecnologías.
- Importancia de utilizar Docker para el despliegue de la aplicación para proveer una capa de abstracción.
- Utilizar el tiempo para pensar una buena solución, arquitectura, desglose de tareas, etc. al comienzo del proyecto es de vital importancia para lograr el éxito del mismo y limitar los cambios en el mismo. 


