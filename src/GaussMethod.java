import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by shambala on 28/03/16.
 */
public class GaussMethod {
    final double EPS = 1e-12;
    Scanner in;
    PrintStream out;

    public static void main(String[] args) {
        new GaussMethod().run(new File("badMatrix.in"), null);
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
            e.printStackTrace();
        }
    }

    public void solve() {
        out.println("Gaussian elimination:");
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
        for (int i = 0; i < n; i++) {
            double absMax = 0;
            int maxRow = 0;
            for (int j = i; j<n; j++) {
                if (Math.abs(matrix[j][i]) > absMax) {
                    absMax = Math.abs(matrix[j][j]);
                    maxRow = j;
                }
            }
            if (absMax == 0) {
                out.println("Error in Gaussian elimination");
                return;
            }
            if (i != maxRow) {
                double[] tmp = matrix[i];
                matrix[i] = matrix[maxRow];
                matrix[maxRow] = tmp;
            }
            double div = matrix[i][i];
            for (int j = i; j<n; j++) {
                matrix[i][j] /= div;
            }
            b[i] /= div;
            for (int k = i+1; k<n; k++) {
                double mul = matrix[k][i];
                for (int j = i; j<n; j++) {
                    matrix[k][j] -= mul*matrix[i][j];
                }
                b[k] -= mul * b[i];
            }
        }
        /* for (int i = 0; i<n ; i++) {
            for (int j = 0; j<n; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        } */
        double[] ans = new double[n];
        for (int i = n-1; i>=0; i--) {
            ans[i] = b[i];
            for (int j = i+1; j<n; j++) {
                ans[i] -= matrix[i][j]*ans[j];
            }
        }
        out.println("Answer: " + Arrays.toString(ans) + "\n");

    }
}
