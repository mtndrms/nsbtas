package com.nsbtas.nsbtas.ui.views;

import static com.nsbtas.nsbtas.utils.Utils.FromDpToPx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.TransitionBuilder;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class ExpandableStackView extends MotionLayout {
    private Context context;
    private AttributeSet attrs = null;
    private int defStyleAttr = 0;

    public ExpandableStackView(@NonNull Context context) {
        super(context);
    }

    public ExpandableStackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableStackView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(BaseAdapter adapter) {
        MotionScene scene = new MotionScene(this);

        // Constrains before transition
        int startSetId = View.generateViewId();
        ConstraintSet startSet = new ConstraintSet();
        startSet.clone(this);

        // Constrains after transition
        int endSetId = View.generateViewId();
        ConstraintSet endSet = new ConstraintSet();
        endSet.clone(this);

        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this);
            view.setId(View.generateViewId());

            // Card's width and height
            startSet.constrainHeight(view.getId(), FromDpToPx(getResources(), 175));
            endSet.constrainHeight(view.getId(), FromDpToPx(getResources(), 175));
            startSet.constrainWidth(view.getId(), FromDpToPx(getResources(), 300));
            endSet.constrainWidth(view.getId(), FromDpToPx(getResources(), 300));

            // Constrain card to parent view's start & end
            connectViewToParent(startSet, view);
            connectViewToParent(endSet, view);

            // Constrain first card to top of it's parent
            if (i == 0) {
                startSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        FromDpToPx(getResources(), 16)
                );
                endSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP
                );
            } else {
                // Others will be constrained to last card
                View last = views.get(i - 1);
                boundTwoViewEnd(endSet, last, view);

                // Last card's position after the transition
                if (i == adapter.getCount() - 1) {
                    endSet.connect(
                            view.getId(),
                            ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM,
                            FromDpToPx(getResources(), 16)
                    );
                }

                // First 3 card should be visible as stack
                int tmp;
                if (i < 3) {
                    tmp = FromDpToPx(getResources(), 16);
                }
                // Others will be on top of each other
                else {
                    tmp = 0;
                }

                boundTwoViewStart(startSet, last, view, tmp);
            }
            views.add(view);
        }

        // Add cards to ConstraintLayout with reverse order
        for (int j = views.size() - 1; j >= 0; j--) {
            addView(views.get(j));
        }

        int transitionId = View.generateViewId();
        MotionScene.Transition transaction = TransitionBuilder.buildTransition(
                scene,
                transitionId,
                startSetId,
                startSet,
                endSetId,
                endSet
        );
        transaction.setDuration(400);

        scene.addTransition(transaction);
        scene.setTransition(transaction);
        setScene(scene);

        setTransition(transitionId);
    }

    // End position of two card (aka. after transition)
    private void boundTwoViewEnd(ConstraintSet constraintSet, View firstView, View secondView) {
        constraintSet.connect(
                secondView.getId(),
                ConstraintSet.TOP,
                firstView.getId(),
                ConstraintSet.BOTTOM,
                FromDpToPx(getResources(), 8)
        );
    }

    // Start position of two card (aka. before transition)
    // Margin will give stack effect
    private void boundTwoViewStart(ConstraintSet constraintSet, View firstView, View secondView, int marginBottom) {
        constraintSet.connect(
                secondView.getId(),
                ConstraintSet.TOP,
                firstView.getId(),
                ConstraintSet.TOP,
                marginBottom
        );
    }

    private void connectViewToParent(ConstraintSet constraintSet, View firstView) {
        constraintSet.connect(
                firstView.getId(),
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
        );

        constraintSet.connect(
                firstView.getId(),
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
        );
    }
}
