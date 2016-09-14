package phunware.assignment.weatherapp.OWMResponses;

import org.json.JSONObject;

/**
 * Created by Bharath on 13/09/16.
 */
public abstract class AbstractOWMResponse {

    private static final String JSON_COD = "cod";

    private int code = -1;

    public AbstractOWMResponse(JSONObject response){
        code = response.optInt(JSON_COD);
    }

    public int getCode() {
        return code;
    }
}
