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








