package in.games.OnlineMatka.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.Withdraw_requwset_obect;
import in.games.OnlineMatka.R;

public class Withdraw_request_Adapter extends RecyclerView.Adapter<Withdraw_request_Adapter.ViewHolder> {

    Context context;
    private ArrayList<Withdraw_requwset_obect> list;
    Common common;


    public Withdraw_request_Adapter(Context context, ArrayList<Withdraw_requwset_obect> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Withdraw_request_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.withdraw_request_history_layout,null);

        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Withdraw_request_Adapter.ViewHolder viewHolder, int i) {

        Withdraw_requwset_obect postion=list.get(i);

        viewHolder.txtId.setText(postion.getId());

        String[] arrTime=postion.getTime().toString().split(" ");
        viewHolder.txtdate.setText(arrTime[0].toString()+" "+common.changeTimeFormat(arrTime[1].toString()));

        String type = postion.getType().trim();
        if (type.equals("Add"))
        {
            viewHolder.txtAmount.setText(postion.getWithdraw_points());
        }
        else if (type.equals("Withdrawal"))
        {
            viewHolder.txtAmount.setText(postion.getWithdraw_points());
        }
        viewHolder.txtStatus.setText(type);

//        String st=postion.getWithdraw_status().toString().trim();
//        if(st.equals("pending"))
//        {
//            viewHolder.txtStatus.setTextColor(Color.parseColor("#FFA44546"));
//            viewHolder.txtStatus.setText(st);
//        }
//        else if(st.equals("approved"))
//        {
//            viewHolder.txtStatus.setTextColor(Color.parseColor("#316D35"));
//            viewHolder.txtStatus.setText(st);
//
//
//        }

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
            common=new Common(context);
        }
    }
}
