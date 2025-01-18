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


CONTROLLER

CustomerUserDetails

package com.smartcontactmanager.smartcontactmanager.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartcontactmanager.smartcontactmanager.Entities.User;


public class CustomUserdetail implements UserDetails {
    private User user;

    public CustomUserdetail(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ensure the role is not null or empty
        if (user.getRole() == null) {
            return Collections.emptyList();
        }
        
        // Prefix the role with "ROLE_" before passing to SimpleGrantedAuthority
        String rolePrefix = "ROLE_";
        String role = rolePrefix + user.getRole().name();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);

        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();  // Return the actual enabled status from the User entity
    }
}

myconfig.java

package com.smartcontactmanager.smartcontactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class myconfig {

    // Provide your UserDetailsService implementation
    @Bean
    public UserDetailsService userDetailsService() {
        return new USerDetailsServiceImpl(); // Ensure this implementation exists
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/signin", "/signup", "/home").permitAll() // Public pages
                .requestMatchers("/user/**").authenticated() // Protected user routes
                .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-specific pages
                .requestMatchers("/passengerPage").hasRole("PASSENGER") // Passenger-specific pages
                .requestMatchers("/driverPage").hasRole("DRIVER") // Driver-specific pages
                .anyRequest().authenticated() // All other requests must be authenticated
            )
            .formLogin(form -> form
                .permitAll()
                .loginPage("/signin")
                .loginProcessingUrl("/dologin")
                .successHandler(successHandler()) // Use the custom success handler
                .failureUrl("/signin?error=true")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/signin?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for development (enable for production)
        
        return http.build();
    }

    // Custom AuthenticationSuccessHandler to handle redirection based on role
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().toString();
            if (role.contains("ROLE_PASSENGER")) {
                response.sendRedirect("/user/index"); // Redirect to passenger page
            } else if (role.contains("ROLE_DRIVER")) {
                response.sendRedirect("/driverPage"); // Redirect to driver page
            } else {
                response.sendRedirect("/"); // Default page for other roles
            }
        };
    }
}

USerDetailsServiceImpl
package com.smartcontactmanager.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;

public class USerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetching user from the database
        User user = userRepository.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new CustomUserdetail(user); // Returning custom UserDetails implementation
    }
}

Controller

DriverController

package com.smartcontactmanager.smartcontactmanager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DriverController {

    // Handle driver page
    @GetMapping("/driverPage")
    public String driverPage() {
        return "driver/driverPage"; 
    }
}

HomeController
package com.smartcontactmanager.smartcontactmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.smartcontactmanager.Entities.Role;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    // Home page
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }

    // About page
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About-Smart Contact Manager");
        return "about";
    }

    // Signup page
    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup-Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    // Registration handler
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result1,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                               @RequestParam(value = "role", required = true) String role, // Required role parameter
                               Model model,
                               HttpSession session) {

        try {
            // Agreement validation
            if (!agreement) {
                throw new Exception("You have not agreed to the terms and conditions.");
            }

            // Validation result check
            if (result1.hasErrors()) {
                model.addAttribute("user", user);
                return "signup";
            }

            // Validate the role
            try {
                Role userRole = Role.valueOf(role);  // Convert string role to Role enum
                user.setRole(userRole);
            } catch (IllegalArgumentException e) {
                session.setAttribute("message", new Message("Invalid role selected", "alert-danger"));
                model.addAttribute("user", user);
                return "signup";
            }

            // Set default user properties
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password

            // Save the user to the database
            userRepository.save(user);

            // Clear the user form
            model.addAttribute("user", new User());

            // Set success message in session
            session.setAttribute("message", new Message("Successfully registered", "alert-success"));
            return "signup"; // Redirect back to signup page with success message

        } catch (Exception e) {
            // Exception handling
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }

    // Handler for custom Login
    @GetMapping("/signin")
    public String customLogin(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }
}
RideController

package com.smartcontactmanager.smartcontactmanager.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smartcontactmanager.smartcontactmanager.Entities.Ride;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.dao.RideRepository;
import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;

@Controller
public class RideController {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/schedule")
    public String scheduleRide(@ModelAttribute Ride ride, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Get the logged-in user's email
    
        User user = userRepository.getUserByUserName(userEmail);
    
        if (user != null) {
            ride.setUser(user); // Set the user for the ride
            ride.setStatus("Pending"); // Set the status to Pending
            rideRepository.save(ride); // Save the ride to the database
    
            // Redirect to the ride details page with the ride's ID
            return "normal/success"; // Redirect to view rides after booking
        } else {
            model.addAttribute("error", "User not found");
            return "signup"; // Error page if user is not found
        }
    }
    
@GetMapping("/user/viewRides")
public String viewUserRides(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName(); // Get logged-in user's email

    User user = userRepository.getUserByUserName(userEmail);

    if (user != null) {
        // Fetch rides for the logged-in user only
        List<Ride> userRides = rideRepository.findByUser(user); 
        model.addAttribute("rides", userRides);
        return "normal/view_ride"; // Points to templates/normal/view_ride.html
    } else {
        model.addAttribute("error", "User not found");
        return "error"; // Redirect to an error page if user is not found
    }
}


}
UserController

package com.smartcontactmanager.smartcontactmanager.Controller;

// import java.io.File;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.smartcontactmanager.Entities.Contact;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
import com.smartcontactmanager.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.smartcontactmanager.dao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // This will run all the time along with user
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("UserName" + userName);

        // GET the user Details using User NAME(EMAIL)
        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER" + user);
        model.addAttribute("user", user);

    }

    // DAshbord Home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {

        return "/normal/user_dashboard";
    }

    // open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // Processing and adding contact form
   @PostMapping("/process-contact")
public String processContact(@ModelAttribute Contact contact,
                        
                             Principal principal,HttpSession session) {
    try {
        String name = principal.getName();
        User user = this.userRepository.getUserByUserName(name);

        // Handle file upload
        // if (file.isEmpty()) {
        //     contact.setImage("default.png"); // Fallback image
        // } else {
        //     String filename = file.getOriginalFilename();
        //     contact.setImage(filename);

        //     File saveFile = new ClassPathResource("static/image").getFile();
        //     Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + filename);
        //     Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        // }

        // Set user relationship
        contact.setUser(user);
        user.getContacts().add(contact);

        // Save the contact
        this.userRepository.save(user);

        System.out.println("DATA:" + contact);
        System.out.println("Added to database");

        //message successfully
        session.setAttribute("messsage",new Message("Your Contact is Added!! Add more", "success"));
         

    } catch (Exception e) {
        e.printStackTrace();
        //error message
        session.setAttribute("messsage",new Message("Something went wrong...!! TRY AGAIN!!", "danger"));
        return "normal/add_contact_form";
    }
    return "normal/add_contact_form";
}


}

dao

RideRespository

package com.smartcontactmanager.smartcontactmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.smartcontactmanager.Entities.Ride;
import com.smartcontactmanager.smartcontactmanager.Entities.User;
@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByUser(User user);
}

UserRepository

package com.smartcontactmanager.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.smartcontactmanager.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByUserName(@Param("email") String email);
}

Entities

Contact

package com.smartcontactmanager.smartcontactmanager.Entities;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;

    private String name;  

    private String secondName;
    private String work;
    private String email;
    private String phone;
    private String description;
    private String image;

    @ManyToOne
    private User user;

    // Getters and Setters
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getters and setters for image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Contact [cid=" + cid + ", name=" + name + ", secondName=" + secondName + 
               ", work=" + work + ", email=" + email + ", phone=" + phone + 
               ", description=" + description + ", image=" + image + "]";
    }
    
}

Ride

package com.smartcontactmanager.smartcontactmanager.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromLocation;
    private String toLocation;
    private LocalDate date;
    private LocalTime time;

    private String status = "Pending"; // Default status

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

User

package com.smartcontactmanager.smartcontactmanager.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name field is required")
    @Size(min = 2, max = 20, message = "2-20 characters are allowed")
    private String name;

    @Column(unique = true)
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email")
    private String email;

    private String password;

    @Enumerated(EnumType.STRING) // Ensures role is stored as string in the database
    private Role role;

    private boolean enabled;

    private String imageUrl;

    @Column(length = 500)
    private String about;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();

    // Default constructor
    public User() {
        super();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
                + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts + "]";
    }
}
Helper
Message

package com.smartcontactmanager.smartcontactmanager.Helper;

public class Message {
    private String content;
    private String type;
    public Message(String content, String type) {
        this.content = content;
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}




Email:elizabethrashmi044@gmail.com
GitHub:https://github.com/RashmiPatel00/carPooling
