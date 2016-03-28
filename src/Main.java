import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        new Main().run(new File("randomTest.in"), new PrintStream("output.txt"));
    }

    void run(File file, PrintStream out) {
        TestGenerator.main(null);
        new GaussMethod().run(file, out);
        new IterationJacobi().run(file, out);
        new IterationSeidel().run(file, out);
        new ConjugateGradients().run(file, out);
        new Conditionality().run(file, out);
    }
}
