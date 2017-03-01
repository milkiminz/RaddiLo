package com.example.milkiminz.raddilo;

/**
 * Created by Milki Minz on 2/28/2017.
 */

import android.appwidget.AppWidgetProvider;




    import android.app.PendingIntent;
    import android.appwidget.AppWidgetManager;
    import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
    import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

    import java.io.BufferedReader;
    import java.io.FileInputStream;
    import java.io.InputStreamReader;
    import java.util.Random;



public class SimpleWidgetProvider  extends AppWidgetProvider {
    RemoteViews remoteViews;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.simple_widget);


        remoteViews.setOnClickPendingIntent(R.id.aboutdev,
                actionPendingIntent(context));
        remoteViews.setOnClickPendingIntent(R.id.ufeatures,
                actionPendingIntent1(context));
        pushWidgetUpdate(context, remoteViews);

    }
    public static PendingIntent actionPendingIntent(Context context) {
        Intent intent = new Intent(context,AboutDevelopers.class);
        intent.setAction("LAUNCH_ACTIVITY");
        return PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public static PendingIntent actionPendingIntent1(Context context) {
        Intent intent = new Intent(context,UpcomingFeatures.class);
        intent.setAction("LAUNCH_ACTIVITY");
        return PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,SimpleWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

}
