package com.wanda.network;

/**
 * Created by sash on 05/02/14.
 */

/**
 * kind of a cool solution to toss results back to an activity :)
 * @param <T>
 */

public interface CallbackListenerInterface<T> {
    public void onTaskComplete(T result);
}
