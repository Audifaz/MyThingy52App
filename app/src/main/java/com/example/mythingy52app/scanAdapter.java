package com.example.mythingy52app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class scanAdapter extends RecyclerView.Adapter<scanAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    public LinkedList<String> mList = new LinkedList<String>();
    public scanAdapter(Context context, LinkedList<String> list){
        this.mList=list;
        mInflater= LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public scanAdapter mAdapter;
        public ViewHolder(@NonNull View itemView, scanAdapter adapter) {
            super(itemView);
            this.mTextView=itemView.findViewById(R.id.scanText);
            this.mAdapter=adapter;
        }
    }

    @NonNull
    @Override
    public scanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.scanlist, parent, false);
        return new ViewHolder(mView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull scanAdapter.ViewHolder holder, int position) {
        String current = mList.get(position);
        holder.mTextView.setText(current);
    }

    @Override
    public int getItemCount() {
        if(mList==null){
        return 0;}
        else{
            return mList.size();
        }
    }
}
