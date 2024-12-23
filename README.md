# LoanApp Application Guide

This guide explains how to set up, run, and use the LoanApp application.

---

## Prerequisites

Ensure you have the following installed on your machine:

1. **Java**: Java Development Kit (JDK) version 17 or later.

---

## Steps to Run the Application

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/sigma-field/LoanApp.git
   cd LoanApp
   ```

2. **Build the Application**:

   ```bash
   ./gradlew clean build
   ```

3. **Run the Application**:

   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`.

4. **Access the H2 Console**:

   The app uses H2 as database, to access the H2 console for database inspection:

   - URL: `http://localhost:8080/h2-console`
   - dbName: `mydb`
   - Username is admin and there is no password.

---

## Authentication and Authorization

### Default Admin User

The application comes with a pre-configured SUPER_ADMIN user and this user is capable of creating admin users which are employees:

- **Username**: `super_admin@email.com`
- **Password**: `password`

### Signing In

1. Send a POST request to the `sign-in` endpoint to authenticate as the admin:

   - URL: `http://localhost:8080/api/v1/auth/sign-in`
   - Method: `POST`
   - Body (JSON):
     ```json
     {
       "email": "super_admin@email.com",
       "password": "password"
     }
     ```

2. On successful authentication, you will receive a response with a Bearer token:

   ```json
   {
       "access_token": "<jwt-token>"
   }
   ```

3. Use the token for subsequent requests by adding it to the `Authorization` header:

   ```
   Authorization: Bearer <jwt-token>
   ```

---

## Using the Application

1. **Access Secured Endpoints**:

   - All API endpoints are secured and require authentication.
   - Include the Bearer token obtained during sign-in in every request.

2. **Testing the Application**:

   - Use tools like Postman or curl to test endpoints.
   
    The API documentation can be found here, endpoints can also be invoked from the api doc. You should use the
    bearer token by putting it into the inputbox that opens once you click on the right top corner. Please just paste the token
    Bearer will automatically be appended to the beginning of the header.

    ```
    http://localhost:8080/swagger-ui/index.html
    ```

---



