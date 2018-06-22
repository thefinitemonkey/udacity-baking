package com.example.a0603614.udacity_baking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Recipe;

import java.io.IOException;

public class Prefs {
    public static final String PREFS_NAME = "prefs";
    public static final String TAG = Prefs.class.getSimpleName();

    public static void saveRecipe(Context context, Recipe recipe) {
        // Save out the recipe object as a base64 string to prefs
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        String encodedObj;
        try {
            encodedObj = Recipe.toString(recipe);
        } catch (IOException e) {
            Log.i(TAG, "saveRecipe: could not encode recipe object");
            return;
        }
        prefs.putString(context.getString(R.string.widget_recipe_key), encodedObj);

        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        // Reconstitute the recipe object from a base64 string in prefs
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String recipeBase64 = prefs.getString(context.getString(R.string.widget_recipe_key), "");

        Recipe recipe;
        try {
            recipe = (Recipe) Recipe.fromString(recipeBase64);
        } catch (IOException e) {
            Log.i(TAG, "loadRecipe: could not convert string to Recipe object");
            return null;
        } catch (ClassNotFoundException e) {
            Log.i(TAG, "loadRecipe: could not find class for Recipe");
            return null;
        }

        return recipe;
    }
}