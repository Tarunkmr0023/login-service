# Login Service

The `Login Service` is a Spring Boot application designed for user authentication and token-based access. It provides the following functionalities:

- Generate Access and Refresh Tokens
- Validate Tokens
- Renew Tokens
- Logout
- Create Users

---

## Features

### 1. **Generate Token**
- API: `/auth/sso`
- Method: `POST`
- Request Body:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
- Response Body:
  ```json
  {
  "accessToken": "ACCESS_TOKEN",
  "refreshToken": "REFRESH_TOKEN",
  "email": "user@example.com",
  "accessTokenExpiryTime": "2025-01-16T12:00:00",
  "refreshTokenExpiryTime": "2025-01-17T12:00:00"
  }
- **Description**: Validates user credentials, generates JWT tokens, and stores them in an in-memory list.

### 2. **Validate Token**
- API: `/auth/validate`
- Method: `POST`
- Headers: `authToken : "AUTH_TOKEN"`
- Response Body:
  ```json
  {
    "valid": true,
    "email": "user@example.com"
  }
- **Description**: Validates if the provided token is present in the active list and has not expired.

### 3. **Renew Token**
- API: `/auth/refresh`
- Method: `POST`
- Headers: `refreshToken : "REFRESH_TOKEN"`
- Response Body:
  ```json
  {
    "accessToken": "NEW_ACCESS_TOKEN",
    "refreshToken": "REFRESH_TOKEN",
    "email": "user@example.com",
    "accessTokenExpiryTime": "2025-01-16T14:00:00",
    "refreshTokenExpiryTime": "2025-01-17T12:00:00"
  }

- **Description**: Generates a new access token if the refresh token is valid and not expired.

### 4. **Logout**
- API: `/auth/logout`
- Method: `POST`
- Headers: `authToken : "AUTH_TOKEN"`
- Response Body:
  ```json
  {
    "message": "Logged out successfully"
  }

- **Description**: Removes the specified token from the active tokens list.
### 5. **Create User**
- API: `/users/create`
- Method: `POST`
- Headers:
  `auth-token: <YOUR_SERVICE_AUTH_TOKEN>`
- Request Body:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }

- Response Body:
  ```json
  {
    "message": "User created successfully"
  }

- **Description**: Creates a new user. The auth-token header value must match the token configured in the service.

## Environment

The application requires the following environment variables to be configured:

| Variable              | Description                                | Example Value               |
|-----------------------|--------------------------------------------|-----------------------------|
| `jwt.secret`          | Secret key for signing JWT tokens         | `your-secret-key`           |
| `jwt.expiration`      | Access token expiration time in milliseconds | `3600000` (1 hour)          |
| `jwt.refreshExpiration` | Refresh token expiration time in milliseconds | `86400000` (24 hours)       |
| `auth.token`          | Custom header token for user creation authorization | `secure-auth-token`         |

Ensure these variables are properly set in your `application.properties` or environment configuration file.

## How It Works

1. **Login**: Users authenticate by providing their `email` and `password`. If valid, access and refresh tokens are generated.
2. **Token Validation**: Verifies the token's validity and checks if it is in the active tokens list.
3. **Token Renewal**: Refresh tokens allow generating new access tokens without re-authentication.
4. **Logout**: Removes a token from the active tokens list, invalidating it.
5. **User Creation**: Requires a custom header (`auth-token`) for authorization to create new users.

---

## Security

- Passwords are hashed using `BCrypt` for secure storage.
- Tokens are verified for expiration and presence in the active tokens list.
- Custom header authentication is implemented for sensitive operations like user creation.

---

## Tools & Technologies

- **Java** (Spring Boot)
- **JWT** for token generation and validation
- **BCrypt** for secure password hashing




