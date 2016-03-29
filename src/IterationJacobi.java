import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationJacobi {
    final double EPS = 1e-10;
    private Scanner in;
    PrintStream out;

    public static void main(String[] args) {
        new IterationJacobi().run(new File("randomTest.in"), null);
    }

    public void run(File file, PrintStream out) {
        if (out == null) {
            this.out = System.out;
        } else {
            this.out = out;
        }
        try {
            Locale format = new Locale("US");
            Locale.setDefault(format);
            in = new Scanner(new FileInputStream(file));
            solve();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void solve() {
        out.println("Jacobi's method of simple iterations:");
        int n = in.nextInt();
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextDouble();
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = in.nextDouble();
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }

        double[] tempX;
        double norm;
        boolean broken = false;

        int iter = 0;
        double[][] matrixB = CommonMethods.calculateB(matrix);
        double[] c = CommonMethods.calculateC(matrix, b);
        double normB = CommonMethods.matrixNormEuclidean(matrixB);
        double lim = EPS;
        if (1 - normB < EPS) {
            lim *= (1 - normB) / normB;
        }

        if (normB > 1) {
            System.err.println("||B|| > 1. Jacobi's Method may not converge.");
        }
        do {
            iter++;

            tempX = CommonMethods.sum(CommonMethods.mul(matrixB, x), c);

            norm = CommonMethods.vectorNormEuclidean(CommonMethods.sub(x, tempX));
            x = tempX;
            if (iter == 10000) {
                broken = true;
                break;
            }
        } while (norm > lim);

        if (broken) {
            out.println("Didn't converge");
        }
        out.println(iter + ": " + Arrays.toString(x) + " <- ans\n");

    }

}
