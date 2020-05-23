package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.NotificationObjects;
import in.games.OnlineMatka.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NotificationObjects> list;

    public NotificationAdapter(Context context, ArrayList<NotificationObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,final int i) {

       final View view= LayoutInflater.from(context).inflate(R.layout.notification_items_layout,null);
       final ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String s_time="s";
        NotificationObjects postion=list.get(i);
        viewHolder.txtTime.setText(postion.getTime());
        viewHolder.txtNotification.setText(postion.getNotification());
        viewHolder.txtNotificationId.setText(postion.getNotification_id());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime,txtNotification,txtNotificationId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTime=(TextView)itemView.findViewById(R.id.notification_time);

            txtNotification=(TextView)itemView.findViewById(R.id.notification);
            txtNotificationId=(TextView)itemView.findViewById(R.id.notification_ID);





        }
    }
}
