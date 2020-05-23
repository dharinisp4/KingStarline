package in.games.OnlineMatka.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.Starline_History_Objects;
import in.games.OnlineMatka.R;

public class Starline_History_Adapter extends RecyclerView.Adapter<Starline_History_Adapter.ViewHolder> {
    private Context context;
    private Dialog dialog;
    private ArrayList<Starline_History_Objects> list;

    public Starline_History_Adapter(Context context, ArrayList<Starline_History_Objects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Starline_History_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.bid_history_layout,null);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull Starline_History_Adapter.ViewHolder viewHolder, int i) {

        Starline_History_Objects objects=list.get(i);


        viewHolder.txtPlayOn.setText("Play On "+objects.getPlay_on());
        viewHolder.txtPlayFor.setText("Play For "+objects.getPlay_for());
      //  String dddy=getDay(dy_date);
     //   viewHolder.txtMatkaName.setText("Nagpur Starline "+objects.getBet_type()+" "+objects.getS_game_time());
        viewHolder.txtMatkaName.setText("Nagpur Starline "+"\n "+objects.getS_game_time());
        //viewHolder.txtPlayOn.setText(sd[0]+" "+dt.toString());
        //viewHolder.txtPlayFor.setText(sd[0]+" "+dt.toString());
        viewHolder.txtDigits.setText(objects.getDigits());
        viewHolder.txtPoints.setText(objects.getPoints());
        viewHolder.txtId.setText(objects.getId());
        viewHolder.txtDay.setText("("+objects.getDay()+")");

        String st=objects.getStatus().toString();
        if(st.equals("win"))
        {
//

            viewHolder.txtCredit.setText("You WON");
        }
        else if(st.equals("loss"))
        {
//
            viewHolder.txtCredit.setText("Better Luck Next Time");
        }
        else
        {
            viewHolder.credit_rel.setVisibility(View.GONE);

        }





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMatkaName,txtPlayOn,txtPlayFor,txtDay,txtId,txtPoints,txtDigits,txtCredit;
        RelativeLayout credit_rel ;
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
            credit_rel=itemView.findViewById(R.id.credit_rel);

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
