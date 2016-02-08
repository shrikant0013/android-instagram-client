package com.shrikant.instagrampopularphotosclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity {

    @Bind(R.id.lvComments) ListView lvComments;
    @Bind(R.id.toolbarComments) Toolbar toolbarComments;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarComments);
        comments = getIntent().getParcelableArrayListExtra("comments");
        Toast.makeText(this, "Activity called", Toast.LENGTH_SHORT).show();
        commentAdapter = new CommentAdapter(this, comments);
        lvComments.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }
}
