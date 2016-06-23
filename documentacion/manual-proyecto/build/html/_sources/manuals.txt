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
Organización de tareas
============================================
Gestión
----------------------------------------
- Utilización de la herramienta Trello para seguimiento de tareas y manejo de tickets
.. image:: Screenshots/trello.png

https://trello.com/b/MKvp61Xg/taller2-matchapp

- Repositorios taggeados en cada checkpoint
	- Shared Server: https://github.com/PabloFederico/SharedServer
	- App Server: https://github.com/nicolas-vazquez/tp75521c
	- Cliente y Documentación: https://github.com/gisedaye/taller2android


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

============================================
Hipótesis y Supuestos
============================================

- El usuario tendrá conocimientos en la utilización de un Sistema Operativo y contará con un dispositivo móvil con una adecuada conexión para el correcto funcionamiento del aplicativo.
- La documentación del sistema (manuales de usuario, proyecto, administración y programador) será escrita en Español.

============================================
Lecciones Aprendidas
============================================

- Decidimos separar las tareas sin tener en cuenta las tecnologías que manejaba cada integrante y eso nos hizo atrasarnos en los primeros checkpoints. 
- Importancia de llevar un backlog de tareas, a fín de organizar el proyecto y el trabajo en grupo. 
- Estimación de tareas y control de tickets.
- Integración en un solo aplicativo de varias tecnologías.
- Importancia de utilizar Docker para el despliegue de la aplicación de manera de proveer una capa de abstracción.
- Utilizar el tiempo para plantear una solución de calidad tanto en arquitectura como en diseño al comienzo del proyecto es de vital importancia para lograr el éxito del mismo y minimizar los riesgos futuros.


