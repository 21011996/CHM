import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ilya239 on 27.03.2016.
 */
public class IterationSeidel {
    final double EPS = 1e-10;
    private Scanner in;
    PrintStream out;

    public static void main(String[] args) {
        new IterationSeidel().run(new File("randomTest.in"), null);
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
        out.println("Seidel's method of simple iterations:");
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
        double[][] matrixB1 = CommonMethods.getBottomTriangle(matrixB);
        double[][] matrixB2 = CommonMethods.getTopTriangle(matrixB);


        double normB = CommonMethods.matrixNormEuclidean(matrixB);
        double normB1 = CommonMethods.matrixNormEuclidean(matrixB1);
        double normB2 = CommonMethods.matrixNormEuclidean(matrixB2);
        double lim = EPS;
        if (1 - normB1 < EPS) {
            lim *= (1 - normB1) / normB;
        }

        int iteration = 0;
        double tempX[];
        double norm;

        boolean broken = false;
        if (normB1 + normB2 > 1) {
            System.err.println("||B1|| + ||B2|| > 1, Seidel's Method may not converge.");
        }
        do {
            iteration++;

            tempX = CommonMethods.sum(CommonMethods.mul(matrixB2, x), c);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    tempX[i] += matrixB1[i][j] * tempX[j];
                }
            }

            norm = CommonMethods.vectorNormEuclidean(CommonMethods.sub(x, tempX));
            x = tempX;
            if (iteration == 10000) {
                broken = true;
                break;
            }
        } while (norm > lim);

        if (broken) {
            out.println("Didn't converge");
        }
        out.println(iteration + ": " + Arrays.toString(x) + " <- ans\n");


    }
}
