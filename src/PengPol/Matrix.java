package PengPol;
import static PengPol.Matrix.transpose;
import static PengPol.Vector.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
/**
 * Kelas Library Operasi Matrix
 *
 * @author Edho
 */
public class Matrix {
    public static class EigenPair {
        double[][] eigenVector;
        double[] eigenValue;
    }
    public static double[][] add(double[][] m1, double[][] m2) {
        if (check_matrix_length(m1, m2)) {
            double[][] result = new double[m1.length][m2[0].length];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[i].length; j++) {
                    result[i][j] = m1[i][j] + m2[i][j];
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Matrix must be the same length");
        }
    }
    public static double[][] subtract(double[][] m1, double[][] m2) {
        if (check_matrix_length(m1, m2)) {
            double[][] result = new double[m1.length][m2[0].length];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[i].length; j++) {
                    result[i][j] = m1[i][j] - m2[i][j];
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Matrix must be the same length");
        }
    }
    public static double[][] multiply(double[][] m1, double[][] m2) {
        if (check_matrix_mult(m1, m2)) {
            double[][] result = new double[m1.length][m2[0].length];
//            for (int i = 0; i < m1.length; i++) {
//                for (int j = 0; j < m2[0].length; j++) {
//                }
//            }
            IntStream.range(0, m1.length).parallel().forEach(i -> {
                IntStream.range(0, m2[0].length).parallel().forEach(j -> {
                    for (int k = 0; k < m1[0].length; k++) {
                        result[i][j] += m1[i][k] * m2[k][j];
                    }
                });
            });
            return result;
        } else {
            throw new IllegalArgumentException("Matrix must be the same length");
        }
    }
    public static double[][] add(double[][] m1, double scalar) {
        double[][] result = new double[m1.length][m1[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                result[i][j] = m1[i][j] + scalar;
            }
        }
        return result;
    }
    public static double[][] multiply(double[][] m1, double scalar) {
        double[][] result = new double[m1.length][m1[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                result[i][j] = m1[i][j] * scalar;
            }
        }
        return result;
    }
    public static double[][] transpose(double[][] m) {
        double[][] result = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                result[j][i] = m[i][j];
            }
        }
        return result;
    }
    public static double[][] transpose(double[][] m, int num_of_thread) {
        double[][] result = new double[m[0].length][m.length];
        ExecutorService exe = Executors.newFixedThreadPool(num_of_thread);
        for (int i = 0; i < m.length; i++) {
            exe.execute(new trans_w(m, result, i));
        }
        exe.shutdown();
        try {
            exe.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        return result;
    }
    private static class trans_w implements Runnable {
        double[][] m;
        double[][] r;
        int i;
        public trans_w(double[][] m, double[][] r, int i) {
            this.m = m;
            this.r = r;
            this.i = i;
        }
        @Override
        public void run() {
            for (int j = 0; j < m[i].length; j++) {
                r[j][i] = m[i][j];
            }
        }
    }
    public static double gaussian_determinant(double[][] m) {
        double result = 1;
        for (int i = 0; i < m[0].length; i++) {
            for (int j = i + 1; j < m.length; j++) {
                double ratio = m[j][i] / m[i][i];
                for (int k = 0; k < m[i].length; k++) {
                    m[j][k] = m[j][k] - m[i][k] * ratio;
                }
            }
        }
        for (int i = 0; i < m.length; i++) {
            result *= m[i][i];
        }
        return result;
    }
    public static double[][] cofactor(double[][] m) {
        double[][] result = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                result[i][j] = gaussian_determinant(sub_matrix(m, i, j));
            }
        }
        return result;
    }
    public static double[][] adjoint(double[][] m) {
        return transpose(cofactor(m));
    }
    public static double[][] invers(double[][] m) {
        return multiply(adjoint(m), 1 / gaussian_determinant(m));
    }
    public static double[][] copy_matrix(double[][] m) {
        double[][] result = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                result[i][j] = m[i][j];
            }
        }
        return result;
    }
    private static double[][] sub_matrix(double[][] m, int row, int col) {
        if (check_matrix_square(m)) {
            double[][] result = new double[m.length - 1][m[0].length - 1];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    result[i][j] = m[(row + 1 + i) % m.length][(col + 1 + j) % m[i].length];
                }
            }
            return result;
        } else {
            return null;
        }
    }
    public static void printf_matrix(double[][] m, int dec_place) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.printf("%." + dec_place + "f ", m[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
    @Deprecated
    public static double[][] orthogonal_matrix(double[][] m) {
        double[][] result = transpose(m);
        m = transpose(m);
        for (int i = 1; i < m.length; i++) {
            result[i] = Vector.vector_copy(m[i]);
            double[][] cache = new double[i][];
            for (int j = 0; j < i; j++) {
         //       cache[i] = Vector.projection(result[j], m[i]);
            }
            for (int j = 0; j < i; j++) {
                result[i] = Vector.sub(result[i], cache[j]);
            }
        }
        return transpose(result);
    }
    public static double[][] orthonormal_matrix(double[][] m) throws InterruptedException {
        double[][] result = orthogonal_matrix(m);
        result = transpose(result);
        for (int i = 0; i < result.length; i++) {
            double l = Vector.vector_length(result[i]);
            result[i] = Vector.multiply(result[i], 1 / l);
        }
        return transpose(result);
    }
    public static double[][] Q_decompose(double[][] m) throws InterruptedException {
        return orthonormal_matrix(m);
    }
    public static double[][] R_decompose(double[][] m) throws InterruptedException {
        return multiply(transpose(Q_decompose(m)), m);
    }
    public static EigenPair eigen_pair(double[][] m, double threshold) throws InterruptedException {
        double[][] result = copy_matrix(m);
        double error = Double.MAX_VALUE;
        double[][] Q = Q_decompose(result);
        double[][] pro = Q;
        while (error > threshold) {
            double[][] new_result = multiply(multiply(transpose(Q), result), Q);
            error = error(new_result, result);
            result = new_result;
            Q = Q_decompose(result);
            pro = multiply(pro, Q);
        }
        double[] real_result = new double[m.length];
        for (int i = 0; i < real_result.length; i++) {
            real_result[i] = result[i][i];
        }
        EigenPair ret = new EigenPair();
        ret.eigenValue = real_result;
        ret.eigenVector = pro;
        return ret;
    }
    public static EigenPair eigen_pair_non_square(double[][] m, double threshold) throws InterruptedException {
        return eigen_pair(covariance_matrix(m), threshold);
    }
    public static double[][] zero_mean_matrix(double[][] m) {
        double[][] result = transpose(m);
        for (int i = 0; i < result.length; i++) {
            double mean = Vector.mean(result[i]);
            result[i] = Vector.add(result[i], mean * -1);
        }
        return transpose(result);
    }
    public static double[][] covariance_matrix(double[][] m) {
        double[][] result = zero_mean_matrix(m);
        result = multiply(multiply(transpose(result), result), ((double) 1) / (result.length));
        return result;
    }
    public static double[] linear_solver(double[][] m, double[] r) {
        double[][] new_r = new double[r.length][1];
        for (int i = 0; i < r.length; i++) {
            new_r[i][0] = r[i];
        }
        double[][] new_m = invers(m);
        double[][] result = multiply(new_m, new_r);
        return transpose(result)[0];
    }
    private static double error(double[][] m1, double[][] m2) {
        if (!check_matrix_length(m1, m2)) {
            return -1;
        }
        double result = 0;
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                result += Math.abs(Math.abs(m1[i][j]) - Math.abs(m2[i][j]));
            }
        }
        return result;
    }
    private static boolean check_matrix_length(double[][] m1, double[][] m2) {
        return m1.length == m2.length && m1[0].length == m2[0].length;
    }
    private static boolean check_matrix_mult(double[][] m1, double[][] m2) {
        return m1[0].length == m2.length;
    }
    private static boolean check_matrix_square(double[][] m) {
        return m.length == m[0].length;
    }
    public static void main(String[] args) throws InterruptedException {
        double[][] m = new double[][]{
            {1, 3, 0, 0},
            {3, 2, 1, 0},
            {0, 1, 3, 4},
            {0, 0, 4, 1}
        };
        double[][] m1 = new double[50][100];
        Random rand = new Random();
        for (int i = 0; i < m1.length; i++) {
            int max = rand.nextInt(25) + 1;
            for (int j = 0; j < m1[i].length; j++) {
                m1[i][j] = rand.nextInt(max);
            }
        }
        double[][] m2 = new double[][]{
            {52, 30, 49, 28},
            {30, 50, 8, 44}, {49, 8, 46, 16}, {28, 44, 16, 22}
        };
        double[] m3 = new double[]{
            1, 3, 5
        };
        EigenPair eigen = eigen_pair_non_square(m1, 0.00001);
        Vector.printf_vector(eigen.eigenValue, 2);
        printf_matrix(eigen.eigenVector, 2);
        double[][] ev = Vector.to_array(transpose(eigen.eigenVector)[1]);
        printf_matrix(ev, 2);
        printf_matrix(multiply(covariance_matrix(m1), ev), 2);
//        double[][] Q = Q_decompose(m);
//        double[][] R = R_decompose(m);
        //       Vector.printf_vector(eigen_value(m, 0.0001), 2);
//        Vector.printf_vector(linear_solver(m2, m3), 2);
//        double[] eigenValue = eigen_value_non_square(m1, 0.0001);
//        double[][] eigenVector = eigen_vector_non_square(m1, 0.0001);
//        Vector.printf_vector(eigenValue, 2);
//        printf_matrix(eigenVector, 2);
////        for (int i = 0; i < m2.length; i++) {
////            m2[i][i] -= eigenValue[0];
////        }
//        double[][] ev = Vector.to_array(transpose(eigenVector)[0]);
//        printf_matrix(covariance_matrix(m1), 2);
//        printf_matrix(ev, 2);
//        printf_matrix(multiply(covariance_matrix(m1), ev), 2);
//        long time = System.nanoTime();
//        covariance_matrix(m1);
//        System.out.println(System.nanoTime() - time);
//        time = System.nanoTime();
//        covariance_matrix(m1, 6);
//        System.out.println(System.nanoTime() - time);
    }
}
class Worker {
}
