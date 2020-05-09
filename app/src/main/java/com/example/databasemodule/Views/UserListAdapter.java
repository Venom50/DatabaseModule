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

import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private ArrayList<User> mDataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView login;
        ConstraintLayout parentLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            login = itemView.findViewById(R.id.loginTextView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public UserListAdapter(Context context, ArrayList<User> myDataset)
    {
        mDataset = myDataset;
        mContext = context;
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = mDataset.get(position);
        holder.login.setText(user.login);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Info", "onClick: clicked on: " + user.login);

                Toast.makeText(mContext, user.login, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
