package com.codepath.popularphotos;

import java.util.ArrayList;

public abstract class PhotoResponseHandler {
  public abstract void onSuccess(ArrayList<Photo> photos);
}
