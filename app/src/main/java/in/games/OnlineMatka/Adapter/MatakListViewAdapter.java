package in.games.OnlineMatka.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.R;

public class MatakListViewAdapter extends BaseAdapter {

    public static Boolean isScrolling =true;
    //public static int flag;
    Context context;
    private ArrayList<MatkasObjects> list;
    private String m_id;
    public static int flag=0;

    public MatakListViewAdapter(Context context, ArrayList<MatkasObjects> list) {
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

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         View view= LayoutInflater.from(context).inflate(R.layout.matks_items_layout,null);
        //MatkasObjects objects=list.get(position);
       TextView txtDess2=(TextView)view.findViewById(R.id.matka_dess2);
       TextView txtmatkaBid_openTime=(TextView)view.findViewById(R.id.matka_open_bid_Time);
        TextView txtmatkaBid_closeTime=(TextView)view.findViewById(R.id.matka_close_bid_Time);
        TextView txtMatkaName=(TextView)view.findViewById(R.id.matkaName);
        TextView  txtMatka_startingNo=(TextView)view.findViewById(R.id.matka_starting_Number);
        TextView txtMatka_resNo=(TextView)view.findViewById(R.id.matka_res_Number);
        TextView txtMatka_endNo=(TextView)view.findViewById(R.id.matka_end_Number);
        RelativeLayout rl=(RelativeLayout) view.findViewById(R.id.rlchange);
        TextView txtStatus=(TextView)view.findViewById(R.id.matkaBettingStatus);
        ImageView  imageGame=(ImageView)view.findViewById(R.id.matka_image);
        TextView  txtMatka_id=(TextView) view.findViewById(R.id.matka_id);
TextView txt_play = view.findViewById(R.id.txt_play);


        MatkasObjects postion=list.get(position);

        String dt=new SimpleDateFormat("EEEE").format(new Date());

        txtMatkaName.setText(postion.getName());
        String s_time=null;
        String e_time=null;
        String s=null;
        String e=null;
        if(dt.equals("Sunday"))
        {
            String s_n=postion.getStart_time().toString();
            if(s_n.isEmpty() && s_n.equals("null"))
            {


                s ="null";
                e = "null";
            }
            else {
                s = postion.getStart_time().toString();
                e = postion.getEnd_time().toString();
            }
        }
        else if(dt.equals("Saturday"))
        {
         String s_n=postion.getSat_start_time().toString();

            if(s_n.isEmpty() && s_n.equals("null"))
            {
             s="null";
             e="null";
            }
            else
            {
                s=postion.getSat_start_time().toString();
                e=postion.getSat_end_time().toString();
            }

        }
        else
        {
            s=postion.getBid_start_time().toString();
            e=postion.getBid_end_time().toString();
        }


        if(s.equals("null") && e.equals("null"))
        {
            list.remove(position);
            notifyDataSetChanged();

        }
        else
        {
            try {
                Date date=new SimpleDateFormat("HH:mm:ss").parse(s);
                Date date1=new SimpleDateFormat("HH:mm:ss").parse(e);
                s_time=new SimpleDateFormat("h:mm a").format(date);
                e_time=new SimpleDateFormat("h:mm a").format(date1);

            } catch (ParseException ex) {
                ex.printStackTrace();
            }



            txtmatkaBid_openTime.setText(String.valueOf(s_time));
            // viewHolder.txtmatkaBid_openTime.setText(stat_time.toString());
            //viewHolder.txtmatkaBid_closeTime.setText(postion.getBid_end_time());
            txtmatkaBid_closeTime.setText(String.valueOf(e_time));

            txtMatka_resNo.setText(postion.getNumber());
            String end_number=postion.getEnd_num().toString();
            String start_number=postion.getStarting_num().toString();

            if(TextUtils.isEmpty(postion.getStarting_num()))
            {
                txtMatka_startingNo.setText("");
            }
            else if(start_number.equals("null"))
            {
                txtMatka_startingNo.setText("");
            }
            else
            {
                txtMatka_startingNo.setText(postion.getStarting_num());
            }
            if(TextUtils.isEmpty(end_number))
            {
                txtMatka_endNo.setText("");
            }
            else if(end_number.equals("null"))
            {
                txtMatka_endNo.setText("");
                txtDess2.setVisibility(View.INVISIBLE);

            }
            else
            {
                txtDess2.setVisibility(View.VISIBLE);
                txtMatka_endNo.setText(end_number);
            }


            txtMatka_id.setText(postion.getId());
            String status=postion.getStatus();

            Date date=new Date();
            SimpleDateFormat sim=new SimpleDateFormat("HH:mm:ss");
            String ct=sim.format(date);

            String time1 = s.toString();
            String time2 = e.toString();

            Date cdate=new Date();



            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String time3=format.format(cdate);
            Date date1 = null;
            Date date2=null;
            Date date3=null;
            try {
                date1 = format.parse(time1);
                date2 = format.parse(time2);
                date3=format.parse(time3);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            long difference = date3.getTime() - date1.getTime();
            long as=(difference/1000)/60;

            long diff_close=date3.getTime()-date2.getTime();
            long c=(diff_close/1000)/60;

            long current_time=date3.getTime();
            long cur=(current_time/1000)/60;
            // viewHolder.txtStatus.setText("cu "+cur+"\n o  "+as+"\n c "+c);
            if (status.equals( "active" )) {
                if (as < 0) {
                    flag = 2;
                    txtStatus.setTextColor( Color.parseColor( "#053004" ) );
                    txtStatus.setText( "BETTING IS RUNNING" );


                } else if (c > 0) {
                    flag = 3;
//                    txtStatus.setTextColor( Color.parseColor( "#FFA44546" ) );
                    txtStatus.setTextColor( Color.parseColor( "#b31109" ) );
                    txtStatus.setText( "BETTING IS CLOSED" );
                } else {
                    flag = 1;
                    // viewHolder.txtStatus.setText("a");
                    txtStatus.setVisibility( View.INVISIBLE );
                }
            }
            else
            {
                txtStatus.setText( "BETTING IS CLOSED" );
                txtStatus.setTextColor( Color.parseColor( "#b31109" ) );
            }

        }


//        String s=postion.getBid_start_time().toString();
//        String e=postion.getBid_end_time().toString();
        // viewHolder.txtMatka_resNo.setText(postion.getName());

        //String openTime=postion.getBid_start_time();
        // viewHolder.txtTime.setText(postion.getTime());

        // viewHolder.txtNumber.setText(postion.getNumber());
        // viewHolder.txtStatus.setText(postion.getStatus());
        //imageGame.setImageResource(R.drawable.pll);


        int cl=position%4;
        switch (cl)
        {
            case 0:
//                rl.setBackgroundColor(context.getResources().getColor(R.color.play1));
               imageGame.setImageTintList(context.getColorStateList(R.color.play1));
               txt_play.setTextColor(context.getResources().getColor(R.color.play1));
               // imageGame.setBackgroundResource(R.drawable.play_game_1);
                break;

            case 1:
//                rl.setBackgroundColor(context.getResources().getColor(R.color.play2));
                imageGame.setImageTintList(context.getColorStateList(R.color.play2));
                txt_play.setTextColor(context.getResources().getColor(R.color.play2));
                break;

            case 2:
//                rl.setBackgroundColor(context.getResources().getColor(R.color.play3));
                imageGame.setImageTintList(context.getColorStateList(R.color.play3));
                txt_play.setTextColor(context.getResources().getColor(R.color.play3));
                break;

            case 3:
//                rl.setBackgroundColor(context.getResources().getColor(R.color.play4));
                imageGame.setImageTintList(context.getColorStateList(R.color.play4));
                txt_play.setTextColor(context.getResources().getColor(R.color.play4));
                break;

//            default:rl.setBackgroundColor(GRAY);
        }

        return view;
    }


}
