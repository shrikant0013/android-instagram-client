package com.shrikant.instagrampopularphotosclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Shrikant Pandhare
 */
public class PhotoAdapter extends ArrayAdapter<PhotoResource> {

    // View lookup cache
    static class ViewHolder {
        @Bind(R.id.ivPhoto) ImageView imageView;
        @Bind(R.id.ivOwner) ImageView ivOwner;
        @Bind(R.id.tvOwner) TextView tvOwner;
        @Bind(R.id.tvCaption) TextView tvCaption;
        @Bind(R.id.tvLikes) TextView tvLikes;
        @Bind(R.id.tvDateCreated) TextView tvDateCreated;
        @Bind(R.id.tvCommet_1) TextView tvComment1;
        @Bind(R.id.tvCommet_2) TextView tvComment2;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public PhotoAdapter(Context context, List<PhotoResource> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoResource photo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_item_layout,
                    parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder == null)
            Log.d("ViewHolder", "ViewHolder from convertView.getTag is null");

        // Populate the data into the template view using the data object

        //ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPhoto);
        viewHolder.imageView.setImageResource(0); //clear off
        Picasso.with(getContext()).load(photo.getUrl()).into(viewHolder.imageView);

        //ImageView ivOwner = (ImageView) convertView.findViewById(R.id.ivOwner);
        viewHolder.ivOwner.setImageResource(0); //clear off
        Picasso.with(getContext()).load(photo.getOwnerURL()).fit()
                .centerCrop().into(viewHolder.ivOwner);

        //TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        viewHolder.tvCaption.setText(photo.getCaption());

        //TextView tvOwner = (TextView) convertView.findViewById(R.id.tvOwner);
        viewHolder.tvOwner.setText(photo.getOwner());

        //TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        viewHolder.tvLikes.setText(Long.toString(photo.getLikeCount()) + " likes");

        //TextView tvDateCreated = (TextView) convertView.findViewById(R.id.tvDateCreated);
        CharSequence displayCreated = DateUtils.getRelativeTimeSpanString(
                photo.getCreatedTime() * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL);
        viewHolder.tvDateCreated.setText(displayCreated);

        List<Comment> comments = photo.getComment();

        if (comments != null && comments.size() > 0) {
            viewHolder.tvComment1.setText(
                    comments.get(0).userName + ": " + comments.get(0).commentText);
        }
        if (comments != null && comments.size() > 1) {
            viewHolder.tvComment2.setText(
                    comments.get(1).userName + ": " + comments.get(1).commentText);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
