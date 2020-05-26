package in.games.OnlineMatka;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;


public class OddEvenActivity extends MyBaseActivity {

    Common common;
    ListView listView;
    List<TableModel> list;
    TableAdaper tableAdaper;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    public static Button btnAdd,btnSave,btnType,btnGameType;
    private Dialog dialog;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txtdgt;

    private TextView txt_day;
    private int stat=0;
    private TextView txtDigit,txtPoint,txtType,bt_back,txtWallet_amount;
    private String game_id;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
    TextView btnDelete;
    CheckBox chkOdd,chkEven;
    private String m_id ,start_time,end_time;
    private String m_type;
//    private Dialog dialog;
    private TextView txtOpen,txtClose ,txt_timer,tv_timer;
    String bet_date="";
    private String bet_status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odd_even);

        final String dashName=getIntent().getStringExtra("matkaName");
        list=new ArrayList<>();
        listView=(ListView)findViewById(R.id.list_table);
        common=new Common(OddEvenActivity.this);
        m_id=getIntent().getStringExtra("m_id");
        game_id=getIntent().getStringExtra("game_id");
        m_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        txtdgt=findViewById(R.id.txtdgt);
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
//        txt_day=(TextView)findViewById(R.id.txt_day);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        progressDialog=new LoadingBar(OddEvenActivity.this);

        btnType.setText("Select Type");
        chkOdd=findViewById(R.id.oddDigits);
        chkEven=findViewById(R.id.evenDigits);

        common.currentDateDay(btnGameType);


        txtMatka.setText(dashName.toString()+"- Odd/Even Board");

        txtMatka.setSelected(true);
        bt_back=(TextView)findViewById(R.id.txtBack);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        txtdgt.setText(getResources().getString(R.string.tab_digit));
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        String cur_time = format.format(date);

        try {
            Date s_date = format.parse(start_time);
            Date e_date = format.parse(end_time);
            Date c_date = format.parse(cur_time);
            common.setCounterTimer( common.getTimeDifference(start_time),txt_timer);
            common.setEndCounterTimer( common.getTimeDifference(end_time),tv_timer);
//
            if (c_date.before(s_date))
            {

                tv_timer.setVisibility(View.GONE);
                txt_timer.setVisibility(View.VISIBLE);

            }
            else if (c_date.before(e_date) && c_date.after(s_date))
            {
                tv_timer.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);

            }
            else if (c_date.after(e_date))
            {
                txt_timer.setText("Bid Closed");
                tv_timer.setVisibility(View.GONE);
            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        chkOdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(list.size()>0)
        {
            list.clear();

        }
        if(chkEven.isChecked())
        {
            chkOdd.setChecked(true);
            chkEven.setChecked(false);
        }
        else
        {
            chkOdd.setChecked(true);
        }

    }
});

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
               String getValue=radioButton.getText().toString();
                if(getValue.equalsIgnoreCase("Open"))
                {
                    if(txt_timer.getVisibility()==View.GONE)
                    {
                        txt_timer.setVisibility(View.VISIBLE);
                    }
                    if(tv_timer.getVisibility()==View.VISIBLE)
                    {
                        tv_timer.setVisibility(View.GONE);
                    }
                }
                else if(getValue.equalsIgnoreCase("Close"))
                {
                    if(txt_timer.getVisibility()==View.VISIBLE)
                    {
                        txt_timer.setVisibility(View.GONE);
                    }
                    if(tv_timer.getVisibility()==View.GONE)
                    {
                        tv_timer.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

chkEven.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(list.size()>0)
        {
            list.clear();

        }
        if(chkOdd.isChecked())
        {
            chkOdd.setChecked(false);
            chkEven.setChecked(true);
        }
        else
        {
            chkEven.setChecked(true);
        }

    }
});

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bet="Select Type";
                if(rd_close.isChecked())
                {
                    bet="close";
                }
                else if(rd_open.isChecked())
                {
                    bet="open";
                }

                if(bet.equals("Select Type"))
                {
                    String message="Select bet type";
                   common.errorMessageDialog(message);
                    return;
                    //Toast.makeText(OddEvenActivity.this,"Please select bet type",Toast.LENGTH_LONG).show();
                    //return;
                }
                // String dData=editText.getText().toString().trim();
                else if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }



                else
                {
                    int pints=Integer.parseInt(etPoints.getText().toString().trim());
                    if(pints<10)
                    {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    }
                    else
                    {
                        String th=null;
                        if(stat==1)
                        {
                            th="open";
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("open"))
                            {
                                th="open";
                            }
                            else  if(bet.equals("close"))
                            {
                                th="close";
                            }

                        }


                        String p=etPoints.getText().toString().trim();
                        if(chkOdd.isChecked())
                        {

                            String[] odd={"1","3","5","7","9"};

                            for(int i=0; i<=odd.length-1; i++)
                            {

                                common.addData(odd[i],p,th,list,tableAdaper,listView,btnSave);
                          //      setBidsDialog(OddEvenActivity.this,,list);
                                //setOddData(odd[i],p,th);
                            }
                        }
                        else if(chkEven.isChecked())
                        {

                            String[] even={"0","2","4","6","8"};


                            for(int i=0; i<=even.length-1; i++)
                            {
                                //addData(even[i],p,th);
                                common.addData(even[i],p,th,list,tableAdaper,listView,btnSave);
                                // setOddData(even[i],p,th);
                                // arrayList.add(new SingleDigitObjects(even[i],p,th));
                            }
                        }
                        else
                        {
                            Toast.makeText(OddEvenActivity.this,"Please select any digit type",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                }



            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String dt=btnGameType.getText().toString().trim();
                        String d[]=dt.split(" ");

                        String c=d[0].toString();
                        String w= txtWallet_amount.getText().toString().trim();

//              common.insertData(list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

            }});

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String game_type=btnGameType.getText().toString().trim();
                String g[]=game_type.split(" ");
                String t=g[3];
                String dww=g[0];
                //   Toast.makeText(OddEvenActivity.this,""+dww,Toast.LENGTH_LONG).show();
                if(t.equals("Close"))
                {
                    common.errorMessageDialog("Biding closed this date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    common.setBetTypeTooText(dialog,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString(),tv_timer);
                  //  details.setBetTypeTo(OddEvenActivity.this,dialog,start_time,end_time,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();




            }
        });


        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        dialog=new Dialog(OddEvenActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_date_bet);
//
//
//        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
//        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
//        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
//        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);
//
//txtDate_id.setVisibility(View.GONE);
//        dialog.setCanceledOnTouchOutside(false);
//
//        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
//        dialog.show();
//        details.getDateData(OddEvenActivity.this,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);
//
//
//
//
//      txtCurrentDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//              String c=txtCurrentDate.getText().toString();
//
//           ///   String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//      txtNextDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//
//              String c=txtNextDate.getText().toString();
//
//             // String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//      txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//
//              String c=txtAfterNextDate.getText().toString();
//
//             // String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//
           //     details.setDateAndBetTpe(OddEvenActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);


            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(OddEvenActivity.this);
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m> Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
         common.getStarlineGameData(String.valueOf(m),btnType,progressDialog);
           // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
        }
        else
        {
            stat=2;
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
          //  details.setBetDateDay(OddEvenActivity.this,m_id,btnGameType,progressDialog);
           // details.setBetDemoDay(OddEvenActivity.this,m_id,btnGameType,progressDialog);
            common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
                @Override
                public void getTimeDiffrence(HashMap<String, String> map) {

                    long s_diff=Long.parseLong(map.get("s_diff").toString());
                    long e_diff=Long.parseLong(map.get("e_diff").toString());
                    Date c_dat=new Date();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                    String s_dt=dateFormat.format(c_dat);
                    if(e_diff>0)
                    {

                        btnGameType.setText(s_dt+" Bet Open");
                    }
                    else
                    {
                        btnGameType.setText(s_dt+" Bet Close");

                    }

                    if(s_diff>0)
                    {
                        rd_open.setChecked(true);
                    }
                    else if(s_diff<0 && e_diff>0)
                    {
                        rd_open.setChecked(false);
                        rd_open.setEnabled(false);
                        rd_close.setChecked(true);
                    }
                    else
                    {
                        rd_open.setChecked(false);
                        rd_open.setEnabled(false);
                        rd_close.setChecked(false);
                        rd_close.setEnabled(false);

                    }

                    progressDialog.dismiss();
                }
            });
        }

        }




//    private static void setData(final TextView txtCurrentDate, final TextView txtNextDate,final TextView txtAfterNextDate,final TextView txtDate_id, final String m_id, final ProgressDialog progressDialog, final Context context) {
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try
//                {
//                    JSONObject jsonObject=new JSONObject(response);
//                    String status=jsonObject.getString("status");
//                    if(status.equals("success"))
//                    {
//                        JSONObject object=jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects=new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start=matkasObjects.getBid_start_time();
//                        Date current_time=new Date();
//                        SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
//                        //Date time_start=sformat.parse(bid_start);
//                        String c_date=sformat.format(current_time);
//
//                        // int flag=time_start.compareTo(current_time);
//                        //txtOpen.setText(""+d+"\n"+current_time);
//                        // txtClose.setText(current_time.toString());
//
//                        String startTimeSplliting[]=bid_start.split(":");
//                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
//                        int s_min=Integer.parseInt(startTimeSplliting[1]);
//                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
//                        String currentTimeSplitting[]=c_date.split(":");
//                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
//                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
//                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);
//
//                        int flag=0;
//                        if(s_hours>c_hours)
//                        {
//                            flag=1;
//                        }
//                        else if(s_hours==c_hours)
//                        {
//                            if(s_min>c_min)
//                            {
//                                flag=1;
//                            }
//                            else if(s_min==c_min)
//                            {
//                                if(s_sec>c_sec)
//                                {
//                                    flag=1;
//                                }
//                                else
//                                {
//                                    flag=0;
//                                }
//                                flag=0;
//                            }
//                            else
//                            {
//                                flag=0;
//                            }
//                        }
//                        else
//                        {
//                            flag=0;
//                        }
//
//                        Date c_dat=new Date();
//                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//                        String s_dt=dateFormat.format(c_dat);
//
//
//
//                     String   ss = getNextDate(s_dt);
//
//                        String dd=getNextDate(ss);
//
//                        if(flag==0)
//                        {
//                            txtCurrentDate.setText(s_dt+" Bet Close");
//                            txtNextDate.setText(ss+" Bet Open");
//                            txtAfterNextDate.setText(dd+" Bet Open");
//
//                            txtDate_id.setText("c");
//                            txtDate_id.setVisibility(View.GONE);
//
//                           // txtClose.setText("Close Bet");
//                        }
//                        else if(flag==1)
//                        {
//                            txtCurrentDate.setText(s_dt+" Bet Open");
//                            txtNextDate.setText(ss+" Bet Open");
//                            txtAfterNextDate.setText(dd+" Bet Open");
//                            txtDate_id.setText("o");
//                            txtDate_id.setVisibility(View.GONE);
//                            //txtOpen.setVisibility(View.GONE);
//                        }
//
////String data="Hours : "+s_hours+"\n min : "+s_min+"\n sec : "+s_sec+"\n hours :"+c_hours+"\n minn :"+c_min+"\n seccc :"+c_sec;
//
//                        progressDialog.dismiss();
//                        // Toast.makeText(DoublePanaActivity.this,data,Toast.LENGTH_LONG).show();
//
//
//                    }
//                    else
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"Something ",Toast.LENGTH_LONG).show();
//
//                    }
//                }
//                catch(Exception ex)
//                {
//                    progressDialog.dismiss();
//                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"Something erong",Toast.LENGTH_LONG).show();
//
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("id",m_id);
//
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//
//    }

//    public static String getNextDate(String currentDate)
//    {
//        String nextDate="";
//
//        try
//        {
//            Calendar calendar=Calendar.getInstance();
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//            Date c=simpleDateFormat.parse(currentDate);
//            calendar.setTime(c);
//            calendar.add(Calendar.DAY_OF_WEEK,1);
//            nextDate=simpleDateFormat.format(calendar.getTime());
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
//        }
//
//        return nextDate.toString();
//    }


//    public void getDateData(final String m_id,final TextView txtCurrentDate,final TextView txtNextDate,final TextView txtAfterNextDate)
//    {
//
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//                    if (status.equals("success")) {
//                        JSONObject object = jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects = new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start = matkasObjects.getBid_start_time();
//                        String bid_end=matkasObjects.getBid_end_time().toString();
//
//                        String time1 = bid_start.toString();
//                        String time2 = bid_end.toString();
//
//                        Date cdate=new Date();
//
//
//
//                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//                        String time3=format.format(cdate);
//                        Date date1 = null;
//                        Date date2=null;
//                        Date date3=null;
//                        try {
//                            date1 = format.parse(time1);
//                            date2 = format.parse(time2);
//                            date3=format.parse(time3);
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//
//                        long difference = date3.getTime() - date1.getTime();
//                        long as=(difference/1000)/60;
//
//                        long diff_close=date3.getTime()-date2.getTime();
//                        long c=(diff_close/1000)/60;
//
//                        Date c_dat=new Date();
//                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//                        String s_dt=dateFormat.format(c_dat);
//                       String n_dt= getNextDate(s_dt);
//                       String a_dt= getNextDate(n_dt);
//                        if(as<0)
//                        {
//                            progressDialog.dismiss();
//                            //btn.setText(s_dt+" Bet Open");
//                            txtCurrentDate.setText(s_dt+" Bet Open");
//                            txtNextDate.setText(n_dt+" Bet Open");
//                            txtAfterNextDate.setText(a_dt+" Bet Open");
//
//                            //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
//                        }
//                        else if(c>0)
//                        {progressDialog.dismiss();
//                            txtCurrentDate.setText(s_dt+" Bet Close");
//                            txtNextDate.setText(n_dt+" Bet Open");
//                            txtAfterNextDate.setText(a_dt+" Bet Open");
//
//                           // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
//                        }
//
//
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(OddEvenActivity.this,"Something erong",Toast.LENGTH_LONG).show();
//
//
//                    }
//                } catch (Exception ex) {
//                    progressDialog.dismiss();
//                    Toast.makeText(OddEvenActivity.this,"Something erong"+ex.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(OddEvenActivity.this,"Something erong",Toast.LENGTH_LONG).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("id",m_id);
//
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(OddEvenActivity.this);
//        requestQueue.add(stringRequest);
//
//
//    }

//    public String getDataString(String dt)
//    {
//        String as[]=dt.split(" ");
//        String date=as[0];
//        String bet_type=as[3];
//        return (date+" "+bet_type).toString();
//    }

//    public void addData(String digit,String point,String type)
//    {
//        list.add(new TableModel(digit,point,type));
//        tableAdaper=new TableAdaper(list,OddEvenActivity.this,btnSave);
//        listView.setAdapter(tableAdaper);
//        tableAdaper.notifyDataSetChanged();
//        int we=list.size();
//        int points=Integer.parseInt(point);
//        int tot_pnt=we*points;
//
//        btnSave.setText("(BIDS="+we+")(Points="+tot_pnt+")");
//
//    }
}
