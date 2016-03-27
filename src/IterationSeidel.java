import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationSeidel {
    final double EPS = 1e-10;
    private Scanner in;

    public static void main(String[] args) {
        new IterationSeidel().run();
    }

    private void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix1" + ".in")));
            solve();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
