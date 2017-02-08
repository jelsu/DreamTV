package com.teaching.jelus.dreamtv.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teaching.jelus.dreamtv.R;
import com.teaching.jelus.dreamtv.database.MostPopularTvDbHelper;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Cursor mCursor;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mOriginalNameTextView;

        public ViewHolder(View v) {
            super(v);
            mOriginalNameTextView = (TextView) v.findViewById(R.id.text_original_name);
        }
    }

    public RecyclerAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int originalNameColIndex = mCursor.getColumnIndex(MostPopularTvDbHelper.ORIGINAL_NAME_COLUMN);
        String originalName = mCursor.getString(originalNameColIndex);
        holder.mOriginalNameTextView.setText(originalName);
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        this.mCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }
}