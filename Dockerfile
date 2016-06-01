##########################################
## Dockerfile 				##
##########################################

FROM ubuntu:14.04
MAINTAINER gisedaye gisedaye
COPY ./AppServer /home
RUN cd /home/AppServer && chmod 777 install.sh && ./install.sh -docker
WORKDIR /home/AppServer/build
CMD ["bash"]

