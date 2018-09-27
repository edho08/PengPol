/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Edho
 */
public class Klas extends AbstractFitur {
    public Klas() {
        data = new java.util.ArrayList<>();
    }
    /**
     * Konstruktor
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public Klas(String[] data, String name) {
        this();
        this.name = name;
        Collections.addAll(this.data, data);
    }
    /**
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public Klas(java.util.List<String> data, String name) {
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
    public Map<String, Double> getAllEvidence() {
        Map<String, Integer> dCount = getDiscreteCount();
        Map<String, Double> evidence = new HashMap<>();
        dCount.forEach((k, v) -> {
            evidence.put(k, (double)v / data.size());
        });
        return evidence;
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
}
