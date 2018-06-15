package com.example.a0603614.udacity_baking;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a0603614.udacity_baking.fragments.IngredientListFragment;
import com.example.a0603614.udacity_baking.fragments.RecipeStepListFragment;
import com.example.a0603614.udacity_baking.fragments.StepDetailsFragment;
import com.example.a0603614.udacity_baking.objects.Recipe;

import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepListFragment.StepListClickListener {

    private static final int RESULT_CODE = 2008;
    private Recipe mRecipe;
    private String[] mSteps;
    private boolean mIsTablet = false;
    private int mPosition = 0;
    private boolean mRotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        // Check if we're on tablet
        if (findViewById(R.id.cl_tablet_step_details) != null) mIsTablet = true;

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

        // Set the title of the screen to the recipe name
        try {
            getSupportActionBar().setTitle(mRecipe.name);
        } catch (Exception e) {

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedPosition", mPosition);
        outState.putBoolean("wasRotated", true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mPosition = savedInstanceState.getInt("selectedPosition", 0);
        mRotated = savedInstanceState.getBoolean("wasRotated", false);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mRotated) return;

        // Send the list of steps to the step list fragment
        Bundle bundle = new Bundle();
        bundle.putStringArray("stepList", mSteps);
        RecipeStepListFragment fragStepList = new RecipeStepListFragment();
        fragStepList.setArguments(bundle);

        // Add the  step list fragment to the appropriate display
        FragmentManager fm = getSupportFragmentManager();
        if (!mIsTablet) {
            fm.beginTransaction().add(R.id.cl_steps_list, fragStepList).commit();
        } else {
            fm.beginTransaction().add(R.id.cl_tablet_steps_list, fragStepList).commit();
        }

        // If we're in tablet mode then we also need to set up the details display
        if (mIsTablet) {
            setTabletDetailsDisplay(mPosition);
        }
    }

    private void setTabletDetailsDisplay(int position) {
        clearDetailFragment();
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();

        if (position == 0) {
            // Bundle the recipe object for display and add the fragment
            bundle.putParcelable(
                    getResources().getString(R.string.recipe_data_intent_extra), mRecipe);
            IngredientListFragment ingredientListFragment = new IngredientListFragment();
            ingredientListFragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.cl_tablet_step_details, ingredientListFragment).commit();
        } else {
            // Bundle the step object for display and add the fragment
            bundle.putParcelable(
                    getResources().getString(R.string.recipe_step_data_intent_extra),
                    mRecipe.steps.get(position - 1)
            );
            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.cl_tablet_step_details, detailsFragment).commit();
        }
    }

    private void clearDetailFragment() {
        // Remove all the fragments from the table detail display
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> frags = fm.getFragments();
        if (frags == null) return;

        View cl = findViewById(R.id.cl_tablet_step_details);
        for (Fragment frag : frags) {
            if (frag.getView() == null) return;

            View view = (View) frag.getView().getParent();
            if (view.equals(cl)) {
                fm.beginTransaction().remove(frag).commit();
            }
        }
    }

    @Override
    public void onStepListClick(int position) {
        mPosition = position;

        // If we're in tablet view display that version
        if (mIsTablet) {
            setTabletDetailsDisplay(position);
            return;
        }

        // Create the intent with the selected recipe and navigate to the details display
        Class destinationClass = DetailsActivity.class;
        Intent showDetails = new Intent(this, destinationClass);
        showDetails.putExtra(
                getResources().getString(R.string.recipe_data_intent_extra),
                mRecipe
        );
        if (position == 0) {
            showDetails.putExtra(
                    getResources().getString(R.string.recipe_data_detail_extra),
                    getResources().getString(R.string.recipe_detail_ingredients)
            );
        } else {
            showDetails.putExtra(
                    getResources().getString(R.string.recipe_data_detail_extra),
                    getResources().getString(R.string.recipe_detail_step)
            );
        }
        showDetails.putExtra(
                getResources().getString(R.string.recipe_detail_step_position), position - 1);
        startActivityForResult(showDetails, RESULT_CODE);
    }
}
