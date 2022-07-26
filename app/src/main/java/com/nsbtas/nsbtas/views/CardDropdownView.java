package com.nsbtas.nsbtas.views;

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

import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.ArrayList;

public class CardDropdownView extends MotionLayout {
    private Context context;
    private AttributeSet attrs = null;
    private int defStyleAttr = 0;
    private int rotationSide = 1;
    private final int rotationRate = 5;

    public CardDropdownView(@NonNull Context context) {
        super(context);
    }

    public CardDropdownView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardDropdownView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(BaseAdapter adapter) {
        MotionScene scene = new MotionScene(this);

        // Constrains before transition
        int startSetId = View.generateViewId();
        ConstraintSet startSet = new ConstraintSet();
        startSet.clone(this);

        int middleSetId = View.generateViewId();
        ConstraintSet middleSet = new ConstraintSet();
        middleSet.clone(this);

        // Constrains after transition
        int endSetId = View.generateViewId();
        ConstraintSet endSet = new ConstraintSet();
        endSet.clone(this);

        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this);
            view.setId(View.generateViewId());

            // Card's width and height
            startSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));
            middleSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));
            endSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));

            startSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));
            middleSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));
            endSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));

            // Constrain card to parent view's start & end
            connectViewToParent(startSet, view);
            connectViewToParent(middleSet, view);
            connectViewToParent(endSet, view);

            // Constrain first card to top of it's parent
            if (i == 0) {
                startSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        fromDpToPx(getResources(), 16)
                );
                middleSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        fromDpToPx(getResources(), 16)
                );
            } else {
                // Others will be constrained to card before
                View last = views.get(i - 1);
                boundTwoViewMiddle(middleSet, last, view);

                // Last card's position after the transition
                if (i == adapter.getCount() - 1) {
                    middleSet.connect(
                            view.getId(),
                            ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM,
                            fromDpToPx(getResources(), 16)
                    );
                }
            }
            boundViewEnd(endSet, view);
            middleSet.setRotation(view.getId(), rotationSide * rotationRate);
            endSet.setRotation(view.getId(), rotationSide * -1 * rotationRate);
            views.add(view);
            rotationSide *= -1;
        }

        // Add cards to ConstraintLayout with reverse order
        for (int j = views.size() - 1; j >= 0; j--) {
            addView(views.get(j));
        }

        int transitionFirstPartId = View.generateViewId();
        MotionScene.Transition transitionFirstPart = TransitionBuilder.buildTransition(
                scene,
                transitionFirstPartId,
                startSetId,
                startSet,
                middleSetId,
                middleSet
        );
        transitionFirstPart.setDuration(500);

        int transitionSecondPartId = View.generateViewId();
        MultiStepPaymentFormHelper.setTransitionId(transitionSecondPartId);
        MotionScene.Transition transitionSecondPart = TransitionBuilder.buildTransition(
                scene,
                transitionSecondPartId,
                middleSetId,
                middleSet,
                endSetId,
                endSet
        );
        transitionSecondPart.setDuration(500);

        scene.addTransition(transitionFirstPart);
        scene.addTransition(transitionSecondPart);
        scene.setTransition(transitionFirstPart);

        setScene(scene);
        setTransition(transitionFirstPartId);
    }

    private void boundViewEnd(ConstraintSet constraintSet, View view) {
        constraintSet.connect(
                view.getId(),
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
        );
    }

    // Middle position of two card (aka. after fragment loads)
    private void boundTwoViewMiddle(ConstraintSet constraintSet, View firstView, View secondView) {
        constraintSet.connect(
                secondView.getId(),
                ConstraintSet.TOP,
                firstView.getId(),
                ConstraintSet.BOTTOM,
                fromDpToPx(getResources(), 8)
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
