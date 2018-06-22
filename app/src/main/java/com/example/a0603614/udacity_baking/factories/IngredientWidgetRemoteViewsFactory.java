package com.example.a0603614.udacity_baking.factories;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Ingredient;
import com.example.a0603614.udacity_baking.objects.Recipe;
import com.example.a0603614.udacity_baking.utils.Prefs;
import com.example.a0603614.udacity_baking.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.internal.Util;

public class IngredientWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredientList;
    private int mCount = 0;
    private static String TAG = IngredientWidgetRemoteViewsFactory.class.getSimpleName();


    public IngredientWidgetRemoteViewsFactory(Context context, Intent intent) {
        /*
        Ingredient ingredient1 = new Ingredient();
        ingredient1.ingredient = "Blood";
        ingredient1.quantity = 1.0;
        ingredient1.measure = "Cup";
        Ingredient ingredient2 = new Ingredient();
        ingredient2.ingredient = "Sweat";
        ingredient2.quantity = 1.0;
        ingredient2.measure = "Cup";
        Ingredient ingredient3 = new Ingredient();
        ingredient3.ingredient = "Tears";
        ingredient3.quantity = 1.0;
        ingredient3.measure = "Cup";
        List<Ingredient> sampleList = new ArrayList<Ingredient>(){};
        sampleList.add(ingredient1);
        sampleList.add(ingredient2);
        sampleList.add(ingredient3);

        Log.i("============>>>", "IngredientWidgetRemoteViewsFactory: created placeholder ingredient list");
        */

        mContext = context;
        Log.i("============>>>", "IngredientWidgetRemoteViewsFactory: set context");
        /*
        if (intent.hasExtra(
                mContext.getResources().getString(R.string.recipe_detail_ingredients))) {
            mIngredientList = intent.getParcelableArrayListExtra(
                    mContext.getResources().getString(R.string.recipe_detail_ingredients));
            Log.i("============>>>", "IngredientWidgetRemoteViewsFactory: found intent extra with ingredient list so using that");
        } else {
            Log.i("============>>>", "IngredientWidgetRemoteViewsFactory: no ingredient list intent extra so using default list");
            mIngredientList = sampleList;
        }
        mCount = mIngredientList.size();
        */
    }

    public void updateData(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        mCount = mIngredientList.size();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.i("============>>>", "onDataSetChanged: ");

        Recipe recipe = Prefs.loadRecipe(mContext);
        try {
            mIngredientList = recipe.ingredients;
            mCount = mIngredientList.size();
        } catch (Exception e) {
            Log.i(TAG, "onDataSetChanged: error getting ingredients");
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.i("============>>>", "getCount: " + mCount);

        return mCount;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.i("============>>>", "getViewAt " + position);

        if (position < 0 || mIngredientList == null) return null;

        // Create the remote view for the ingredient
        Ingredient ingredient = mIngredientList.get(position);
        Log.i("============>>>", "getViewAt: ingredient " + ingredient.ingredient);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_widget_item);
        rv.setTextViewText(R.id.tv_widget_ingredient_name, StringUtils.toProperCase(ingredient.ingredient));
        Log.i("============>>>", "getViewAt: set ingredient name " + ingredient.ingredient);
        rv.setTextViewText(R.id.tv_widget_ingredient_measure, ingredient.measure);
        Log.i("============>>>", "getViewAt: set ingredient measure " + ingredient.measure);
        rv.setTextViewText(R.id.tv_widget_ingredient_quantity, Double.toString(ingredient.quantity));
        Log.i("============>>>", "getViewAt: set ingredient quantity " + ingredient.quantity);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
