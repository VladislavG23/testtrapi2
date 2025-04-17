# Hotelbeds API Integration Documentation

## General Overview

This documentation provides a comprehensive guide to the integration of the Hotelbeds API within our application. The integration allows users to check hotel availability through a RESTful endpoint. The backend is implemented using Spring Boot and communicates with the Hotelbeds API to fetch hotel data. The integration ensures secure communication by generating a signature for each request.

## Quick Start Guide

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Spring Boot
- Python (for running integration tests)
- Internet connection for accessing the Hotelbeds API

### Running the Backend Server Locally

1. **Clone the Repository:**
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Configure Environment Variables:**
   Ensure that the `HOTELBEDS_HOTEL_API_KEY` and `HOTELBEDS_HOTEL_SECRET` environment variables are set with your Hotelbeds API credentials.

3. **Build the Project:**
   ```bash
   mvn clean install
   ```

4. **Run the Server:**
   ```bash
   mvn spring-boot:run
   ```
   The server will start on port 5000 as specified in `application.properties`.

### Running the Backend Server Remotely

1. **Deploy the Application:**
   - Package the application using Maven:
     ```bash
     mvn package
     ```
   - Deploy the generated JAR file to your remote server.

2. **Set Environment Variables:**
   Ensure that the `HOTELBEDS_HOTEL_API_KEY` and `HOTELBEDS_HOTEL_SECRET` are set on the remote server.

3. **Start the Application:**
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

### Running the Frontend Locally

1. **Navigate to the Frontend Directory:**
   ```bash
   cd <frontend-directory>
   ```

2. **Install Dependencies:**
   ```bash
   npm install
   ```

3. **Start the Frontend Server:**
   ```bash
   npm start
   ```
   The frontend will typically run on `http://localhost:3000`.

### Running the Frontend Remotely

1. **Build the Frontend:**
   ```bash
   npm run build
   ```

2. **Deploy the Build:**
   Upload the contents of the `build` directory to your remote server or hosting service.

### Testing Options

- **Integration Tests:**
  The integration tests are located in `integration_tests.py`. To run the tests, ensure Python is installed and execute:
  ```bash
  python integration_tests.py
  ```

## Troubleshooting Guide

- **Common Errors:**
  - **401 Unauthorized:** Ensure that the API key and secret are correctly set and valid.
  - **Timeout Errors:** Check your internet connection and ensure the Hotelbeds API is accessible.
  - **Signature Generation Errors:** Verify that the system clock is accurate, as the signature relies on the current timestamp.

- **Logs:**
  Check the application logs for detailed error messages. Logs are printed to the console and can be redirected to a file if needed.

## Support Contact Information

For support, please contact our technical team at:
- Email: support@example.com
- Phone: +1-800-555-0199

## Links to API Provider Documentation

- [Hotelbeds API Documentation](https://developer.hotelbeds.com/documentation/hotels/overview)
- [Hotelbeds API Authentication Guide](https://developer.hotelbeds.com/documentation/hotels/authentication)