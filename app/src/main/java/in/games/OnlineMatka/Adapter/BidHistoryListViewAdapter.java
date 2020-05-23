package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


import in.games.OnlineMatka.Model.BidHistoryObjects;
import in.games.OnlineMatka.R;

public class BidHistoryListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BidHistoryObjects> list;

    public BidHistoryListViewAdapter(Context context, ArrayList<BidHistoryObjects> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

            return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.bid_history_layout,null);
        TextView txtMatkaName=(TextView)itemView.findViewById(R.id.matkaname);
        TextView  txtPlayOn=(TextView)itemView.findViewById(R.id.play_on);
        TextView  txtPlayFor=(TextView)itemView.findViewById(R.id.play_for);
        TextView txtDay=(TextView)itemView.findViewById(R.id.day);
        TextView txtId=(TextView)itemView.findViewById(R.id.bid_id);
        TextView txtDigits=(TextView)itemView.findViewById(R.id.digit);
        TextView txtPoints=(TextView)itemView.findViewById(R.id.points);
        TextView txtCredit=(TextView)itemView.findViewById(R.id.credit);
        RelativeLayout credit_rel=itemView.findViewById(R.id.credit_rel);

        BidHistoryObjects objects=list.get(position);

        int gm_id= Integer.parseInt(objects.getGame_id());

        String bet=objects.getBet_type().toString();
        if(bet.equals("open"))
        {
            txtMatkaName.setText(objects.getName()+" "+objects.getBet_type());
            txtDigits.setText(objects.getDigits());
        }
        else if(bet.equals("close"))
        {
            txtMatkaName.setText(objects.getName()+" "+objects.getBet_type());
            txtDigits.setText(objects.getDigits());
        }
        else
        {
            if(gm_id==12)
            {
            txtMatkaName.setText(objects.getName()+" Half Sangam");

            }
            else if(gm_id==13)
            {
            txtMatkaName.setText(objects.getName()+" Full Sangam");
            }
            txtDigits.setText(objects.getDigits()+" - "+objects.getBet_type());
        }


        txtPlayOn.setText("Play On "+objects.getPlay_on());
        txtPlayFor.setText("Play For "+objects.getPlay_for());

        txtPoints.setText(objects.getPoints());
        txtId.setText(objects.getId());
        txtDay.setText("("+objects.getDay()+")");

        String st=objects.getStatus().toString();

        if(st.equals("win"))
        {
//            credit_rel.setVisibility(View.VISIBLE);

            txtCredit.setText("You WON");
        }
        else if(st.equals("loss"))
        {
//            credit_rel.setVisibility(View.VISIBLE);
            txtCredit.setText("Better Luck Next Time");
        }
        else
        {
            credit_rel.setVisibility(View.GONE);
        }



        return itemView;
    }
}
