# generic-soap-ws

Example for a cached Spring Boot SOAP Webservice.
This APP retrieves Data from a local H2 Database.

This example serves also as a backend for the REST Example:

(https://github.com/eugen-cc/resilient-cached-rest-ws).

## Note
This implementation is based on Java 17 and Spring-Boot 3.x, but it is very similar in other Java / Spring-Boot Versions.

In order to run you can either use the mvn spring-boot-plugin :
```
mvn spring-boot:run
```
or, after building run

```
 java -jar target/dataservice-0.0.1-SNAPSHOT.jar
```
## API
There is a SOAPUI Project included in the project src.
You will find the wsdl at : 
```
http://localhost:8081/webservice/XDataWebservice.wsdl
```

## Configuration
There is a configurable response delay in order to slow down the response for testing purpose.

```webservice.read.timeout.ms``` : ms to wait before processing the request. Omit this setting or set it to zero if you want.




