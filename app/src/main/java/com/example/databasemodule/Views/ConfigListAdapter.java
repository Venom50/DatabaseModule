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

import com.example.databasemodule.Models.Config;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;

import java.util.ArrayList;

public class ConfigListAdapter extends RecyclerView.Adapter<ConfigListAdapter.MyViewHolder> {
    private ArrayList<Config> mDataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tempF;
        ConstraintLayout parentLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tempF = itemView.findViewById(R.id.tempFTextView);
            parentLayout = itemView.findViewById(R.id.config_parent_layout);
        }
    }

    public ConfigListAdapter(Context context, ArrayList<Config> myDataset)
    {
        mDataset = myDataset;
        mContext = context;
    }

    @NonNull
    @Override
    public ConfigListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.config_item, parent, false);

        ConfigListAdapter.MyViewHolder holder = new ConfigListAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigListAdapter.MyViewHolder holder, int position) {
        Config config = mDataset.get(position);
        holder.tempF.setText(String.valueOf(config.TEMP_F));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Info", "onClick: clicked on: " + String.valueOf(config.TEMP_F));

                Toast.makeText(mContext, String.valueOf(config.TEMP_F), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
