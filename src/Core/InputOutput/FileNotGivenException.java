package Core.InputOutput;

import java.io.IOException;

public class FileNotGivenException extends IOException {
    FileNotGivenException(String message) {
        super(message);
    }
}
