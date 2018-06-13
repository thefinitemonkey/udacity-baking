package com.example.a0603614.udacity_baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a0603614.udacity_baking.adapters.IngredientsAdapter;
import com.example.a0603614.udacity_baking.objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientListFragment extends Fragment {
    private Recipe mRecipe;
    private IngredientsAdapter mIngredientsAdapter;
    @BindView (R.id.rv_ingredient_list)
    RecyclerView mIngredientListView;


    public IngredientListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the recipe object from the fragment arguments
        mRecipe = getArguments().getParcelable(getResources().getString(R.string.recipe_data_intent_extra));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        Context context = view.getContext();
        ButterKnife.bind(this, view);

        // Create the adapter and attach it
        mIngredientsAdapter = new IngredientsAdapter();
        mIngredientListView.setAdapter(mIngredientsAdapter);

        // Create the linear layout manager and attach it
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mIngredientListView.setLayoutManager(linearLayoutManager);
        mIngredientListView.setHasFixedSize(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mIngredientsAdapter.setIngredientsList(mRecipe.ingredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
