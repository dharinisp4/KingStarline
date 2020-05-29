package in.games.OnlineMatka;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class SinglePannaActivity extends MyBaseActivity {

    Common common;
    private int stat=0;
    ListView list_table;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    TableAdaper tableAdaper;
    List<TableModel> list;
private final String[] singlePaana=
        {"137","128","146","236","245","290","380","470","489","560","678","579",
        "129","138","147","156","237","246","345","390","480","570","589","679","120","139","148","157","238","247",
        "256","346","490","580","670","689","130","149","158","167","239","248","257","347","356","590","680","789",
        "140","159","168","230","249","258","267","348","357","456","690","780","123","150","169","178","240","259",
        "268","349","358","367","457","790","124","160","179","250","269","278","340","359","368","458","467","890",
        "125","134","170","189","260","279","350","369","378","459","468","567","126","135","180","234","270","289",
        "360","379","450","469","478","568","127","136","145","190","235","280","370","389","460","479","569","578"};
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    int val_p=0;
    TextView txtMatka;
    private TextView txtDigit,txtPoint,txtType;
    TextView btnDelete;
    TextView bt_back;

    private EditText etDgt,etPnt;
    String matName="",bet_type;
    private EditText etPoints;
   LoadingBar progressDialog;
    private String game_id;
    private String m_id,start_time,end_time;
    private TextView txtWallet_amount ,txt_timer,tv_timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_panna);
        common=new Common(SinglePannaActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(SinglePannaActivity.this);
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txtMatka.setSelected(true);
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        bt_back=(TextView)findViewById(R.id.txtBack);

        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(SinglePannaActivity.this,android.R.layout.simple_list_item_1,singlePaana);
        editText.setAdapter(adapter);
        txtMatka.setText(dashName.toString()+"- Single Patti Board");

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                details.setDateAndBetTpe(SinglePannaActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });

        if(bet_type.equalsIgnoreCase("Open"))
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
        else if(bet_type.equalsIgnoreCase("Close"))
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

            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

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
//            if (c_date.before(s_date))
//            {
//
//                tv_timer.setVisibility(View.GONE);
//                txt_timer.setVisibility(View.VISIBLE);
//
//            }
//            else if (c_date.before(e_date) && c_date.after(s_date))
//            {
//                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.GONE);
//
//            }
//            else if (c_date.after(e_date))
//            {
//                txt_timer.setText("Bid Closed");
//            }
//            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
//                String getValue=radioButton.getText().toString();
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
//                    if(txt_timer.getVisibility()==View.VISIBLE)
//                    {
//                        txt_timer.setVisibility(View.GONE);
//                    }
//                    if(tv_timer.getVisibility()==View.GONE)
//                    {
//                        tv_timer.setVisibility(View.VISIBLE);
//                    }
//                }
//
//            }
//        });


        // t1.setGravity(Gravity.CENTER_HORIZONTAL);


        //tr=(TableRow)findViewById(R.id.tableRow1);



        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dt=btnGameType.getText().toString().trim();
                String d[]=dt.split(" ");

                String c=d[0].toString();
                String w= txtWallet_amount.getText().toString().trim();
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                String cur_time = format.format(date);

                try {
                    Date s_date = format.parse(start_time);
                    Date e_date = format.parse(end_time);
                    Date c_date = format.parse(cur_time);
                    long difference = c_date.getTime() - s_date.getTime();
                    long as=(difference/1000)/60;

                    long diff_close=c_date.getTime()-e_date.getTime();
                    long curr=(diff_close/1000)/60;
                    long current_time=c_date.getTime();
                    if (bet_type.equalsIgnoreCase("open"))
                    {
                    if (as < 0) {

                        common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

                    }
                    else
                    {
                        common.errorMessageDialog("Betting is Closed Now");
                    }
                    }
                    else if (bet_type.equalsIgnoreCase("close"))
                    {
                    if (curr < 0) {
                        common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

                    }
                    else {
                        common.errorMessageDialog("Betting is Closed Now");
                    }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                }




        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bet = bet_type;

//                String bet="Select Type";
//                if(rd_close.isChecked())
//                {
//                    bet="close";
//                }
//                else if(rd_open.isChecked())
//                {
//                    bet="open";
//                }


                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {

                    String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);
                    return;
                }
                else if(TextUtils.isEmpty(editText.getText().toString()))
                {
                    editText.setError("Please enter any digit");
                    editText.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }

                else if(!Arrays.asList(singlePaana).contains(dData))
                {
                    Toast.makeText(SinglePannaActivity.this,"This is invalid patti",Toast.LENGTH_LONG).show();
                    editText.setText("");
                    editText.requestFocus();
                    return;
                }
                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
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


                        String d = editText.getText().toString();
                        final String p = etPoints.getText().toString();
                        String g = btnGameType.getText().toString();

                        common.addData(d,p,th,list,tableAdaper,list_table,btnSave);

                        editText.setText("");
                        etPoints.setText("");
                        editText.requestFocus();
                       // btnType.setText("Select Type");


                    }

                }

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
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
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
            common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
                @Override
                public void getTimeDiffrence(HashMap<String, String> map) {

                    long s_diff=Long.parseLong(map.get("s_diff").toString());
                    long e_diff=Long.parseLong(map.get("e_diff").toString());
                    Date c_dat=new Date();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                    String s_dt=dateFormat.format(c_dat);
                    btnGameType.setText(s_dt+" Bet " +bet_type.toUpperCase());
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
