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
- Para ejecutar el servidor mediante Docker:
    - En la consola ejecutar el comando ./install-docker.sh para instalar dependencias.
    - Luego ejecutar el siguiente comando:
        - sudo docker-compose up
    - Para detener la aplicación ejecutar en la consola:
        - sudo docker-compose stop

- Para utilizar la app ingresar a https://tallerdeprogramacionii-1c2016.herokuapp.com/


Application Server
--------------------------------------------

Pasos para ejecutar la aplicación

- En una consola ejecutar el siguiente comando para obtener una copia local del repositorio remoto:
    - git clone ghttps://github.com/nicolas-vazquez/tp75521c.git
- Ejecutar los siguientes comandos en la consola para instalar las dependencias necesarias:
    - cd tp75521c/AppServer
    - sudo ./install.sh
- Buildear aplicación para crear ejecutable:
    - mkdir build
    - cd build
    - cmake ..
    - sudo make
- Ejecutar la aplicación:
    - ./appServer
- El Shared Server debe estar en ejecución para el correcto funcionamiento del servidor.

Para ejecutar el servidor mediante Docker:

 - Buildear contenedor de Docker:
    - sudo docker build -t appserver/ubuntu:14.04 .
 - Correr contenedor de Docker:
    - sudo docker run -t -i -p 127.0.0.1:8083:8083 appserver/ubuntu:14.04v3 /bin/bash
    - ./appServer
- El Shared Server debe estar en ejecución para el correcto funcionamiento del servidor.


Cliente
--------------------------------------------

- Setear la IP de la PC a 192.168.1.33
- Ejecutar el Appserver y el Shared Server
- Bajar apk de https://drive.google.com/open?id=0B96FtE1h2ukFNHdob042a3ZQU1k desde el celular donde se ejecutará la aplicación
- Abrir MatchApp desde el icono de la aplicación

