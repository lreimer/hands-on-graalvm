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

    // see https://picocli.info/#_options_and_parameters

    @Parameters(arity = "0..1", description = "The message to echo.")
    private String message;

    @Option(names = "--quiet", description = "No ouput.")
    private boolean quiet = false;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new HandsOnGraal()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (!quiet) {
            String output = Optional.ofNullable(message).orElse("Hands on GraalVM.");
            LOGGER.info(output);
        }
    }
}
