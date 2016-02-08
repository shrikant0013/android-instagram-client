package com.shrikant.instagrampopularphotosclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * @author Shrikant Pandhare
 */
public class PhotoActivity extends AppCompatActivity {

    final static String URL =
            "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
    List<PhotoResource> photos;
    PhotoAdapter photoAdapter;
    List<PhotoResource> photosLocal;
    @Bind(R.id.lvPhotos)ListView listView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        photos = new ArrayList<>();

        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
                fetchPopularPhotos(true);
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchPopularPhotos(false);

        photoAdapter = new PhotoAdapter(this, photos);
        listView.setAdapter(photoAdapter);

    }

    public void fetchPopularPhotos(final boolean isRefreshed){
        photosLocal = new ArrayList<>();
        if (isRefreshed) {
            photoAdapter.clear();
        }

        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray dataArray = new JSONArray();
                    if (response.optJSONArray("data") != null) {
                        dataArray = response.getJSONArray("data");
                    }

                    for (int i = 0; i < dataArray.length(); i++) {
                        PhotoResource photoResource = new PhotoResource();
                        JSONObject obj = dataArray.getJSONObject(i);
                        //Type : { “data" => [ x ] => “type" }  <- image, video
                        if (obj.optString("type") != null &&
                                (obj.getString("type").equalsIgnoreCase("image"))) {
                                //|| obj.getString("type").equalsIgnoreCase("video"))) {

                            //Created time: { "data" => [ x ] => "created_time" }
                            photoResource.setCreatedTime(
                                    Long.parseLong(obj.optString("created_time")));

                            //URL:  { "data" => [ x ] => “images” => “standard_resolution” . url }
                            JSONObject jsonImagesObject = obj.optJSONObject("images");
                            if (jsonImagesObject != null) {
                                JSONObject jsonStandardResolutionObject =
                                        jsonImagesObject.optJSONObject("standard_resolution");
                                if (jsonStandardResolutionObject != null) {
                                    photoResource.setUrl(jsonStandardResolutionObject
                                            .optString("url"));
                                }
                            }
                            //Caption:  { "data" => [ x ] => “images” => “caption” . “text” }
                            JSONObject jsonCaptionObject = obj.optJSONObject("caption");
                            if (jsonCaptionObject != null) {
                                photoResource.setCaption(jsonCaptionObject.optString("text"));
                            }

                            //Owner Name: { “data => [ x ] => “user” . “username” }
                            //Owner Profile URL: {“data => [ x ] => “user” . “profile_picture”}
                            JSONObject jsonUserObject = obj.optJSONObject("user");
                            if (jsonUserObject != null) {
                                photoResource.setOwner(jsonUserObject.optString("username"));
                                photoResource.setOwnerURL(jsonUserObject
                                        .optString("profile_picture"));
                            }

                            //Likes: { “data => [ x ] => "likes" . "count" }
                            JSONObject jsonLikesObject = obj.optJSONObject("likes");
                            if (jsonLikesObject != null) {
                                photoResource.setLikeCount(jsonLikesObject.optLong("count"));
                            }

                            //Comments created time:
                            // { “data[ x ] => "comments" => "data[x]" => created_time }
                            //Comments text:
                            // { “data[ x ] => "comments" => "data[x]" => text }
                            //Comments username:
                            // { “data[ x ] => "comments" => "data[x]" => "from" . "username" }
                            //Comments profile url:
                            // { “data[ x ] => "comments" =>
                            // "data[x]"  => "from" . "profile_picture" }

                            List<Comment> comments = new ArrayList<>();
                            JSONObject jsonCommentsObject = obj.optJSONObject("comments");
                            if (jsonCommentsObject != null
                                    && jsonCommentsObject.optJSONArray("data") != null) {
                                JSONArray commentsDataArray =
                                        jsonCommentsObject.optJSONArray("data");

                                for (int j = 0; j < commentsDataArray.length(); j++) {
                                    Comment comment = new Comment();
                                    JSONObject commentJsonObject =
                                            commentsDataArray.getJSONObject(j);

                                    if (commentJsonObject != null) {
                                        comment.commentText = commentJsonObject.optString("text");
                                        comment.createdTime =
                                                Long.parseLong(commentJsonObject
                                                        .optString("created_time"));

                                        JSONObject commentFromJsonObject =
                                                commentJsonObject.getJSONObject("from");

                                        if (commentFromJsonObject != null) {
                                            comment.userName =
                                                    commentFromJsonObject.optString("username");
                                            comment.profileURL =
                                                    commentFromJsonObject.optString("profile_picture");
                                        }
                                    }
                                    comments.add(comment);
                                }
                                Collections.sort(comments, new Comparator<Comment>() {
                                    @Override
                                    public int compare(Comment lhs, Comment rhs) {
                                        return (int) (rhs.createdTime - lhs.createdTime);
                                    }
                                });

                                photoResource.setComment(comments);
                            }
                            photos.add(photoResource);
                        }
                    }
                } catch (JSONException e) {
                    Log.i("Json exception", "In exception: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong getting photos",
                            Toast.LENGTH_SHORT).show();
                }
                if (isRefreshed) {
                    photoAdapter.addAll(photos);
                    swipeRefreshLayout.setRefreshing(false);
                } else
                    photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("Async", "In onFailure");
                Toast.makeText(getApplicationContext(), "Something went wrong getting photos",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @OnRefreshListener(R.id.swipeContainer)
//    public void refresh() {
//
//    }

}
