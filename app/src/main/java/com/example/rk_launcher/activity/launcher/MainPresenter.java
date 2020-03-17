package com.example.rk_launcher.activity.launcher;

import java.util.Calendar;

public class MainPresenter {

    private final ViewNavigator viewNavigator;

    public MainPresenter(ViewNavigator viewNavigator) {
        this.viewNavigator=viewNavigator;
    }

    public void checkBlockTime(String packageName) {
        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        if(currentHour < 16)
            viewNavigator.allowApp(packageName);
        else
            viewNavigator.blockApp();

    }
}
