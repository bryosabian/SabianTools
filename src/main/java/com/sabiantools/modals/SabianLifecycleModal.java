package com.sabiantools.modals;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Use a dialog fragment instead when possible
 */
public class SabianLifecycleModal extends SabianCustomModal implements LifecycleOwner, ViewModelStoreOwner {

    private LifecycleRegistry mLifecycleRegistry;

    private ViewModelStore mViewModelStore;

    public SabianLifecycleModal(@NonNull Context context) {
        super(context);
    }

    public SabianLifecycleModal(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLifecycleRegistry = new LifecycleRegistry(this);
        super.onCreate(savedInstanceState);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (mViewModelStore == null)
            mViewModelStore = new ViewModelStore();
        return mViewModelStore;
    }
}
