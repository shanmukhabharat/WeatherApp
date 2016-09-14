package phunware.assignment.weatherapp.WeatherResponseModels;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public class Weather {

    final String JSON_ID = "id";
    final String JSON_MAIN = "main";
    final String JSON_DESCRIPTION = "description";
    final String JSON_ICON = "icon";

    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather(){}

    public Weather(JSONArray obj){

        for(int i = 0 ; i < obj.length(); i++){
            JSONObject eachObj = obj.optJSONObject(i);
            if(eachObj != null){
                id = eachObj.optInt(JSON_ID);
                main = eachObj.optString(JSON_MAIN);
                description = eachObj.optString(JSON_DESCRIPTION);
                icon = eachObj.optString(JSON_ICON);
            }
        }
    }

    public Weather(int id, String main, String description,
                        String icon){
        this.description = description;
        this.icon = icon;
        this.main = main;
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getMain() {
        return main;
    }
}

