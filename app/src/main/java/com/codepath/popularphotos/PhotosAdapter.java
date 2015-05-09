package com.codepath.popularphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Photo> {
  private static class ViewHolder {
    ImageView photoImageView;
    TextView photoTextView;
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
      viewHolder.photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
      viewHolder.photoTextView = (TextView) convertView.findViewById(R.id.photoTextView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    viewHolder.photoImageView.getLayoutParams().height = photo.imageHeight;
    viewHolder.photoImageView.setImageDrawable(null);
    Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.photoImageView);
    viewHolder.photoTextView.setText(photo.username + " - " + photo.caption);

    return convertView;
  }
}
