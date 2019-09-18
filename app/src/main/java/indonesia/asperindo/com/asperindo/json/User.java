package indonesia.asperindo.com.asperindo.json;

public class User {

    String id_user;
    String nama;
    String username;
    String password;
    String no_telp;
    String email_pribadi;
    String foto_profile;
    String bidang_usaha;
    String nama_usaha;
    String chapter;

    public User(String id_user, String nama, String username, String password, String no_telp, String email_pribadi, String foto_profile, String bidang_usaha, String nama_usaha, String chapter) {
        this.id_user = id_user;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.no_telp = no_telp;
        this.email_pribadi = email_pribadi;
        this.foto_profile = foto_profile;
        this.bidang_usaha = bidang_usaha;
        this.nama_usaha = nama_usaha;
        this.chapter = chapter;
    }

    public String getId_user() {
        return id_user;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getEmail_pribadi() {
        return email_pribadi;
    }

    public String getFoto_profile() {
        return foto_profile;
    }

    public String getBidang_usaha() {
        return bidang_usaha;
    }

    public String getNama_usaha() {
        return nama_usaha;
    }

    public String getChapter() {
        return chapter;
    }
}