package com.codepath.popularphotos;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class StreamActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stream);
    setupPhotoSwipeContainer();
  }

  private void setupPhotoSwipeContainer() {
    SwipeRefreshLayout photosSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.photosSwipeContainer);
    PhotoRefreshListener photoRefreshListener = new PhotoRefreshListener(photosSwipeContainer);
    photosSwipeContainer.setOnRefreshListener(photoRefreshListener);
    photoRefreshListener.onRefresh();
  }

  class PhotoRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    final SwipeRefreshLayout photosSwipeContainer;

    PhotoRefreshListener(SwipeRefreshLayout photosSwipeContainer) {
      this.photosSwipeContainer = photosSwipeContainer;
    }

    @Override
    public void onRefresh() {
      Photo.fetchPopular(new PhotoResponseHandler() {
        @Override
        public void onSuccess(ArrayList<Photo> photos) {
          PhotosAdapter photosAdapter = new PhotosAdapter(StreamActivity.this, photos);
          ListView photosListView = (ListView) findViewById(R.id.photosListView);
          photosListView.setAdapter(photosAdapter);
          photosSwipeContainer.setRefreshing(false);
        }
      });
    }
  }
}
