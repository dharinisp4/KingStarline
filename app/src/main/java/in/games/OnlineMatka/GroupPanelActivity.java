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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Objects.sp_input_data;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class GroupPanelActivity extends MyBaseActivity{
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    Common common;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    private int stat=0;
    private Button btnAdd,btnSave,btnType,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    TextView bt_back;
    TextView btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,tv_timer;
    private final String[] d1={"123","178","137","678","236","367","128","268"};
    private final String[] d2={"240","245","790","470","290","579","259","457"};
    private final String[] d3={"100","150","600","556","155","560"};
    private final String[] d4={"330","880","358","380","588","335"};
    private final String[] d5={"119","169","146","466","114","669"};
    private final String[] d6={"349","344","899","448","489","399"};
    private final String[] d7={"777","222","277","227"};
    private final String[] d8={"246","129","179","147","124","679","467","269"};
    private final String[] d9={"480","340","345","890","390","458","589","359"};
    private final String[] d10={"336","133","138","188","688","368"};
    private final String[] d11={"110","660","566","115","156","160"};
    private final String[] d12={"200","255","557","700","570","250"};
    private final String[] d13={"377","237","223","778","278","228"};
    private final String[] d14={"444","999","449","499"};
    private final String[] d15={"139","189","134","148","468","346","369","689"};
    private final String[] d16={"157","170","567","120","670","125","260","256"};
    private final String[] d17={"238","233","337","378","788","288"};
    private final String[] d18={"300","355","800","558","580","350"};
    private final String[] d19={"490","990","599","445","440","459"};
    private final String[] d20={"247","477","279","779","229","224"};
    private final String[] d21={"666","111","116","166"};

    private final String[] d22={"478","248","234","789","289","347","239","379"};
    private final String[] d23={"135","130","680","180","158","568","360","356"};
    private final String[] d24={"400","455","450","900","559","590"};
    private final String[] d25={"220","225","770","270","577","257"};
    private final String[] d26={"149","144","446","469","699","199"};
    private final String[] d27={"112","126","117","167","667","266"};
    private final String[] d28={"333","888","388","338"};

    private final String[] d29={"258","235","578","780","230","280","357","370"};
    private final String[] d30={"140","456","159","145","460","569","190","690"};
    private final String[] d31={"249","299","799","479","447","244"};
    private final String[] d32={"122","177","267","127","677","226"};
    private final String[] d33={"113","136","168","118","668","366"};
    private final String[] d34={"348","334","389","339","488","889"};
    private final String[] d35={"555","000","550","500"};

    private final String[][] main=new String[][]{d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27
    ,d28,d29,d30,d31,d32,d33,d34,d35};
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
   // private ListView lstView;
   AutoCompleteTextView editText;
    private String game_id;
    private String m_id ,start_time,end_time;
    private TextView txtWallet_amount ,txt_timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_panel);
        common=new Common(GroupPanelActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        txtDigit=(TextView)findViewById(R.id.dgt);
        txtPoint=(TextView)findViewById(R.id.pnt);
        txtType=(TextView)findViewById(R.id.type);
        btnDelete=(TextView) findViewById(R.id.del);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        tv_timer = findViewById(R.id.tv_timer);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
       editText=(AutoCompleteTextView) findViewById(R.id.etSingleDigit);
        progressDialog=new LoadingBar(GroupPanelActivity.this);
       // lstView=findViewById(R.id.list);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        txtMatka.setSelected(true);
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        txt_timer = findViewById(R.id.timer);
        bt_back=(TextView)findViewById(R.id.txtBack);


//        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(GroupPanelActivity.this,android.R.layout.simple_list_item_1,sp_input_data.panel_group_array);
        editText.setAdapter(adapter);
        txtMatka.setText(dashName.toString()+"- Group Panel Board");

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
//                details.setDateAndBetTpe(GroupPanelActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });

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
            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.clear();
                String bet="Select Type";
                if(rd_close.isChecked())
                {
                    bet="close";
                }
                else if(rd_open.isChecked())
                {
                    bet="open";
                }
                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {
                    String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);
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
                else if(dData.length()!=3)
                {
                    editText.setError("Please enter digitin right format");
                    editText.requestFocus();
                    return;
                }
                else if(!Arrays.asList(sp_input_data.panel_group_array).contains(dData))
                {
                    common.errorMessageDialog("Invalid Panna");
                    editText.setText("");
                    etPoints.setText("");
                    editText.requestFocus();

                }


                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 1");
                        etPoints.requestFocus();
                        return;


                    } else {

//
//                    arrayList.clear();
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




                        int key = -1;
                        boolean st = false;
                        final String d = editText.getText().toString();
                        p = etPoints.getText().toString();
                        g = btnGameType.getText().toString();

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

                                //         Toast.makeText(GroupPanelActivity.this,"exist"+key,Toast.LENGTH_LONG).show();


                                ArrayList<String> list1 = new ArrayList<String>();
                                for (int k = 0; k <= main[key].length - 1; k++) {
                                    // progressDialog.show();
                                    //list.add(main[key][k].toString());
//                                 arrayList.add(new SingleDigitObjects(main[key][k].toString(),p,th));
                                   // setTableData(main[key][k], p, th);
                                    common.addData(main[key][k], p, th,list,tableAdaper,list_table,btnSave);
                                    editText.setText("");
                                    etPoints.setText("");
                                    editText.requestFocus();
                                    //arrayList.clear();
                                }


                                // Toast.makeText(GroupPanelActivity.this,"Data in j: "+list,Toast.LENGTH_LONG).show();
                                //  progressDialog.dismiss();
                                break;

                            }


                        }
                        if (st == false) {
                            Toast.makeText(GroupPanelActivity.this, "not exist ", Toast.LENGTH_LONG).show();
                            //progressDialog.dismiss();
                        }

                        // arrayList.clear();
//                    Toast.makeText(GroupPanelActivity.this,"exist"+key,Toast.LENGTH_LONG).show();
//                    arrayList.add(new SingleDigitObjects(p,d,th));
//                    adapter1.notifyDataSetChanged();

                        editText.setText("");
                        etPoints.setText("");

                        btnType.setText("Select Type");
                    }

                    //  arrayList.clear();
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

//                module.insertData(GroupPanelActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);
                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(GroupPanelActivity.this);
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
}
