package usiu.ac.ke.studentdriven;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private EditText inputCity;
    private Button btnSearch;
    private ProgressBar progressBar;
    private CardView weatherCard;
    private ImageView imgWeatherIcon;
    private TextView txtTemperature, txtWeatherDescription, txtWindSpeed;
    private SwitchMaterial darkModeSwitch;

    private RequestQueue queue;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "WeatherAppPrefs";
    private static final String KEY_DARK_MODE = "isDarkMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup for Dark Mode persistence
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        updateTheme(isDarkMode);

        setContentView(R.layout.activity_main);
        initializeUI();

        // Set the switch to its saved state and add a listener
        darkModeSwitch.setChecked(isDarkMode);
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            updateTheme(isChecked);
        });

        queue = Volley.newRequestQueue(this);
        btnSearch.setOnClickListener(v -> fetchData());
    }

    private void updateTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initializeUI() {
        inputCity = findViewById(R.id.inputCity);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        weatherCard = findViewById(R.id.weatherCard);
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtWeatherDescription = findViewById(R.id.txtWeatherDescription);
        txtWindSpeed = findViewById(R.id.txtWindSpeed);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
    }

    private void fetchData() {
        // Basic internet check
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            Toast.makeText(this, "Please connect to the internet first.", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "https://api.open-meteo.com/v1/forecast?latitude=-1.2864&longitude=36.8172&current_weather=true";

        // Show loading state
        progressBar.setVisibility(View.VISIBLE);
        weatherCard.setVisibility(View.GONE); // Hide card while loading

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Success
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject current = response.getJSONObject("current_weather");
                        double temp = current.getDouble("temperature");
                        double wind = current.getDouble("windspeed");
                        int code = current.getInt("weathercode");

                        // Update the new UI elements
                        updateWeatherUI(temp, wind, code);

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to parse weather data.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Error
                    progressBar.setVisibility(View.GONE);
                    handleVolleyError(error);
                }
        );
        queue.add(request);
    }

    private void updateWeatherUI(double temp, double wind, int code) {
        // Set Temperature and Wind Speed
        txtTemperature.setText(String.format("%.1f°C", temp));
        txtWindSpeed.setText(String.format("Wind: %.1f km/h", wind));

        // Map weather code to icon and description
        if (code == 0) {
            imgWeatherIcon.setImageResource(R.drawable.ic_sunny);
            txtWeatherDescription.setText("Clear Sky");
        } else if (code <= 3) {
            imgWeatherIcon.setImageResource(R.drawable.ic_cloudy);
            txtWeatherDescription.setText("Partly Cloudy");
        } else {
            imgWeatherIcon.setImageResource(R.drawable.ic_rainy);
            txtWeatherDescription.setText("Rainy");
        }

        // Make the results card visible
        weatherCard.setVisibility(View.VISIBLE);
    }

    private void handleVolleyError(Exception error) {
        String message;
        if (error instanceof TimeoutError) {
            message = "Request timed out. Please try again.";
        } else if (error instanceof NoConnectionError) {
            message = "Connection lost. Please check your internet.";
        } else {
            message = "An unexpected error occurred.";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
