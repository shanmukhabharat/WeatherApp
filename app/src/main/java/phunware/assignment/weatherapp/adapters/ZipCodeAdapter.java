package phunware.assignment.weatherapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import phunware.assignment.weatherapp.R;

/**
 * Created by Bharath on 12/09/16.
 */
public class ZipCodeAdapter extends RecyclerView.Adapter<ZipCodeAdapter.ViewHolder> {

    private final String TAG = ZipCodeAdapter.class.getSimpleName();

    private ArrayList<String> mZipCodes;
    private final OnItemClickListener listener;

    public ZipCodeAdapter(ArrayList<String> zipCodes, OnItemClickListener listener){
        this.mZipCodes = zipCodes;
        this.listener = listener;
    }

    /**
     * Interface to be implemented for listening to OnItemClick
     */
    public interface OnItemClickListener {
        void onItemClick(String zipCode);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_item_recycler_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String currentZipCode = mZipCodes.get(position);

        //binding the current zipcode and listener
        holder.bind(currentZipCode, listener);
    }

    @Override
    public int getItemCount() {
        return mZipCodes.size();
    }


    /**
     * View Holder class for RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView zipCodeText;

        public ViewHolder(View itemView) {
            super(itemView);
            zipCodeText = (TextView) itemView.findViewById(R.id.each_item_zipcode);
        }


        /**
         * Binds the value and OnItemClickListener to the Views
         * @param currentZipCodeText    zipCode value to be displayed
         * @param listener  setting the listener for onItemClick
         */
        public void bind(final String currentZipCodeText, final OnItemClickListener listener) {

            zipCodeText.setText(currentZipCodeText);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(currentZipCodeText);
                }

            });

        }

    }

    /**
     * Sets new list of zipcodes to the adapter
     * @param items
     */
    public void setItems(ArrayList<String> items){
        this.mZipCodes = items;
    }

    /**
     * Adds a new zipcode to the list of zip codes
     * @param zipcode
     */
    public void addZipCode(String zipcode){
        this.mZipCodes.add(zipcode);
    }

}
