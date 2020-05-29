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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.games.OnlineMatka.Adapter.SingleDigitAdapter;
import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;


public class SingleDigitActivity extends MyBaseActivity {
    Common common;
    private Button btnAdd,btnSave,btnType,btnGameType;
    TextView btnDelete,txtdgt;

    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    TextView bt_back;
    TextView txtMatka;
    private int stat=0;
    ArrayList list_digits = new ArrayList();
    ArrayList list_type = new ArrayList();
    ArrayList list_points = new ArrayList();
    private EditText etDgt,etPnt;
    String matName="";
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    LoadingBar progressDialog;
    private String game_id;
    private String m_id ,end_time,start_time ,bet_type;
    private TextView txtWallet_amount,txt_timer,tv_timer;
    SingleDigitAdapter adapter;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_digit);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
        common=new Common(SingleDigitActivity.this);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        list_table=findViewById(R.id.list_table);

        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txtdgt=(TextView)findViewById(R.id.txtdgt);
        txtdgt.setText("Digit");
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txt_timer = findViewById(R.id.timer);
        tv_timer= findViewById(R.id.tv_timer);
        txtMatka.setSelected(true);
        bt_back=(TextView)findViewById(R.id.txtBack);
        progressDialog=new LoadingBar(SingleDigitActivity.this);
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
                tv_timer.setVisibility(View.GONE);
                txt_timer.setText("Bid Closed");
            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


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

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
      txtMatka.setText(dashName.toString()+"- Single Digit Board");


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
                    common.errorMessageDialog("Biding closed for this date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    common.setBetTypeTooText(dialog,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString(),tv_timer);
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();


            }
        });

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpe(SingleDigitActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });

        etDgt=(EditText)findViewById(R.id.etSingleDigit);
        etPnt=(EditText)findViewById(R.id.etPoints);

         btnAdd=(Button)findViewById(R.id.digit_add);
         btnSave=(Button)findViewById(R.id.digit_save);

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
                    String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);return;
                }
                else if(TextUtils.isEmpty(etDgt.getText().toString()))
                {
                   etDgt.setError("Please enter any digit");
                   etDgt.requestFocus();
                   return;
                }
               else if(TextUtils.isEmpty(etPnt.getText().toString()))
                {
                    etPnt.setError("Please enter some point");
                    etPnt.requestFocus();
                    return;

                }

                else {

                    int pints = Integer.parseInt(etPnt.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPnt.setError("Minimum Biding amount is 10");
                        etPnt.requestFocus();
                        return;


                    } else {
                        //String bet= btnType.getText().toString().trim();
                        String th = null;
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


                        String d = etDgt.getText().toString();
                        final String p = etPnt.getText().toString();
                        String g = btnGameType.getText().toString();
                        String type = btnType.getText().toString().trim();
                        common.addData(d,p,th,list,tableAdaper,list_table,btnSave);
                        etDgt.setText("");
                        etPnt.setText("");
                        etDgt.requestFocus();


                    }
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//             common.showToast("sdasdasd");
                String dt=btnGameType.getText().toString().trim();
                String d[]=dt.split(" ");

                String c=d[0].toString();
                String w= txtWallet_amount.getText().toString().trim();

                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

            }
        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());


        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);


    }

    @Override
    protected void onStart() {
        super.onStart();
        int m=Integer.parseInt(m_id.toString());
        if(m> Prevalent.Matka_count)
        {
            //Toast.makeText(SingleDigitActivity.this,"starline game-  "+Prevalent.Matka_count+"\n m- "+m,Toast.LENGTH_SHORT).show();
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            common.getStarlineGameData(String.valueOf(m),btnType,progressDialog);
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
        }
        else
        {
           // Toast.makeText(SingleDigitActivity.this,"matka game-  "+Prevalent.Matka_count+"\n m- "+m,Toast.LENGTH_SHORT).show();
            stat=2;
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());

            common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
                @Override
                public void getTimeDiffrence(HashMap<String, String> map) {

                    long s_diff=Long.parseLong(map.get("s_diff").toString());
                    long e_diff=Long.parseLong(map.get("e_diff").toString());
                    Date c_dat=new Date();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                    String s_dt=dateFormat.format(c_dat);
                    btnGameType.setText(s_dt+" Bet" +bet_type.toUpperCase());
//                    if(e_diff>0)
//                    {
//
//                        btnGameType.setText(s_dt+" Bet Open");
//                    }
//                    else
//                    {
//                        btnGameType.setText(s_dt+" Bet Close");
//
//                    }
//
//                    if(s_diff>0)
//                    {
//                        rd_open.setChecked(true);
//                    }
//                    else if(s_diff<0 && e_diff>0)
//                    {
//                        rd_open.setChecked(false);
//                        rd_open.setEnabled(false);
//                        rd_close.setChecked(true);
//                    }
//                    else
//                    {
//                        rd_open.setChecked(false);
//                        rd_open.setEnabled(false);
//                        rd_close.setChecked(false);
//                        rd_close.setEnabled(false);
//
//                    }

                    progressDialog.dismiss();
                }
            });





        }



    }

}
