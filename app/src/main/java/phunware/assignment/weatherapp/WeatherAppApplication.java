package phunware.assignment.weatherapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bharath on 13/09/16.
 */
public class WeatherAppApplication extends Application{

    public static final String TAG = "WeatherAppApplication";

    private RequestQueue mRequestQueue;

    private static WeatherAppApplication mApplicationInstance;

    private static SharedPreferences sharedPreferences;


    /**
     * Returns an Instance of application class
     * @return  Singleton instance of application class
     */
    public static synchronized WeatherAppApplication getApplicationInstance(){
        return mApplicationInstance;
    }

    /**
     * Returns the shared preferences
     * @return  SharedPreferences
     */
    public static synchronized SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //initialising the application instance
        mApplicationInstance = WeatherAppApplication.this;

        //initialising the shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * @return request queue of volley
     */
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param request   request to be added
     * @param tag   set the tag for the request
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    /**
     * Cancels all pending requests using a TAG
     *
     * @param tag   tag to be used
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
