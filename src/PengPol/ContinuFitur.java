package PengPol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Edho
 */
public class ContinuFitur extends Fitur {
    public ContinuFitur() {
        super();
    }
    public ContinuFitur(String[] data, String name) {
        this();
        this.name = name;
        Collections.addAll(this.data, data);
    }
    public ContinuFitur(java.util.List<String> data, String name) {
        this();
        this.name = name;
        this.data.addAll(data);
    }
    @Override
    public double getEvidence(String q) {
        System.exit(0);
        return 0;
    }
    @Override
    public Map<String, Double> getLikelihood(String s, Klas klas) {
        Map<String, Double> mean = getMappingMean(klas);
        Map<String, Double> varian = getVarian(klas);
        Map<String, Double> ll = new HashMap<>();
        String[] uni = klas.getUniqueValue();
        for (String st : uni) {
            Double d = 0d;
            try {
                d = Double.valueOf(s);
            } catch (Exception e) {
                System.exit(1);
            }
            Double low = 1 / Math.sqrt(2 * Math.PI * varian.get(st));
            Double high = Math.exp((-1*(Math.pow(d - mean.get(st), 2))/(2*Math.pow(d, varian.get(st)))));
            ll.put(st, low*high);
        }
        return ll;
    }
    public Map<String, Double> getMappingMean(Klas klas) {
        /*
         Map<String, Double> map = new HashMap<>();
         String[] uni = klas.getData();
         Map<String, Integer> klasCount = klas.getDiscreteCount();
         for (int i = 0; i < data.size(); i++) {
         if (!map.containsKey(uni[i])) {
         map.put(uni[i], Double.valueOf(0));
         }
         map.replace(uni[i], map.get(uni[i]) + Double.valueOf(data.get(i)));
         }
         for (Map.Entry<String, Double> entry : map.entrySet()) {
         entry.setValue(entry.getValue() / klasCount.get(entry.getKey()));
         }
         return map;*/
        Map<String, ArrayList<String>> map = getMapping(klas);
        Map<String, Double> mapMean = new HashMap<>();
        Map<String, Integer> dCount = klas.getDiscreteCount();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            double count = 0;
            for (String s : entry.getValue()) {
                count += Double.valueOf(s);
            }
            mapMean.put(entry.getKey(), count / dCount.get(entry.getKey()));
        }
        return mapMean;
    }
    public Map<String, ArrayList<String>> getMapping(Klas klas) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            if (!map.containsKey(klas.data.get(i))) {
                map.put(klas.data.get(i), new ArrayList<>());
            }
            map.get(klas.data.get(i)).add(data.get(i));
        }
        return map;
    }
    public Map<String, Double> getVarian(Klas klas) {
        Map<String, ArrayList<String>> map = getMapping(klas);
        Map<String, Double> mapMean = getMappingMean(klas);
        Map<String, Integer> dCount = klas.getDiscreteCount();
        Map<String, Double> mapVarian = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            double count = 0;
            for (String s : entry.getValue()) {
                count += Math.pow(Double.valueOf(s) - mapMean.get(entry.getKey()), 2);
            }
            mapVarian.put(entry.getKey(), count / (dCount.get(entry.getKey())-1));
        }
        return mapVarian;
    }
}
