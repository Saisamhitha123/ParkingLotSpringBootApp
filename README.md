# ParkingLot Management System

A comprehensive Spring Boot application for managing parking lots with automated spot allocation, fee calculation, and display panel systems.

## Project Overview

The ParkingLot Management System is a RESTful API built with Spring Boot that provides comprehensive parking lot management capabilities including:

- **Parking Lot Management**: Create and manage parking lots with multiple floors
- **Spot Allocation**: Automated parking spot allocation with different strategies
- **Fee Calculation**: Flexible fee calculation with multiple pricing models
- **Display Panels**: Information display systems for entry/exit and floor levels
- **Vehicle Management**: Parking tickets, and spot assignments
- **Concurrency management**: Concurrency management is done at entry and exit panels 

## Architecture

### Design Principles Used

- SOLID, DRY, KISS, LOD, PCI, YAGNI


### Design Patterns Used

- **Observer Pattern**: Updating Display Panels whenever parking spots, parking floors count/status changes and also updating entry/exit display panels with spot and fee accordingly
- **Strategy Pattern**: Different allocation and fee calculation strategies( used PERCENTAGE allocation strategy)
- **Decorator Pattern**: Fee calculation with different fee types (SurgeFeeCalculator, BaseFeeCalculator)
- **Builder Pattern**: Data transfer objects for API responses

### Technology Stack
- **Framework**: Spring Boot 3.x
- **Database**: H2 (in-memory) / MySQL 
- **ORM**: JPA/Hibernate
- **Build Tool**: Gradle
- **Java Version**: 17+



## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 7.x or higher
- IDE (IntelliJ IDEA)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ParkingLotProject
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```



### Database Configuration

The application uses H2 in-memory database by default. To use a different database, update `application.properties`:

properties
# For MySQL
spring.datasource.url=jdbc:mysql://portNumber/schemaname
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update


### Class Diagram/Schema diagram

![Low Level Design Diagram.png](Low%20Level%20Design%20Diagram.png)



