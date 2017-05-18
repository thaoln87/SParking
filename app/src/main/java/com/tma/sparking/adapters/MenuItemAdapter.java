package com.tma.sparking.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tma.sparking.R;
import com.tma.sparking.models.NavMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lnthao on 5/17/2017.
 */
public class MenuItemAdapter extends BaseAdapter {
    private ArrayList<NavMenuItem> data;
    private Context context;
    public MenuItemAdapter(Context context, ArrayList<NavMenuItem> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            vi = inflater.inflate(R.layout.menu_item, null);

        NavMenuItem navMenuItem = data.get(position);
        if (navMenuItem !=null) {
            TextView title = (TextView)vi.findViewById(R.id.tv_nav_menu_item_title);
            ImageView icon = (ImageView)vi.findViewById(R.id.img_nav_menu_item_icon);
            // Setting all values in listview
            title.setText(navMenuItem.getTitle());
            icon.setImageResource(navMenuItem.getIconResId());
        }
        return vi;
    }
}
