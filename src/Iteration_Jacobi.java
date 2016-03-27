import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class Iteration_Jacobi {
    public static void main(String[] args) {
        new Iteration_Jacobi().run();
    }

    private Scanner in;

    private void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix" + ".in")));
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
        b = Common_methods.adjustFree(matrix, b);
        matrix = Common_methods.symmetrizeMatrix(matrix);

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }

        double[] tempX = new double[n];
        double norm;

        int iter = 0;
        do {
            System.out.println(iter + ": " + Arrays.toString(x));
            iter++;
            double[][] matrix_iter = new double[n][n];
            for (int i = 0; i < n; i++) {
                tempX[i] = b[i] / matrix[i][i];
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        matrix_iter[i][j] = matrix[i][j] * x[j] / matrix[i][i];
                        tempX[i] -= matrix[i][j] * x[j] / matrix[i][i];
                    }
                }
            }

            norm = Math.abs(x[0] - tempX[0]);

            for (int i = 0; i < n; i++) {
                if (Math.abs(x[i] - tempX[i]) > norm)
                    norm = Math.abs(x[i] - tempX[i]);
                x[i] = tempX[i];
            }

        } while (norm > EPS);

    }
}
