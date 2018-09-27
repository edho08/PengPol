/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Edho
 */
public class DataSet {
    private List<Fitur> fitur;
    private List<Klas> klas;
    public DataSet() {
        fitur = new LinkedList<>();
        klas = new LinkedList<>();
    }
    public void addFitur(Fitur f) {
        this.fitur.add(f);
    }
    public void addKlas(Klas k) {
        this.klas.add(k);
    }
    public void removeFitur(Fitur f) {
        this.fitur.remove(f);
    }
    public void removeFitur(String s) {
        fitur.forEach(item -> {
            if (item.name.equals(s)) {
                fitur.remove(item);
            }
        });
    }
    public void removeKlas(Klas k) {
        this.klas.remove(k);
    }
    public void removeKlas(String s) {
        klas.forEach(item -> {
            if (item.name.equals(s)) {
                klas.remove(item);
            }
        });
    }
    public Fitur getFitur(String s) {
        for (Fitur f : fitur) {
            if (f.name.equals(s)) {
                return f;
            }
        }
        return null;
    }
    public Map<String, Map<String, Double>> QueryAll(QP[] queryString) {
        Map<String, Map<String, Double>> map = new HashMap<>();
        klas.forEach(item -> {
            map.put(item.name, Query(queryString, item));
        });
        return map;
    }
    public Map<String, Double> Query(QP[] queryString, Klas klas) {
        Map<String, Double> map = new HashMap<>();
        Map<Fitur, Map<String, Double>> map_fitur = new HashMap<>();
        for (QP qs : queryString) {
            Fitur f = getFitur(qs.fitur_Name);
            map_fitur.put(f, f.getLikelihood(qs.value, klas));
        }
        for (Map.Entry<Fitur, Map<String, Double>> map_fitur_entry : map_fitur.entrySet()) {
            for (Map.Entry<String, Double> fitur_entry : map_fitur_entry.getValue().entrySet()) {
                if (!map.containsKey(fitur_entry.getKey())) {
                    map.put(fitur_entry.getKey(), fitur_entry.getValue());
                } else {
                    map.replace(fitur_entry.getKey(), map.get(fitur_entry.getKey()) * fitur_entry.getValue());
                }
            }
        }
        Map<String, Double> evidence = klas.getAllEvidence();
        map.forEach((k, v) -> {
            map.replace(k, map.get(k)*evidence.get(k));
        });
        return map;
    }
}
