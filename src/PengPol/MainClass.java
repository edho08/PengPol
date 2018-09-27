/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.Map;
/**
 *
 * @author Edho
 */
public class MainClass {
    public static void main(String[] args) {
        DiscreteFitur fit1 = new DiscreteFitur(new String[]{
            "Panjang",
            "Panjang",
            "Panjang",
            "Panjang",
            "Panjang",
            "Pendek",
            "Pendek",
            "Pendek",
            "Pendek",
            "Pendek"
        }, "Panjang Kumis");
        Klas k = new Klas(new String[]{
            "Lele",
            "Lele",
            "Lele",
            "Lele",
            "Lele",
            "Lele",
            "Lele",
            "Patin",
            "Patin",
            "Patin"
        }, "Kelas Ikan");
        ContinuFitur cf1 = new ContinuFitur(new String[]{
            "125",
            "100",
            "70",
            "120",
            "95",
            "60",
            "220",
            "85",
            "75",
            "90"
        }, "Pendapatan");
        DiscreteFitur cf2 = new DiscreteFitur(new String[]{
            "Belum Menikah",
            "Menikah",
            "Belum Menikah",
            "Menikah",
            "Cerai",
            "Menikah",
            "Cerai",
            "Belum Menikah",
            "Menikah",
            "Menikah"
        }, "Status");
        Klas k1 = new Klas(new String[]{
            "tidak",
            "tidak",
            "tidak",
            "tidak",
            "ya",
            "tidak",
            "tidak",
            "ya",
            "tidak",
            "ya"
        }, "Memiliki Rumah");
        //Map<String, Double> map = fit1.getLikelihood("Panjang", k);
        Map<String, Double> map1 = cf1.getLikelihood("125", k1);
        String[] uni = k1.getUniqueValue();
        for (String s : uni) {
            System.out.println(s + ": " + map1.get(s));
        }
        DataSet data = new DataSet();
        data.addFitur(cf1);
        data.addKlas(k1);
        data.addFitur(cf2);
        data.QueryAll(new QP[]{new QP(cf1.name, "125"), new QP(cf2.name, "Menikah")});
        System.out.println("");
    }
}
