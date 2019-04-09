package hands.on.graalvm;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Optional;

@Command(version = "@|yellow Hands on GraalVM 1.0|@", mixinStandardHelpOptions = true)
class HandsOnGraal implements Runnable {

    @Parameters(arity = "0..1", description = "The message to echo.")
    private String message;

    public static void main(String[] args) {
        CommandLine.run(new HandsOnGraal(), args);
    }

    @Override
    public void run() {
        String output = Optional.ofNullable(message).orElse("Hands on GraalVM.");
        System.out.println(output);
    }
}
