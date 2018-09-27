/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Edho
 */
public class DiscreteFitur extends Fitur {
    /**
     * Menghitung Likelihood terhadapt sebuah klas
     *
     * @param s String Query
     * @param klas Klas
     * @return Map <klas, value> dari likelihood
     */
    public DiscreteFitur() {
        super();
    }
    /**
     * Konstruktor
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public DiscreteFitur(String[] data, String name) {
        this();
        this.name = name;
        Collections.addAll(this.data, data);
    }
    /**
     * Konstruktor
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public DiscreteFitur(java.util.List<String> data, String name) {
        this();
        this.name = name;
        this.data.addAll(data);
    }
    public double getEvidence(String q) {
        java.util.Map<String, Integer> count = getDiscreteCount();
        if (count.containsKey(q)) {
            return count.get(q) / data.size();
        } else {
            return 0;
        }
    }
    public Map<String, Integer> getDiscreteCount() {
        java.util.HashMap<String, Integer> count = new java.util.HashMap<>();
        for (String s : data) {
            if (s.trim().equalsIgnoreCase("")) {
                continue;
            }
            if (count.containsKey(s)) {
                count.replace(s, count.get(s) + 1);
            } else {
                count.put(s, 1);
            }
        }
        return count;
    }
    public String[] getUniqueValue() {
        java.util.Map<String, Integer> count = getDiscreteCount();
        return count.keySet().toArray(new String[0]);
    }
    @Override
    public Map<String, Double> getLikelihood(String s, Klas klas) {
        if (data.size() != klas.data.size()) {
            System.err.println("Error : klas and fitur doesnt match.. exiting");
            System.exit(1);
        }
        Map<String, Map<String, Integer>> map = getMapping(klas);
        Map<String, Double> ret = new HashMap<>();
        String[] uni = klas.getUniqueValue();
        for (String st : uni) {
            try {
                ret.put(st, map.get(st).get(s).doubleValue() / klas.getDiscreteCount().get(st));
            }catch(NullPointerException e){
                 ret.put(st, Double.valueOf(0));
            }
        }
        return ret;
    }
    public Map<String, Map<String, Integer>> getMapping(Klas klas) {
        if (data.size() != klas.data.size()) {
            System.err.println("Error : klas and fitur doesnt match.. exiting");
            System.exit(1);
        }
        Map<String, Map<String, Integer>> map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            if (!map.containsKey(klas.data.get(i))) {
                map.put(klas.data.get(i), new HashMap<>());
            }
            if (!map.get(klas.data.get(i)).containsKey(data.get(i))) {
                map.get(klas.data.get(i)).put(data.get(i), 1);
            } else {
                Map<String, Integer> temp = map.get(klas.data.get(i));
                temp.replace(data.get(i), temp.get(data.get(i)) + 1);
            }
        }
        return map;
    }
}
