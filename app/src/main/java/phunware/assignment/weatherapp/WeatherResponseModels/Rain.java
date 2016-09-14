package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public class Rain {

    final String JSON_RAIN_PARAM = "3h";

    private double rainVolume;

    public Rain(){

    }

    public Rain(double rainVolume){
        this.rainVolume = rainVolume;
    }

    public Rain(JSONObject obj){
        rainVolume = obj.optDouble(JSON_RAIN_PARAM);
    }

    public void setRainVolume(float rainVolume) {
        this.rainVolume = rainVolume;
    }

    public double getRainVolume() {
        return rainVolume;
    }
}

