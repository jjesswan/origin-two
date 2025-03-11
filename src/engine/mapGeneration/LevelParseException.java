package engine.mapGeneration;

public class LevelParseException extends Exception {

    /**
     * Constructor for the exception
     *
     * @param message message for the exception
     * @param lineCount the row that caused the failure
     */
    public LevelParseException(String message, int lineCount, char[] row) {
      super("Line " + lineCount + ": " + message);

    }
}
