# Usage

### mav_1 - Tomcat + Maven

appena scaricato l'esempio

1. cd ~/mav_1/servlet_one
2. mvn build
3. cp -r ~/mav_1/servlet_one/target/servlet_one ~/apache-tomcat-10.0.27/webapps
4. fate partire tomcat
5. sul browser: localhost:8080/servlet_one/mvnGreet

### mav_2 - Jetty + Maven

1. cd ~/mav_2/jetty_servlet
2. mvn build
3. mvn jetty:run
4. nel browser: localhost:8080/test

**WARNING**: accertatevi che Tomcat o altri server non stiano ascoltando sulla porta 8080