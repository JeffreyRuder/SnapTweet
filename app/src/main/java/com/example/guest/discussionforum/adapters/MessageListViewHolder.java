package com.example.guest.discussionforum.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.models.SnapTweet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 3/30/16.
 */
public class MessageListViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.contentTextView) TextView mContentTextView;
    @Bind(R.id.authorTextView) TextView mAuthorTextView;
    @Bind(R.id.deleteButton) Button mDeleteButton;
    private ArrayList<SnapTweet> mSnapTweets = new ArrayList<>();
    private Context mContext;


    public MessageListViewHolder(View itemView, ArrayList<SnapTweet> tasks) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
        mSnapTweets = tasks;
    }


    public void bindMessage(SnapTweet snapTweet) {
        mContentTextView.setText(snapTweet.getContent());
        mAuthorTextView.setText(snapTweet.getUserName());
    }
}

