package info.androidhive.firebase.util;

import io.reactivex.Scheduler;


public interface IScheduler {
    Scheduler mainThread();
    Scheduler backgroundThread();
}
