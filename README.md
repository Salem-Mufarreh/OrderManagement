# OrderManagementSystem
This is a sample application for managing orders.

## Prerequisites
Before building and running the application, ensure that you have the following dependencies installed:

- Java Development Kit (JDK) 17 or higher
- Gradle build tool
- Docker
- Building the Application

To build the application, follow these steps:

1. Clone the repository to your local machine.

2. Open a terminal/command prompt and navigate to the project directory.

3. Run the following command to build the application:

```
gradle build
```
This command will compile the source code, run tests, and package the application into a JAR file.

Creating and Running the Docker Image
To create and run the Docker image for the Order Management application, follow these steps:

Ensure that Docker is installed and running on your machine.

Open a terminal/command prompt and navigate to the project directory.

Build the Docker image using the provided Dockerfile:

```
docker build -t ordermanagement .
```
This command will build the Docker image with the tag ordermanagement.

Once the image is built, you can run a container based on the image:

```
docker run -p 8080:8080 ordermanagement
```
This command will start a container from the ordermanagement image and map port 8080 of the container to port 8080 of the host machine.

By following these instructions, you should be able to build the application and create/run the Docker image for the Order Management application.

## Work Plan
| Step | Task                                      | Subtasks                                                                                    | Status             |
|------|-------------------------------------------|---------------------------------------------------------------------------------------------|--------------------|
| 1    | Understand the Database Diagram           | - Analyze the provided database diagram                                                     | :heavy_check_mark: |
|      |                                           | - Identify the entities and their relationships                                             | :heavy_check_mark: |
| 2    | Backend Implementation                    | - Create Spring Boot project                                                                | :heavy_check_mark: |
|      |                                           | - Implement entity classes for "product", "stock", "product order", "order", and "customer" | :heavy_check_mark: |
|      |                                           | - Implement repositories for each entity                                                    | :heavy_check_mark: |
|      |                                           | - Implement business logic for managing the entities / Services                             | :heavy_check_mark: |
|      |                                           | - Create REST-FUll API endpoints for CRUD operations                                        | :heavy_check_mark: |
| 3    | Dockerize Your Application                | - Create a Dockerfile for the application                                                   | :heavy_check_mark: |
|      |                                           | - Define necessary dependencies and configurations                                          | :heavy_check_mark: |
|      |                                           | - Build the Docker image                                                                    | :heavy_check_mark: |
| 4    | Push Your Project to GitHub               | - Create a GitHub repository                                                                | :heavy_check_mark: |
|      |                                           | - Initialize the repository and commit the project code                                     | :heavy_check_mark: |
|      |                                           | - Push the code to the GitHub repository                                                    | :heavy_check_mark: |
| 5    | Update the README.md                      | - Add instructions on building the application                                              |                    |
|      |                                           | - Add instructions on creating and running the Docker image                                 |                    |
| 6    | Prepare Postman Collection                | - Create a Postman collection                                                               | :heavy_check_mark: |
|      |                                           | - Add API requests and responses for testing each resource method                           | :heavy_check_mark: |
|      |                                           | - Export the collection and add it to the GitHub repository                                 | :heavy_check_mark: |
| 7    | Add Documentation                         | - Add comments and explanations in the code                                                 |                    |
|      |                                           | - Describe classes, methods, and endpoints                                                  |                    |
| 8    | Implement Entity Relations                | - Define relationships between the entities                                                 | :heavy_check_mark: |
|      |                                           | - Use appropriate annotations or configurations                                             | :heavy_check_mark: |
| 9    | Follow Best Practices and Constraints     | - Write clean and modular code                                                              |                    |
|      |                                           | - Handle exceptions properly                                                                | :heavy_check_mark: |
|      |                                           | - Implement validation techniques                                                           | :heavy_check_mark: |
| 10   | Secure APIs and Use Swagger Documentation | - Implement signup and authentication APIs                                                  | :heavy_check_mark: |
|      |                                           | - Use JWT for authentication and authorization                                              | :heavy_check_mark: |
|      |                                           | - Integrate Swagger for API documentation                                                   | :heavy_check_mark: |
| 11   | Complete the Assignment by the Deadline   | - Ensure all tasks are finished and requirements are met                                    |                    |
