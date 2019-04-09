autoscale: true
footer: // Hands on _GraalVM_ -> { created with :heart: and :coffee: by _@LeanderReimer @qaware_ }
slidenumbers: true

[.hide-footer]
[.slidenumbers: false]
# __Hands on__
# [fit] _GraalVM_

---

![original fit](architecture.jpg)

---

## _GraalVM_ in a Nutshell

- __Polyglot Runtime__: JVM Languages, R, JavaScript, NodeJS, Ruby, Python, C/C++ via LLVM
- __Ahead-of-time Compilation__
  - Memory management, thread scheduling via SubstrateVM
- __GraalVM as a Platform__
  - Embed and extend GraalVM with Truffle
  - Implement your own language and tools

---

# [fit] _Demos_

---

## Build CLIs with _Picocli_ and _GraalVM_

- Picocli is a framework to easily build JVM command line apps.
- Support for ANSI colors, completion, sub commands, annotations and programmatic API.
- Good support for GraalVM AOT Compilation to Native Images via the `ReflectionConfigGenerator` utility.
- Native utilities and sidecar containers can now also be build using Java! __Golang is still cool.__

---

## Polyglot Mayhem

- The Graal Polyglot API allows you to embed and use different languages with full bidirectional interop.

```java
private static void helloR(PolyglotMessage message) {
    try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
        context.getPolyglotBindings().putMember("message", message);
        context.eval("R",
                "message <- import('message');" +
                "message$invocations <- message$invocations + 1;" +
                "print(message$text);");
    }
}
```
- __This is not the same as with the Java Scripting API (defined by JSR 223)!__
