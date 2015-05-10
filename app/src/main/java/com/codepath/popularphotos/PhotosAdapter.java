package com.codepath.popularphotos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.popularphotos.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Photo> {
  private static class ViewHolder {
    ImageView profileImageView;
    TextView usernameTextView;
    ImageView photoImageView;
    TextView captionTextView;
  }

  public PhotosAdapter(Context context, List<Photo> objects) {
    super(context, R.layout.photo_list_item, objects);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Photo photo = getItem(position);
    ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.photo_list_item, parent, false);
      viewHolder.profileImageView = (ImageView) convertView.findViewById(R.id.profileImageView);
      viewHolder.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
      viewHolder.photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
      viewHolder.captionTextView = (TextView) convertView.findViewById(R.id.captionTextView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    if (photo.user.profileImageUrl != null) {
      Picasso.with(getContext()).load(photo.user.profileImageUrl).placeholder(R.drawable.user).into(viewHolder.profileImageView);
    }
    else {
      viewHolder.profileImageView.setImageDrawable(getDrawable(R.drawable.user));
    }

    String username = photo.user.username == null ? "Unknown" : photo.user.username;
    viewHolder.usernameTextView.setText(username);

    viewHolder.photoImageView.getLayoutParams().height = photo.image.height;
    viewHolder.photoImageView.setMinimumWidth(parent.getWidth());
    if (photo.image.url != null) {
      Picasso.with(getContext()).load(photo.image.url).placeholder(R.drawable.camera).into(viewHolder.photoImageView);
    }
    else {
      viewHolder.photoImageView.setImageDrawable(getDrawable(R.drawable.camera));
    }

    String caption = photo.caption == null ? "" : photo.caption;
    viewHolder.captionTextView.setText(caption);

    return convertView;
  }

  private Drawable getDrawable(int id) {
    return getContext().getResources().getDrawable(id);
  }
}
