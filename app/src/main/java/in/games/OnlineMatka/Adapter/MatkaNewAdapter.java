package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.NewGameActivity;
import in.games.OnlineMatka.R;
import maes.tech.intentanim.CustomIntent;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 05,June,2020
 */
public class MatkaNewAdapter extends RecyclerView.Adapter<MatkaNewAdapter.ViewHolder> {
    Context context;
    private ArrayList<MatkasObjects> list;
    private String m_id;
    public static int flag=0;
    Common common;

    public MatkaNewAdapter(Context context, ArrayList<MatkasObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.matks_items_layout,null);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MatkasObjects postion=list.get(position);

        String dt=new SimpleDateFormat("EEEE").format(new Date());

        holder.txtMatkaName.setText(" - "+postion.getName()+" - ");
        String s_time=null;
        String e_time=null;
        String s=null;
        String e=null;
        int day_flag=0;
        if(dt.equals("Sunday"))
        {
            if(postion.getStart_time().toString().equals("00:00:00") &&postion.getEnd_time().toString().equals("00:00:00"))
            {
                day_flag=1;
                s=postion.getBid_start_time().toString();
                e=postion.getBid_end_time().toString();
            }
            else
            {
                day_flag=2;
                s=postion.getStart_time().toString();
                e=postion.getEnd_time().toString();

            }

        }
        else if(dt.equals("Saturday"))
        {

            if(postion.getSat_start_time().toString().equals("00:00:00") && postion.getSat_end_time().toString().equals("00:00:00"))
            {
                day_flag=3;
                s=postion.getBid_start_time().toString();
                e=postion.getBid_end_time().toString();
            }
            else
            {
                day_flag=4;
                s=postion.getSat_start_time().toString();
                e=postion.getSat_end_time().toString();

            }

        }
        else
        {
            day_flag=5;
            s=postion.getBid_start_time().toString();
            e=postion.getBid_end_time().toString();
        }


          try {
                Date date=new SimpleDateFormat("HH:mm:ss").parse(s);
                Date date1=new SimpleDateFormat("HH:mm:ss").parse(e);
                s_time=new SimpleDateFormat("h:mm a").format(date);
                e_time=new SimpleDateFormat("h:mm a").format(date1);

            } catch (ParseException ex) {
                ex.printStackTrace();
            }



            holder.txtmatkaBid_openTime.setText(String.valueOf(s_time));
            holder.txtmatkaBid_closeTime.setText(String.valueOf(e_time));

            holder.txtMatka_resNo.setText(postion.getNumber());
            String end_number=postion.getEnd_num().toString();
            String start_number=postion.getStarting_num().toString();

            if(TextUtils.isEmpty(postion.getStarting_num()))
            {
                holder.txtMatka_startingNo.setText("");
            }
            else if(start_number.equals("null"))
            {
                holder.txtMatka_startingNo.setText("");
            }
            else
            {
                holder.txtMatka_startingNo.setText(postion.getStarting_num());
            }
            if(TextUtils.isEmpty(end_number))
            {
                holder.txtMatka_endNo.setText("");
            }
            else if(end_number.equals("null"))
            {
                holder. txtMatka_endNo.setText("");
                holder.txtDess2.setVisibility(View.INVISIBLE);

            }
            else
            {
                holder.txtDess2.setVisibility(View.VISIBLE);
                holder.txtMatka_endNo.setText(end_number);
            }


            holder.txtMatka_id.setText(postion.getId());
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
                    if(day_flag==1 || day_flag == 3)
                    {
                        holder.txtStatus.setTextColor( Color.parseColor( "#000000" ) );
                        holder.txtStatus.setText( "BID IS CLOSED" );

                    }
                    else
                    {
                        holder.txtStatus.setTextColor( Color.parseColor( "#000000" ) );
                        holder.txtStatus.setText( "BID IS RUNNING" );

                    }

                } else if (c > 0) {
                    flag = 3;
                    holder.txtStatus.setTextColor( Color.parseColor( "#000000" ) );
                    holder.txtStatus.setText( "BID IS CLOSED" );

                } else {
                    flag = 1;
                    if(day_flag==1 || day_flag == 3)
                    {
                        holder.txtStatus.setTextColor( Color.parseColor( "#000000" ) );
                        holder.txtStatus.setText( "BID IS CLOSED" );
                    }
                    else
                    {
                        holder.txtStatus.setVisibility( View.INVISIBLE );
                    }

                }
            }
            else
            {
                holder.txtStatus.setText( "BID IS CLOSED" );
                holder.txtStatus.setTextColor( Color.parseColor( "#000000" ) );

            }





        int cl=position%4;
        switch (cl)
        {
            case 0:
                holder.imageGame.setImageTintList(context.getColorStateList(R.color.play1));
                holder.txt_play.setTextColor(context.getResources().getColor(R.color.play1));
                break;

            case 1:
                holder.imageGame.setImageTintList(context.getColorStateList(R.color.play2));
                holder.txt_play.setTextColor(context.getResources().getColor(R.color.play2));
                break;

            case 2:
                holder.imageGame.setImageTintList(context.getColorStateList(R.color.play3));
                holder.txt_play.setTextColor(context.getResources().getColor(R.color.play3));
                break;

            case 3:
                holder.imageGame.setImageTintList(context.getColorStateList(R.color.play4));
                holder.txt_play.setTextColor(context.getResources().getColor(R.color.play4));
                break;
        }

        holder.rel_matka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dy=new SimpleDateFormat("EEEE").format(new Date());
                MatkasObjects objects=list.get(position);
                String stime ="";
                String etime ="";
                int i=0;
                if(dy.equalsIgnoreCase("Sunday"))
                {
                    if(objects.getStart_time().toString().equals("00:00:00") && objects.getEnd_time().toString().equals("00:00:00"))
                    {
                       i=1;
                    }
                    else
                    {
                        i=2;
                        stime=objects.getStart_time().toString();
                        etime=objects.getEnd_time().toString();

                    }
                    }
                else if(dy.equalsIgnoreCase("Saturday"))
                {
                    if(objects.getSat_start_time().toString().equals("00:00:00") && objects.getSat_end_time().toString().equals("00:00:00"))
                    {
                        i=3;
                    }
                    else
                    {
                        i=4;
                        stime=objects.getSat_start_time().toString();
                        etime=objects.getSat_end_time().toString();
                    }

                }
                else
                {
                    stime=objects.getBid_start_time().toString();
                    etime=objects.getBid_end_time().toString();
                }

                long endDiff=common.getTimeDifference(etime);
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                String cur_time = format.format(date);

                String m_id=objects.getId().toString().trim();
                String matka_name=objects.getName().toString().trim();

                if(endDiff<0)
                {
//                 common.errorMessageDialog("BID IS CLOSED");
                }
                else
                {
                    if(i==1 || i==3)
                    {
//                     common.errorMessageDialog("BID IS CLOSED FOR TODAY");
                    }
                    else {
                        Intent intent = new Intent(context, NewGameActivity.class);
                        //    intent.putExtra("tim",position);
                        intent.putExtra("matkaName", matka_name);
                        intent.putExtra("m_id", m_id);
                        if(dy.equalsIgnoreCase("Sunday"))
                        {
                            intent.putExtra("end_time", objects.getEnd_time());
                            intent.putExtra("start_time", objects.getStart_time());

                        }
                        else if(dy.equalsIgnoreCase("Saturday"))
                        {
                            intent.putExtra("end_time", objects.getSat_end_time());
                            intent.putExtra("start_time", objects.getSat_start_time());

                        }
                        else
                        {
                            intent.putExtra("end_time", objects.getBid_end_time());
                            intent.putExtra("start_time", objects.getBid_start_time());

                        }
                        //  intent.putExtra("bet","cb");
                        context.startActivity(intent);
                        CustomIntent.customType(context, "up-to-bottom");
                    }
                }
                // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDess2,txtmatkaBid_openTime,txtmatkaBid_closeTime,txtMatkaName,txtMatka_startingNo,
                txtMatka_resNo,txtMatka_endNo,txtStatus,txtMatka_id,txt_play;
        RelativeLayout rl,rel_matka;
        ImageView imageGame;
        public ViewHolder(@NonNull View view) {
            super(view);
             txtDess2=(TextView)view.findViewById(R.id.matka_dess2);
             txtmatkaBid_openTime=(TextView)view.findViewById(R.id.matka_open_bid_Time);
             txtmatkaBid_closeTime=(TextView)view.findViewById(R.id.matka_close_bid_Time);
             txtMatkaName=(TextView)view.findViewById(R.id.matkaName);
              txtMatka_startingNo=(TextView)view.findViewById(R.id.matka_starting_Number);
             txtMatka_resNo=(TextView)view.findViewById(R.id.matka_res_Number);
             txtMatka_endNo=(TextView)view.findViewById(R.id.matka_end_Number);
             rl=(RelativeLayout) view.findViewById(R.id.rlchange);
             txtStatus=(TextView)view.findViewById(R.id.matkaBettingStatus);
             imageGame=(ImageView)view.findViewById(R.id.matka_image);
             txtMatka_id=(TextView) view.findViewById(R.id.matka_id);
             rel_matka = view.findViewById(R.id.rlchange);
            txt_play = view.findViewById(R.id.txt_play);
          common=new Common(context);
        }
    }
}
