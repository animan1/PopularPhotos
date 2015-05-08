package com.codepath.popularphotos;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Photo {
  private static AsyncHttpClient client = new AsyncHttpClient();

  String caption;
  String username;
  String imageUrl;

  public Photo(String caption, String username, String imageUrl) {
    this.caption = caption;
    this.username = username;
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return this.caption;
  }

  public static void fetchPopular(final PhotoResponseHandler handler) {
    client.get("https://api.instagram.com/v1/media/popular?client_id=c24aacde80fe44e6bd26404e5e9bca7b", new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
          JSONArray dataArray = response.getJSONArray("data");
          for (int index = 0; index < dataArray.length(); index++) {
            JSONObject dataObject = dataArray.getJSONObject(index);
            if (!dataObject.getString("type").equals("image")) {
              continue;
            }
            String caption = dataObject.getJSONObject("caption").getString("text");
            String username = dataObject.getJSONObject("user").getString("username");
            String imageUrl = dataObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            photos.add(new Photo(caption, username, imageUrl));
          }
        } catch (JSONException e) {
          Log.i(null, response.toString());
          throw new RuntimeException(e);
        }
        handler.onSuccess(photos);
      }
    });
  }
}
