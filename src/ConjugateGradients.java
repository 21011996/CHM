import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by dasha on 3/27/16.
 */

public class ConjugateGradients {
    final double EPS = 1e-15;
    Scanner in;

    public static void main(String[] args) {
        new ConjugateGradients().run();
    }

    public void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix" + ".in")));
            solve();
        } catch (Exception e) {
        }
    }

    public void solve() {
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

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }
        double[] dir = CommonMethods.normalizeVector(CommonMethods.sub(b, CommonMethods.mul(matrix, x)));
        double alpha = -CommonMethods.scalarMulVecVec(CommonMethods.negate(dir), dir) / CommonMethods.scalarMulVecVec(CommonMethods.mul(matrix, dir), dir);

        int iter = 0;
        while (alpha > EPS) {
            System.out.println(iter + ": " + Arrays.toString(x));
            System.out.println(Arrays.toString(dir) + "\n" + alpha);

            x = CommonMethods.sum(x, CommonMethods.scalarMulVecSc(alpha, dir));
            double[] grad = CommonMethods.sub(CommonMethods.mul(matrix, x), b);

            double beta = CommonMethods.scalarMulVecVec(CommonMethods.mul(matrix, dir), grad) / CommonMethods.scalarMulVecVec(CommonMethods.mul(matrix, dir), dir);

            dir = CommonMethods.normalizeVector(CommonMethods.sub(CommonMethods.scalarMulVecSc(beta, dir), grad));
            alpha = -CommonMethods.scalarMulVecVec(grad, dir) / CommonMethods.scalarMulVecVec(CommonMethods.mul(matrix, dir), dir);
            iter++;
        }

        System.out.println(iter + ": " + Arrays.toString(x) + " <- ans");
    }
}
