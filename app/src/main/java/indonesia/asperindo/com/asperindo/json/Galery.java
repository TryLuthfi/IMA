package indonesia.asperindo.com.asperindo.json;

public class Galery {

    private String id_galery;
    private String id_acara;
    private String id_user;
    private String gambar_galery;
    private String judul_acara;

    public Galery(String id_galery, String id_acara, String id_user, String gambar_galery, String judul_acara) {
        this.id_galery = id_galery;
        this.id_acara = id_acara;
        this.id_user = id_user;
        this.gambar_galery = gambar_galery;
        this.judul_acara = judul_acara;
    }

    public String getId_galery() {
        return id_galery;
    }

    public String getId_acara() {
        return id_acara;
    }

    public String getId_user() {
        return id_user;
    }

    public String getGambar_galery() {
        return gambar_galery;
    }

    public String getJudul_acara() {
        return judul_acara;
    }
}
