package usiu.ac.ke.studentdriven;

/**
 * A simple model class to hold the parsed weather data.
 */
public class Weather {
    // Fields to store the weather data
    double temperature;
    double windSpeed;
    int weatherCode;

    /**
     * Constructor to create a new Weather object.
     * @param temperature The current temperature.
     * @param windSpeed The current wind speed.
     * @param weatherCode The code representing the current weather condition.
     */
    public Weather(double temperature, double windSpeed, int weatherCode) {
        // Initialize the fields with the provided values
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.weatherCode = weatherCode;
    }
}
