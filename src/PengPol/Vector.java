/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
/**
 *
 * @author Edho Kelas Library operasi Vector
 */
public  class Vector {
    public static boolean check_dimension(double[] v1, double[] v2) {
        return v1.length == v2.length;
    }
    public static double[] add(double[] v, double scalar) {
        double[] result = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = v[i] + scalar;
        }
        return result;
    }
    public static double[] sub(double[] v, double scalar) {
        double[] result = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = v[i] - scalar;
        }
        return result;
    }
    public static double[] multiply(double[] v, double scalar) {
        double[] result = new double[v.length];
        IntStream.range(0, v.length).parallel().forEach(i-> result[i] = v[i] * scalar);
//        for (int i = 0; i < v.length; i++) {
//            result[i] = v[i] * scalar;
//        }
        return result;
    }
    public static double[] add(double[] v1, double[] v2) {
        if (!check_dimension(v1, v2)) {
            return null;
        }
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;
    }
    public static double[] sub(double[] v1, double[] v2) {
        if (!check_dimension(v1, v2)) {
            return null;
        }
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] - v2[i];
        }
        return result;
    }
    public static double dot_product(double[] v1, double[] v2) {
        if (!check_dimension(v1, v2)) {
            return 0;
        }
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }
    public static double[] projection(double[] v1, double[] v2) {
        return multiply(v1, dot_product(v1, v2) / dot_product(v1, v1));
    }
    public static double[] flip_sign(double[] v) {
        double[] result = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = -1 * v[i];
        }
        return result;
    }
    public static double vector_length(double[] v) {
        double result = 0;
        for (int i = 0; i < v.length; i++) {
            result += Math.pow(v[i], 2);
        }
        return Math.sqrt(result);
    }
    public static double[] vector_copy(double[] v) {
        double[] result = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = v[i];
        }
        return result;
    }
    public static void printf_vector(double[] v, int dec_place) {
        for (int i = 0; i < v.length; i++) {
            System.out.printf("%." + dec_place + "f ", v[i]);
        }
        System.out.println("");
        System.out.println("");
    }
    public static double mean(double[] v) {
        double result = 0;
        for (int i = 0; i < v.length; i++) {
            result += v[i];
        }
        return result / v.length;
    }
    public static double[][] to_array(double[] v) {
        double[][] result = new double[v.length][1];
        for (int i = 0; i < v.length; i++) {
            result[i][0] = v[i];
        }
        return result;
    }
    public static void main(String[] args) {
        double[] v1 = new double[]{1, 0, 3};
        double[] v2 = new double[]{-1, 4, 2};
        printf_vector(v1, 2);
        change(v1);
        printf_vector(v1, 2);
    }
    static void change(double[] a) {
        a[0]++;
    }
}
