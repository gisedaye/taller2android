*********
MatchApp

Manual de Administrador
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
Instalación y puesta en marcha
============================================

Shared Server
--------------------------------------------
- Descargar una copia local del repositorio remoto:
	- ``git clone https://github.com/PabloFederico/SharedServer.git``

- Desde el directorio raíz de la working copy ejecutar ``npm install`` en una consola para descargar las dependencias necesarias
- Hay dos formas de ejecutar la aplicación:
	- Docker:
	    - En la consola ejecutar el comando ``./install-docker.sh`` para instalar el cliente de Docker.
	    - Luego ejecutar los siguientes comandos:
	        - sudo docker-compose up
	    - Para detener la aplicación ejecutar en la consola:
	        - sudo docker-compose stop

	- Otra forma de ejecutar la aplicación es mediante el comando ``node app.js`` desde el directorio raíz de la working copy

- La aplicación también se encuentra disponible a través de la siguiente URL: ``https://tallerdeprogramacionii-1c2016.herokuapp.com/``


Application Server
--------------------------------------------
- Descargar una copia local del repositorio remoto:
	- ``git clone https://github.com/nicolas-vazquez/tp75521c.git``

- Hay dos formas de ejecutar la aplicación:
	- Docker:
		- En una terminal ejecutar el comando ``sudo docker build -t appserver/ubuntu:14.04 .``
	    - Luego para ejecutar el contenedor: ``sudo docker run -t -i -p 127.0.0.1:8083:8083 appserver/ubuntu:14.04v3 /bin/bash``
		- Desde el prompt del contenedor para ejecutar la aplicación se deberá ingresar el siguiente comando: ``./appServer``

	- Otra forma de ejecutar la aplicación es la siguiente:
		- Desde el root del proyecto ejecutar los siguientes comandos:
		    - cd tp75521c/AppServer
		    - sudo ./install.sh
		    - ./appServer



Cliente
--------------------------------------------

- Para ejecutar la aplicación cliente es necesario configurar la IP de la máquina en donde se encuentre corriendo el Application Server. Para esto, se debe cambiar la linea 25 de la clase MatchAPI que contiene la variable APP_SERVER_IP por la IP correspondiente.
- Son necesarias las siguientes dependencias para compilar la aplicación:
	- buildToolsVersion "23.0.1"
 	- Android Support Repository
	- Android Support Library
	- Google Play Services
	- Android SDK Platform 21 o superior
Para generar la APK, desde el directorio MatchApp se pueden ejecutar algunas de estas dos opciones:
	- ``./gradlew clean assembleDebug``, lo que genera el output en la carpeta MatchApp/app/build/outputs/apk
	- En el caso de tener conectado un device se puede también generar e instalar la APK haciendo lo siguiente: ``./gradlew clean installDebug``

Se debe tener en cuenta para el correcto funcionamiento de la aplicación que el location de el emulador o device se encuentre correctamente seteado. Es importante destacar que para la generación de candidatos la locatión debe ser cercana a la de los usuarios almacenados en los del Shared Server o estar dentro del radio configurado en las settings de la aplicación.



