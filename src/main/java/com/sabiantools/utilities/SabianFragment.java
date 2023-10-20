package com.sabiantools.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gc.materialdesign.widgets.SnackBar;
import com.sabiantools.R;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created By Brian Sabana on 6/20/2016.
 */
public class SabianFragment extends Fragment {
    protected View root;
    protected boolean isStarted = false;
    protected boolean isVisible = false;

    protected OnSabianFragmentAnimationLoadListener onAnimationLoaded;

    @Nullable
    public View findViewById(@IdRes int id) {
        return root.findViewById(id);
    }

    public String getTitle() {
        return "Sabian Fragment";
    }

    public static Fragment getInstance() {
        return new SabianFragment();
    }

    protected void displayShortMessage(String message) {
        SnackBar snackBar = new SnackBar(getActivity(), message);
        snackBar.setIndeterminate(true);
        snackBar.show();
    }

    protected void setRootView(View view) {
        this.root = view;
    }


    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        /**
         * Do this for APIs below 23
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this._onAttach(activity);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        /**
         * Do this for APIs above 23
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this._onAttach(context);
        }
    }

    /**
     * Recommended to use this in place of the legacy onAttach(Context/Activity) methods due to deprecation issues.
     * Thanks a lot Google. Fuck you!!
     *
     * @param context
     */
    protected void _onAttach(Context context) {
        if (context instanceof OnSabianFragmentAnimationLoadListener) {
            onAnimationLoaded = (OnSabianFragmentAnimationLoadListener) context;
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {

        if (!loadAnimation())
            return super.onCreateAnimation(transit, enter, nextAnim);

        final int animID = (enter) ? getAnimationStart() : getAnimationExit();

        final Animation animation = AnimationUtils.loadAnimation(getActivity(), animID);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                if (enter)
                    if (onAnimationLoaded != null)
                        onAnimationLoaded.onEnterAnimationStarted();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (enter)
                    if (onAnimationLoaded != null)
                        onAnimationLoaded.onEnterAnimationFinished();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return animation;
    }

    protected int getAnimationStart() {
        return R.anim.sabian_popup_show;
    }

    protected int getAnimationExit() {
        return R.anim.sabian_fade_out;
    }

    protected boolean loadAnimation() {
        return false;
    }

    public interface OnSabianFragmentAnimationLoadListener {
        void onEnterAnimationStarted();

        void onEnterAnimationFinished();
    }
}
