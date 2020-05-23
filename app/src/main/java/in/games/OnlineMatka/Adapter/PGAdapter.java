package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.OnlineMatka.Model.Starline_Objects;
import in.games.OnlineMatka.R;

import static android.graphics.Color.GRAY;

public class PGAdapter extends BaseAdapter {


    Context context;
    private ArrayList<Starline_Objects> list;
    private String m_id;
    public static int flg=0;

    public PGAdapter(Context context, ArrayList<Starline_Objects> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view= LayoutInflater.from(context).inflate(R.layout.play_games_items_layout,null);
        TextView txtNumber=(TextView)view.findViewById(R.id.pg_Number);
        TextView  txtTime=(TextView)view.findViewById(R.id.pg_Time);
        TextView  txtId=(TextView)view.findViewById(R.id.pg_title);
        ImageView img=(ImageView)view.findViewById(R.id.pg_image);
        RelativeLayout rl_change=(RelativeLayout)view.findViewById(R.id.rlchange);
        Starline_Objects postion=list.get(position);


        //viewHolder.txtId.setText(""+postion.getId());

       Date date=new Date();
        SimpleDateFormat format1=new SimpleDateFormat("hh:mm aa");
        String dr=format1.format(date);

        //boolean sTime=getTimeStatus(String.valueOf(postion.getS_game_time()));
        int sTime=getTimeFormatStatus(String.valueOf(postion.getS_game_time()));

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
        String ddt=simpleDateFormat.format(date);
        int c_tm=Integer.parseInt(ddt);
       // txtTime.setText(postion.getS_game_time()+" - "+getCloseStatus(postion.getS_game_end_time().toString(),dr));
       txtTime.setText(postion.getS_game_time());
       //txtTime.setText(postion.getS_game_end_time().toString()+" -- "+dr);
       // Toast.makeText(context,"db_time:-  "++"\n curr_time:-  "+dr,Toast.LENGTH_LONG).show();

      String tm=getCloseStatus(postion.getS_game_end_time().toString(),dr);
       String[] end_time=tm.split(":");
       int h= Integer.parseInt(end_time[0].toString());
       int m= Integer.parseInt(end_time[1].toString());

        if(h<=0 && m<0)
        {
            txtId.setText("Bet Is Running ");
            txtNumber.setText("***-**");

            txtId.setTextColor(Color.parseColor("#053004"));
            //txtStatus.setText("o");

        }
        else
        {
            txtId.setText("Bet is Closed ");

            txtId.setTextColor(Color.parseColor("#b31109"));
            txtNumber.setText(""+postion.getS_game_number());
            img.setVisibility(View.INVISIBLE);
            //txtStatus.setText("c");
        }


      //  img.setImageResource(R.drawable.pll);
        int cl=position%4;
        switch (cl)
        {
            case 0:
                rl_change.setBackgroundColor(context.getResources().getColor(R.color.play1));
                img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pll2));
                // imageGame.setBackgroundResource(R.drawable.play_game_1);
                break;

            case 1:
                rl_change.setBackgroundColor(context.getResources().getColor(R.color.play2));
                img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pll2));
                break;

            case 2:
                rl_change.setBackgroundColor(context.getResources().getColor(R.color.play3));
                img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pll2));
                break;

            case 3:
                rl_change.setBackgroundColor(context.getResources().getColor(R.color.play4));
                img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pll2));
                break;
            default:rl_change.setBackgroundColor(GRAY);
        }
        return view;
    }

    public int getTimeFormatStatus(String time)
    {
        //02:00 PM;
        String t[]=time.split(" ");
        String time_type=t[1].toString();
        String t1[]=t[0].split(":");
        int tm=Integer.parseInt(t1[0].toString());

        if(time_type.equals("AM"))
        {

        }
        else
        {
            if(tm==12)
            {

            }
            else
            {
                tm=12+tm;
            }
        }
        return tm;

    }


    public String getCloseStatus(String gm_time,String current_time)
    {
        int h=0;
        int m=0;
        try {
            int days=0,hours=0,min=0;

            Date date1=new Date();
            Date date2=new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
        boolean st_time=getStatusTime(current_time);
        if(st_time)
        {
            date1 = simpleDateFormat.parse(formatTime(gm_time));
            date2 = simpleDateFormat.parse(current_time);

        }
        else
        {
            date1 = simpleDateFormat.parse(gm_time);
            date2 = simpleDateFormat.parse(current_time);

        }

        long difference = date2.getTime() - date1.getTime();
        days = (int) (difference / (1000*60*60*24));
        hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

//        hours = (hours < 0 ? -hours : hours);
//        min = (min < 0 ? -min : min);
            h=hours;
            m=min;
        Log.i("======= Hours"," :: "+hours);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,"err :--  "+ex.getMessage()+"\n "+gm_time+"\n "+current_time,Toast.LENGTH_LONG).show();
        }
        String d=""+h+":"+m;
        return String.valueOf(d);
    }

    public boolean getStatusTime(String current_tim)
    {
        boolean st=false;
        String t[]=current_tim.split(" ");
        String time_type=t[1].toString();
         if(time_type.equals("a.m.") || time_type.equals("p.m."))
         {
             st=true;
         }
         else if(time_type.equals("AM") || time_type.equals("PM"))
         {
             st=false;
         }
         return st;
    }

    public String formatTime(String time)
    {
        String tm="";
        String t[]=time.split(" ");
        String time_type=t[1].toString();

        if(time_type.equals("PM"))
        {
            tm="p.m.";
        }
        else if(time_type.equals("AM"))
        {
            tm="a.m.";
        }
        else
        {
            tm=time_type;
        }

        String c_tm=t[0].toString()+" "+tm;
        return c_tm;
    }

    public String get12TimeFormatStatus(String time)
    {

        String type="";
        //02:00 PM;
        String t[]=time.split(" ");
        String time_type=t[1].toString();
        String t1[]=t[0].split(":");
        int tm=Integer.parseInt(t1[0].toString());

        if(time_type.equals("AM"))
        {

        }
        else
        {
            if(tm==12)
            {

            }
            else
            {
                tm=12+tm;
            }
        }
        String d=tm+":"+t1[1].toString();
        return d;

    }

}
