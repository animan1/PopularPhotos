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
  int imageHeight;

  public Photo(String caption, String username, String imageUrl, int imageHeight) {
    this.caption = caption;
    this.username = username;
    this.imageUrl = imageUrl;
    this.imageHeight = imageHeight;
  }

  @Override
  public String toString() {
    return this.caption;
  }

  public static void fetchPopular(final PhotoResponseHandler handler) {
    client.get("https://api.instagram.com/v1/media/popular?client_id=c24aacde80fe44e6bd26404e5e9bca7b", new JsonHttpResponseHandler() {
      final JSONObject EMPTY_OBJECT = new JSONObject();

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
          JSONArray dataArray = response.optJSONArray("data");
          if (dataArray == null) {
            fetchPopular(handler);
          }
          for (int index = 0; index < dataArray.length(); index++) {
            JSONObject dataObject = dataArray.getJSONObject(index);
            if (!dataObject.getString("type").equals("image")) {
              continue;
            }
            String caption = getObject(dataObject, "caption").optString("text");
            String username = getObject(dataObject, "user").optString("username");
            JSONObject standardResolutionObject = getObject(getObject(dataObject, "images"), "standard_resolution");
            String imageUrl = standardResolutionObject.optString("url");
            int imageHeight = standardResolutionObject.optInt("height");
            photos.add(new Photo(caption, username, imageUrl, imageHeight));
          }
        } catch (JSONException e) {
          Log.i(null, response.toString());
          throw new RuntimeException(e);
        }
        handler.onSuccess(photos);
      }

      JSONObject getObject(JSONObject object, String name) {
        JSONObject child = object.optJSONObject(name);
        return child == null ? EMPTY_OBJECT : child;
      }
    });
  }
}
