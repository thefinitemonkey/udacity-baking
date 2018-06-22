package com.example.a0603614.udacity_baking.services;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.a0603614.udacity_baking.factories.IngredientWidgetRemoteViewsFactory;
import com.example.a0603614.udacity_baking.objects.Recipe;
import com.example.a0603614.udacity_baking.utils.Prefs;
import com.example.a0603614.udacity_baking.widgets.IngredientListWidgetProvider;

public class IngredientWidgetRemoteViewsService extends RemoteViewsService {
    public static void updateWidget(Context context, Recipe recipe) {
        // Save out the recipe
        Prefs.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientListWidgetProvider.class));
        IngredientListWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("============>>>", "onGetViewFactory: getting factory");
        return new IngredientWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
