import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class Iteration_Jacobi {
    public static void main(String[] args) {
        new Iteration_Jacobi().run();
    }

    private Scanner in;
    private PrintWriter out;

    private void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix1" + ".in")));
            out = new PrintWriter(new File("matrix1" + ".out"));
            solve();
            out.close();
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
            x[i] = in.nextDouble();
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

    double matrix_norm(double[][] matrix) {
        double max = -1e+10;
        for (double[] aMatrix : matrix) {
            double temp = 0;
            for (double anAMatrix : aMatrix) {
                temp += Math.abs(anAMatrix);
            }
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }
}
