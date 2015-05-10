package com.codepath.popularphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
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

    Picasso.with(getContext()).load(photo.profileImageUrl).placeholder(R.drawable.user).into(viewHolder.profileImageView);

    String username = photo.username == null ? "Unknown" : photo.username;
    viewHolder.usernameTextView.setText(username);

    viewHolder.photoImageView.getLayoutParams().height = photo.imageHeight;
    viewHolder.photoImageView.setMinimumWidth(parent.getWidth());
    viewHolder.photoImageView.setImageDrawable(null);
    Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.photoImageView);

    String caption = photo.caption == null ? "" : photo.caption;
    viewHolder.captionTextView.setText(caption);

    return convertView;
  }
}
