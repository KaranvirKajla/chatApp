package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatapp.MessageActivity;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

import Models.Message;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    FirebaseAuth mAuth ;
    List<Message> mMessages;
    Context mContext;

    public MessageAdapter(Context mContext, List<Message> mMessages) {
        this.mMessages = mMessages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);

                return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myEmail = currentUser.getEmail();
        holder.message.setText(message.getMessage());
        holder.date.setText(message.getDate());

        if(message.getFrom().equals(myEmail)){
            holder.cardView.setBackgroundColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message;
        public TextView date;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            date  = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
