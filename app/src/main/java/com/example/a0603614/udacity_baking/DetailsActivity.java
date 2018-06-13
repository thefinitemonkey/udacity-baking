package com.example.a0603614.udacity_baking;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a0603614.udacity_baking.fragments.IngredientListFragment;
import com.example.a0603614.udacity_baking.objects.Recipe;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private String mDetailType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the recipe object from the intent
        mRecipe = getIntent().getParcelableExtra(
                getResources().getString(R.string.recipe_data_intent_extra));
        mDetailType = getIntent().getStringExtra(
                getResources().getString(R.string.recipe_data_detail_extra));

        // Clear existing fragments from display
        clearDetailScreen();

        // Determine the type of detail display and associated fragments
        if (mDetailType.contentEquals(getResources().getString(R.string.recipe_detail_ingredients))) {
            assembleIngredientsScreen();
        } else if (mDetailType.contentEquals(getResources().getString(R.string.recipe_detail_step))) {
            assembleStepScreen();
        }
    }

    private void clearDetailScreen() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> frags = fm.getFragments();
        if (frags == null) return;

        for (Fragment frag: frags) {
            fm.beginTransaction().remove(frag).commit();
        }
    }

    private void assembleIngredientsScreen() {
        // Send the list of steps to the step list fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(getResources().getString(R.string.recipe_data_intent_extra), mRecipe);
        IngredientListFragment fragIngredientList = new IngredientListFragment();
        fragIngredientList.setArguments(bundle);

        // Add the fragment to the display
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.cl_details_display, fragIngredientList).commit();

        // Set the title of the screen accordingly
        try {
            getSupportActionBar().setTitle(
                    mRecipe.name + getResources().getString(R.string.label_ingredients_append));
        } catch (Exception e) {

        }
    }

    private void assembleStepScreen() {

    }
}
