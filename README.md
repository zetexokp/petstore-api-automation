# PetStore API Automation Framework

REST API automation testing project for Swagger PetStore API using Java and REST Assured.

The project demonstrates automated testing of REST endpoints with positive and negative scenarios, request/response validation, and reusable test utilities.


## Technologies

- Java 17
- REST Assured
- JUnit 5
- Maven
- Hamcrest
- JSON
- Git
- IntelliJ IDEA


## Features

- REST API automation testing
- CRUD operations testing
- Positive and negative test scenarios
- HTTP status code validation
- JSON response validation
- Request and response handling using POJO models
- Test data generation
- Automatic test data cleanup after execution


## API Coverage

### Pet API

Implemented scenarios:

- Create pet
- Get pet by ID
- Update existing pet
- Update pet using form data
- Find pets by status
- Upload pet image
- Delete pet
- Validate non-existing pets
- Validate invalid request data


### Store API

Implemented scenarios:

- Create order
- Get order by ID
- Get inventory
- Delete order
- Validate invalid order IDs
- Validate non-existing orders


### User API

Implemented scenarios:

- Create user
- Create users with list
- Create users with array
- Get user by username
- Update user
- Delete user
- Login user
- Logout user
- Validate invalid users
- Negative authentication scenarios


## Test Architecture

The project contains:

- BaseTest - common API configuration and test setup
- Test classes - API test scenarios
- Helper class - generates reusable test data
- Model classes - POJO objects for API requests and responses


## Test Flow

Typical test scenario:

1. Generate test data using Helper class
2. Send API request using REST Assured
3. Validate HTTP response status
4. Verify response body fields
5. Clean up created test data


## Running Tests

Clone repository:

```bash
git clone https://github.com/zetexokp/petstore-api-automation.git
```
Go to project folder:
```bash
cd petstore-api-automation
```
Run tests:
```bash
mvn test
```
Example Test Scenario
Create Pet

Request:

POST /pet

Body:
```JSON
{
  "id": 1234,
  "name": "Dog",
  "status": "available"
}
```

Expected result:

HTTP 200 OK
Validation

Tests verify:

HTTP status codes
JSON response fields
API behavior
Error handling
Request validation
Author

Vlad Smirnov