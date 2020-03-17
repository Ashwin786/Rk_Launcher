package com.example.rk_launcher.activity.launcher;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rk_launcher.R;
import com.example.rk_launcher.data.model.ItemDto;

import java.util.ArrayList;

/**
 * Created by user1 on 19/10/17.
 */
public class LauncherAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private final Context context;

    protected ArrayList<String> selected_list;
    protected ArrayList<ItemDto> app_list;

    public LauncherAdapter(Context context, ArrayList<ItemDto> app_list) {
        this.context = context;
        this.app_list = app_list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        selected_list = new ArrayList<String>();
//        selected_list = Database.getInstance(context).get_apps_name();
//        Log.e("selected_list size ", Integer.toString(selected_list.size()));


    }


    @Override
    public int getCount() {
        return app_list.size();
    }

    @Override
    public ItemDto getItem(int i) {
        return app_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.grid_single, null);
            holder.name = (TextView) view.findViewById(R.id.grid_text);
            holder.icon = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final ItemDto item = getItem(i);
        holder.name.setText(item.getApplicationLabel());
        holder.icon.setBackground(item.getApplicationIcon());

        return view;
    }


    class Holder {
        TextView name;
        ImageView icon;
    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {

        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
}
