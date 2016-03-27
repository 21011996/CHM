import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationJacobi {
    final double EPS = 1e-10;
    private Scanner in;

    public static void main(String[] args) {
        new IterationJacobi().run(new File("matrix.in"));
    }

    public void run(File file) {
        try {
            in = new Scanner(new FileInputStream(file));
            solve();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void solve() {
        System.out.println("Jacobi's method of simple iterations:");
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

        int iter = 0;
        double[][] matrixB = new double[n][n];
        double[] bB = new double[n];
        for (int i = 0; i < n; i++) {
            bB[i] = b[i] / matrix[i][i];
            for (int j = 0; j < n; j++) {
                matrixB[i][j] = -matrix[i][j] / matrix[i][i];

            }
            matrixB[i][i] = 0;
        }

        if (CommonMethods.matrixNormEuclidean(matrixB) > 1) {
            System.out.println("||B|| > 1. Jacobi's Method won't converge.\n");
            return;
        }
        do {
            iter++;

            tempX = CommonMethods.sum(CommonMethods.mul(matrixB, x), bB);

            norm = CommonMethods.vectorNorm(CommonMethods.sub(x, tempX));
            x = tempX;
        } while (norm > EPS);

        System.out.println(iter + ": " + Arrays.toString(x) + " <- ans\n");
    }
}
