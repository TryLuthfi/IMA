package indonesia.ima.com.ima.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import indonesia.ima.com.ima.ChatEntry;
import indonesia.ima.com.ima.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private static final String TAG = "ChatFragment";

    private List<ChatEntry> cahtList;
    private Activity activity;
    private String idUser;

    public ChatAdapter(
            Activity activity,
            String idUser,
            List<ChatEntry> chatList){
        this.activity = activity;
        this.cahtList = chatList;
        this.idUser = idUser;
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_pesan, parent, false);
        return new ChatAdapter.ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        if (cahtList != null && position < cahtList.size()) {
            final ChatEntry pesan = cahtList.get(position);
            if (pesan.penulis.equals(idUser)){
                holder.cPesanAdmin.setVisibility(View.INVISIBLE);
                holder.cPesanUser.setVisibility(View.VISIBLE);
                holder.pesanUser.setText(pesan.pesan);
                holder.waktuUser.setText(getTanggal(pesan.waktu));
            }else{
                holder.cPesanAdmin.setVisibility(View.VISIBLE);
                holder.cPesanUser.setVisibility(View.INVISIBLE);
                holder.pesanAdmin.setText(pesan.pesan);
                holder.waktuAdmin.setText(getTanggal(pesan.waktu));
            }

        }
    }

    public String getTanggal(String tanggal){
        Locale current = activity.getResources().getConfiguration().locale;
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy HH:mm", current);
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm", current);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        return df2.format(date);
    }

    @Override
    public int getItemCount() {
        return cahtList == null ? 0 : cahtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cPesanAdmin;
        TextView pesanAdmin;
        TextView waktuAdmin;
        RelativeLayout cPesanUser;
        TextView pesanUser;
        TextView waktuUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cPesanAdmin = itemView.findViewById(R.id.c_pesan_admin_chat);
            pesanAdmin = itemView.findViewById(R.id.pesan_admin_chat);
            waktuAdmin = itemView.findViewById(R.id.waktu_admin_chat);
            cPesanUser = itemView.findViewById(R.id.c_pesan_user_chat);
            pesanUser = itemView.findViewById(R.id.pesan_user_chat);
            waktuUser = itemView.findViewById(R.id.waktu_user_chat);
        }
    }
}
