package phunware.assignment.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import phunware.assignment.weatherapp.R;
import phunware.assignment.weatherapp.Utils.GlobalUtils;
import phunware.assignment.weatherapp.Utils.SimpleDividerItemDecoration;
import phunware.assignment.weatherapp.WeatherAppApplication;
import phunware.assignment.weatherapp.adapters.ZipCodeAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = MainActivity.class.getSimpleName();

    ArrayList<String> initialZipCodes;

    //Views
    RecyclerView mRecyclerView;
    FloatingActionButton mFloatingActionButton;
    RecyclerView.LayoutManager mLayoutManager;
    ZipCodeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the zip codes from shared prefs
        String zipCodesAsString = WeatherAppApplication.getSharedPreferences()
                                        .getString(GlobalUtils.PREFS_KEY_ZIPCODES, "");


        if(!TextUtils.isEmpty(zipCodesAsString)){
            initialZipCodes = GlobalUtils.getArryListFromString(zipCodesAsString);
        }else{

            initialZipCodes = new ArrayList<>(Arrays.asList("22030", "22031", "73301"));

            //save the initial values in prefs
            String zipCodes = GlobalUtils.getStringFromListOfStrings(initialZipCodes);
            WeatherAppApplication.getSharedPreferences().edit()
                    .putString(GlobalUtils.PREFS_KEY_ZIPCODES, zipCodes).apply();
        }



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.addButton);
        mFloatingActionButton.setOnClickListener(this);

        //LayoutManager for recycler view
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Item decorator for recycler view
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(MainActivity.this));

        //adapter for recycler view
        mAdapter = new ZipCodeAdapter(initialZipCodes, new ZipCodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String zipCode) {

                Log.i(TAG, "ZipCode clicked : "+ zipCode);

                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(GlobalUtils.ARG_ZIPCODE_MAIN_TO_DETAILS, zipCode);
                startActivity(detailsIntent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addButton:
                Intent addCodeIntent = new Intent(MainActivity.this, AddZipCodeActivity.class);
                startActivityForResult(addCodeIntent, GlobalUtils.REQUEST_CODE_MAIN_TO_ADDCODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GlobalUtils.REQUEST_CODE_MAIN_TO_ADDCODE){
            if(resultCode == RESULT_OK){
                String addedZipCode = data.getStringExtra(GlobalUtils.RESULT_PARAM_ADDCODE_TO_MAIN);
                ((ZipCodeAdapter)(mRecyclerView.getAdapter())).addZipCode(addedZipCode);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

}
