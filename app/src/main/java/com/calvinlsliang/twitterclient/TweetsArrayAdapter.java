package com.calvinlsliang.twitterclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.calvinlsliang.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cliang on 11/7/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    private Tweet tweet;
    private String screenname;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    private static class ViewHolder {
        ImageView ivProfile;
        TextView tvUsername;
        TextView tvBody;
        TextView tvRelativeTime;
        TextView tvName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tweet = getItem(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        screenname = tweet.getUser().getScreenName();
        viewHolder.tvUsername.setText(screenname);
        viewHolder.tvName.setText(tweet.getUser().getName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvRelativeTime.setText(tweet.getCreatedAt());
        viewHolder.ivProfile.setBackgroundResource(android.R.color.transparent);

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfile);

        viewHolder.ivProfile.setOnClickListener(new View.OnClickListener() {
            String name = screenname;

            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                i.putExtra("screenname", name);
                v.getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
