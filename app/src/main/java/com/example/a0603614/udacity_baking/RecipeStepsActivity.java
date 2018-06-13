package com.example.a0603614.udacity_baking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a0603614.udacity_baking.fragments.RecipeStepListFragment;
import com.example.a0603614.udacity_baking.objects.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepListFragment.StepListClickListener {

    private static final int RESULT_CODE = 2008;
    private static Recipe mRecipe;
    private static String[] mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        // Get the Recipe from the intent
        mRecipe = getIntent().getParcelableExtra(
                getResources().getString(R.string.recipe_data_intent_extra));

        // Build the mSteps array from the recipe data
        int stepCount = mRecipe.steps.size();
        mSteps = new String[stepCount + 1];
        mSteps[0] = getResources().getString(R.string.label_step_ingredients);
        for (int i = 0; i < stepCount; i++) {
            int pos = i + 1;
            mSteps[pos] = mRecipe.steps.get(i).shortDescription;
        }

        // Send the list of steps to the step list fragment
        Bundle bundle = new Bundle();
        bundle.putStringArray("stepList", mSteps);
        RecipeStepListFragment fragStepList = new RecipeStepListFragment();
        fragStepList.setArguments(bundle);

        // Add the fragment to the display
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.cl_steps_list, fragStepList).commit();

        // Set the title of the screen to the recipe name
        try {
            getSupportActionBar().setTitle(mRecipe.name);
        } catch (Exception e) {

        }
    }

    @Override
    public void onStepListClick(int position) {
        // Create the intent with the selected recipe and navigate to the details display
        Class destinationClass = DetailsActivity.class;
        Intent showDetails = new Intent(this, destinationClass);
        showDetails.putExtra(
                getResources().getString(R.string.recipe_data_intent_extra),
                mRecipe
        );
        showDetails.putExtra(
                getResources().getString(R.string.recipe_data_detail_extra),
                getResources().getString(R.string.recipe_detail_ingredients)
        );
        startActivityForResult(showDetails, RESULT_CODE);
    }
}
