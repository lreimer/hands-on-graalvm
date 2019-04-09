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

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.
