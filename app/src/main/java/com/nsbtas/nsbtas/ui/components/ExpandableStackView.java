package com.nsbtas.nsbtas.ui.components;

import static com.nsbtas.nsbtas.utils.Utils.fromDpToPx;

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

        int startSetId = View.generateViewId();
        ConstraintSet startSet = new ConstraintSet();
        startSet.clone(this);

        int endSetId = View.generateViewId();
        ConstraintSet endSet = new ConstraintSet();
        endSet.clone(this);

        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this);
            view.setId(View.generateViewId());

            // Card's width and height
            startSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));
            endSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));
            startSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));
            endSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));

            connectViewToParent(startSet, view);
            connectViewToParent(endSet, view);

            if (i == 0) {
                startSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        fromDpToPx(getResources(), 16)
                );

                endSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP
                );
            } else {
                View last = views.get(i - 1);
                boundTwoViewEnd(endSet, last, view);
                if (i == adapter.getCount() - 1) {
                    endSet.connect(
                            view.getId(),
                            ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM,
                            fromDpToPx(getResources(), 16)
                    );
                }

                int tmp;
                if (i < 3) {
                    tmp = fromDpToPx(getResources(), 16);
                } else {
                    tmp = 0;
                }

                boundTwoViewStart(startSet, last, view, tmp);
            }
            views.add(view);
        }

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

    private void boundTwoViewEnd(ConstraintSet constraintSet, View firstView, View secondView) {
        constraintSet.connect(
                secondView.getId(),
                ConstraintSet.TOP,
                firstView.getId(),
                ConstraintSet.BOTTOM,
                fromDpToPx(getResources(), 8)
        );
    }

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
