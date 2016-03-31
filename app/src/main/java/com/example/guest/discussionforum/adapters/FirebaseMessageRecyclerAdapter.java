package com.example.guest.discussionforum.adapters;

/**
 * Created by Guest on 3/30/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.models.SnapTweet;
import com.example.guest.discussionforum.ui.MainActivity;
import com.example.guest.discussionforum.util.FirebaseRecyclerAdapter;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by Guest on 3/29/16.
 *
 */
public class FirebaseMessageRecyclerAdapter extends FirebaseRecyclerAdapter<MessageListViewHolder, SnapTweet> {
    RecyclerView mRecyclerView;

    public FirebaseMessageRecyclerAdapter(Query query, Class<SnapTweet> itemClass) {
        super(query, itemClass);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }


    @Override
    public MessageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snaptweet_list_item, parent, false);
        return new MessageListViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(final MessageListViewHolder holder, int position) {
        holder.bindMessage(getItem(position));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(getItem(holder.getAdapterPosition()).getRef());
                ref.removeValue();
            }
        });
    }

    @Override
    protected void itemAdded(SnapTweet item, String key, int position) {

        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected void itemChanged(SnapTweet oldItem, SnapTweet newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(SnapTweet item, String key, int position) {
    }

    @Override
    protected void itemMoved(SnapTweet item, String key, int oldPosition, int newPosition) {

    }

}