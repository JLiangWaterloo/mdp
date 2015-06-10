package gsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author jimmy
 */
public final class DimacsParser {

    private DimacsParser() {
    }

    public static int[][] parse(String in) throws IOException {
        try (StringReader sin = new StringReader(in)) {
            return parse(sin);
        }
    }

    public static int[][] parse(File in) throws IOException {
        try (FileReader fin = new FileReader(in)) {
            return parse(fin);
        }
    }

    public static int[][] parse(Reader in) throws IOException {
        BufferedReader bin = new BufferedReader(in);
        String line;

        int[][] clauses = null;
        int size = 0;

        while ((line = bin.readLine()) != null) {
            if (!line.isEmpty() && line.charAt(0) != 'c') {
                String[] tokens = line.split(" ");
                if (tokens.length == 4 && tokens[0].equals("p") && tokens[1].equals("cnf")) {
                    if (clauses != null) {
                        throw new IOException("Found the line \"p cnf ...\" twice.");
                    }
                    int nClauses = Integer.parseInt(tokens[3]);
                    clauses = new int[nClauses][];
                } else {
                    if (!tokens[tokens.length - 1].equals("0")) {
                        throw new IOException("Expected the clause \"" + line + "\" to end with a 0.");
                    }
                    int[] clause = new int[tokens.length - 1];
                    for (int i = 0; i < clause.length; i++) {
                        clause[i] = Var.fromDimacs(Integer.parseInt(tokens[i]));
                    }
                    clauses[size++] = clause;
                }
            }
        }

        if (size != clauses.length) {
            throw new IOException("Expected " + clauses.length + " clauses, found " + size + ".");
        }

        return clauses;
    }
}
