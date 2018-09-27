package PengPol;
import java.util.Collections;
/**
 * Klas ini adalah kelas abstract untuk Fitur. Anggota kelas : @attr data Data
 * dari fitur, @attr name Nama dari fitur
 *
 * @author Edho
 */
public abstract class AbstractFitur {
    /**
     * kumpulan data yang ada di fitur
     */
    protected java.util.ArrayList<String> data;
    /**
     * nama fitur
     */
    protected String name;
    /**
     * Konstruktor default
     */
    public AbstractFitur() {
        data = new java.util.ArrayList<>();
    }
    /**
     * Konstruktor
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public AbstractFitur(String[] data, String name) {
        this();
        this.name = name;
        Collections.addAll(this.data, data);
    }
    /**
     *
     * @param data data yang akan dimasukkan
     * @param name nama dari fitur
     */
    public AbstractFitur(java.util.List<String> data, String name) {
        this();
        this.name = name;
        this.data.addAll(data);
    }
    
    public String[] getData(){
        return data.toArray(new String[0]);
    }
    public String getNama(){
        return name;
    }
}
