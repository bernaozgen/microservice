# Rent A Car Project

This is a project created in the microservices architecture. The infrastructure and technologies used in this project are as follows:

> Zipkin,Sleuth,Maven,Docker,Lombok,Grafana,Swagger,Validation,MongoDB,Spring Boot,Spring Web,PostgreSQL,Prometheus,Spring Cloud,API Gateway,Apache Kafka,Spring Data JPA,Docker Compose,Spring Cloud Config,Spring Boot Actuator,Spring Boot Dev Tools,OpenFeign,Eureka Discovery Server/Client,Maven Repository

<img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/be4305cf-5b75-4d53-96ed-489be9a3ab15.jpeg " />

## Config Server
>This server enables centralized management of service configurations from a single location.
* We need to add the Spring Cloud Config Server package to the pom.xml file in order to set up a config server.

<img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/config%20dependency.jpg " />
* At the same time, the application.yml file should be in the following format:

<img src = "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/config.yml.jpg " />

* Our config server application file should be as follows: 
<img src = " https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/config%20application.jpg " />

* To view the project on Docker, you can download it from https://hub.docker.com/repository/docker/bernaozgen/config-server-image by using the command 'docker pull bernaozgen/config-server-image'.

* You can run it as a container on docker with the command docker run -d -p 8888:8888 bernaozgen/config-server-image


## Discovery Server
* This is an open-source project developed by Netflix. The Eureka server is our address book in microservice projects. By keeping all services' communication with each other in one place, as you saw above, we perform load balancing.

* The dependency that we need to use to include Eureka is specified below.
<img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/discoveryEklenti.png " />

* application.yml configuration 
<img src= " https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/discoveryApplication.png " />

* To enable the Eureka server for its operation, you need to add the annotation @EnableEurekaServer to the application startup class with a description.

* Discovery Server Development application.yml https://github.com/bernaozgen/microservice-config-server/blob/main/eurekaserver-dev.yml

*  To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/discovery-server-image by using the command 'docker pull bernaozgen/discovery-server-image'.

* To run, execute 'docker run -d -p 9001:9001 bernaozgen/discovery-server-image' command.

## Apigateway 
* Apigateway is responsible for security and load balancing.
* The following dependencies are required for Apigateway installation:
<img src= " https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/apigateway%20kurulum.png " />
* The dependencies for Zipkin and Prometheus, which were used in the project, are also included here:
<img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/zipkin.png " />
* We need to add "@EnableDiscoveryClient" to the startup file.
* You can access the Apigateway application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/gateway-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/api-gateway-image by using the command 'docker pull bernaozgen/api-gateway-image'.

* To run, execute 'docker run -d -p 9011:9011 bernaozgen/api-gateway-image' command.

## Inventory Service

> The service established a relationship between Model, Car, and Brand information.
* @OneToMany relationship was used to associate Model with Brand.
* @OneToMany relationship was used to associate Car with Model.
* @ManyToOne relationship was used to associate Brand with Model.
* @ManyToOne relationship was used to associate Model with Car.
* JpaRepository was used in the project.
* Request-Response pattern was used. The development was in accordance with Business Rules, Clean Code, and Domain Driven Design approach.
* The project includes techniques such as Exceptions, Mapping, and Result.
* Restful infrastructure was used.
* Global error handling was implemented.
* In this service, asynchronous communication was established with the rental service using Kafka. Asynchronous communication was also established between the filtering service and this service.

* To connect to the Eureka server, we need to add a description "@EnableDiscoveryClient" to the InventoryService application class.

* To use general exception handling methods, we add the "@RestControllerAdvice" annotation to the startup class of InventoryService.

* Kafka configuration is shown below:

  <img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/kafka.png " />

* You can access the inventory service application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/stock-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/inventory-service-image by using the command 'docker pull bernaozgen/inventory-service-image'.

* To run, execute 'docker run -d -p  bernaozgen/inventory-service-image' command.

## Rental Service

>This is the service where the rental process takes place.

* Asynchronous communication was established with the  Inventory Service and Invoice Service using Kafka.
* Synchronous communication was established with the Payment Service using OpenFeign.
* JpaRepository was used in the project.

* Request-Response pattern was used. The development was in accordance with Business Rules, Clean Code, and Domain Driven Design approach.

* The project includes techniques such as Exceptions, Mapping, and Result.

* Restful infrastructure was used.

* Global error handling was implemented.
* To connect to the Eureka server, we need to add a description "@EnableDiscoveryClient" to the Rental Service application class.

* To use general exception handling methods, we add the "@RestControllerAdvice" annotation to the startup class of Rental Service.

* You can access the Rental service application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/rental-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/rental-service-image by using the command 'docker pull bernaozgen/rental-service-image'.

* To run, execute 'docker run -d -p bernaozgen/rental-service-image' command.  

## Payment Service 

> This is the service where the payment process is handled.
* JpaRepository was used in the project.

* Request-Response pattern was used. The development was in accordance with Business Rules, Clean Code, and Domain Driven Design approach.

* The project includes techniques such as Exceptions, Mapping, and Result.

* Restful infrastructure was used.

* Global error handling was implemented.

* To connect to the Eureka server, we need to add a description "@EnableDiscoveryClient" to the Payment Service application class.

* To use general exception handling methods, we add the "@RestControllerAdvice" annotation to the startup class of Payment Service.

* You can access the payment service application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/payment-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/payment-service-image by using the command 'docker pull bernaozgen/payment-service-image'.

* To run, execute 'docker run -d -p bernaozgen/payment-service-image' command.

## Invoice Service
> This is the service where the invoicing process takes place.

* Synchronous communication was established with the Inventory Service using OpenFeign.

* JpaRepository was used in the project.

* Request-Response pattern was used. The development was in accordance with Business Rules, Clean Code, and Domain Driven Design approach.

* The project includes techniques such as Exceptions, Mapping, and Result.

* Restful infrastructure was used.

* Global error handling was implemented.

* To connect to the Eureka server, we need to add a description "@EnableDiscoveryClient" to the Invoice Service application class.

* To use general exception handling methods, we add the "@RestControllerAdvice" annotation to the startup class of Invoice Service.

* You can access the payment service application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/invoice-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/invoice-service-image by using the command 'docker pull bernaozgen/invoice-service-image'.

* To run, execute 'docker run -d -p bernaozgen/invoice-service-image' command.


## Filter Service 

> Araç ile ilgili bilgileri filtrelemek iin kullanıldı.
* Mongorepository kullanıldı.
* İnventory servis ile aralarında asenkron iletişim kurup kafka kullandım.
* Request-Response pattern was used. The development was in accordance with Business Rules, Clean Code, and Domain Driven Design approach.

* The project includes techniques such as Exceptions, Mapping, and Result.

* Restful infrastructure was used.
* To connect to the Eureka server, we need to add a description "@EnableDiscoveryClient" to the Filter Service application class.
* You can access the payment service application.yml file from the following link: https://github.com/bernaozgen/microservice-config-server/blob/main/filter-dev.yml

* To view the project on Docker, you can download it from https://hub.docker.com/r/bernaozgen/filter-service-image by using the command 'docker pull bernaozgen/invoice-service-image'.

* To run, execute 'docker run -d -p bernaozgen/filter-service-image' command.

## Common 
 This is the project that I extracted from my Rent A Car Microservice project and uploaded to the Maven repository. You can go to the link below to get the dependency extension for Common and paste it into your project's pom.xml file.

(https://mvnrepository.com/search?q=io.github.bernaozgen)

What's in the common repository?

- The events I used in the microservice project
- The card information I created for the payment service
- The utilities folder that contains exceptions, mapping, and results

* I kept the common files in this package and used it in my microservice project with a dependency.

* Version 1.6.5 of the "io.github.bernaozgen/common" library was used.

<img src= "https://github.com/bernaozgen/microservice/blob/main/micrservicesImg/common.png " />

