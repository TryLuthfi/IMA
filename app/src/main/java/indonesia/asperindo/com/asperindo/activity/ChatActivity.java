package indonesia.asperindo.com.asperindo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.adapter.ChatAdapter;
import indonesia.asperindo.com.asperindo.json.ChatEntry;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getId();
        sokcetInit();
    }

    //init all var UI and Other
    LinearLayout back;
    ConstraintLayout kirim;
    TextInputEditText pesan;
    RecyclerView rPesan;
    ChatAdapter chatAdapter;
    List<ChatEntry> chatList;
    String obj = "[]";
    String v_id = "1";
    String v_username;

    String ChatURL = "http://imaindonesia.000webhostapp.com/chat.php/semuachat";


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://imachaptermalang.herokuapp.com");
        } catch (URISyntaxException e) {
            Log.d(TAG, "Socket io init: " + e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        back = findViewById(R.id.back_chat);
        rPesan = findViewById(R.id.r_pesan_fc);
        pesan = findViewById(R.id.pesan_text_fc);
        kirim = findViewById(R.id.kirim_fc);

        chatList = ChatEntry.initProductEntryListServer(obj);
        chatAdapter = new ChatAdapter(this, v_id, chatList);
        chatAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rPesan.setLayoutManager(layoutManager);
        rPesan.setAdapter(chatAdapter);
        rPesan.scrollToPosition(chatList.size() - 1);

        getAllChatHistory();

    }

    public void getId(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        v_id = preferences.getString("id_user", "null");
        v_username = preferences.getString("username", "null");
    }

    public void sokcetInit(){
        mSocket.connect();
        mSocket.on("pesan baru", onPesanBaru);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);

    }
    private Emitter.Listener onPesanBaru = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, "on pesan baru: " + data);
                    try {
                        String id_ = data.getString("id");
                        String usename_ = data.getString("penulis");
                        String pesan_ = data.getString("pesan");
                        String waktu_ = data.getString("waktu");
                        addMessage(id_, usename_, pesan_, waktu_);
                    } catch (JSONException e) {
                        Log.d(TAG, "error pesan baru: " + e);
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private void addMessage(String idUser, String username, String pesan, String waktu) {
        chatList.add(new ChatEntry(idUser, username, pesan, waktu));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        scrollToBottom();
    }
    private void scrollToBottom() {
        rPesan.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    public void back(View v){
        finish();
    }
    public void kirim(View v){
        if (null == v_id) return;
        String text = pesan.getText().toString();
        if (TextUtils.isEmpty(text)) {
            pesan.requestFocus();
            return;
        }

        pesan.setText("");
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        JSONObject kirimPesan = new JSONObject();
        try {
            kirimPesan.put("id", v_id);
            kirimPesan.put("penulis", v_username);
            kirimPesan.put("pesan", text);
            kirimPesan.put("waktu", dateFormatGmt.format(new Date()));
            mSocket.emit("pesan baru", kirimPesan);
            addMessage(v_id, null, text, dateFormatGmt.format(new Date()));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onClick error kirim pesan: " + e);
        }


    }

    public void getAllChatHistory(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                ChatURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        chatList.addAll(ChatEntry.initProductEntryListServer(response));
                        chatAdapter.notifyDataSetChanged();
                        rPesan.scrollToPosition(chatList.size() - 1);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, "chatHistory err: " + e);

                    }
                });

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: is destroy");
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("pesan baru", onPesanBaru);
    }

    boolean isConnected = false;
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = true;
                    Log.d(TAG, "OnConnect: connect");
                }
            });
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                    Log.i(TAG, "diconnected");
                }
            });
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                }
            });
        }
    };

}
