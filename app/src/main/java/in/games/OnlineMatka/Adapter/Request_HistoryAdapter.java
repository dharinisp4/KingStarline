package in.games.OnlineMatka.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Objects.Fund_Request_HistoryObject;
import in.games.OnlineMatka.R;


public class Request_HistoryAdapter extends RecyclerView.Adapter<Request_HistoryAdapter.ViewHolder> {
    private Context context;
    private Dialog dialog;
    private ArrayList<Fund_Request_HistoryObject> list;

    public Request_HistoryAdapter(Context context, ArrayList<Fund_Request_HistoryObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Request_HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.fund_request_history_layout,null);

        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Request_HistoryAdapter.ViewHolder viewHolder, int i) {

        Fund_Request_HistoryObject postion=list.get(i);

        viewHolder.txtId.setText(postion.getRequest_id());
        viewHolder.txtAmount.setText(postion.getRequest_points());
        viewHolder.txtdate.setText(postion.getTime());

        String st=postion.getRequest_status().toString().trim();
        if(st.equals("pending"))
        {
            viewHolder.txtStatus.setTextColor(Color.parseColor("#FFA44546"));
            viewHolder.txtStatus.setText(st);
        }
        else if(st.equals("approved"))
        {
            viewHolder.txtStatus.setTextColor(Color.parseColor("#316D35"));
            viewHolder.txtStatus.setText(st);


        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId,txtAmount,txtStatus,txtdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId=(TextView)itemView.findViewById(R.id.fund_id);
            txtAmount=(TextView)itemView.findViewById(R.id.fund_amount);
            txtStatus=(TextView)itemView.findViewById(R.id.fund_status);
            txtdate=(TextView)itemView.findViewById(R.id.fund_Date);



        }
    }
}
