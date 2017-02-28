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
        Cursor c;
        ContentProvider cp;
        String Name,Phone,Email;
        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            final int count = appWidgetIds.length;
            cp=new ContentProvider(context);
            c=null;
            for (int i = 0; i < count; i++) {
                int widgetId = appWidgetIds[i];

                remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.simple_widget);
                try {
                    c = cp.getData();
                    if(c.getCount() > 0) {
                        c.moveToFirst();
                        Name = c.getString(c.getColumnIndex("name"));
                        Phone=c.getString(c.getColumnIndex("phone"));
                        Email=c.getString(c.getColumnIndex("email"));
                    }

                }finally {
                    c.close();
                }

                Intent intent = new Intent(context, SimpleWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

                appWidgetManager.updateAppWidget(widgetId, remoteViews);
                remoteViews.setTextViewText(R.id.name,Name);
                remoteViews.setTextViewText(R.id.phone,Phone);
                remoteViews.setTextViewText(R.id.email,Email);

            }
        }


    }

