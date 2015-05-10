package com.codepath.popularphotos.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Photo {
  public static interface ResponseHandler {
    void onSuccess(ArrayList<Photo> photos);
  }

  private static AsyncHttpClient client = new AsyncHttpClient();

  public final User user;
  public final String caption;
  public final String imageUrl;
  public final int imageHeight;

  public Photo(String caption, String imageUrl, int imageHeight, User user) {
    this.caption = caption;
    this.imageUrl = imageUrl;
    this.imageHeight = imageHeight;
    this.user = user;
  }

  @Override
  public String toString() {
    return this.caption;
  }

  public static void fetchPopular(final ResponseHandler handler) {
    client.get("https://api.instagram.com/v1/media/popular?client_id=c24aacde80fe44e6bd26404e5e9bca7b", new JsonHttpResponseHandler() {
      final JSONObject EMPTY_OBJECT = new JSONObject();

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        ArrayList<Photo> photos = new ArrayList<>();
        JSONArray dataArray = response.optJSONArray("data");
        if (dataArray == null) {
          fetchPopular(handler);
        }
        for (int index = 0; index < dataArray.length(); index++) {
          JSONObject dataObject = dataArray.optJSONObject(index);
          if (!"image".equals(dataObject.optString("type"))) {
            continue;
          }
          String caption = getObject(dataObject, "caption").optString("text");
          User user = User.fromJSON(getObject(dataObject, "user"));
          JSONObject standardResolutionObject = getObject(getObject(dataObject, "images"), "standard_resolution");
          String imageUrl = standardResolutionObject.optString("url");
          int imageHeight = standardResolutionObject.optInt("height");
          photos.add(new Photo(caption, imageUrl, imageHeight, user));
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
