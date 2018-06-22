package com.example.a0603614.udacity_baking.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.adapters.IngredientsAdapter;
import com.example.a0603614.udacity_baking.objects.Recipe;
import com.example.a0603614.udacity_baking.utils.Prefs;
import com.example.a0603614.udacity_baking.widgets.IngredientListWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientListFragment extends Fragment {
    @BindView(R.id.rv_ingredient_list)
    RecyclerView mIngredientListView;
    @BindView(R.id.btn_set_widget_ingredients)
    Button mButton;
    private Recipe mRecipe;
    private IngredientsAdapter mIngredientsAdapter;
    private Context mContext;


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
        mRecipe = getArguments().getParcelable(
                getResources().getString(R.string.recipe_data_intent_extra));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        mContext = view.getContext();
        ButterKnife.bind(this, view);

        // Create the adapter and attach it
        mIngredientsAdapter = new IngredientsAdapter();
        mIngredientListView.setAdapter(mIngredientsAdapter);

        // Create the linear layout manager and attach it
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                mContext, LinearLayoutManager.VERTICAL, false);
        mIngredientListView.setLayoutManager(linearLayoutManager);
        mIngredientListView.setHasFixedSize(false);


        // Set the button click handler
        mButton.setOnClickListener(new WidgetButtonClickListener());


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

    class WidgetButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Prefs.saveRecipe(mContext, mRecipe);
            IngredientListWidgetProvider.sendRefreshBroadcast(mContext, mRecipe.ingredients);
        }
    }


}
