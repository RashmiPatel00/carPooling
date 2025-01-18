# carPooling
Carpooling Manager is a demo project built using Spring Boot to manage carpooling services. The application provides functionalities for users to create, manage, and join carpools, ensuring an efficient and user-friendly carpooling experience.

Features
1. User Management

    User Registration: Allows users to register with a valid email and password.
    User Authentication: Users can log in with their credentials.
    Role-based Access: Users can have different roles such as ADMIN, PASSENGER, or DRIVER to access role-specific content.

2. Carpool Management

    Schedule a Ride: Users can schedule a ride, which is stored in the database with a Pending status.
    View Rides: Users can view their scheduled rides.
    Ride Status: The status of rides is tracked (e.g., Pending, Confirmed, Completed).

3. Contact Management

    Add Contacts: Users can add contacts to their account. Each contact is associated with the logged-in user.
    Contact List: Users can manage their contacts from their dashboard.

4. Custom User Dashboard

    The application provides a personalized dashboard where users can view their scheduled rides and contacts.

5. Error Handling and Messaging

    Provides session-based feedback messages for success or error events (e.g., contact added successfully, ride scheduled, etc.).

Prerequisites

Before you begin, ensure you have met the following requirements:

    Java 17 or higher: The application is built using Java 17.
    Maven: Used for project build and dependency management.
    MySQL: A MySQL database is required to store user and ride data.

Installation
1. Clone the repository

git clone https://github.com/your-repo/carpooling-manager.git
cd carpooling-manager

2. Configure the database

In the src/main/resources/application.properties file, update the database connection details:

spring.datasource.url=jdbc:mysql://localhost:3306/smartcontact00
spring.datasource.username=root
spring.datasource.password=******
spring.jpa.hibernate.ddl-auto=update

3. Build the project

Ensure that Maven is installed, then build the project using:

mvn clean install

4. Run the application

To run the application, execute:

mvn spring-boot:run

Visit http://localhost:8080 to access the application.


Folder Structure
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── smartcontactmanager/
│   │           ├── controller/      # Controllers (e.g., RideController, UserController)
│   │           ├── entities/        # Entities (e.g., User, Ride, Contact)
│   │           ├── dao/             # Repositories (e.g., UserRepository, RideRepository)
│   │           ├── helper/          # Helper classes (e.g., Message class)
│   │           └── config/          # Security and application config
│   └── resources/
│       ├── static/                   # Static files (e.g., images, CSS)
│       ├── templates/                 # Thymeleaf templates (e.g., add_contact_form.html)
│       └── application.properties    # Application configuration
└── pom.xml                           # Maven configuration

Usage
1. User Registration and Login

    Navigate to /signup to register a new account.
    Use /signin to log in with your credentials.
    After logging in, you will be redirected to your personalized dashboard.

2. Schedule a Ride

    After logging in, navigate to /user/index for your dashboard.
    Schedule a new ride by navigating to /schedule (you need to be a registered passenger or driver).

3. View Rides

    You can view your scheduled rides at /user/viewRides.

4. Add a Contact

    From your dashboard (/user/index), navigate to /user/add-contact to add a new contact to your account.

  

Controllers Overview
1. HomeController

    Routes: /, /about, /signup, /signin
    Handles general pages such as the homepage, about page, and user authentication.

2. RideController

    Routes: /schedule, /user/viewRides
    Handles scheduling rides and viewing user-specific rides.

3. UserController

    Routes: /user/index, /user/add-contact, /user/process-contact
    Manages the user dashboard and contact management functionality.

4. DriverController

    Route: /driverPage
    Displays a page specifically for drivers.


   Security Overview

    Spring Security is used for user authentication and role-based authorization.
    Roles: ADMIN, PASSENGER, and DRIVER are available for different user types.
    BCryptPasswordEncoder is used to securely encode passwords before saving them in the database.
    Custom login page: /signin and custom registration form: /signup.

  

Dependencies

    Spring Boot: Main framework for the application.
    Spring Security: Handles authentication and authorization.
    Spring Data JPA: Manages interactions with the database.
    MySQL Connector: Allows integration with the MySQL database.
    Thymeleaf: For rendering dynamic HTML pages.
    BCryptPasswordEncoder: For secure password hashing.
    Maven: For dependency management.


Troubleshooting

    Database Connection Issues:
        Ensure that your MySQL server is running and the database credentials are correct.
        Check if the database schema is created automatically (due to spring.jpa.hibernate.ddl-auto=update).

    Common Errors:
        If you encounter "User not found" errors, ensure the correct authentication mechanism is in place and that the user exists in the database.

        License

This project is licensed under the MIT License.


Author

Your Name
Email:elizabethrashmi044@gmail.com
GitHub:https://github.com/RashmiPatel00/carPooling
