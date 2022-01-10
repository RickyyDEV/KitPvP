package ridev.com.br.utils.errors.types;

public class ArenaException extends Exception {

    public ArenaException(String message, String code, int line) {
        super(message + " ERR: " + code + " LN: " + line);
    }
}
