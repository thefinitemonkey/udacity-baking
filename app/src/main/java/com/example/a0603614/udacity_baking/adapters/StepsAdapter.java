package com.example.a0603614.udacity_baking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private int mItemCount = 0;
    private Context mContext;
    private String[] mSteps;
    public final ListItemClickListener mListItemClickListener;


    public StepsAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mListItemClickListener = listener;
    }


    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    public void setStepsData(String[] steps) {
        mSteps = steps;
        mItemCount = mSteps.length;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        @BindView(R.id.tv_recipe_step)
        TextView tvRecipeStep;


        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int itemIndex = getAdapterPosition();
            mListItemClickListener.onListItemClick(itemIndex);
        }

        public void bind(int position) {
            // Get the display text for the given position
            String step = mSteps[position];
            tvRecipeStep.setText(step);
        }
    }
}
