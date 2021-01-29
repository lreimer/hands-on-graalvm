# Hands-on GraalVM

This is my Hands-on on GraalVM demo repository. The README contains the basic
instructions and commands that I used to create the project.

## GraalVM installation

I used Jabba as a JVM manager to install GraalVM R14 on my machine. Once this is
done, make sure to install R, Python and Ruby using the Graal updater.

```
# use Jabba to install GraalVM
jabba remote-ls
jabba install graalvm-ce-java8@20.1.0
jabba use graalvm-ce-java8@20.1.0

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
    implementation 'info.picocli:picocli:4.5.2'
    implementation 'info.picocli:picocli-jansi-graalvm:1.2.0'

    runtime 'info.picocli:picocli-codegen:4.1.4'
    runtime 'info.picocli:picocli-shell-jline3:4.1.4'
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
            '-cp', getClasspath(),
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

On the command line you can also run Ruby or Python scripts using the GraalVM. Make sure to install
the binaries using the `gu` command.

```
$ ruby src/test/polyglot/hello-ruby.rb
$ time ruby src/test/polyglot/hello-ruby.rb

# with GraalVM Ruby
$ gu install ruby
$ time $JAVA_HOME/bin/ruby src/test/polyglot/hello-ruby.rb

$ python src/test/polyglot/hello-python.py
$ time python src/test/polyglot/hello-python.py

$ python3 src/test/polyglot/hello-python.py
$ time python3 src/test/polyglot/hello-python.py

# with GraalVM Python
$ gu install python
$ time $JAVA_HOME/bin/graalpython src/test/polyglot/hello-python.py
```


## More demos

Make sure to checkout the official _GraalVM_ demo repository with its many examples.

```
git clone https://github.com/graalvm/graalvm-demos
cd graalvm-demos/polyglot-javascript-java-r
./build.sh
$GRAALVM_HOME/bin/node --polyglot --inspect --jvm server.js
open http://localhost:3000
```

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
