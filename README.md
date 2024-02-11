# Weather Info for Pincode API (Spring Boot)

This project provides a REST API to retrieve weather information for a particular day and a pincode. The API allows you to save the pincode's latitude and longitude separately and store the weather information in a relational database (RDBMS) for optimized API calls in the future. It is implemented using Spring Boot framework.

## Installation

1. Clone the repository:

   ```
   git clone https://github.com/kediasparsh/openweatherapi-wrapper.git
   ```

2. Import the project into your preferred IDE.

3. Build the project using Maven or your IDE's build feature.

4. Set up the database:

   - Create a new RDBMS (e.g., MySQL, PostgreSQL) database.
   - Update the database connection configuration in the `application.properties` file with your database credentials.

5. Run the application:

   - If using an IDE, run the main application class.
   - If using Maven, run `mvn clean spring-boot:run` from the project's root directory.

6. The application should now be running on `http://localhost:8080`.
7. Sign in on `https://home.openweathermap.org/users/sign_in` and replace the `openWeatherMapAPIKey` String with the appropriate value in the `WeatherService.java` file.

## API Usage

### Get Weather Information

#### Request

```
GET /weather
Query Params

Key                         Value
pincode                     395007
for_date                    2024-02-05

Warning: The API call accesses the current weather data for the above pincode. For weather data for the above date, a nominal fee is required.
```

Parameters:
- `pincode` (required): The pincode for which you want to retrieve weather information.
- `for_date` (required): The specific date for which you want weather information.

#### Response

```json
{
    "id": 1,
    "pincode": 395007,
    "date": "2024-02-05",
    "temperature": 293.17,
    "pressure": 1015,
    "humidity": 39,
    "description": "few clouds"
}
```

## Database Schema

The database schema consists of two tables: `http://localhost:8080/h2`

### location

| Column     | Type        | Description                            |
| ---------- | ----------- | -------------------------------------- |
| latitude   | Double       | The latitude of the pincode's location  |
| longitude  | Double       | The longitude of the pincode's location |
| pincode   | int         | The pincode associated with the location|

### weather

| Column        | Type         | Description                                |
| ------------- | ------------ | ------------------------------------------ |
| date       | LocalDate          | The specific date for the request |
| humidity            | int       | Humidity, %                                |
| id  | int         | Primary key    |
| pincode          | int | The pincode associated with the request     |
| pressure          | int | Atmospheric pressure on the sea level, hPa     |
| temperature          | double | Temperature     |
| description          | String | Weather condition     |
