package com.codepath.popularphotos.models;

import org.json.JSONObject;

/**
 * Created by animan1 on 5/9/15.
 */
public class Image {
  public final String url;
  public final int height;

  public Image(String url, int height) {
    this.url = url;
    this.height = height;
  }

  public static Image fromJSON(JSONObject imageObject) {
    String imageUrl = imageObject.optString("url");
    int imageHeight = imageObject.optInt("height");
    return new Image(imageUrl, imageHeight);
  }
}
