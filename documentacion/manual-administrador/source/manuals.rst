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
Instalacion y puesta en marcha
============================================

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
- Debe tambien tener corriendo el Shared Server para el correcto funcionamiento de todos los endpoints

Para correr con docker

 - Buildear contenedor de Docker
    - sudo docker build -t appserver/ubuntu:14.04 .
 - Correr contenedor de Docker
    - sudo docker run -t -i -p 127.0.0.1:8083:8083 appserver/ubuntu:14.04v3 /bin/bash
    - ./appServer
 - Debe tambien tener corriendo el Shared Server para el correcto funcionamiento de todos los end


Cliente
--------------------------------------------

- Setear ip de computadora a 192.168.1.33
- Correr el Appserver y el Shared Server
- Bajar apk de https://drive.google.com/open?id=0B96FtE1h2ukFNHdob042a3ZQU1k desde el celular donde se ejecutara la aplicacion
- Abrir MatchApp desde el icono de la aplicación

