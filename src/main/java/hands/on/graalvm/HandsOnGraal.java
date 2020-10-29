package hands.on.graalvm;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Optional;
import java.util.logging.Logger;

@Command(version = "@|yellow Hands on GraalVM 1.0|@", mixinStandardHelpOptions = true)
class HandsOnGraal implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(HandsOnGraal.class.getName());

    @Parameters(arity = "0..1", description = "The message to echo.")
    private String message;

    @Option(names = "--quiet", description = "No ouput.")
    boolean quiet = false;

    public static void main(String[] args) {
        CommandLine.run(new HandsOnGraal(), args);
    }

    @Override
    public void run() {
        if (!quiet) {
            String output = Optional.ofNullable(message).orElse("Hands on GraalVM.");
            LOGGER.info(output);
        }
    }
}
