# Presearch Job

This project is designed to handle daily requests to a Presearch API for get stats of your node, built with Spring Boot, and using PostgreSQL to store API responses.
 Below are the steps to set up and run the project.

## Prerequisites

Before running the project, ensure you have the following installed on your system:

- **Java 11** or higher
- **Maven** for dependency management
- **PostgreSQL** as the database

## Configuration Instructions

### 1. Create the `env.properties` File

The project requires an `env.properties` file located at the following directory to load environment configurations.

Follow these steps to create and configure the file:

#### Step 1: Create the File

Navigate to the `src/main/resources` folder in your project and create a new file named `env.properties`.

#### Step 2: Add the Required Properties

In the `env.properties` file, add the following properties with the corresponding values:

```properties
API_KEY=your_api_key_here
DB_URL=jdbc:postgresql://localhost:5432/your_database
DB_USER=your_database_user
DB_PASSWORD=your_database_password
```

#### Step 3: Update the application.yml File
In order to fetch statistics from a specific starting date up to the current date, you will need to update the initial_date property in the application.yml file.

Step 1: Locate the `application.yml` file.

Step 2: Replace the `initial_date` property:

Inside the application.yml file, replace the initial_date property with the desired date to start fetching statistics:

initial_date: 'YYYY-MM-DDT00:00:00' # Replace with your start date (e.g., '2023-01-01T00:00:00')



