import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        new Main().run(new File("randomTest.in"));
    }

    void run(File file) {
        TestGenerator.main(null);
        new GaussMethod().run(file);
        new IterationJacobi().run(file);
        new IterationSeidel().run(file);
        new ConjugateGradients().run(file);
        new Conditionality().run(file);
    }
}
