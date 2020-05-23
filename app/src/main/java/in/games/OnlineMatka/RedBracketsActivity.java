package in.games.OnlineMatka;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class RedBracketsActivity extends MyBaseActivity {
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    Common common;
   private final String[] red_bracket={"00","11","22","33","44","55","66","77","88","99","05","16","27","38","49","50",
           "61","72","83","94"};
   int stat =0 ;

    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    TextView  txtClose,txtOpen;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    private int val_p=0;
    TextView btnDelete,txtdgt;
    private TextView txtDigit,txtPoint,txtType;
    TextView bt_back;
    TextView txtMatka;
    AutoCompleteTextView etDgt;
    private EditText etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
    private String game_id;
    private String m_id,start_time,end_time;
    private TextView txtWallet_amount ,txt_timer,tv_timer;
    private RelativeLayout relativeLayout;
    private Dialog dialog;
    private CheckBox chkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_brackets);

        common=new Common(RedBracketsActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
        txtdgt=findViewById(R.id.txtdgt);
        txtdgt.setText(getResources().getString(R.string.tab_pana));
        list_table=findViewById(R.id.list_table);
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(RedBracketsActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        relativeLayout=findViewById(R.id.relativeLayout4);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        rd_open.setVisibility(View.GONE);
        txtMatka.setSelected(true);
        chkBox=findViewById(R.id.chk_bx);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtDigit=(TextView)findViewById(R.id.txtSingleDigit);
        etDgt=(AutoCompleteTextView)findViewById(R.id.etSingleDigit);
        etPoints=(EditText)findViewById(R.id.etPoints);

        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setText(dashName.toString()+"- Red Bracket Board");
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

                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.VISIBLE);

            }
            else if (c_date.before(e_date) && c_date.after(s_date))
            {
                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.GONE);

            }
            else if (c_date.after(e_date))
            {
                if(tv_timer.getVisibility()==View.VISIBLE)
                {
                    tv_timer.setVisibility(View.GONE);
                }
                txt_timer.setText("Bid Closed");

            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
//                details.setDateAndBetTpeTo(RedBracketsActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());
        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(RedBracketsActivity.this,android.R.layout.simple_list_item_1,red_bracket);
        etDgt.setAdapter(adapter);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true)
                {
                    relativeLayout.setVisibility(View.GONE);
                  //    txtDigit.setVisibility(View.INVISIBLE);
//                    etDgt.setVisibility(View.INVISIBLE);
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    list.clear();
                    adapter.notifyDataSetChanged();

                }

            }
        });

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
                String getValue=radioButton.getText().toString();
//                if(getValue.equalsIgnoreCase("Open"))
//                {
//                    if(txt_timer.getVisibility()==View.GONE)
//                    {
//                        txt_timer.setVisibility(View.VISIBLE);
//                    }
//                    if(tv_timer.getVisibility()==View.VISIBLE)
//                    {
//                        tv_timer.setVisibility(View.GONE);
//                    }
//                }
//                else if(getValue.equalsIgnoreCase("Close"))
//                {
                if(txt_timer.getVisibility()==View.VISIBLE)
                {
                    txt_timer.setVisibility(View.GONE);
                }
                if(tv_timer.getVisibility()==View.GONE)
                {
                    tv_timer.setVisibility(View.VISIBLE);
                }
//                }

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_b=btnGameType.getText().toString().trim();
                String b[]=date_b.split(" ");
                String vt=b[3];

                if(vt.equals("Open"))
                {

                    if(chkBox.isChecked()==true)
                    {
                        String points=etPoints.getText().toString().trim();
                        if(TextUtils.isEmpty(points))
                        {
                            etPoints.setError("Enter Some Points");
                            etPoints.requestFocus();
                            return;
                        }
                        else
                        {
                            int pints = Integer.parseInt(etPoints.getText().toString().trim());
                            if (pints < 10) {
                                //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                                etPoints.setError("Minimum Biding amount is 10");
                                etPoints.requestFocus();
                                return;


                            } else {
                                for (int i = 0; i <= red_bracket.length - 1; i++) {
                                    //setOddData(red_bracket[i], points, "close");
                                    common.addData(red_bracket[i],points,"close",list,tableAdaper,list_table,btnSave);

                                }

                                etPoints.setText("");
                                etPoints.requestFocus();
                            }
                        }
                    }
                    else
                    {
                        String digits=etDgt.getText().toString().trim();
                        String points=etPoints.getText().toString().trim();

                        if(TextUtils.isEmpty(digits))
                        {
                            etDgt.setError("Enter Some Digits");
                            etDgt.requestFocus();
                            return;
                        }
                        else if(TextUtils.isEmpty(points))
                        {
                            etPoints.setError("Enter Some Points");
                            etPoints.requestFocus();
                            return;
                        }
                        else if(!Arrays.asList(red_bracket).contains(digits))
                        {
                            common.errorMessageDialog("Invalid Jodi");
                            return;
                        }
                        else
                        {
                            int pints = Integer.parseInt(points);
                            if (pints < 10) {
                                //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                                etPoints.setError("Minimum Biding amount is 10");
                                etPoints.requestFocus();
                                return;


                            } else {

                                //setOddData(digits, points, "close");
                                common.addData(digits,points,"close",list,tableAdaper,list_table,btnSave);

                                etPoints.setText("");
                                etDgt.setText("");
                                etDgt.requestFocus();
                            }
                        }
                    }
                }
                else if(vt.equals("Close"))
                {
                    String message="Biding closed for this date";
                    common.errorMessageDialog(message);
                    return;
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

//                module.insertData(RedBracketsActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);
                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(RedBracketsActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m> Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
//            details.getStarlineGameData(DPMotorActivity.this,String.valueOf(m),btnType,progressDialog);
            common.setBetDateDayTo(m_id,btnGameType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
//            btnType.setClickable(false);
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
        }
        else
        {
            stat=2;
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
//            common.setBetDateDay(m_id,btnGameType,progressDialog);
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
                        rd_close.setChecked(true);
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


}
