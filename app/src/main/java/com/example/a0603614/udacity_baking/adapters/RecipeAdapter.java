package com.example.a0603614.udacity_baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private final ListItemClickListener mOnClickListener;
    private Context mContext;
    private int mItemCount = 0;
    private List<Recipe> mRecipeList;

    public RecipeAdapter(Context context, ListItemClickListener listener) {
        mOnClickListener = listener;
        mContext = context;
    }

    public Recipe getRecipeObj(int index) {
        if (index > -1 && mRecipeList.size() > 0) {
            return mRecipeList.get(index);
        }
        return null;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int itemId = R.layout.recipe_list_item;
        View view = inflater.inflate(itemId, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setRecipeData(List<Recipe> recipes) {
        // Store the data, update the count, and notify that the data set has changed
        mRecipeList = recipes;
        mItemCount = recipes.size();
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int itemIndex);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        @BindView(R.id.tv_recipe_name)
        TextView mTitle;
        @BindView(R.id.tv_serving_count)
        TextView mServings;
        @BindView(R.id.iv_recipe_image)
        ImageView mImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int itemIndex = getAdapterPosition();
            mOnClickListener.onListItemClick(itemIndex);
        }

        public void bind(int position) {
            // Display the name, serving size, and image (if available) for the recipe
            // at this position in the dataset
            Recipe recipe = mRecipeList.get(position);
            try {
                mTitle.setText(recipe.name);
            } catch (Exception e) {
                mTitle.setText("");
            }
            try {
                mServings.setText(Integer.toString(recipe.servings));
            } catch (Exception e) {
                mServings.setText("");
            }
            try {
                Picasso.get().load(recipe.image).placeholder(R.drawable.ic_no_image).error(
                      R.drawable.ic_no_image).into(mImage);
            } catch (Exception e) {

            }
        }
    }
}
