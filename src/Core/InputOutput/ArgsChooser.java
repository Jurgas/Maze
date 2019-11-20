package Core.InputOutput;

import java.io.File;

class ArgsChooser {
    private static File f;

    static File fileChooser(String[] args, String flag) throws FileNotGivenException {
        for (int i = 0; f == null && i < args.length; i++)
            if (args[i].equals(flag))
                if (i + 1 < args.length)
                    return f = new File(args[i + 1]);
        throw new FileNotGivenException("Nie podano pliku!");
    }
}
