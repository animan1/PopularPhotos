package com.codepath.popularphotos;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;


public class StreamActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stream);
    populatePhotos();
  }

  private void populatePhotos() {
    Photo.fetchPopular(new PhotoResponseHandler() {
      @Override
      public void onSuccess(ArrayList<Photo> photos) {
        PhotosAdapter photosAdapter = new PhotosAdapter(StreamActivity.this, photos);
        ListView photosListView = (ListView)findViewById(R.id.photosListView);
        photosListView.setAdapter(photosAdapter);
      }
    });
  }
}
