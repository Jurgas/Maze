package Core.InputOutput;

import java.io.IOException;

class FileNotGivenException extends IOException {
    FileNotGivenException(String message) {
        super(message);
    }
}
