package phunware.assignment.weatherapp.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bharath on 12/09/16.
 */
public class GlobalUtils {

    //zipcode param sent from MainActivity to DetailsActivity
    public static final String ARG_ZIPCODE_MAIN_TO_DETAILS = "zipCodeFromMainToDetails";

    //OpenWeatherMap API Key
    public static final String API_KEY = "86af4f47537d7ef47e825854a1d57d53";

    //URL for open weather map api
    public static final String OWM_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    //value of code for successful request
    public static final int OWM_REQUEST_SUCCESS = 200;

    //value of code for invalid zipcode
    public static final int OWM_REQUEST_INVALID_ZIPCODE = 404;

    //Request code for starting AddZipCodeActivity from MainActivity
    public static final int REQUEST_CODE_MAIN_TO_ADDCODE = 0;

    //Param key for result from AddZipCode to MainActivity
    public static final String RESULT_PARAM_ADDCODE_TO_MAIN = "addedCode";

    //Key for saving and getting the zip codes from shared preferences
    public static final String PREFS_KEY_ZIPCODES = "zipCodes";

    /**
     *  Converts the temp from Kelvin to Fahrenheit
     *  @param tempInKelvin Temperature value in Kelvin
     *  @return Temperature in fahrenheit
     */
    public static double getFahrenheitFromKelvin(double tempInKelvin){
        return (((tempInKelvin - 273) * 9.0/5) + 32);
    }

    /**
     * Creates a single string by appending all the strings from the list
     * @param input List of strings
     * @return a Single string
     */
    public static String getStringFromListOfStrings(ArrayList<String> input){

        String prefix = "";
        StringBuilder finalString = new StringBuilder();

        for (String each : input) {
            finalString.append(prefix);
            prefix = ", ";
            finalString.append(each);
        }

        return finalString.toString();
    }

    /**
     * Creates a list of strings by splitting a single string separated by comma.
     * @param input Single string containing values separated by comma
     * @return  ArrayList   list of strings
     */
    public static ArrayList<String> getArryListFromString(String input){
       return  new ArrayList<>(Arrays.asList(input.split(", ")));
    }
}
