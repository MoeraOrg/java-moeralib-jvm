# java-moeralib-jvm

JVM-specific bindings for [java-moeralib][1].

The library provides OkHttp implementations for the HTTP and JSON-RPC transport
interfaces in `moeralib`:

- `org.moera.lib.http.OkHttpTransport`
- `org.moera.lib.jsonrpc.OkHttpJsonRpcFetcher`

## Maven

```xml
<dependency>
    <groupId>org.moera</groupId>
    <artifactId>moeralib-jvm</artifactId>
    <version>0.18.6</version>
</dependency>
```

## Usage

```java
var node = new MoeraNode(new OkHttpTransport(), nodeUrl);
var naming = new MoeraNaming(new OkHttpJsonRpcFetcher(namingUrl));
```

Read the [java-moeralib documentation][2] on the Moera website.

[1]: https://github.com/MoeraOrg/java-moeralib
[2]: https://moera.org/development/java-moeralib/
