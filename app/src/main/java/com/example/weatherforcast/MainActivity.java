package com.example.weatherforcast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText cities;

    ImageView img;
    TextView Results;
    private final String website = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "cf208298f4e6c490d72858070f16fea1";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = findViewById(R.id.city_name_edit);

        Results = findViewById(R.id.results_display);

        img = findViewById(R.id.weather_condition_image);




    }

    public void WeatherData(View view) {
        String tempUrl = "";
        String city = cities.getText().toString().trim();

        if(city.equals("")){
            Results.setText("Error city is not allowed to be empty!");
        }else {
            if (!city.equals("")) {
                tempUrl = website + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d("response",response);
                    /*
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        Results.setTextColor(Color.rgb(68,134,199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        Results.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                     */

                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description").toLowerCase();
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;




                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");

                        int cloudiness = jsonObjectClouds.getInt("all"); // Cloudiness percentage
                        long sunrise = jsonObjectSys.getLong("sunrise") * 1000; // Milliseconds
                        long sunset = jsonObjectSys.getLong("sunset") * 1000; // Milliseconds
                        long currentTime = System.currentTimeMillis();

                        boolean isNight = currentTime < sunrise || currentTime > sunset;

                        if(description.contains("rain")) {
                            img.setImageResource(R.drawable.rainy);
                        } else if (isNight) {
                            img.setImageResource(R.drawable.nighty); // Night image logic
                        } else if(description.contains("clear")) {
                            img.setImageResource(R.drawable.sunnys);
                        } else {
                            if(cloudiness > 50) {
                                img.setImageResource(R.drawable.cloudy);
                            } else if(temp <= 10) {
                                img.setImageResource(R.drawable.coldy);
                            } else {
                                img.setImageResource(R.drawable.sunnys);
                            }
                        }

                        /*

                        if(description.contains("overcast")) {
                            img.setImageResource(R.drawable.cloudy);
                        } else if(description.contains("rain")) {
                            img.setImageResource(R.drawable.rainy);
                        } else if(description.contains("sun")) {
                            img.setImageResource(R.drawable.sunnys);
                        } else if(description.contains("cold")) {
                            img.setImageResource(R.drawable.coldy);
                        } else if (temp <= 10){

                            img.setImageResource(R.drawable.coldy);
                        }
                        else if (temp > 10 && temp < 15){
                            img.setImageResource(R.drawable.cloudy);

                        }
                        else{
                            img.setImageResource(R.drawable.weather_logo);
                        }

                         */
                        Results.setTextColor(Color.rgb(68,134,199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        Results.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }
    }
}