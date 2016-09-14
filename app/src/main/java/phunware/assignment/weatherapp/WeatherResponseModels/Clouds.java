package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public class Clouds {

    final String JSON_ALL = "all";

    private double cloudiness;

    public Clouds(){
    }

    public Clouds(float cloudiness){
        this.cloudiness = cloudiness;
    }

    public Clouds(JSONObject object){
        cloudiness = object.optDouble(JSON_ALL);
    }

    public void setCloudiness(float cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getCloudiness() {
        return cloudiness;
    }
}
