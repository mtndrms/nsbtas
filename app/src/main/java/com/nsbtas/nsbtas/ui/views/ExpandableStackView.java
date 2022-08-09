package com.nsbtas.nsbtas.ui.views;

import static com.nsbtas.nsbtas.utils.Utils.fromDpToPx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.TransitionBuilder;
import androidx.constraintlayout.widget.ConstraintSet;

import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.ArrayList;

public class ExpandableStackView extends MotionLayout {
    private Context context;
    private AttributeSet attrs = null;
    private int defStyleAttr = 0;
    View lastMovedTop;
    View lastMovedBottom;
    View middle;
    private boolean isGoingToTop = false;

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
            startSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));
            endSet.constrainHeight(view.getId(), fromDpToPx(getResources(), 175));

            startSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));
            endSet.constrainWidth(view.getId(), fromDpToPx(getResources(), 300));

            // Constrain card to parent view's start & end
            connectViewToParent(startSet, view);
            connectViewToParent(endSet, view);

            startSet.connect(
                    view.getId(),
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP);
            startSet.connect(
                    view.getId(),
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
            );

            if (middle == null) {
                middle = view;
                endSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP
                );
                endSet.connect(
                        view.getId(),
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                );
            } else if (lastMovedTop == null) {
                lastMovedTop = view;
                endSet.connect(
                        view.getId(),
                        ConstraintSet.BOTTOM,
                        middle.getId(),
                        ConstraintSet.TOP,
                        16
                );
            } else if (lastMovedBottom == null) {
                lastMovedBottom = view;
                endSet.connect(
                        view.getId(),
                        ConstraintSet.TOP,
                        middle.getId(),
                        ConstraintSet.BOTTOM,
                        16
                );
            } else {
                if (isGoingToTop) {
                    endSet.connect(
                            view.getId(),
                            ConstraintSet.BOTTOM,
                            lastMovedTop.getId(),
                            ConstraintSet.TOP,
                            16
                    );
                    lastMovedTop = view;
                    isGoingToTop = false;
                } else {
                    endSet.connect(
                            view.getId(),
                            ConstraintSet.TOP,
                            lastMovedBottom.getId(),
                            ConstraintSet.BOTTOM,
                            16
                    );
                    lastMovedBottom = view;
                    isGoingToTop = true;
                }
            }

            views.add(view);
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
                endSetId,
                endSet
        );
        transitionFirstPart.setDuration(500);

        scene.addTransition(transitionFirstPart);
        scene.setTransition(transitionFirstPart);

        setScene(scene);
        setTransition(transitionFirstPartId);
    }

    // Middle position of two card (aka. after fragment loads)
    private void boundTwoViewEnd(ConstraintSet constraintSet, View firstView, View secondView) {
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
