package in.games.OnlineMatka.Adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.OnlineMatka.Model.TransactionHistoryObjects;
import in.games.OnlineMatka.R;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private Context context;
    private Dialog dialog;
    private ArrayList<TransactionHistoryObjects> list;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionHistoryObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.transaction_history_layoout,null);
        final ViewHolder viewHolder=new ViewHolder(view);

       /* int position=i%4;
        switch (position)
        {
            case 0:
                viewHolder.rel_back.setBackgroundColor(context.getResources().getColor(R.color.play2));

                // imageGame.setBackgroundResource(R.drawable.play_game_1);
                break;

            case 1:
                viewHolder.rel_back.setBackgroundColor(context.getResources().getColor(R.color.play2));

                break;

            case 2:
                viewHolder.rel_back.setBackgroundColor(context.getResources().getColor(R.color.play3));

                break;

            case 3:
                viewHolder.rel_back.setBackgroundColor(context.getResources().getColor(R.color.play4));

                break;
            default:viewHolder.rel_back.setBackgroundColor(context.getResources().getColor( R.color.textColor ));
        }*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.ViewHolder viewHolder, int i) {

        TransactionHistoryObjects objects=list.get(i);

        viewHolder.txtId.setText(objects.getId());
        viewHolder.txtAmount.setText(objects.getAmt());

        viewHolder.txtStatus.setText("success");
        String st=objects.getType().toString();
        String day="";
        try
        {
            Date dt=new Date(  );
            dt=new SimpleDateFormat( "dd/MM/yyyy" ).parse( objects.getDate() );
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "EEEE" );
             day=simpleDateFormat.format( dt);
        }
        catch (Exception ex)
        {
         ex.printStackTrace();
        }

        if(st.equals("d"))
        {
            viewHolder.txtDescription.setText("Amount Debited For bidding " + objects.getName() + " "+objects.getBet_type()+" \n Bid Id :#"+objects.getBid_id());
        }
        else
        {
            viewHolder.txtDescription.setText("Amount Credited For bidding " + objects.getName() + " "+objects.getBet_type()+" \n Bid Id :#"+objects.getBid_id());
        }
        viewHolder.txtDate.setText(objects.getDate()+" "+day);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId,txtAmount,txtDescription,txtStatus,txtDate;
        RelativeLayout rel_back ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId=(TextView)itemView.findViewById(R.id.trans_id);
            txtAmount=(TextView)itemView.findViewById(R.id.trans_amount);
            txtDescription=(TextView)itemView.findViewById(R.id.description);
            txtStatus=(TextView)itemView.findViewById(R.id.status);
            txtDate=(TextView)itemView.findViewById(R.id.trans_date);
//            rel_back = itemView.findViewById( R.id.rel_transaction );

        }
    }
}
