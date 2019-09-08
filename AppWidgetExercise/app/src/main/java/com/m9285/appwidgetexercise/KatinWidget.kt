package com.m9285.appwidgetexercise

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */

val goodthings = arrayOf("Coffee in the morning.",
    "Laughing with friend.",
    "Running in the forest.",
    "Sea.",
    "Happy people.")

const val WIDGET_SYNC = "WIDGET_SYNC"

class KatinWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //widgetin päivitys
    override fun onReceive(context: Context, intent: Intent?) {
        if(WIDGET_SYNC == intent?.action) {
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }
        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            /*fun showGoodThing(index: Int) {
                R.id.goodthingsTextView = goodthings[index]
            }

            fun generateNumber(index: Int): Int {
                var index = (0..4).random()
                return(index)
            }*/

            //var index: Int = 1

            var index = (0..4).random()

            val widgetText = goodthings[index]

            //päivitys klikkauksella
            val intent = Intent(context,KatinWidget::class.java)
            intent.action = WIDGET_SYNC
            intent.putExtra("appWidgetId", appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.katin_widget)
            views.setTextViewText(R.id.goodthingsTextView, widgetText)

            //luodaan klikkauksen kuuntelija
            views.setOnClickPendingIntent(R.id.button1, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

