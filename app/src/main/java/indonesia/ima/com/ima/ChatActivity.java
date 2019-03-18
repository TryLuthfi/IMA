package indonesia.ima.com.ima;

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


import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import indonesia.ima.com.ima.adapter.ChatAdapter;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    //for local storage
    SharedPreferences sharedPrefs;

    //init all var UI and Other
    LinearLayout back;
    View chatView;
    ConstraintLayout kirim;
    TextInputEditText pesan;
    RecyclerView rPesan;
    ChatAdapter chatAdapter;
    List<ChatEntry> chatList;
    String obj = "[]";
    String v_id = "1";
    String v_username;


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://imachaptermalang.herokuapp.com/IMA");
        } catch (URISyntaxException e) {
            Log.d(TAG, "Socket io init: " + e);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        getId();
        sokcetInit();
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

    }

    public void getId(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        v_id = preferences.getString("id_user", "null");
        v_username = preferences.getString("username", "null");
    }


    public void sokcetInit(){
        mSocket.connect();
        mSocket.on("pesan baru", onPesanBaru);
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "socket: connect" );
            }
        });

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
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: is destroy");
        //socket.disconnect();
        //socket.off(Socket.EVENT_CONNECT, onConnect);
        //socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        //socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        //socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("pesan baru", onPesanBaru);
    }

}
