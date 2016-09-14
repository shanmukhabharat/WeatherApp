package phunware.assignment.weatherapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddZipCodeActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = AddZipCodeActivity.class.getSimpleName();

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
    private TextInputLayout zipCodeInputLayout;
    private EditText zipCodeEditText;
    private LinearLayout zipCodeLayout;
    private Button submitButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details);

        //initialise the views
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
        zipCodeInputLayout = (TextInputLayout) findViewById(R.id.details_zipcode_text_input_layout);
        zipCodeEditText = (EditText) findViewById(R.id.details_zipcode_edit);
        zipCodeLayout = (LinearLayout) findViewById(R.id.details_zipcode_layout);
        submitButton = (Button) findViewById(R.id.details_submit_button);
        progressDialog = new ProgressDialog(AddZipCodeActivity.this);

        //setting the views as invisible when we first open the page
        setVisibilityForDetailsViews(View.INVISIBLE);

        //Onclick listener for submit
        submitButton.setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();

        // Cancel the requests from the RequestQueue.
        WeatherAppApplication.getApplicationInstance()
                .cancelPendingRequests(TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addzipcode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.addzipcode_add:

                String codeToBeAdded = zipCodeEditText.getText().toString().trim();

                //if the zipcode is not empty set it as result
                if(!TextUtils.isEmpty(codeToBeAdded)){

                    //store the value in shared preferences
                    String currentZipcodes = WeatherAppApplication.getSharedPreferences().
                                                getString(GlobalUtils.PREFS_KEY_ZIPCODES, "");

                    currentZipcodes = currentZipcodes + ", "+ codeToBeAdded;
                    WeatherAppApplication.getSharedPreferences().edit()
                            .putString(GlobalUtils.PREFS_KEY_ZIPCODES, currentZipcodes).apply();


                    //start the result intent
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(GlobalUtils.RESULT_PARAM_ADDCODE_TO_MAIN, codeToBeAdded);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }else{
                    //show an error message
                    noWeatherText.setVisibility(View.VISIBLE);
                    setVisibilityForDetailsViews(View.INVISIBLE);
                    noWeatherText.setText(getString(R.string.error_invalid_zipcode));
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.details_submit_button:

                //Todo : Validate the zipcode before making the request
                showProgressDialog();
                getWeatherDetails(zipCodeEditText.getText().toString().trim());
                break;
        }
    }

    /**
     * Shows a progress dialog with message "loading.."
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

    /**
     * Gets the weather details from the server by making a volley request
     */
    private void getWeatherDetails(String zipcode){

        String URL_FINAL = GlobalUtils.OWM_BASE_URL +
                zipcode + "&appid="+ GlobalUtils.API_KEY;

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
                            String message = ErrorHandler.getMessage(error, AddZipCodeActivity.this);

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

        }else if(weather.getCode() == GlobalUtils.OWM_REQUEST_INVALID_ZIPCODE){

            //change the ui to display the error message
            noWeatherText.setVisibility(View.VISIBLE);
            setVisibilityForDetailsViews(View.INVISIBLE);
            noWeatherText.setText(getString(R.string.error_invalid_zipcode));
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
}
