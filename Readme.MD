# CustomerTaxCalculator

A Spring Boot application for calculating vehicle tax fees based on pass times.

## Features

- REST API for tax calculation
- Integration with SQL database
- Gradle build system
- Integration and unit tests

## Technologies

- Java
- Spring Boot
- SQL
- Gradle

## Getting Started

### Prerequisites

- Java 17+
- Gradle
- SQL database (e.g., PostgreSQL, MySQL)

### Setup

1. Clone the repository:
```markdownl   
git clone git@github.com:mruzman0109/CustomerTaxCalculator.git
cd CustomerTaxCalculator
   ```
2. Configure your database in `src/main/resources/application.properties`.

3. Build the project:
   ```markdown
    ./gradlew build
   ```

4. Run the application:
   ```
    ./gradlew bootRun
   ```

### API Usage

- **POST** `/api/tax/calculate`
  - Request Body: JSON array of vehicles and pass times
  - Response: Calculated tax fee (float)

Example request:
```json
[
  {
    "vehicleType": "Car",
    "passTime": [
      "2025-08-25T06:15:00",
      "2025-08-25T07:10:00"
    ]
  }
]
```

### Testing

Run all tests:
```
./gradlew test
```
