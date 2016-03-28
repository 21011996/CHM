import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by msviridenkov on 28.03.16.
 */
public class Conditionality {
    private Scanner in;
    PrintStream out;

    public void solve() throws IOException {
        int n = in.nextInt();
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextDouble();
            }
        }

        double inverseMatrix[][] = invert(matrix);

        /*for (int i = 0; i < n; i++) {
            for (int j = 0; j < n;j ++) {
                out.print(inverseMatrix[i][j] + " ");
            }
            out.println();
        }*/

        //out.println(CommonMethods.matrixNormEuclidean(matrix) * CommonMethods.matrixNormEuclidean(inverseMatrix));
        out.println(CommonMethods.matrixNormInf(matrix) * CommonMethods.matrixNormInf(inverseMatrix));
    }

    public double[][] invert(double matrix[][]) {
        int n = matrix.length;
        double[][] identityMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                identityMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            identityMatrix[i][i] = 1;
        }

        //Прямой проход
        for (int k = 0; k < n; k++) {
            //Проверяем matrix[k][k] на равенство нулю, если верно - меняем k-ю строку с i-ой такой, что matrix[i][k] != 0
            if (matrix[k][k] == 0.0) {
                for (int i = k + 1; i < n; i++) {
                    if (matrix[i][k] != 0.0) {
                        for (int j = 0; j < n; j++) {
                            double tmp = matrix[k][j];
                            double tmp1 = identityMatrix[k][j];

                            matrix[k][j] = matrix[i][j];
                            matrix[i][j] = tmp;
                            identityMatrix[k][j] = identityMatrix[i][j];
                            identityMatrix[i][j] = tmp1;
                        }
                        break;
                    }
                }
            }

            //Разделим k-ю строку матрицы matrix на matrix[k][k], тем самым matrix[k][k] = 1, выполним то же самое для identityMatrix
            double tmp = matrix[k][k];
            for (int j = 0; j < n; j++) {
                matrix[k][j] /=  tmp;
                identityMatrix[k][j] /= tmp;
            }

            //Будем образовывать 0 в k-ом столбце матрицы matrix, а так же повторять все действия для identityMatrix
            double[] tmpAr = new double[n];
            for (int i = 0; i < n; i++) {
                tmpAr[i] = matrix[i][k];
            }

            for (int i = k + 1; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] -= matrix[k][j] * tmpAr[i];
                    identityMatrix[i][j] -= identityMatrix[k][j] * tmpAr[i];
                }
            }
        }

        //Обратный проход
        for (int k = n - 1; k > -1; k--) {
            double[] tmpAr = new double[n];
            for (int i = n - 1; i > -1; i--) {
                tmpAr[i] = matrix[i][k];
            }

            for (int i = k - 1; i > -1; i--) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] -= matrix[k][j] * tmpAr[i];
                    identityMatrix[i][j] -= identityMatrix[k][j] * tmpAr[i];
                }
            }
        }

        return identityMatrix;
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
}
