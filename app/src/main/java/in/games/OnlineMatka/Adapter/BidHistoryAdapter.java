package in.games.OnlineMatka.Adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.BidHistoryObjects;
import in.games.OnlineMatka.R;

public class BidHistoryAdapter extends RecyclerView.Adapter<BidHistoryAdapter.ViewHolder> {
    private Dialog dialog;
    private Context context;
    private ArrayList<BidHistoryObjects> list;

    public BidHistoryAdapter(Context context, ArrayList<BidHistoryObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BidHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.bid_history_layout,null);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BidHistoryAdapter.ViewHolder viewHolder, int i) {
        BidHistoryObjects objects=list.get(i);

        int gm_id= Integer.parseInt(objects.getGame_id());

        String bet=objects.getBet_type().toString();
        if(bet.equals("open"))
        {
            viewHolder.txtMatkaName.setText(objects.getName()+" "+objects.getBet_type());
            viewHolder.txtDigits.setText(objects.getDigits());
        }
        else if(bet.equals("close"))
        {
            viewHolder.txtMatkaName.setText(objects.getName()+" "+objects.getBet_type());
            viewHolder.txtDigits.setText(objects.getDigits());
        }
        else
        {
            if(gm_id==12)
            {
                viewHolder.txtMatkaName.setText(objects.getName()+" Half Sangam");

            }
            else if(gm_id==13)
            {
                viewHolder.txtMatkaName.setText(objects.getName()+" Full Sangam");
            }
                     viewHolder.txtDigits.setText(objects.getDigits()+" - "+objects.getBet_type());
        }


        viewHolder.txtPlayOn.setText("Play On "+objects.getPlay_on());
        viewHolder.txtPlayFor.setText("Play For "+objects.getPlay_for());

        viewHolder.txtPoints.setText(objects.getPoints());
        viewHolder.txtId.setText(objects.getId());
        viewHolder.txtDay.setText("("+objects.getDay()+")");

        String st=objects.getStatus().toString();
        if(st.equals("win"))
        {

            viewHolder.txtCredit.setText("You WON");
        }
        else
        {
            viewHolder.txtCredit.setText( "Better Luck Next Time" );

            // viewHolder.txtCredit.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMatkaName,txtPlayOn,txtPlayFor,txtDay,txtId,txtPoints,txtDigits,txtCredit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMatkaName=(TextView)itemView.findViewById(R.id.matkaname);
            txtPlayOn=(TextView)itemView.findViewById(R.id.play_on);
            txtPlayFor=(TextView)itemView.findViewById(R.id.play_for);
            txtDay=(TextView)itemView.findViewById(R.id.day);
            txtId=(TextView)itemView.findViewById(R.id.bid_id);
            txtDigits=(TextView)itemView.findViewById(R.id.digit);
            txtPoints=(TextView)itemView.findViewById(R.id.points);
            txtCredit=(TextView)itemView.findViewById(R.id.credit);


        }
    }

    public String getDay(int n_d)
    {
        String day=null;
        switch (n_d)
        {
            case 1:
                day="Sunday";
                break;
            case 2:
                day="Monday";
                break;

            case 3:
                day="Tuesday";
                break;
            case 4:
                day="Wednesday";
                break;
            case 5:
                day="Thursday";
                break;
            case 6:
                day="Friday";
                break;
            case 7:
                day="Saturday";
                break;

              default:day="";
        }

        return day.toString();
    }
}
