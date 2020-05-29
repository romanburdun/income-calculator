# Income calculator

This microservice was created to to provide information about England's current and previous tax years as well as calculate income for a sole trader trading in England.

##Before running
Create database and user for the application
Set environment variables for the application to use database: DB_URL, DB_USER, DB_PASSWORD.
Set SECRET environment variable for JWT and PROD_HOST/DEV_HOST for your domain name used in production/development e.g. https://www.example.com which is used for setting up http only cookies

## How to run

Set profile (prod or dev) in application.properties
````
mvn spring-boot:run // Run using mvn

````
or

````
mvn package
java -jar income_calculator-0.0.1-SNAPSHOT.jar
````

## After running the microservice
Create an account using following endpoint

````
http://localhost:8080/api/v1/auth/register

````

That will allow you to create, update and delete tax year information.

## Documentation
Full documentation is be available on:

````
http://localhost:8080/swagger-ui.html
````
