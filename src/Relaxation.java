import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by heat_wave on 29/03/16.
 */
public class Relaxation {
    final double EPS = 1e-15;
    final double OMEGA = 0.8d;
    FastScanner in;

    public static void main(String[] args) {
        new Relaxation().run();
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
                tempX[i] = CommonMethods.scalarMulVecVec(matrixB2[i], tempX) +
                        CommonMethods.scalarMulVecVec(matrixB1[i], x) + c[i];
            }
            tempX = CommonMethods.sum(CommonMethods.scalarMulVecSc(OMEGA, tempX),
                    CommonMethods.scalarMulVecSc(1 - OMEGA, x));

            norm = CommonMethods.vectorNormInf(CommonMethods.sub(x, tempX));
            x = tempX;
        } while (norm > EPS);
    }

    public void run() {
        in = new FastScanner(new File("goodMatrix.in"));
        solve();
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}
