/**
 * Created by dasha on 3/27/16.
 */
public class CommonMethods {
    public static double[][] calculateB(double[][] matrix, int n) {
        double[][] matrixB = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixB[i][j] = -matrix[i][j] / matrix[i][i];
            }
            matrixB[i][i] = 0;
        }
        return matrixB;
    }

    public static double[] calculateC(double[][] matrix, double[] b, int n) {
        double[] c = new double[n];
        for (int i = 0; i < n; i++) {
            c[i] = b[i] / matrix[i][i];
        }
        return c;
    }

    public static double[] negate(double[] vec) {
        double[] res = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            res[i] = -vec[i];
        }
        return res;
    }

    public static double[] scalarMulVecSc(double scalar, double[] vec) {
        double[] res = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            res[i] = scalar * vec[i];
        }
        return res;
    }

    public static double scalarMulVecVec(double[] vec1, double[] vec2) {
        int n = vec1.length;
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += vec1[i] * vec2[i];
        }
        return res;
    }


    public static double[] mul(double[][] matrix, double[] vec) {
        int n = vec.length;
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = scalarMulVecVec(matrix[i], vec);
        }
        return res;
    }

    public static double[] sub(double[] vec1, double[] vec2) {
        int n = vec1.length;
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = vec1[i] - vec2[i];
        }
        return res;
    }

    public static double[] sum(double[] vec1, double[] vec2) {
        return sub(vec1, negate(vec2));
    }

    public static double vectorNorm(double[] vec) {
        return Math.sqrt(scalarMulVecVec(vec, vec));
    }

    public static double[] normalizeVector(double[] vec) {
        return scalarMulVecSc(1 / vectorNorm(vec), vec);
    }


    public static double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    public static double[][] symmetrizeMatrix(double[][] matrix) {
        return mulMatrices(transpose(matrix), matrix);
    }

    public static double[] adjustFree(double[][] matrix, double[] vec) {
        return mul(transpose(matrix), vec);
    }


    public static double[][] mulMatrices(double[][] matrix1, double[][] matrix2) {
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

    public static double matrixNormInf(double[][] matrix) {
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

    public static double matrixNormEuclidean(double[][] matrix) {
        double sum = 0;
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix) {
                sum += anAMatrix * anAMatrix;
            }
        }
        return Math.sqrt(sum);
    }
}
