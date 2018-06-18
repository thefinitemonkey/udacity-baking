package com.example.a0603614.udacity_baking.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0603614.udacity_baking.R;
import com.example.a0603614.udacity_baking.objects.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {
    @BindView(R.id.tv_step_details_text)
    TextView mStepText;
    @BindView(R.id.iv_step_image)
    ImageView mRecipeImage;
    private SimpleExoPlayerView mExoView;
    private ExoPlayer mExoPlayer;
    private Step mRecipeStep;


    public StepDetailsFragment() {
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
        mRecipeStep = getArguments().getParcelable(
                getResources().getString(R.string.recipe_step_data_intent_extra));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        mExoView = (SimpleExoPlayerView) view.findViewById(R.id.exo_step_video_player);

        ButterKnife.bind(this, view);

        // Initialize the video player
        initializePlayer();

        // Determine what items to put into the fragment view
        assemblePortraitView();


        // Return the complete view
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void initializePlayer() {
        // Create default track selector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(
                bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Initialize the player
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        // Initialize the SimpleExoPlayerView
        mExoView.setPlayer(mExoPlayer);
    }

    private void assemblePortraitView() {
        // If the step has video set the video. Otherwise remove it from the fragment.
        Context context = getActivity();
        if (mRecipeStep.videoURL != null && !mRecipeStep.videoURL.isEmpty()) {

            // Create DataSource instance for video
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    getActivity(), Util.getUserAgent(
                    getActivity(), "recipeStepVideo"));

            // Create extractor for parsing media data
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // Set video to be player
            Uri videoUri = Uri.parse(mRecipeStep.videoURL);
            MediaSource videoSource = new ExtractorMediaSource(
                    videoUri, dataSourceFactory, extractorsFactory, null, null);

            // Prepare the player with the source
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(false);

        } else {
            // Remove the view with the exo player
            mExoPlayer.release();
            ((ViewGroup) mExoView.getParent()).removeView(mExoView);

            // Set the text view to attach to the parent view
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mStepText.getLayoutParams();
            View parentView = (View) mStepText.getParent();
            params.topToTop = parentView.getId();
            mStepText.requestLayout();
        }

        // If this step has a thumbnail image then set the image. Otherwise remove it from the step.
        if (mRecipeStep.thumbnailURL != null && !mRecipeStep.thumbnailURL.isEmpty()) {
            Picasso.get().load(mRecipeStep.thumbnailURL).into(mRecipeImage);
        } else {
            ((ViewGroup) mRecipeImage.getParent()).removeView(mRecipeImage);
        }

        // Set the text for the step details
        mStepText.setText(mRecipeStep.description);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}
