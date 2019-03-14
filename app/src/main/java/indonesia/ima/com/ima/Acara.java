package indonesia.ima.com.ima;

public class Acara {
    private String id_acara;
    private String judul_acara;
    private String gambar_acara;
    private String jam_acara;
    private String tanggal_acara;
    private String alamat_acara;
    private String alamat_lengkap_acara;
    private String deskripsi_acara;

    public Acara(String id_acara, String judul_acara, String gambar_acara, String jam_acara, String tanggal_acara, String alamat_acara, String alamat_lengkap_acara, String deskripsi_acara) {
        this.id_acara = id_acara;
        this.judul_acara = judul_acara;
        this.gambar_acara = gambar_acara;
        this.jam_acara = jam_acara;
        this.tanggal_acara = tanggal_acara;
        this.alamat_acara = alamat_acara;
        this.alamat_lengkap_acara = alamat_lengkap_acara;
        this.deskripsi_acara = deskripsi_acara;
    }

    public String getId_acara() {
        return id_acara;
    }

    public String getJudul_acara() {
        return judul_acara;
    }

    public String getGambar_acara() {
        return gambar_acara;
    }

    public String getJam_acara() {
        return jam_acara;
    }

    public String getTanggal_acara() {
        return tanggal_acara;
    }

    public String getAlamat_acara() {
        return alamat_acara;
    }

    public String getAlamat_lengkap_acara() {
        return alamat_lengkap_acara;
    }

    public String getDeskripsi_acara() {
        return deskripsi_acara;
    }
}