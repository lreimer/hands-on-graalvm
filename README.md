# Hands-on GraalVM

This is my Hands-on on GraalVM demo repository. The README contains the basic
instructions and commands that I used to create the project.

## GraalVM installation

I used Jabba as a JVM manager to install GraalVM R14 on my machine. Once this is
done, make sure to install R, Python and Ruby using the Graal updater.

```
# use Jabba to install GraalVM
jabba remote-ls
jabba install graalvm@1.0.0-14
jabba use graalvm@1.0.0-14

export GRAALVM_HOME=$JAVA_HOME

# Use the GraalVM updater to install additional languages
gu available
gu install R
gu install python
gu install ruby
```

## Native CLIs with _Picocli_ and _GraalVM_

The combination of Picocli and GraalVM makes it possible to build cool and slim native
CLI applications in Java. Have a look at the `hands.on.graalvm.HandsOnGraal` implementation.
In the Gradle build file, the following 3 dependencies are required.

```groovy
dependencies {
  implementation 'info.picocli:picocli:3.9.6'
  runtime 'info.picocli:picocli-codegen:3.9.6'
  runtime 'jline:jline:2.14.6'
}
```

Next, we have to generate the information about reflective access in the CLI application.
Picocli provides a utility application to do this for us. The following Gradle tasks takes
care of the generation.

```groovy
task reflectionConfigGenerator(description: 'Generate reflection config', dependsOn: 'assemble', type: JavaExec) {
    main = 'picocli.codegen.aot.graalvm.ReflectionConfigGenerator'
    classpath = sourceSets.main.runtimeClasspath
    args = ['hands.on.graalvm.HandsOnGraal', '--output', 'build/reflect.json']
}
```

Once this is done we can use the GraalVM `native-image` command to compile the native executable
from the bytecode of our Java CLI application.

```groovy
task graalNativeImage(description: 'Generate native image with GraalVM', dependsOn: 'reflectionConfigGenerator', type: Exec) {
    workingDir "$buildDir"
    commandLine = [
            'native-image',
            '-cp', 'libs/picocli-3.9.6.jar:libs/picocli-codegen-3.9.6.jar:libs/jline-2.14.6.jar:libs/hands-on-graalvm.jar',
            '-H:ReflectionConfigurationFiles=reflect.json',
            '-H:+ReportUnsupportedElementsAtRuntime',
            '--delay-class-initialization-to-runtime=org.fusesource.jansi.WindowsAnsiOutputStream',
            '--no-server',
            'hands.on.graalvm.HandsOnGraal'
    ]
}
```

## Polyglot Mayhem

Another power of GraalVM is that you can run multiple languages in the same VM at the same time and
you can also access each others type system. Polyglot mayhem!

> This is not the same as provided by the Java Scripting API (defined by JSR 223)!

To run this demo simply perform `./gradlew polyglotMayhem`. This will use and call Java, JavaScript,
R, Ruby and Python all in the same JVM and also pass and modify a single share object. The output of
this tasks should be something like

```
> Task :polyglotMayhem
Hello from Java!
Hello from JavaScript!
[1] "Hello from R!"
Hello from Ruby!
Hello from Python!

Called 5 languages in the same JVM. Polyglot Mayhem!
```

Have a look at the class `hands.on.graalvm.PolyglotMayhem` and also the official Graal documentation
on how to embed and use other languages via the Graal Polyglot API. 

## References

- https://github.com/remkop/picocli
- https://picocli.info/picocli-on-graalvm.html
- https://picocli.info/quick-guide.html
- https://www.graalvm.org/docs/getting-started/
- https://github.com/oracle/graal
- https://www.graalvm.org/docs/graalvm-as-a-platform/embed/
- https://github.com/oracle/graal/tree/master/substratevm

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.
