package com.example.rk_launcher.activity.launcher;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rk_launcher.R;
import com.example.rk_launcher.data.model.ItemDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LauncherActivity extends AppCompatActivity implements ViewNavigator{

    private static final String TAG = "LauncherActivity";
    private GridView gridview;
    private PackageManager pm = null;
    private List<PackageInfo> packages = null;
    protected ArrayList<ItemDto> app_list;
    private ImageButton menu_button;
    private ConstraintLayout front_cover;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        presenter = new MainPresenter(this);
        front_cover = (ConstraintLayout) findViewById(R.id.front_cover);
        setwallpaper();
        remove_unwanted_apps();
        init_Grid();
        intialize_menu();

    }

    private void init_Grid() {
        gridview = (GridView) findViewById(R.id.gridview);
        LauncherAdapter adapter = new LauncherAdapter(this, app_list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                presenter.checkBlockTime(app_list.get(position).getPackageName());
            }
        });
    }

    private void setwallpaper() {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        ConstraintLayout constraint_layout = (ConstraintLayout) findViewById(R.id.constraint_layout);
        constraint_layout.setBackground(wallpaperDrawable);
    }

    private void intialize_menu() {
        menu_button = (ImageButton) findViewById(R.id.menu_button);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible_applications(true);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        visible_applications(false);
    }


    private void visible_applications(boolean b) {
        if (b) {
            front_cover.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
        } else {
            front_cover.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
        }
    }

    private void remove_unwanted_apps() {
        this.pm = getPackageManager();
        this.packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        app_list = new ArrayList<>();
        for (int i = packages.size() - 1; i >= 0; i--) {
            Log.e(TAG, "remove_unwanted_apps: "+ packages.get(i).packageName);
            if (is_installedApp(packages.get(i)) && !packages.get(i).packageName.equals("com.android.settings"))
                packages.remove(i);
            else
                app_list.add(new ItemDto((String) pm.getApplicationLabel(packages.get(i).applicationInfo), pm.getApplicationIcon(packages.get(i).applicationInfo), packages.get(i).packageName));
        }
        Collections.sort(app_list,new ItemDto());
    }

    private boolean is_installedApp(PackageInfo packageInfo) {
        Intent intent = pm
                .getLaunchIntentForPackage(packageInfo.packageName);

        if (intent == null)
            return true;
        else
            return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void allowApp(String packageName) {
        Log.e(TAG, "allowApp: " );
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(LaunchIntent);
    }

    @Override
    public void blockApp() {
        Log.e(TAG, "blockApp: " );
        Toast.makeText(this, "App is Blocked", Toast.LENGTH_SHORT).show();
    }
}
