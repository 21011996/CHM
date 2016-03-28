import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Ilya239 on 28.03.2016.
 */
public class TestGenerator {
    static PrintStream out;

    public static void main(String[] args) {
        Locale format = new Locale("US");
        Locale.setDefault(format);
        int n = 50;
        try {
            out = new PrintStream(new File("randomTest.in"));
        } catch (Exception e) {
            System.err.println("Sth went wrong");
        }
        double[][] matrix = new double[n][n];
        double[] b = new double[n];
        double[] x = new double[n];

        for (int i = 0; i < n; i++) {
            x[i] = i + 1;
        }

        out.println(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double tmp = Math.floor(new Random().nextDouble() * 10000) / 1000;
                if (i == j) {
                    tmp *= 10 * n;
                }
                b[i] += x[j] * tmp;
                matrix[i][j] = tmp;
                out.print(tmp + " ");
            }
            out.println();
        }

        for (int i = 0; i < n; i++) {
            out.print(b[i] + " ");
        }
    }
}
