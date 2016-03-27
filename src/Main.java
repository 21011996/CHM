import java.io.File;

public class Main {

    public static void main(String[] args) {
        new Main().run(new File("matrix.in"));
    }

    void run(File file) {
        new IterationJacobi().run(file);
        new ConjugateGradients().run(file);
    }

}
