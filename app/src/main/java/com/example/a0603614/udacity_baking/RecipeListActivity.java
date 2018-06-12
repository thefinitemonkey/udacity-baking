package com.example.a0603614.udacity_baking;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;

import com.example.a0603614.udacity_baking.adapters.RecipeAdapter;
import com.example.a0603614.udacity_baking.data_utils.JsonSourceLoader;
import com.example.a0603614.udacity_baking.objects.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {
    private static int RESULT_CODE = 1970;
    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecipeRecycler;
    private int mRecipeLoaderId = 1969;
    private RecipeAdapter mRecipeAdapter;
    private String mJsonUri;
    private LoaderManager.LoaderCallbacks<List<Recipe>> mRecipeListLoaderCallback =
            new LoaderManager.LoaderCallbacks<List<Recipe>>() {
                @Override
                public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
                    if (id != mRecipeLoaderId) {
                        return null;
                    }
                    return new JsonSourceLoader(
                            RecipeListActivity.this, args.getString("queryUrl"));
                }

                @Override
                public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
                    if (data == null) return;

                    mRecipeAdapter.setRecipeData(data);
                }

                @Override
                public void onLoaderReset(Loader<List<Recipe>> loader) {

                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        mJsonUri = getResources().getString(R.string.json_uri);

        // Create the adapter
        mRecipeAdapter = new RecipeAdapter(this, this);
        // Bind the adapter to the recycler
        mRecipeRecycler.setAdapter(mRecipeAdapter);

        // Determine whether we're portrait or landscape and set the number of spans accordingly
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            spanCount = 3;

        // Create a gid layout manager for the display of the recycler
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount,
                                                                    GridLayout.VERTICAL, false
        );
        mRecipeRecycler.setLayoutManager(gridLayoutManager);
        mRecipeRecycler.setHasFixedSize(true);

        // Check if there's an already existing loader for the recipe data and get things rolling
        if (getSupportLoaderManager().getLoader(mRecipeLoaderId) != null) {
            getSupportLoaderManager().initLoader(mRecipeLoaderId, null, mRecipeListLoaderCallback);
        }

        loadRecipeData();
    }

    @Override
    public void onListItemClick(int itemIndex) {
        // Create the intent with the selected recipe and navigate to the steps display
        Class destinationClass = RecipeStepsActivity.class;
        Intent showRecipeSteps = new Intent(this, destinationClass);
        showRecipeSteps.putExtra(
                getResources().getString(R.string.recipe_data_intent_extra),
                mRecipeAdapter.getRecipeObj(itemIndex)
        );
        startActivityForResult(showRecipeSteps, RESULT_CODE);
    }

    private void loadRecipeData() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryUrl", mJsonUri);
        getSupportLoaderManager().restartLoader(
                mRecipeLoaderId, queryBundle, mRecipeListLoaderCallback);
    }
}
