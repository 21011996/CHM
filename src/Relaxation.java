import java.io.*;
import java.util.*;

/**
 * Created by heat_wave on 29/03/16.
 */
public class Relaxation {
    final double EPS = 1e-15;
    final double OMEGA = 0.7d;
    Scanner in;
    PrintStream out;

    public static void main(String[] args) {
        new Relaxation().run(new File("randomTest.in"), null);
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
        System.out.println("Relaxation:");
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
        b = CommonMethods.adjustFree(matrix, b);
        matrix = CommonMethods.symmetrizeMatrix(matrix);
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }
        double[][] matrixB = CommonMethods.calculateB(matrix);
        double[] c = CommonMethods.calculateC(matrix, b);
        double[][] matrixB1 = CommonMethods.getBottomTriangle(matrixB);
        double[][] matrixB2 = CommonMethods.getTopTriangle(matrixB);

        int iteration = 0;
        double tempX[] = new double[n];
        double norm;

        if (CommonMethods.matrixNormEuclidean(matrixB1) + CommonMethods.matrixNormEuclidean(matrixB2) > 1) {
            System.err.println("||B1|| + ||B2|| > 1, relaxation method may not converge.");
        }

        do {
            System.out.println(iteration + ": " + Arrays.toString(x));
            iteration++;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    tempX[i] += matrixB1[i][j] * tempX[j];
                }
                for (int j = i + 1; j < n; j++) {
                    tempX[i] += matrixB2[i][j] * x[j];
                }
            }
            tempX = CommonMethods.sum(CommonMethods.scalarMulVecSc(OMEGA, tempX),
                    CommonMethods.scalarMulVecSc(1 - OMEGA, x));

            norm = CommonMethods.vectorNormInf(CommonMethods.sub(x, tempX));
            x = tempX;
        } while (norm > EPS);
        System.out.println(iteration + ": " + Arrays.toString(x) + " <- ans\n");
    }
}
