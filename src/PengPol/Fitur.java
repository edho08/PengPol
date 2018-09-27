/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PengPol;
import java.util.Collections;
/**
 *
 * @author Edho
 */
public abstract class Fitur extends AbstractFitur implements FiturInterface {
    public Fitur() {
        super();
    }
    public Fitur(String[] data, String name) {
        this();
        this.name = name;
        Collections.addAll(this.data, data);
    }
    public Fitur(java.util.List<String> data, String name) {
        this();
        this.name = name;
        this.data.addAll(data);
    }
    public abstract double getEvidence(String q);
    public abstract java.util.Map<String, Double> getLikelihood(String s, Klas klas);
}
