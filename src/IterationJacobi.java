import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationJacobi {
    public static void main(String[] args) {
        new IterationJacobi().run();
    }

    private Scanner in;

    private void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix1" + ".in")));
            solve();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private double EPS = 1e-10;

    private void solve() {
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
                if (i != j) {
                    matrixB[i][j] = -matrix[i][j] / matrix[i][i];
                }
            }
        }

        if (CommonMethods.matrixNormEuclidean(matrixB) < 1) {
            do {
                System.out.println(iter + ": " + Arrays.toString(x));
                iter++;

                tempX = CommonMethods.sum(CommonMethods.mul(matrixB, x), bB);

                norm = CommonMethods.vectorNorm(CommonMethods.sub(x, tempX));

                x = tempX;

            } while (norm > EPS);
        } else {
            System.out.println("||B|| = " + CommonMethods.matrixNormEuclidean(matrixB) + " > 1");
        }
    }
}
