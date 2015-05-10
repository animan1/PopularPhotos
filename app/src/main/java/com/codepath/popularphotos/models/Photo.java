package com.codepath.popularphotos.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Photo {
  public interface ResponseHandler {
    void onSuccess(ArrayList<Photo> photos);
  }

  private static AsyncHttpClient client = new AsyncHttpClient();

  public final User user;
  public final Image image;
  public final String caption;
  public final long createdTimestamp;

  public Photo(String caption, User user, Image image, long createdTimestamp) {
    this.caption = caption;
    this.image = image;
    this.user = user;
    this.createdTimestamp = createdTimestamp;
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
          return;
        }
        for (int index = 0; index < dataArray.length(); index++) {
          JSONObject dataObject = dataArray.optJSONObject(index);
          if (!"image".equals(dataObject.optString("type"))) {
            continue;
          }
          String caption = getObject(dataObject, "caption").optString("text");
          User user = User.fromJSON(getObject(dataObject, "user"));
          JSONObject imageObject = getObject(getObject(dataObject, "images"), "standard_resolution");
          Image image = Image.fromJSON(imageObject);
          long createdTimestamp = dataObject.optLong("created_time");
          photos.add(new Photo(caption, user, image, createdTimestamp));
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
