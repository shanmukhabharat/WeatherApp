package phunware.assignment.weatherapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import phunware.assignment.weatherapp.OWMResponses.OWMCurrentWeatherResponse;
import phunware.assignment.weatherapp.R;
import phunware.assignment.weatherapp.Utils.ErrorHandler;
import phunware.assignment.weatherapp.Utils.GlobalUtils;
import phunware.assignment.weatherapp.WeatherAppApplication;


public class DetailsActivity extends AppCompatActivity {

    private final String TAG = DetailsActivity.class.getSimpleName();

    protected String mZipCode;

    //Views
    private TextView city;
    private TextView humidity;
    private TextView pressure;
    private TextView temp_range;
    private TextView weatherCond;
    private ImageView weatherImage;
    private LinearLayout weatherConditionsLayout;
    private LinearLayout humidityLayout;
    private LinearLayout pressureLayout;
    private LinearLayout tempRangeLayout;
    private TextView noWeatherText;
    private ProgressDialog progressDialog;
    private LinearLayout zipCodeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details);

        Bundle params = getIntent().getExtras();
        mZipCode = params.getString(GlobalUtils.ARG_ZIPCODE_MAIN_TO_DETAILS);

        //initialising the views
        city = (TextView) findViewById(R.id.details_city);
        humidity = (TextView) findViewById(R.id.details_humidity);
        pressure = (TextView) findViewById(R.id.details_pressure);
        temp_range = (TextView) findViewById(R.id.details_temp_range);
        weatherCond = (TextView) findViewById(R.id.details_weather_conditions);
        weatherImage = (ImageView) findViewById(R.id.details_weather_conditions_image);
        weatherConditionsLayout = (LinearLayout) findViewById(R.id.details_weather_conditions_parent);
        humidityLayout = (LinearLayout) findViewById(R.id.details_humidity_parent);
        pressureLayout = (LinearLayout) findViewById(R.id.details_pressure_parent);
        tempRangeLayout = (LinearLayout) findViewById(R.id.details_temp_range_parent);
        noWeatherText = (TextView) findViewById(R.id.details_weather_not_available);
        zipCodeLayout = (LinearLayout) findViewById(R.id.details_zipcode_layout);
        progressDialog = new ProgressDialog(DetailsActivity.this);

        //Hide the layout to enter zip code
        zipCodeLayout.setVisibility(View.GONE);

        String URL_FINAL = GlobalUtils.OWM_BASE_URL +
                mZipCode + "&appid="+ GlobalUtils.API_KEY;

        //Start the progress dialog before we start the request
        showProgressDialog();

        //ToDo : Caching the response from server

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FINAL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            //dismiss the progress dialog
                            dismissProgressDialog();

                            Log.d(TAG, "response from OWM : "+response);

                            //create a JSON object from the string response
                            JSONObject responseObj = new JSONObject(response);

                            //create a custom response object from current weather
                            OWMCurrentWeatherResponse OWMResponse =
                                    new OWMCurrentWeatherResponse(responseObj);

                            displayOWMResponseOnUi(OWMResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //dismiss the progress dialog
                        dismissProgressDialog();

                        //get the appropriate message from error handler
                        String message = ErrorHandler.getMessage(error, DetailsActivity.this);

                        //change the ui to display the error message
                        noWeatherText.setVisibility(View.VISIBLE);
                        setVisibilityForDetailsViews(View.INVISIBLE);
                        noWeatherText.setText(message);
                    }
                });

        // Add the request to the RequestQueue.
        WeatherAppApplication.getApplicationInstance()
                .addToRequestQueue(stringRequest, TAG);

    }


    /**
     * Displays the current weather conditions from OWM on screen
     * @param weather   Current weather conditions
     */
    private void displayOWMResponseOnUi(OWMCurrentWeatherResponse weather){

        //If the request is successful and we have weather data
        if(weather.getCode() == GlobalUtils.OWM_REQUEST_SUCCESS){


            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.CEILING);

            //setting city, humidity, pressure
            city.setText(weather.getName());
            humidity.setText(df.format(weather.getMain().getHumidity()));
            pressure.setText(df.format(weather.getMain().getPressure()));

            //setting the temp range
            double minTempInF = GlobalUtils.getFahrenheitFromKelvin(weather.getMain().getTemp_min());
            double maxTempInF = GlobalUtils.getFahrenheitFromKelvin(weather.getMain().getTemp_max());
            String tempRangeAsString = df.format(maxTempInF)+"/"+df.format(minTempInF)+" F";
            temp_range.setText(tempRangeAsString);

            //weather cond
            weatherCond.setText(weather.getWeather().getDescription());

            //ToDo : weatherImage and other weather data from OWM

            //Setting the visibilities for views
            setVisibilityForDetailsViews(View.VISIBLE);
            noWeatherText.setVisibility(View.GONE);

        }
    }

    /**
     * Sets the visibility for all the views showing weather details
     * @param visibility    Visibility to be set {VISIBLE, GONE or INVISIBLE}
     */
    private void setVisibilityForDetailsViews(int visibility){
        weatherConditionsLayout.setVisibility(visibility);
        humidityLayout.setVisibility(visibility);
        pressureLayout.setVisibility(visibility);
        tempRangeLayout.setVisibility(visibility);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Cancel the requests from the RequestQueue.
        WeatherAppApplication.getApplicationInstance()
                .cancelPendingRequests(TAG);
    }

    /**
     * Shows the progress dialog on screen
     */
    private void showProgressDialog(){
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

    /**
     * Dismisses the progress dialog if its showing
     */
    private void dismissProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
