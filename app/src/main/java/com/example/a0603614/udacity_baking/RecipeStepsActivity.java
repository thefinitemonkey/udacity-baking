package com.example.a0603614.udacity_baking;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a0603614.udacity_baking.fragments.RecipeStepListFragment;
import com.example.a0603614.udacity_baking.objects.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepListFragment.OnFragmentInteractionListener {

    private static Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        mRecipe = getIntent().getParcelableExtra(
                getResources().getString(R.string.recipe_data_intent_extra));

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
