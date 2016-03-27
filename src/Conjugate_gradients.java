import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by dasha on 3/27/16.
 */

public class Conjugate_gradients {
    public static void main(String[] args) {
        new Conjugate_gradients().run();
    }

    Scanner in;
    double EPS = 1e-15;

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

        b = Common_methods.adjustFree(matrix, b);
        matrix = Common_methods.symmetrizeMatrix(matrix);

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }
        double[] dir = Common_methods.normalizeVector(Common_methods.sub(b, Common_methods.mul(matrix, x)));
        double alpha = -Common_methods.scalarMulVecVec(Common_methods.negate(dir), dir) / Common_methods.scalarMulVecVec(Common_methods.mul(matrix, dir), dir);

        int iter = 0;
        while (alpha > EPS) {
            System.out.println(iter + ": " + Arrays.toString(x));
            System.out.println(Arrays.toString(dir) + "\n" + alpha);

            x = Common_methods.sum(x, Common_methods.scalarMulVecSc(alpha, dir));
            double[] grad = Common_methods.sub(Common_methods.mul(matrix, x), b);

            double beta = Common_methods.scalarMulVecVec(Common_methods.mul(matrix, dir), grad) / Common_methods.scalarMulVecVec(Common_methods.mul(matrix, dir), dir);

            dir = Common_methods.normalizeVector(Common_methods.sub(Common_methods.scalarMulVecSc(beta, dir), grad));
            alpha = -Common_methods.scalarMulVecVec(grad, dir) / Common_methods.scalarMulVecVec(Common_methods.mul(matrix, dir), dir);
            iter++;
        }

        System.out.println(iter + ": " + Arrays.toString(x) + " <- ans");
    }
}
