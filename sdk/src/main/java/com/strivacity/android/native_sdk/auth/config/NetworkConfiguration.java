package com.strivacity.android.native_sdk.auth.config;

import com.strivacity.android.native_sdk.BuildConfig;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Static configuration for the network communication layer of the SDK.
 *
 * <p>Use {@link NetworkConfiguration#builder()} to construct an instance, or
 * {@link NetworkConfiguration#defaultConfiguration()} for the default settings.
 *
 * <p><b>userAgent</b> – The User-Agent header value sent with every network request. Defaults to
 * {@code "strivacity-sdk-android"}. Must be at least 3 characters after trimming; an
 * {@link IllegalArgumentException} is thrown at construction time otherwise.
 *
 * <p><b>customRequestHeaders</b> – Additional HTTP headers included in every network request.
 * Every key must follow the {@code CustomHeaderFieldName} convention: it must start with the
 * {@code x-sty-} prefix, be entirely lowercase, and not be equal to the bare prefix
 * {@code "x-sty-"} — all three rules are enforced at construction time and an
 * {@link IllegalArgumentException} is thrown on violation. Headers with the {@code x-sty-} prefix
 * are available in server-side event Hooks on the backend. Defaults to an empty map.
 */
@Data
@Builder
public final class NetworkConfiguration {

    private static final String DEFAULT_USER_AGENT = "strivacity-sdk-android-native";
    private static final String CUSTOM_HEADER_PREFIX = "x-sty-";

    @lombok.Builder.Default
    private final String userAgent = DEFAULT_USER_AGENT;

    @lombok.Builder.Default
    @With(value = AccessLevel.PRIVATE)
    private final Map<String, String> customRequestHeaders = Collections.emptyMap();

    private NetworkConfiguration(String userAgent, Map<String, String> customRequestHeaders) {
        if (userAgent.trim().length() < 3) {
            throw new IllegalArgumentException("User agent must be at least 3 characters");
        }

        for (String key : customRequestHeaders.keySet()) {
            if (!key.equals(key.toLowerCase())) {
                throw new IllegalArgumentException(
                    "Custom request headers must be defined with lowercase. eg. `x-sty-my-header`"
                );
            }
            if (!key.startsWith(CUSTOM_HEADER_PREFIX)) {
                throw new IllegalArgumentException("Custom request headers must start with `x-sty-` prefix.");
            }
            if (key.trim().equals(CUSTOM_HEADER_PREFIX)) {
                throw new IllegalArgumentException("Cannot add \"x-sty-\" header as it is a reserved header prefix.");
            }
        }

        this.userAgent = userAgent;
        this.customRequestHeaders = Collections.unmodifiableMap(new HashMap<>(customRequestHeaders));
    }

    public NetworkConfiguration addSdkVersionCustomHeader() {
        if (customRequestHeaders.containsKey("x-sty-sdk-version")) {
            return this;
        }

        final Map<String, String> copy = new LinkedHashMap<>(customRequestHeaders);
        copy.put("x-sty-sdk-version", BuildConfig.STRIVACITY_SDK_VERSION);
        return this.withCustomRequestHeaders(copy);
    }

    public static NetworkConfiguration defaultConfiguration() {
        return NetworkConfiguration.builder().build();
    }
}
