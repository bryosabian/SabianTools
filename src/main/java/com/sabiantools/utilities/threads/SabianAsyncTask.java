package com.sabiantools.utilities.threads;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Utility thread tool for multithreading. Alternative to {@link android.os.AsyncTask}
 */
public class SabianAsyncTask {

    /**
     * The single thread executor
     */
    public static final String SERVICE_TYPE_SINGLE = "single";


    /**
     * Our executor
     */
    private ExecutorService executor;

    /**
     * Our handler
     */
    private Handler handler;

    /**
     * The service collections
     */
    private HashMap<String, ExecutorService> services;


    /**
     * The current service to be used
     * See {@link SabianAsyncTask#SERVICE_TYPE_SINGLE}
     */
    private String service = SERVICE_TYPE_SINGLE;

    /**
     * Whether to cancel all tasks once they're finished
     */
    private boolean awaitAllTasksToFinishBeforeCancel = false;

    /**
     * The time it will take for all tasks to complete before force shutting down in millisconds
     */
    private int taskCancelDelay = 500;

    /**
     * Init all services
     */
    public SabianAsyncTask() {
        initServices();
        initExecutor();
    }

    /**
     * Inits the executor
     */
    private void initExecutor() {
        if (executor == null) {
            executor = services.get(service);
        }
    }

    /**
     * Inits the handler
     */
    private void initHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * Inits all the services
     */
    private void initServices() {
        if (services != null)
            return;
        services = new HashMap<>();
        services.put(SERVICE_TYPE_SINGLE, Executors.newSingleThreadExecutor());
    }

    /**
     * Registers a new service to the thread pool collection
     *
     * @param name
     * @param service
     * @return
     */
    public SabianAsyncTask registerService(String name, ExecutorService service) {
        this.services.put(name, service);
        return this;
    }

    /**
     * Sets the default thread service to be used
     * See {@link SabianAsyncTask#SERVICE_TYPE_SINGLE}
     * You can register a custom one using {@link SabianAsyncTask#registerService(String, ExecutorService)}
     *
     * @param service
     * @return
     */
    public SabianAsyncTask setService(String service) {
        this.service = service;
        executor = services.get(service);
        return this;
    }

    /**
     * Executes a background task
     *
     * @param callable
     * @param callback
     * @param <R>
     */
    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {

        initExecutor();

        callback.onBefore();

        executor.execute(() -> {

            R result = null;
            Throwable throwable = null;

            try {
                result = callable.call();
            } catch (Exception e) {
                throwable = e;
            }

            boolean isSuccess = throwable == null;
            R finalResult = result;
            Throwable error = throwable;

            initHandler();

            handler.post(() -> {
                if (isSuccess)
                    callback.onComplete(finalResult);
                else {
                    callback.onError(error);
                }
            });
        });
    }

    /**
     * Cancels running background tasks if any
     */
    public void cancel() {
        try {
            if (executor != null) {

                // Stop accepting new tasks
                executor.shutdown();

                // Attempt to shut down left tasks if any
                try {
                    //Shut down all tasks immediately
                    if (!awaitAllTasksToFinishBeforeCancel) {
                        executor.shutdown();
                    } else {
                        // Wait a while for existing tasks to terminate
                        if (!executor.awaitTermination(taskCancelDelay, TimeUnit.MILLISECONDS)) {

                            //Terminate all pending running tasks
                            executor.shutdownNow();
                        }
                    }
                } catch (Exception e) {
                    executor.shutdownNow();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            if (executor != null) {
                executor = null;
            }
        }

    }
}