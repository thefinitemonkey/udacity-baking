package com.example.a0603614.udacity_baking.data_utils;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.a0603614.udacity_baking.objects.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonSourceLoader extends AsyncTaskLoader<List<Recipe>> {
    OkHttpClient mClient = new OkHttpClient();
    String mJsonUrl;
    Context mContext;

    public JsonSourceLoader(Context context, String url) {
        super(context);
        mContext = context;
        mJsonUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        // Load the JSON data from the source URL
        Request request = new Request.Builder().url(mJsonUrl).build();
        Response response;
        Moshi moshi;
        List<Recipe> recipes;

        try {
            // Get the JSON response
            response = mClient.newCall(request).execute();
            // Use Moshi to parse the JSON into a Java list
            moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Recipe.class);
            JsonAdapter<List> adapter = moshi.adapter(type);
            recipes = adapter.fromJson(response.body().string());

            // Return the completed list of recipes
            return recipes;
        } catch (IOException e) {
            return null;
        }
    }
}
