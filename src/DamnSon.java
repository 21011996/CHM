import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by Ilya239 on 29.03.2016.
 */
public class DamnSon {
    public static class Solver implements Runnable {
        IterationJacobi ij;
        File file;
        PrintStream out;
        boolean done;

        public void run() {
            ij.run(file, out);
            done = true;
        }

        Solver(File file, PrintStream out) {
            done = false;
            this.file = file;
            this.out = out;
            ij = new IterationJacobi();
        }
    }

    static Solver solver;

    public static void main(String[] args) {
        try {
            solver = new Solver(new File("badMatrix.in"), new PrintStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        solver.run();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!solver.done) {
            solver = null;
        }
    }
}
