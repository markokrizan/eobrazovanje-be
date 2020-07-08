# FTN - eobrazovanje

**Project setup: (Using VS code)** 

- Pull github repo:
- Open with VS code
- Search for and install theese extensions:
	- Maven for Java
	- Spring boot dashboard
	- Java extension pack
	- VS code should automatically install maven dependencies - if not go to pom.xml and press ctrl + s - it should then prompt you for the installation
- Run the app using spring-boot-dashboard menu on the left sidebar or go to the main app class - right click - and press run

**Database**
* You can setup a local instance of a MySQL database or install docker engine - [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop) and run > `docker-compose up` in the terminal within the project directory (for now the app service is commeted out - when finished the entire project stack will be run like this)

**Swagger**
- See api documentation at: http://localhost:8080/swagger-ui.html
