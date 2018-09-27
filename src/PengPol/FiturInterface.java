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
public interface FiturInterface {
    double getEvidence(String q);
    Map<String, Double> getLikelihood(String s, Klas klas);

}
