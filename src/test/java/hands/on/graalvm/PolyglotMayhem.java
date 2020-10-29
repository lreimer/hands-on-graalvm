package hands.on.graalvm;

import org.graalvm.polyglot.Context;

/**
 * Simple test for the polyglot embedded capabilities of GraalVM.
 */
public class PolyglotMayhem {

    public static void main(String[] args) {
        PolyglotMessage message = new PolyglotMessage();

        message.text = "Hello from Java!";
        helloJava(message);

        message.text = "Hello from JavaScript!";
        helloJavaScript(message);

        message.text = "Hello from R!";
        helloR(message);

        message.text = "Hello from Ruby!";
        helloRuby(message);

        message.text = "Hello from Python!";
        helloPython(message);

        System.out.println();
        System.out.printf("Called %s languages in the same JVM. Polyglot Mayhem!", message.invocations);
    }

    private static void helloJava(PolyglotMessage message) {
        message.invocations++;
        System.out.println(message.toString());
    }

    private static void helloJavaScript(PolyglotMessage message) {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getBindings("js").putMember("message", message);
            context.eval("js",
                            "message.invocations++;" +
                            "print(message);");
        }
    }

    private static void helloR(PolyglotMessage message) {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getPolyglotBindings().putMember("message", message);
            context.eval("R",
                    "message <- import('message');" +
                            "message$invocations <- message$invocations + 1;" +
                            "print(message$text);");
        }
    }

    private static void helloRuby(PolyglotMessage message) {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getPolyglotBindings().putMember("message", message);
            context.eval("ruby",
                    "message = Polyglot.import('message')\n" +
                            "message[:invocations] += 1\n" +
                            "puts message[:text]");
        }
    }

    private static void helloPython(PolyglotMessage message) {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            context.getPolyglotBindings().putMember("message", message);
            context.eval("python",
                    "import polyglot\n" +
                            "message =  polyglot.import_value('message')\n" +
                            "message['invocations'] += 1\n" +
                            "print(message['text'])");
        }
    }
}
