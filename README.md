# BACKEND E-LEARNING  API PROJECT
This project is a backend API for an e-learning platform ,built using Springboot , Spring  security and JWT for authentication and authorization.
The application allows user to create an account , login , and access role based secured endpoints.

### FEATURES
* #### User Authentication
     * User can register with their details
     * User can login with their emails and password.
     * Passwords are securely encoded using HS256 Algorithm.

* ### JWT AUTHORIZATION
    * Secure endpoints using JWT token.
    * Supports multiple users [ADMIN , INSTRUCTOR , STUDENT , GUEST]
    * Roles can be assigned during registration.

* ### TECHNOLOGIES USED
    * Java 21
    * Spring Boot 3.x
    * Maven 
    * Spring Security
    * JWT
    * Hibernate
    * MySQL
    * Postman 

* ### Getting Started
    * Java 17 or higher
    * Maven (Build tool)
    * MySQL(or any preferred database)
    * Postman(For testing the API)
  
* ### INSTALLATION
1. #### Clone the Repository
  * git clone https://github.com/sapphire0602/e-learning.git

2. #### Configure your database
 * Update the application.properties file with your database credentials
   >spring.datasource.url=jdbc:mysql://localhost:3306/e-learning_db
   >spring.datasource.username=your username
   >spring.datasource.password=your password
   >spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
3. #### Build the Project
   >mvn clean install

4. #### Run the Project
   > mvn spring-boot:run

* ### SAMPLE API Endpoints
1. Register User
* ENDPOINT:
 > POST api/v1/register


2. Login
* ENDPOINT:
> POST api/v1/user/login
##### Request Body
>{
"email": "user@gmail.com",
"password": "user password",
"firstName": "John",
"lastName": "Doe",
"roles": ["ADMIN" , "STUDENT"]
}

#### Response 
>{
"token": "JWT_TOKEN"
}
> 
3. Get All Users

* ENDPOINT:
> POST api/v1/user/all

