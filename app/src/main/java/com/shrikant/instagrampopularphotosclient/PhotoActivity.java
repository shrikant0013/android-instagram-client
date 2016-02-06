package com.shrikant.instagrampopularphotosclient;

import android.os.Bundle;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PhotoActivity extends AppCompatActivity {

    final static String URL = "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
    List<PhotoResource> photos;
    PhotoAdapter photoAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        photos = new ArrayList<>();
        fetchPopularPhotos();

        photoAdapter = new PhotoAdapter(this, photos);
        listView = (ListView) findViewById(R.id.lvPhotos);
        listView.setAdapter(photoAdapter);

    }

    /*
    Type : { “data" => [ x ] => “type" }  <- image, video
    URL:  { "data" => [ x ] => “images” => “standard_resolution” . url }
    Caption:  { "data" => [ x ] => “images” => “caption” . “text” }
    Author Name: { “data => [ x ] => “user” . “username” }
     */
    public void fetchPopularPhotos(){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray dataArray =  response.getJSONArray("data");
                    //Log.i("Async length", "" + dataArray.length());
                    for (int i = 0; i < dataArray.length(); i++) {
                        //Log.i("Async i", "" + i);
                        JSONObject obj = dataArray.getJSONObject(i);
                        //Type : { “data" => [ x ] => “type" }  <- image, video
                        //Log.i("Async type", obj.getString("type"));
                        if (obj.getString("type").equalsIgnoreCase("image")) {
                            PhotoResource photoResource = new PhotoResource();
                            //URL:  { "data" => [ x ] => “images” => “standard_resolution” . url }
                            photoResource.setUrl(obj.getJSONObject("images")
                                    .getJSONObject("standard_resolution").getString("url"));
                            //Caption:  { "data" => [ x ] => “images” => “caption” . “text” }
                            photoResource.setCaption(obj.getJSONObject("caption").getString("text"));

                            //Author Name: { “data => [ x ] => “user” . “username” }
                            photoResource.setOwner(obj.getJSONObject("user").getString("username"));

                            //Likes
                            photoResource.setLikeCount(obj.getJSONObject("likes").getLong("count"));

                            //owner url
                            //Likes
                            photoResource.setOwnerURL(obj.getJSONObject("user")
                                    .getString("profile_picture"));

                            Log.i("Async", photoResource.getOwner());

                            photos.add(photoResource);
                        }
                    }
                } catch(JSONException e) {
                    Log.i("Json exception", "In exception: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong getting phots",
                            Toast.LENGTH_SHORT).show();
                }
                photoAdapter.notifyDataSetChanged();
                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("Async", "in onFailure");
                Toast.makeText(getApplicationContext(), "Something went wrong getting photos",
                        Toast.LENGTH_SHORT).show();
                //super.onFailure(statusCode, headers, responseString, throwable);
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
}
