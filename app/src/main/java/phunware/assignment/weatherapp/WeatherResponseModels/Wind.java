package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public class Wind {

    final String JSON_SPEED = "speed";
    final String JSON_DEGREE = "deg";

    private double speed;
    private double deg;

    public Wind(){}

    public Wind(JSONObject obj){
        speed = obj.optDouble(JSON_SPEED);
        deg = obj.optDouble(JSON_DEGREE);
    }

    public Wind(double speed, double deg){
        this.speed = speed;
        this.deg = deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }
}
