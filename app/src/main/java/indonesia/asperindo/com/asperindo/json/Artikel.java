package indonesia.asperindo.com.asperindo.json;

public class Artikel {
    public String id_artikel;
    public String judul_artikel;
    public String gambar_artikel;
    public String deskripsi_artikel;
    public String artikel_created_date;

    public Artikel(String id_artikel, String judul_artikel,String gambar_artikel, String deskripsi_artikel, String artikel_created_date) {
        this.id_artikel = id_artikel;
        this.judul_artikel = judul_artikel;
        this.gambar_artikel = gambar_artikel;
        this.deskripsi_artikel = deskripsi_artikel;
        this.artikel_created_date = artikel_created_date;
    }

    public String getId_artikel() {
        return id_artikel;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public String getGambar_artikel(){ return gambar_artikel;}

    public String getDeskripsi_artikel() {
        return deskripsi_artikel;
    }

    public String getArtikel_created_date() {
        return artikel_created_date;
    }

}
