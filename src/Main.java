import java.io.File;

public class Main {

    public static void main(String[] args) {
        new Main().run(new File("goodMatrix.in"));
    }

    void run(File file) {
        new IterationJacobi().run(file);
        new IterationSeidel().run(file);
        new ConjugateGradients().run(file);
    }
}
