package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.Starline_History_Objects;
import in.games.OnlineMatka.R;

public class StarlinehistoryAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Starline_History_Objects> list;

    public StarlinehistoryAdapter(Context context, ArrayList<Starline_History_Objects> list) {
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
        TextView txtPlayOn=(TextView)itemView.findViewById(R.id.play_on);
        TextView txtPlayFor=(TextView)itemView.findViewById(R.id.play_for);
        TextView txtDay=(TextView)itemView.findViewById(R.id.day);
        TextView txtId=(TextView)itemView.findViewById(R.id.bid_id);
        TextView txtDigits=(TextView)itemView.findViewById(R.id.digit);
        TextView txtPoints=(TextView)itemView.findViewById(R.id.points);
        TextView txtCredit=(TextView)itemView.findViewById(R.id.credit);
        RelativeLayout credit_rel=itemView.findViewById(R.id.credit_rel);

        Starline_History_Objects objects=list.get(position);


      txtPlayOn.setText("Play On "+objects.getPlay_on());
      txtPlayFor.setText("Play For "+objects.getPlay_for());
        //  String dddy=getDay(dy_date);
//      txtMatkaName.setText("Nagpur Starline "+objects.getBet_type()+" "+objects.getS_game_time());
        txtMatkaName.setText("Nagpur Starline "+" \n"+objects.getS_game_time());
        //viewHolder.txtPlayOn.setText(sd[0]+" "+dt.toString());
        //viewHolder.txtPlayFor.setText(sd[0]+" "+dt.toString());
      txtDigits.setText(objects.getDigits());
      txtPoints.setText(objects.getPoints());
      txtId.setText(objects.getId());
      txtDay.setText("("+objects.getDay()+")");

        String st=objects.getStatus().toString();
//        if(st.equals("win"))
//        {
//            credit_rel.setVisibility(View.VISIBLE);
//
//           txtCredit.setText(objects.getStatus());
//        }
//        else
//        {
//
//        }

        if(st.equals("win"))
        {
            credit_rel.setVisibility(View.VISIBLE);

            txtCredit.setText("You Won");
        }
        else if(st.equals("loss"))
        {
            credit_rel.setVisibility(View.VISIBLE);

        }

        return itemView;
    }
}
