package in.games.OnlineMatka;

import android.app.Dialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Objects.sp_input_data;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class GroupJodiActivity extends MyBaseActivity {
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    Common common;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    int stat = 0;
    private Button btnAdd,btnSave,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    TextView bt_back;
    TextView btnDelete;
    private Dialog dialog;
    private TextView txtOpen,txtClose,tv_timer;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;


    private final String[] d1={"11","16","61","66"};
    private final String[] d2={"12","21","17","71","26","62","67","76"};
    private final String[] d3={"13","31","18","81","36","63","68","86"};
    private final String[] d4={ "14","41","19","91","46","64","69","96"};
    private final String[] d5={"15","51","10","01","56","65","60","06"};
    private final String[] d6={"22","27","72","77"};
    private final String[] d7={"23","32","28","82","37","73","78","87"};
    private final String[] d8={"24","42","29","92","47","74","79","97"};
    private final String[] d9={"25","52","20","02","57","75","70","07"};
    private final String[] d10={"33","38","83","88"};
    private final String[] d11={"34","43","39","93","48","84","89","98"};
    private final String[] d12={"35","53","30","03","58","85","80","08"};
    private final String[] d13={"44","49","94","99"};
    private final String[] d14={"45","54","40","04","59","95","90","09"};
    private final String[] d15={"55","50","05","00"};

    private final String[][] main=new String[][]{d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15};
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;

    AutoCompleteTextView editText;
    private String game_id;
    private String m_id,end_time,start_time;
    private TextView txtWallet_amount,txt_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_jodi);

        common=new Common(GroupJodiActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        list=new ArrayList<>();
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        rd_open.setVisibility(View.GONE);
        list_table=findViewById(R.id.list_table);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        editText=(AutoCompleteTextView) findViewById(R.id.etSingleDigit);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        progressDialog=new LoadingBar(GroupJodiActivity.this);
        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setSelected(true);

//        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(GroupJodiActivity.this,android.R.layout.simple_list_item_1, sp_input_data.group_jodi_array);
        editText.setAdapter(adapter);


        txtMatka.setText(dashName.toString()+"- Group Jodi Board");



        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);


//        txtMatka.setText(dashName.toString()+"- Group Panel Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpeTo(GroupJodiActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//
//            }
//        });

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());
        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);

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


                if(txt_timer.getVisibility()==View.VISIBLE)
                {
                    txt_timer.setVisibility(View.GONE);
                }
                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.VISIBLE);

            }
            else if (c_date.before(e_date) && c_date.after(s_date))
            {
                if(txt_timer.getVisibility()==View.VISIBLE)
                {
                    txt_timer.setVisibility(View.GONE);
                }
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
                //String bet=btnType.getText().toString();
                String date_b=btnGameType.getText().toString().trim();
                String b[]=date_b.split(" ");
                String vt=b[3];

                if(vt.equals("Open")) {

                    String dData = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        editText.setError("Please enter any digit");
                        editText.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                        etPoints.setError("Please enter some point");
                        etPoints.requestFocus();
                        return;

                    } else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 1");
                            etPoints.requestFocus();
                            return;


                        } else {
                            String th = null;

                            th = "close";
                            int key = -1;
                            boolean st = false;
                            String d = editText.getText().toString();
                            String p = etPoints.getText().toString();
                            String g = btnGameType.getText().toString();
                            boolean sr = false;

                            for (int i = 0; i <= main.length - 1; i++) {
                                for (int j = 0; j <= main[i].length - 1; j++) {
                                    if (main[i][j].contains(d)) {
                                        key = i;
                                        st = true;

                                        break;
                                    }

                                    // Toast.makeText(GroupPanelActivity.this,"Data in j: "+main[i][j],Toast.LENGTH_LONG).show();
                                }
                                if (st == true) {

                                    list.clear();
//                            Toast.makeText(GroupJodiActivity.this,"exist"+key,Toast.LENGTH_LONG).show();


                                   // ArrayList<String> list = new ArrayList<String>();
                                    for (int k = 0; k <= main[key].length - 1; k++) {
                                        // progressDialog.show();
                                        //list.add(main[key][k].toString());
                                //        setTableData(main[key][k], p, th);
                                        common.addData(main[key][k], p, th,list,tableAdaper,list_table,btnSave);


                                        //arrayList.clear();
                                    }


                                    // Toast.makeText(GroupPanelActivity.this,"Data in j: "+list,Toast.LENGTH_LONG).show();
                                    //  progressDialog.dismiss();
                                    break;

                                }


                            }
                            if (st == false) {
                                Toast.makeText(GroupJodiActivity.this, "not exist ", Toast.LENGTH_LONG).show();
                                // progressDialog.dismiss();
                            }

                            editText.setText("");
                            etPoints.setText("");

                            editText.requestFocus();
                        }
                        //  arrayList.clear();
                    }
                }
                else
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

//                module.insertData(GroupJodiActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);
                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(GroupJodiActivity.this);
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
