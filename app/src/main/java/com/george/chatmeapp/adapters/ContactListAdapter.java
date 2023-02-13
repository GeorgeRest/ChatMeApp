package com.george.chatmeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.george.chatmeapp.R;
import com.george.chatmeapp.beans.UserItem;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private int resource;
    private Context context;
    private List<UserItem> userItemList;

    public ContactListAdapter(Context context,int resource,  List<UserItem> userItemList) {
        this.resource = resource;
        this.context = context;
        this.userItemList = userItemList;
    }

    @Override
    public int getCount() {
        return userItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return userItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserItem user = (UserItem) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, parent,false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_contact_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.iv_contact_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image.setImageResource(user.getImageId());
        viewHolder.title.setText(user.getUserName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;

    }

}
