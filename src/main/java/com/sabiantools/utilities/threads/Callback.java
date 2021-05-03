package com.sabiantools.utilities.threads;

public interface Callback<R> {

    default void onBefore() {

    }

    void onComplete(R result);

    default void onError(Throwable e) {
        e.printStackTrace();
    }
}
