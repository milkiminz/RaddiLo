package com.example.milkiminz.raddilo;

/**
 * Created by Milki Minz on 2/28/2017.
 */

import android.appwidget.AppWidgetProvider;




    import android.app.PendingIntent;
    import android.appwidget.AppWidgetManager;
    import android.appwidget.AppWidgetProvider;
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


        String Name,Phone,Email;
        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            final int count = appWidgetIds.length;

            for (int i = 0; i < count; i++) {
                int widgetId = appWidgetIds[i];

                remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.simple_widget);


                Intent intent = new Intent(context, SimpleWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

                appWidgetManager.updateAppWidget(widgetId, remoteViews);


            }
        }


    }

