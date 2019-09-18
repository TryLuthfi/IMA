package indonesia.asperindo.com.asperindo.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatEntry {
    private static final String TAG = "ChatEntry";

    public final String id;
    public final String penulis;
    public final String pesan;
    public final String waktu;

    public ChatEntry(String id, String penulis,String pesan, String waktu){
        this.id = id;
        this.penulis = penulis;
        this.pesan = pesan;
        this.waktu = waktu;
    }

    public static List<ChatEntry> initProductEntryListServer(String list) {
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<ChatEntry>>() {}.getType();
        List<ChatEntry> listProduk = gson.fromJson(list, productListType);
        return listProduk;
    }
}
