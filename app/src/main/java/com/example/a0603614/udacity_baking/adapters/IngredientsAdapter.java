package com.example.a0603614.udacity_baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Ingredient;
import com.example.a0603614.udacity_baking.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private int mItemCount = 0;
    private List<Ingredient> mIngredients;


    public IngredientsAdapter() {
    }

    @NonNull
    @Override
    public IngredientsAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for the ingredient display
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setIngredientsList(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        mItemCount = mIngredients.size();
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_name)
        TextView mIngredientText;
        @BindView(R.id.tv_ingredient_quantity)
        TextView mIngredientQuantity;
        @BindView(R.id.tv_ingredient_measure)
        TextView mIngredientMeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            if (mIngredients == null || mIngredients.size() == 0) return;

            // Set the text displays on the ingredient view
            Ingredient ingredient = mIngredients.get(position);
            mIngredientText.setText(StringUtils.toProperCase(ingredient.ingredient));
            mIngredientQuantity.setText(Double.toString(ingredient.quantity));
            mIngredientMeasure.setText(ingredient.measure);
        }
    }

}
