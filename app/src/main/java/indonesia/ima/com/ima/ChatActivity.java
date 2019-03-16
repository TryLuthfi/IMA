package indonesia.ima.com.ima;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import indonesia.ima.com.ima.adapter.ChatAdapter;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    //init all var UI and Other
    View chatView;
    ConstraintLayout kirim;
    TextInputEditText pesan;
    RecyclerView rPesan;
    ChatAdapter chatAdapter;
    List<ChatEntry> chatList;
    String obj = obj = "[{\"penulis\":\"5c8b4dcfc2f42200041b6d71\",\"pesan\":\"7t88tt8\",\"waktu\":\"16:03:2019 04:15:37\",\"_id\":\"5c8c786bcfea410004137138\",\"dibaca\":false}]";
    String v_id = "admin";
    @Override
    protected void onStart() {
        super.onStart();


//        chatView = getLayoutInflater().inflate(R.layout.activity_chat, null);
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
        kirim.setOnClickListener(this);

    }

    private void addMessage(String idUser, String pesan, String waktu) {
        chatList.add(new ChatEntry(pesan, idUser, waktu));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        scrollToBottom();
    }
    private void scrollToBottom() {
        rPesan.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: " + v);
        switch (v.getId()){
            case R.id.kirim_fc: {
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
                addMessage(v_id, text, dateFormatGmt.format(new Date()));
                break;
            }
        }
    }
}
