package com.example.a0603614.udacity_baking.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Ingredient;
import com.example.a0603614.udacity_baking.objects.Recipe;
import com.example.a0603614.udacity_baking.services.IngredientWidgetRemoteViewsService;
import com.example.a0603614.udacity_baking.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientListWidgetProvider extends AppWidgetProvider {

    private static List<Ingredient> mIngredientList;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Recipe recipe = Prefs.loadRecipe(context);

        RemoteViews views = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_list_widget);
        // Set the recipe name
        if (recipe != null) {
            views.setTextViewText(R.id.tv_widget_recipe_name, recipe.name);
        }

        Intent intent = new Intent(context, IngredientWidgetRemoteViewsService.class);
        if (mIngredientList != null) {
            intent.putExtra(
                    context.getResources().getString(R.string.recipe_detail_ingredients),
                    (ArrayList<Ingredient>) mIngredientList
            );
            Log.i("============>>>", "onUpdate: put ingredient list into intent extra");
        }

        views.setRemoteAdapter(R.id.lv_ingredient_list, intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Log.i("============>>>", "onUpdate: set remote adapter and sent update");

        // updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    public static void sendRefreshBroadcast(Context context, List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        // Create new intent to broadcast and set the ingredientList as an extra on it
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, IngredientListWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (intent.hasExtra(context.getResources().getString(R.string.recipe_detail_ingredients))) {
            mIngredientList = intent.getParcelableArrayListExtra(
                    context.getResources().getString(R.string.recipe_detail_ingredients));
        }
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // Refresh widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, IngredientListWidgetProvider.class);
            onUpdate(context, mgr, mgr.getAppWidgetIds(cn));
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lv_ingredient_list);
        }

        super.onReceive(context, intent);
    }
}

