package Core.InputOutput;

import java.io.File;

public class ArgsChooser {

    public File fileChooser(String[] args, String flag) throws FileNotGivenException {
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(flag))
                if (i + 1 < args.length)
                    return new File(args[i + 1]);
        throw new FileNotGivenException("Nie podano pliku!");
    }
}
