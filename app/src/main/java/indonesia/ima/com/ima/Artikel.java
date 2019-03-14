package indonesia.ima.com.ima;

public class Artikel {
    public String id_artikel;
    public String id_user;
    public String judul_artikel;
    public String deskripsi_artikel;
    public String artikel_created_date;
    public String nama;

    public Artikel(String id_artikel, String id_user, String judul_artikel, String deskripsi_artikel, String artikel_created_date, String nama) {
        this.id_artikel = id_artikel;
        this.id_user = id_user;
        this.judul_artikel = judul_artikel;
        this.deskripsi_artikel = deskripsi_artikel;
        this.artikel_created_date = artikel_created_date;
        this.nama = nama;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public String getDeskripsi_artikel() {
        return deskripsi_artikel;
    }

    public String getArtikel_created_date() {
        return artikel_created_date;
    }

    public String getNama(){ return nama;}
}
