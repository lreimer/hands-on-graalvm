package hands.on.graalvm;

/**
 * Simple DTO to demonstrate polyglot access.
 */
public class PolyglotMessage {
    public String text;
    public int invocations = 0;

    @Override
    public String toString() {
        return text;
    }
}
