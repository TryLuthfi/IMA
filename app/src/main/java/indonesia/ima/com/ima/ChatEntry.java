package indonesia.ima.com.ima;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatEntry {
    private static final String TAG = "ChatEntry";

    public final String pesan;
    public final String penulis;
    public final String waktu;

    public ChatEntry(String pesan, String penulis, String waktu){
        this.pesan = pesan;
        this.penulis = penulis;
        this.waktu = waktu;
    }

    public static List<ChatEntry> initProductEntryListServer(String list) {
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<ChatEntry>>() {}.getType();
        List<ChatEntry> listProduk = gson.fromJson(list, productListType);
        return listProduk;
    }
}
