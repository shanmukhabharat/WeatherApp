package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public class Coord {

    final String JSON_LAT = "lat";
    final String JSON_LONG = "lon";

    private double latitude;
    private double longitude;

    public Coord(){

    }

    public Coord(JSONObject object){
        latitude = object.optDouble(JSON_LAT);
        longitude = object.optDouble(JSON_LONG);
    }

    public Coord(double lat, double longitude){
        this.latitude = lat;
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
