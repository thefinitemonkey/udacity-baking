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

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.RecipeStepsActivity;
import com.example.a0603614.udacity_baking.adapters.StepsAdapter;
import com.example.a0603614.udacity_baking.objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepListFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private static Recipe mRecipe;
    @BindView(R.id.rv_recipe_steps)
    RecyclerView mStepRecycler;
    private Context mContext;
    private StepsAdapter mStepsAdapter;
    private StepListClickListener mListener;
    private String[] mSteps;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get the steps data
        mSteps = getArguments().getStringArray("stepList");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        Context context = view.getContext();
        ButterKnife.bind(this, view);

        // Create the adapter and attach it
        mStepsAdapter = new StepsAdapter(context, this);
        mStepRecycler.setAdapter(mStepsAdapter);

        // Create a linear layout manager to attach to the recycler
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        mStepRecycler.setLayoutManager(linearLayoutManager);
        mStepRecycler.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        // Set the data on the adapter
        mStepsAdapter.setStepsData(mSteps);

        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepListClickListener) {
            mListener = (StepListClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                                               + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int position) {
        // Pass the item click back to the activity for handling
        if (mListener != null) {
            mListener.onStepListClick(position);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface StepListClickListener {
        // TODO: Update argument type and name
        void onStepListClick(int position);
    }
}
