# carPooling
Carpooling Manager is a demo project built using Spring Boot to manage carpooling services. The application provides functionalities for users to create, manage, and join carpools, ensuring an efficient and user-friendly carpooling experience.
Features

    User Management: User authentication and authorization using Spring Security.
    Carpool Management: CRUD operations for carpools.
    Validation: Input validation using Hibernate Validator and Validation API.
    Frontend Integration: Dynamic web pages rendered using Thymeleaf.
    Database Support: Persistent data storage with MySQL.
    Development Tools: Spring Boot DevTools for live reloading.

Technologies Used

    Java 17
    Spring Boot 3.4.0
        Spring Boot Starter Web
        Spring Boot Starter Data JPA
        Spring Boot Starter Thymeleaf
        Spring Boot Starter Security
    MySQL for database
    Hibernate Validator and Validation API for data validation
    Maven for dependency management
    Thymeleaf for frontend rendering

Prerequisites

    Java 17 or later
    Maven installed
    MySQL Server running

Getting Started
Clone the Repository

git clone https://github.com/your-repo/carpooling-manager.git
cd carpooling-manager

Configure the Database

MySQL credentials in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/smartcontact00
spring.datasource.username=root
spring.datasource.password=******
spring.jpa.hibernate.ddl-auto=update

Build and Run

    Build the project:

mvn clean install

Run the application:

    mvn spring-boot:run

Access the Application

Open your browser and go to:
http://localhost:8080
Dependencies

The following dependencies are used in the project:

    Spring Boot Starter Web: To build RESTful web services.
    Spring Boot Starter Data JPA: For database access and ORM.
    Spring Boot Starter Thymeleaf: To render dynamic views.
    Spring Boot Starter Security: For user authentication and authorization.
    Hibernate Validator: For input validation.
    Validation API: To define validation constraints.
    MySQL Connector: For connecting to the MySQL database.
    Spring Boot DevTools: For development utilities.
    Spring Boot Starter Test: For writing and running tests.

License

This project is licensed under the MIT License.
Author

Your Name
Email: elizabethrashmi044@gmail.com
GitHub:https://github.com/RashmiPatel00/carPooling
