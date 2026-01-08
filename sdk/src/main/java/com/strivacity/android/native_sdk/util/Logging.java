package com.strivacity.android.native_sdk.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Logging interface used by the SDK.
 * <p>
 * **Thread safety:** Implementations of this interface must be thread-safe. Methods will be called
 * concurrently from multiple threads.
 * <p>
 * **Exception handling:** Implementations should handle exceptions internally and not throw from
 * these methods, as that could disrupt the SDK's operation.
 */
public interface Logging {
    void debug(@NonNull String body);

    void debug(@NonNull String body, @Nullable Throwable exception);

    void info(@NonNull String body);

    void info(@NonNull String body, @Nullable Throwable exception);

    void warn(@NonNull String body);

    void warn(@NonNull String body, @Nullable Throwable exception);

    void error(@NonNull String body);

    void error(@NonNull String body, @Nullable Throwable exception);

    class DefaultLogging implements Logging {

        @Override
        public void debug(@NonNull String body) {
            debug(body, null);
        }

        @Override
        public void debug(@NonNull String body, @Nullable Throwable exception) {
            Log.d("NativeSDK", body, exception);
        }

        @Override
        public void info(@NonNull String body) {
            info(body, null);
        }

        @Override
        public void info(@NonNull String body, @Nullable Throwable exception) {
            Log.i("NativeSDK", body, exception);
        }

        @Override
        public void warn(@NonNull String body) {
            warn(body, null);
        }

        @Override
        public void warn(@NonNull String body, @Nullable Throwable exception) {
            Log.w("NativeSDK", body, exception);
        }

        @Override
        public void error(@NonNull String body) {
            error(body, null);
        }

        @Override
        public void error(@NonNull String body, @Nullable Throwable exception) {
            Log.e("NativeSDK", body, exception);
        }
    }
}
