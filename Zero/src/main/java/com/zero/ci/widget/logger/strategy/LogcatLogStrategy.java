package com.zero.ci.widget.logger.strategy;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.zero.ci.widget.logger.LoggerUtils.checkNotNull;


/**
 * LogCat implementation for {@link LogStrategy}
 * <p>
 * This simply prints out all logs to Logcat by using standard {@link Log} class.
 */
public class LogcatLogStrategy implements LogStrategy {

    static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        checkNotNull(message);

        if (tag == null) {
            tag = DEFAULT_TAG;
        }

        Log.println(priority, tag, message);
    }
}
