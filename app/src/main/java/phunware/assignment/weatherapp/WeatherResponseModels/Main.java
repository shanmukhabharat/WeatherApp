package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by Bharath on 13/09/16.
 */
public class Main {

    final String JSON_TEMP = "temp";
    final String JSON_PRESSURE = "pressure";
    final String JSON_HUMIDITY = "humidity";
    final String JSON_TEMP_MIN = "temp_min";
    final String JSON_TEMP_MAX = "temp_max";

    private double temp;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double pressure;

    DecimalFormat df;

    public Main(){

    }

    public Main(JSONObject obj){
        humidity = obj.optDouble(JSON_HUMIDITY);
        temp = obj.optDouble(JSON_TEMP);
        temp_max = obj.optDouble(JSON_TEMP_MAX);
        temp_min = obj.optDouble(JSON_TEMP_MIN);
        pressure = obj.optDouble(JSON_PRESSURE);
    }

    public Main(double humidity, double temp, double temp_max,
                    double temp_min, double pressure){
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

}
