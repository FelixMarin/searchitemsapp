
# Comparador de precios de supermercados online

Una aplicación web desarrollada en java, Spring Framework e Hibernate. Consiste en un comparador de precios de supermercados online. Partiendo de los siguientes parámetros de entrada, la aplicación devolverá una estructura en formato json con una lista ordenada de productos de alimentación. La característica principal de la aplicación es la extracción de datos usando la técnica del web scraping.
La aplicación es una API REST, lo que significa que para obtener la informcación habrá que solicitarla mediante una URL.

# Herramientas

- [Ubuntu Server 18.04](https://ubuntu.com/download/server)
- [Eclipse IDE for J2EE 2020](https://www.eclipse.org/ide/)
- [PostgresSQL](https://www.postgresql.org/)
- [OpenJDK 14](https://openjdk.java.net/projects/jdk/)
- [Spring Framework](https://spring.io/)
- [Apache Tomcat 9](http://tomcat.apache.org/)
- [SmartBear SoapUI](https://www.soapui.org/)
- [Oracle VirtualBox](https://www.virtualbox.org/)

# Preparación del Entorno

## Instalación del SGBD  

Se procede a [instalar PosgreSQL](https://www.digitalocean.com/community/tutorials/como-instalar-y-utilizar-postgresql-en-ubuntu-18-04-es) en el sistema operativo. Una vez instalado y configurado el SGBD, se ejecuta el script de la base de datos que se encuentra en **./BBDD/backup_sia_bbdd.sql**. Finalmente, Crear los siguientes **'Login/Grup Roles'** usados por la aplicación: 

| Usuario | Permisos |
| --- | --- |
| pgadmin | administrador |
| sia_select | Lectura |

## Instalación del entorno 
El primer paso consiste en descargar el proyecto de GitHub.  

```bash
~$ sudo apt install git 
~$ git clone https://github.com:/FelixMarin/searchitemsapp.git 
```
A continuacion, importar el proyecto en Eclipse IDE:

```bash
1. 'File > Import'.

En el Import Wizard:

2. Expandir 'Git' => 'Projects from Git' => 'Next'.
3. 'Existing local repository' => 'bash Next'.
4. 'Add' (para navegar a cualquier repositorio local).
5. En 'Wizard for project import' => 'Next'. 
6. 'Import  existing Eclipse project' => 'Next'.
7. 'Select nested projects'.
8. 'Import Projects'.
9. 'Finish'.
```

Una vez importado el proyecto, actualizar las dependencias Maven: 

```bash
1. 'Project Properties' => 'Maven' => 'Maven Update'.
2. 'Project Properties' => 'Run' => 'Maven Clean'.
```

Se crea un el directorio **'/resources/'** en la raiz del sistema. 

![Drivers en './resources/'](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/000007.png)

Este directorio contiene los ficheros **'.properties'**:

| Properties | Descripción |
| --- | --- |
| **db.properties** | contiene los literales y datos de conexión a la base de datos. |
| **flow.properties** | contiene todos los literales de la aplicación. |
| **log4j.properties** | contiene la configuración de la libreria de registros log4j. |

El fichero **flow.properties** contiene tres rutas a tener encueta. Son las rutas al ejecutabme del navegador firefox y los drivers de chrome y gecko. Hay que colocar los drivers en la ruta indicada.

```console
folw.value.firefox.ejecutable.path=/usr/bin/firefox
flow.value.google.driver.path=/usr/local/bin/drivers/chrome/chromedriver 
flow.value.firefox.driver.path=/usr/local/bin/drivers/firefox/geckodriver
```

![Drivers en './resources/flow.properties'](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/000008.png)

A continuación, descargar los drivers de Firefox y Chrome y situarlos en la ruta que aparece a continuación. 

```console
/usr/local/bin/drivers/chrome/chromedriver 
/usr/local/bin/drivers/firefox/geckodriver 
```

El siguiente paso es añadir al fichero **/etc/environmet** las siguientes variables de entorno.  

| Variable de Entorno | Valor |
| --- | --- |
| **PROPERTIES_SIA** | "/resources" | 
| **CATALINA_HOME** | "/[path_to]/apache-tomcat-9" | 
| **JAVA_HOME** | "/[path_to]/java-14-openjdk-amd64" | 
| **JRE_HOME** | "/[path_to]/java-14-openjdk-amd64" |

Se crea un directorio llamado logs en la raíz de sistema para recoger los logs que va escribiendo la aplicación.  

![Directorio '/log4j/'](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/000009.png)

[Instalar el servidor de aplicaciones Apache Tomcat 9.](https://tecnstuff.net/how-to-install-tomcat-9-on-ubuntu-18-04/) y [Vicular Tomcat a Eclipse IDE](https://www.codejava.net/servers/tomcat/how-to-add-tomcat-server-in-eclipse-ide). 
 
Finalmente, una vez preparado el entorno habrá que compilar el proyecto y desplegar la aplicación en el servidor Tomcat.

![Instalación Apache Tomcat](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/tomcat.png)

Para realizar pruebas se acoseja usar la aplicación de escritorio [Postman](https://www.postman.com/downloads/). 

Para acceder a la interfaz gráfica de la aplicación se hará mediante el fichero index.jsp:

```console
http://[url]:[port]/searchitemsapp/index.jsp
```

Formato de la URL con la que se realizará la solicitud al servicio:

```console
http://[url]:[port]/searchitemsapp/search/[país]/[categoría]/[ordenar]/[producto]/[super]
```

Lista de parámetros de la petición:

| Parámetro | Valor |
| --- | --- |
| __país__ | 101 (España). |
| __categoría__ | 101 (Supermercados) |
| __ordenar__ | precio: 1 / volumen: 2 |
| __producto__ | (Arroz, Aceite, sal, ...) |
| __super__ | [101] , [101,103,104] , [ALL] |
 

Ejemplo de uso:

Esta URL devolverá un listado de objetos json con los productos de todos los supermercados ordenados por precio. 

```console
http://[url]:[port]/searchitemsapp/search/101/101/1/arroz/ALL
```

Esta URL devolverá un listado de objetos json con los productos de un supermercado ordenados por volumen.

```console
http://[url]:[port]/searchitemsapp/search/101/101/2/sal/103
```

Ejemplo de respuesta:

```console
{
    "resultado": [
        {
            "identificador": "1",
            "nomProducto": "Arroz categoría extra Carrefour 1 kg.",
            "didEmpresa": "104",
            "nomEmpresa": "CARREFOUR",
            "imagen": "https://static.carrefour.es/hd_280x_/img_pim_food/101424_00_1.jpg",
            "nomUrl": "https://www.carrefour.es/supermercado/arroz-categoria-extra-carrefour-1/
            "precio": "0,78 eur",
            "precioKilo": "0,78 eur/kg"
        },
        {
            "identificador": "2",
            "nomProducto": "Arroz redondo Hacendado",
            "didEmpresa": "101",
            "nomEmpresa": "MERCADONA",
            "imagen": "https://prod-mercadona.imgix.net/images/930c97c9.jpg?fit=crop&h=300&w=300",
            "nomUrl": "null",
            "precio": "0,79",
            "precioKilo": "0,79"
        }
]}
```

#### [Ejemplo de la aplicación en vídeo](https://youtu.be/K_4Wp0Poh2Q)

[![Ejemplo de la aplicación en vídeo](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/portada-video-0.png)](https://youtu.be/K_4Wp0Poh2Q)
 


