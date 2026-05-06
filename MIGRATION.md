# Migration guide between versions

## v2.1.0 - v2.2.0

Prefer to use `NativeSDK.builder()` to initialize the SDK. Old class constructors will be removed in a future release. 

> Reasoning: possible permutations of all constructor params make construction via builder more practical.

**Old (Deprecated):**

```java
final NativeSDK nativeSDK =
    new NativeSDK(
        tenantConfiguration,
        new ViewFactory(this),
        new CookieManager(),
        this.getSharedPreferences("test", MODE_PRIVATE)
    );
```

**New:**

```java
final NativeSDK nativeSDK =
    NativeSDK
        .builder()
        .tenantConfiguration(tenantConfiguration)
        .viewFactory(new ViewFactory(this))
        .cookieHandler(new CookieManager())
        .sharedPreferences(this.getSharedPreferences("test", MODE_PRIVATE))
        .build();
```

### Capability differences

Some of the features (eg. custom user agent, custom request headers) is only available via builder. In the future, only the builder will be expanded with new features, configurations.
