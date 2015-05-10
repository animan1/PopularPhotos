package com.codepath.popularphotos.models;

import org.json.JSONObject;

public class User {
  public final String username;
  public final String profileImageUrl;

  public User(String username, String profileImageUrl) {
    this.username = username;
    this.profileImageUrl = profileImageUrl;
  }

  public static User fromJSON(JSONObject userObject) {
    String username = userObject.optString("username");
    String profileImageUrl = userObject.optString("profile_picture");
    return new User(username, profileImageUrl);
  }
}
