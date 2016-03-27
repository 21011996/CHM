import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dasha on 3/27/16.
 */

public class Conjugate_gradients {
    public static void main(String[] args) {
        new Conjugate_gradients().run();
    }

    Scanner in;
    PrintWriter out;
    double EPS = 1e-15;

    public void run() {
        try {
            in = new Scanner(new FileInputStream(new File("matrix" + ".in")));
            out = new PrintWriter(new File("matrix" + ".out"));
            solve();
            out.close();
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
        double[][] transposeM = transpose(matrix);
        matrix = mulMatrices(transposeM, matrix);

        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = in.nextDouble();
        }
        b = mul(transposeM, b);

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = (new Random()).nextDouble();
        }
        double[] dir = getToNorm(sub(b, mul(matrix, x)));
        double alpha = -scalarMulVecVec(negate(dir), dir) / scalarMulVecVec(mul(matrix, dir), dir);

        double normB = norm(b);
        int iter = 0;
        while (alpha > EPS) {
            System.out.println(iter + ": " + Arrays.toString(x));
            System.out.println(Arrays.toString(dir) + "\n" + alpha);

            x = sum(x, scalarMulVecSc(alpha, dir));
            double[] grad = sub(mul(matrix, x), b);

            double beta = scalarMulVecVec(mul(matrix, dir), grad) / scalarMulVecVec(mul(matrix, dir), dir);

            dir = getToNorm(sub(scalarMulVecSc(beta, dir), grad));
            alpha = -scalarMulVecVec(grad, dir) / scalarMulVecVec(mul(matrix, dir), dir);
            iter++;
        }

        System.out.println(iter + ": " + Arrays.toString(x) + " <- ans");
    }

    double[] negate(double[] vec) {
        double[] res = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            res[i] = -vec[i];
        }
        return res;
    }

    double[] scalarMulVecSc(double scalar, double[] vec) {
        double[] res = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            res[i] = scalar * vec[i];
        }
        return res;
    }

    double scalarMulVecVec(double[] vec1, double[] vec2) {
        int n = vec1.length;
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += vec1[i] * vec2[i];
        }
        return res;
    }


    double[] mul(double[][] matrix, double[] vec) {
        int n  = vec.length;
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = scalarMulVecVec(matrix[i], vec);
        }
        return res;
    }

    double[] sub(double[] vec1, double[] vec2) {
        int n = vec1.length;
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = vec1[i] - vec2[i];
        }
        return res;
    }

    double[] sum(double[] vec1, double[] vec2) {
        return sub(vec1, negate(vec2));
    }

    double norm(double[] vec) {
        return Math.sqrt(scalarMulVecVec(vec, vec));
    }

    double[] getToNorm(double[] vec) {
        double[] res = scalarMulVecSc(1 / norm(vec), vec);
        return res;
    }


    double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    double[][] mulMatrices(double[][] matrix1, double[][] matrix2) {
        int n = matrix1.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[] aux = new double[n];
                for (int k = 0; k < n; k++) {
                    aux[k] = matrix2[k][j];
                }
                res[i][j] = scalarMulVecVec(matrix1[i], aux);
            }
        }
        return res;
    }
}
