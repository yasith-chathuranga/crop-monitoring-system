# Crop-Monitoring-System-API

## Introduction
This API is developed for Green Shadow (Pvt) Ltd., a company specializing in root crops and cereals. As the company expands to large-scale production, this system helps manage crops and assets efficiently. The API supports real-time tracking of crop data and field conditions, enabling data-driven decision-making for optimized farming operations.

## Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Data**
- **Spring Web MVC**
- **Spring Validation**
- **Spring Security**
- **Hibernate ORM**
- **Model Mapper**
- **MySQL**
- **Lombok**
- **Jackson**
- **JWT**

## Features
- **Staff Management (Create, Read, Update, Delete)**
- **Field Management (Create, Read, Update, Delete)**
- **Crop Management (Create, Read, Update, Delete)**
- **Equipment Management (Create, Read, Update, Delete)**
- **Monitoring Log Management (Create, Read, Update, Delete)**
- **Vehicle Management (Create, Read, Update, Delete)**
- **User Management (Authenticates)**
- **Exception Handling and Validation using Hibernate Validator**
- **JSON Response formatting**

## Validation

Validation is implemented using Hibernate Validator annotations in the DTO classes to ensure data integrity and correctness.

## Logging

Logging is configured using Logback. Logs are written to both the console and a file.

## Frontend Repository Link   
      https://github.com/yasith-chathuranga/crop-monitoring-system-frontend.git

## Postman Documentation

For detailed API documentation and testing, please refer to the [Postman API Documentation](https://documenter.getpostman.com/view/37565373/2sAYBaAVdi).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Getting Started

### Prerequisites
To run this project, ensure you have the following installed:
- **Java Development Kit (JDK) 17 or higher**
- **IDE (Integrated Development Environment)**
- **MySQL**
- **Maven**

### Running the Application
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yasith-chathuranga/crop-monitoring-system.git

3. Update the MySQL database configuration in `application-dev.properties`:
    ```java
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/green_shadow?createDatabaseIfNotExist=true
    spring.datasource.username=your_username
    spring.datasource.password=your_password


##
<div align="center">
<a href="https://github.com/yasith-chathuranga" target="_blank"><img src = "https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a>
<a href="https://git-scm.com/" target="_blank"><img src = "https://img.shields.io/badge/Git-100000?style=for-the-badge&logo=git&logoColor=white"></a>
<a href="https://spring.io/projects/spring-boot" target="_blank"><img src = "https://img.shields.io/badge/Spring_Boot-100000?style=for-the-badge&logo=spring&logoColor=white"></a>
<a href="https://spring.io/projects/spring-data-jpa" target="_blank"><img src = "https://img.shields.io/badge/Spring_Data_JPA-100000?style=for-the-badge&logo=spring&logoColor=white"></a>
<a href="https://hibernate.org/orm/" target="_blank"><img src = "https://img.shields.io/badge/Hibernate-100000?style=for-the-badge&logo=Hibernate&logoColor=white"></a>
<a href="https://logback.qos.ch/documentation.html" target="_blank"><img src = "https://img.shields.io/badge/Logback-100000?style=for-the-badge&logo=ko-fi&logoColor=white"></a>
<a href="https://maven.apache.org/download.cgi" target="_blank"><img src = "https://img.shields.io/badge/Maven-100000?style=for-the-badge&logo=apachemaven&logoColor=white"></a>
<a href="https://www.mysql.com/downloads/" target="_blank"><img src = "https://img.shields.io/badge/Mysql-100000?style=for-the-badge&logo=mysql&logoColor=white"></a>
<a href="https://www.postman.com/downloads/" target="_blank"><img src = "https://img.shields.io/badge/Postman-100000?style=for-the-badge&logo=Postman&logoColor=white"></a>
</div> <br>
<p align="center">
  &copy; 2024 Yasith Chathuranga
</p>
