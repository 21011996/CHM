import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationSeidel {
    final double EPS = 1e-10;
    private Scanner in;

    public static void main(String[] args) {
        new IterationSeidel().run(new File("randomTest.in"));
    }

    public void run(File file) {
        try {
            in = new Scanner(new FileInputStream(file));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void solve() {
        System.out.println("Seidel's method of simple iterations:");
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

        double[][] matrixB = CommonMethods.calculateB(matrix);
        double[] c = CommonMethods.calculateC(matrix, b);
        double[][] matrixB1 = new double[n][n];
        double[][] matrixB2 = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    matrixB1[i][j] = matrixB[i][j];
                } else {
                    matrixB2[i][j] = matrixB[i][j];
                }
            }
        }

        int iteration = 0;
        double tempX[];
        double norm;

        if (CommonMethods.matrixNormEuclidean(matrixB1) + CommonMethods.matrixNormEuclidean(matrixB2) > 1) {
            System.err.println("||B1|| + ||B2|| > 1, Seidel's Method may not converge.");
        }
        do {
            System.out.println(iteration + ": " + Arrays.toString(x));
            iteration++;

            tempX = CommonMethods.sum(CommonMethods.mul(matrixB2, x), c);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    tempX[i] += matrixB1[i][j] * tempX[j];
                }
            }

            norm = CommonMethods.vectorNorm(CommonMethods.sub(x, tempX));
            x = tempX;
        } while (norm > EPS);

        System.out.println(iteration + ": " + Arrays.toString(x) + " <- ans\n");


    }
}
