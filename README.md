# Proyecto Cliente-Servidor en Java

Este es un proyecto de ejemplo de una aplicación cliente-servidor en Java que se comunica a través de sockets. El cliente envía y recibe mensajes del servidor, que los procesa y responde.

## Descripción

Este proyecto implementa una arquitectura cliente-servidor en Java utilizando sockets. Cada cliente se conecta al servidor y puede enviar mensajes. Además, de ver mensajes de otros clientes. 

## Requisitos

Para ejecutar este proyecto, se precisa de la instalación de los siguientes programas en el dispositivo que lo vaya a ejecutar:

- JDK 21 o superior: El proyecto está desarrollado en Java.

## Estructura del Proyecto

-/src/Cliente/Cliente.java : Conexión con el servidor a través de los sockets.

-/src/Server/Servidor.java : Clase principal del servidor - gestiona la conexión de múltiples clientes a través de hilos.

-src/Server/OrganizacionCliente : Representa a un cliente conectado al servidor - Se encarga de recibir y enviar mensajes, así como gestionar su desconexión.

-/src/Vista/VistaCliente : Interfaz gráfica del chat - desarrollada mediante Java Swing.

## Instrucciones de Uso

El primer paso consiste en la clonación del repositorio en tu máquina local utilizando Git:

Instrucción de clonación: git clone https://github.com/ddeiv021/WhatsappJava.git


El siguiente paso es compilar el proyecto desde la terminal. Mi proyecto está desarrollado para funcionar de forma óptima en CMD. 

Para compilar el proyecto es necesario estar dentro de la carpeta src y ejecutar el siguiente comando: 
javac -d . Server/Servidor.java Server/OrganizacionCliente.java Cliente/Cliente.java Vista/VistaCliente.java

Este comando compila el proyecto.


Para ejecutar el servidor es necesario ejecutar el siguiente comando dentro de la carpeta src:
java Server.Servidor

Este comando ejecutará el servidor, que se quedará a la escucha de conexiones con clientes. El servidor debe estar activo para el funcionamiento de la aplicación.
Para entrar en carpetas desde la terminal en Windows, se puede utilizar: (cd + carpeta que queramos entrar).


Para poder ejecutar un cliente, deberemos abrir otro terminal y en la ruta src del proyecto ejecutamos el siguiente comando:
java Vista.VistaCliente

Esto ejecutará un cliente y se abrirá una ventana solicitando nombre de usuario. Tras introducirlo, se abrirá el chat.
Si precisásemos de más clientes, deberemos repetir el proceso de abrir ventana de terminal ejecutar la vista e introducir un nombre para nuestro usuario. Cada ventana de terminal equivale a un nuevo cliente.

Para desconectar al cliente, es suficiente con pinchar en "Cerrar Sesión" o con cerrar la ventana.

Una vez que el servidor se esté ejecutando y haya varios clientes, se podrán enviar mensajes desde el cliente y el servidor procesará y responderá esos mensajes.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo LICENSE para más detalles.