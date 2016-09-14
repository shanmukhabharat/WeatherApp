package phunware.assignment.weatherapp.OWMResponses;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import phunware.assignment.weatherapp.Utils.GlobalUtils;
import phunware.assignment.weatherapp.WeatherResponseModels.Clouds;
import phunware.assignment.weatherapp.WeatherResponseModels.Coord;
import phunware.assignment.weatherapp.WeatherResponseModels.Main;
import phunware.assignment.weatherapp.WeatherResponseModels.Rain;
import phunware.assignment.weatherapp.WeatherResponseModels.Weather;
import phunware.assignment.weatherapp.WeatherResponseModels.Wind;

/**
 * Created by Bharath on 13/09/16.
 */
public class OWMCurrentWeatherResponse extends AbstractOWMResponse {

    //JSON keys for getting the values
    private final String JSON_KEY_COORD = "coord";
    private final String JSON_KEY_WEATHER = "weather";
    private final String JSON_KEY_MAIN = "main";
    private final String JSON_KEY_WIND = "wind";
    private final String JSON_KEY_CLOUDS = "clouds";
    private final String JSON_KEY_RAIN = "rain";
    private final String JSON_KEY_SNOW = "snow";
    private final String JSON_KEY_NAME = "name";

    //Response models
    private Clouds clouds;
    private Coord coord;
    private Main main;
    private Rain rain;
    private Weather weather;
    private Wind wind;
    private String name;

    //Message to show when the request is not successful
    private String message;

    public OWMCurrentWeatherResponse(JSONObject response) {

        super(response);

        //If the request is successful get all the weather conditions from the response;
        if(getCode() == GlobalUtils.OWM_REQUEST_SUCCESS) {

            //initialising coordinates from weather response
            JSONObject obj = response.optJSONObject(JSON_KEY_COORD);
            if (obj != null) {
                coord = new Coord(obj);
            } else {
                coord = null;
            }

            //initialising main from weather response
            obj = response.optJSONObject(JSON_KEY_MAIN);
            if (obj != null) {
                main = new Main(obj);
            } else {
                main = null;
            }

            //initialising clouds from weather response
            obj = response.optJSONObject(JSON_KEY_CLOUDS);
            if (obj != null) {
                clouds = new Clouds(obj);
            } else {
                clouds = null;
            }

            //initialising rain from weather response
            obj = response.optJSONObject(JSON_KEY_RAIN);
            if (obj != null) {
                rain = new Rain(obj);
            } else {
                rain = null;
            }

            //initialising weather from weather response
            JSONArray jsonArray = response.optJSONArray(JSON_KEY_WEATHER);
            if (jsonArray != null) {
                weather = new Weather(jsonArray);
            } else {
                weather = null;
            }

            //initialising wind from weather response
            obj = response.optJSONObject(JSON_KEY_WIND);
            if (obj != null) {
                wind = new Wind(obj);
            } else {
                wind = null;
            }

            //initialising name from weather response
            String name = response.optString(JSON_KEY_NAME);
            if (!TextUtils.isEmpty(name)) {
                this.name = name;
            } else {
                this.name = null;
            }

            //setting the message
            message = "Request is successful";

        }else{

            //setting the message as unsuccessful
            message = "Request is not successful";

        }
    }

    @Override
    public int getCode() {
        return super.getCode();
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public Rain getRain() {
        return rain;
    }

    public Weather getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }
}
