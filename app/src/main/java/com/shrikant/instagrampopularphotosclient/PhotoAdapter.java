package com.shrikant.instagrampopularphotosclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by spandhare on 2/5/16.
 */
public class PhotoAdapter extends ArrayAdapter<PhotoResource> {
    public PhotoAdapter(Context context, List<PhotoResource> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoResource photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_item_layout,
                    parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPhoto);
        imageView.setImageResource(0); //clear off
        Picasso.with(getContext()).load(photo.getUrl()).into(imageView);

        ImageView ivOwner = (ImageView) convertView.findViewById(R.id.ivOwner);
        ivOwner.setImageResource(0); //clear off
        Picasso.with(getContext()).load(photo.getOwnerURL()).fit().centerCrop().into(ivOwner);

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.getCaption());

        TextView tvOwner = (TextView) convertView.findViewById(R.id.tvOwner);
        tvOwner.setText(photo.getOwner());

        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        tvLikes.setText(Long.toString(photo.getLikeCount()) + " likes");

        TextView tvDateCreated = (TextView) convertView.findViewById(R.id.tvDateCreated);
        CharSequence displayCreated = DateUtils.getRelativeTimeSpanString(
                photo.getCreatedTime() * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL);
        tvDateCreated.setText(displayCreated);

        return convertView;
    }
}
