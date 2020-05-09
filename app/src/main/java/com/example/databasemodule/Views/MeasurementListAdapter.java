package com.example.databasemodule.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.R;

import java.util.ArrayList;

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MyViewHolder> {
    private ArrayList<Measurement> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView temp;
        ConstraintLayout parentLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.measurementTextView);
            parentLayout = itemView.findViewById(R.id.measurement_parent_layout);
        }
    }

    public MeasurementListAdapter(Context context, ArrayList<Measurement> myDataset)
    {
        mDataset = myDataset;
        mContext = context;
    }

    @NonNull
    @Override
    public MeasurementListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measurement_item, parent, false);

        MeasurementListAdapter.MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementListAdapter.MyViewHolder holder, int position) {
        Measurement measurement = mDataset.get(position);
        holder.temp.setText(String.valueOf(measurement.TEMP));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Info", "onClick: clicked on: " + String.valueOf(measurement.TEMP));

                Toast.makeText(mContext, String.valueOf(measurement.TEMP), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
