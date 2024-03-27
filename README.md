                                                 SULUHU Application README

Introduction

Welcome to the README file for our Suluhu application. This document serves as a guide to understand the structure, setup, and usage of the application. Below you'll find information on prerequisites, installation steps, configuration, usage, and other relevant details.

Prerequisites

Before getting started with the application, ensure you have the following prerequisites installed:

. Java Development Kit (JDK) 8 or later

. Java version 17 or later

. Maven (for building and managing dependencies)

. An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse (optional but recommended)

. node version 16 or later

. npm version 10 or later

. vite version 5 or later

. PostgrsSQL database installed


Installation

. To install and run the application, follow these steps:

. Clone the repository from https://github.com/Qarani-m/Hackathon-Stuff-Grp-2.git

. Open the project in your preferred IDE(prefererbly Intellij or Eclipse).

. Build the project using Maven: mvn clean install.

. Run the application using the Spring Boot Maven plugin: mvn spring-boot:run.


Configuration

. The application configuration can be found in the application.properties file located in the src/main/resources directory. Customize the configuration properties such as server port, database connection details, etc., as per your requirements.

Usage

. Once the application is up and running, you can access the APIs through your preferred REST client (e.g., Postman). The endpoints and their functionalities are documented in the API documentation.

. The user interface can be accessed by moving to the FrontEnd folder, installing the packages by running "npm install", then run "npm run dev" to spin up the frontend server

1# System Overview

The system is a Spring Boot application built to handle user management functionalities and data retrieval from an external database. It provides RESTful endpoints for user login, registration, and role management. Additionally, it includes scheduled tasks to fetch data from a remote database periodically.


#### Components:

1. *User Management*:
   
   - Provides endpoints for user login (
   - /api/users/login
   - ).
     
   - Supports changing user roles (
   - /api/users/changeUserRole
   - ).
  
  
2. *Logging*:

   - Offers endpoints to retrieve logs stored in a file (
   - /api/users/logs,
   - /api/users/logs/level/{logLevel},
   - /api/users/logs/type/{logType},
   - /api/users/logs/date/{date},
   - /api/users/logs/datetime/{logType}
   -   ).
     


3. **Data Fetching**:
   - Fetches data from a remote database at regular intervals using scheduled tasks.

- `@Scheduled(fixedDelay = 2000)`: This annotation configures the method to be invoked at regular intervals, with a fixed delay of 2000 milliseconds (or 2 seconds) between the completion of the previous invocation and the start of the next one.

- `public void fetchDataFromDatabase() { ... }`: This method is responsible for fetching data from a remote database. It is triggered periodically by the scheduler.

- `getRequest()`: This method performs an HTTP GET request to a specified URL, presumably a database endpoint. It includes basic authentication credentials in the request header to authenticate with the database.

- Inside the `getRequest()` method:
  - Authentication credentials (username and password) are encoded using Base64 encoding and added to the request header.
  - An HTTP GET request is created using `HttpURLConnection` to the specified URL.
  - The response code is checked, and if it indicates success (`HTTP_OK`), the response data is read and processed.
  - The response, typically in JSON format, is parsed using Gson library into a `JsonObject`.
  - The method iterates over the JSON object to extract and print specific values, such as "id", "key", and "rev" for each row.

### Modifications:


- The JSON response is mapped to the `EchiData` table, which  consists of fields like `form_id`, `chu_id`, `element`, `value`, `date`, and `person`.
- Additionally, there's an `OrgUnit` table with fields such as `chu_id`, `name`, `parent`, `ward`, `sub_county`, and `nationality`.
- Another table named `DataElement` might have fields like `data_uid`, `name`, and `description`.

- The `id` field from the JSON response corresponds to the `form_id` attribute in the `EchiData` class.
- The `key` field corresponds to some other attribute, etc.

#### Dependencies:
- **Spring Boot**: Provides the core framework for building the application.
- **Spring Web Starter**: Offers necessary dependencies to build web applications with Spring MVC.
- **Spring Data JPA**: Simplifies the implementation of JPA-based data access layers.
- **Spring Security Crypto**: Provides cryptographic functionality for securing user data.
- **Gson**: Library for JSON serialization and deserialization.
- **PostgreSQL Driver**: Enables communication with PostgreSQL database.
- **Lombok**: Library for reducing boilerplate code in Java.
- **Spring Boot Starter Test**: Provides dependencies for testing Spring Boot applications.
  
     
  

  #### Dependencies:
  
- * Initialise new project with * : https://start.spring.io/
     
- *Spring Boot*: Provides the core framework for building the application.
  
- *Spring Web Starter*: Offers necessary dependencies to build web applications with Spring MVC.
  
- *Spring Data JPA*: Simplifies the implementation of JPA-based data access layers.
  
- *Spring Security Crypto*: Provides cryptographic functionality for securing user data.

- *Spring Security *: Provide authentication and authorization for users.
  
- *Gson*: Library for JSON serialization and deserialization.
  
- *PostgreSQL Driver*: Enables communication with PostgreSQL database.
  
- *Lombok*: Library for reducing boilerplate code in Java.
  
- *Spring Boot Starter Test*: Provides dependencies for testing Spring Boot applications.


   #### Technologies:
  
- *Java*: Programming language used for backend development.

- *Spring Boot*: Framework for building robust and scalable applications.
  
- *Maven*: Build automation tool for managing project dependencies and builds.
  
- *PostgreSQL*: Database management system for storing application data.
  
- *RESTful API*: Architecture style for designing networked applications.

- *Javascript*: Programming language used for frontend development.
  
- *React*: Frontend framework for creating components.
  
- *Vite*: Build tool that aims to provide a faster and leaner development experience for modern web projects.
 

Contributing

. We welcome contributions from the community. If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request on GitHub.


Contact

. For any inquiries or assistance regarding the application, you can contact SuluhuAdmin at suluhuadmin@admin.


  
  

  . Overall, the system provides a comprehensive solution for user management and data retrieval tasks, leveraging Spring Boot's powerful features and various libraries for efficient development.










