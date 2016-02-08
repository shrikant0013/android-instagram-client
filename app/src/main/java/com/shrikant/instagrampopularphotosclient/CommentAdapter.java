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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Shrikant Pandhare.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    static class ViewHolder {
        @Bind(R.id.ivCommentOwner) ImageView ivCommentOwner;
        @Bind(R.id.tvCommentOwner) TextView tvCommentOwner;
        @Bind(R.id.tvCommentDateCreated) TextView tvCommentDateCreated;
        @Bind(R.id.tvCommentText) TextView tvCommentText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public CommentAdapter(Context context,List<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.comment_item_layout, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivCommentOwner.setImageResource(0); //clear off
        Picasso.with(getContext()).load(comment.profileURL).into(viewHolder.ivCommentOwner);

        viewHolder.tvCommentOwner.setText(comment.userName);

        viewHolder.tvCommentText.setText(comment.commentText);

        CharSequence dateFormat = DateUtils.getRelativeTimeSpanString(comment.createdTime * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL);

        viewHolder.tvCommentDateCreated.setText(dateFormat.toString());

        return convertView;
    }
}
