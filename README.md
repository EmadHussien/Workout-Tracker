# Workout Tracker Project Documentation

### 1. Introduction

The Workout Tracker is a backend system built with Spring Boot that allows users to manage their workouts, track progress, and generate reports. The system features JWT authentication, CRUD operations for workouts, and a data seeder for exercises.

### 2. Project Features

### Key Features of Workout Tracker Application

#### Exercise Data
- **Data Seeder**: Populate the database with a list of exercises.
- **Exercise Attributes**: Each exercise should have a name, description, and category (e.g., cardio, strength, flexibility) or muscle group (e.g., chest, back, legs).

#### User Authentication and Authorization
- **Sign-Up**: Allow users to create an account.
- **Login**: Allow users to log in to their account.
- **JWT Authentication**: Use JSON Web Tokens for authentication and authorization.
- **Access Control**: Only authenticated users should be able to create, update, and delete workout plans. Users should only be able to access their own workout plans.

#### Workout Management
- **Create Workout**: Allow users to create workouts composed of multiple exercises.
- **Update Workout**: Allow users to update workouts and add comments.
- **Delete Workout**: Allow users to delete workouts.
- **Schedule Workouts**: Allow users to schedule workouts for specific dates and times.
- **List Workouts**: List active or pending workouts sorted by date and time.
- **Generate Reports**: Generate reports on past workouts and progress.

#### Constraints
- **Database**: Use a relational database to store user data, workout plans, and exercise data.
- **API**: Develop a RESTful API to interact with the database.
- **Security**: Implement JWT authentication to secure the API endpoints.
  

### 3. Project Setup

#### Prerequisites
- Java Development Kit (JDK) 11 or later
- Maven or Gradle
- Database (e.g., PostgreSQL, MySQL)
- Git

#### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/workout-tracker.git
   cd workout-tracker
   ```

2. Build the project:
   ```bash
   mvn clean install
   # or
   gradle build
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   # or
   gradle bootRun
   ```

#### Configuration
- Database Configuration: Update the database connection settings in the `application.properties` or `application.yml` file.
- JWT Configuration: Set the JWT secret and expiration time in the `application.properties` or `application.yml` file.

### 4. Database Schema

![image](https://github.com/user-attachments/assets/84e35111-35b6-45f6-be80-9a7234a3def4)



### 5. API Documentation

For detailed API documentation, please refer to the [Postman Docs](https://documenter.getpostman.com/view/21228226/2sAY4uCids).

### Workout Tracker API Documentation

1. **Create Workout Plan**
   - URL: `http://localhost:8080/api/v1/workouts`
   - Method: `POST`

2. **Update Workout Plan**
   - URL: `http://localhost:8080/api/v1/workouts/{id}`
   - Method: `PATCH`

3. **Delete Workout Plan**
   - URL: `http://localhost:8080/api/v1/workouts/{id}`
   - Method: `DELETE`

4. **Get Workout Plans**
   - URL: `http://localhost:8080/api/v1/user/{userId}/workouts`
   - Method: `GET`

5. **Get a Single Workout Plan**
   - URL: `http://localhost:8080/api/v1/workouts/{id}`
   - Method: `GET`

6. **Schedule Workout Session**
   - URL: `http://localhost:8080/api/v1/workouts/{id}/schedule`
   - Method: `POST`

7. **Get User Upcoming Sessions**
   - URL: `http://localhost:8080/api/v1/user/{userId}/upcoming-sessions`
   - Method: `GET`

8. **Get Report**
   - URL: `http://localhost:8080/api/v1/reports/workouts`
   - Method: `GET`

9. **Register**
   - URL: `http://localhost:8080/api/v1/auth/register`
   - Method: `POST`

10. **Login**
    - URL: `http://localhost:8080/api/v1/auth/login`
    - Method: `POST`

### 6. Security

- **JWT Authentication**: JSON Web Tokens are used for authentication. Tokens are issued upon login and must be included in the Authorization header for protected routes.
- **Password Hashing**: Passwords are hashed using a secure hashing algorithm (e.g., bcrypt).

