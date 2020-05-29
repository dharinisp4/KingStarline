package in.games.OnlineMatka;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import in.games.OnlineMatka.Objects.sp_input_data;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class CyclePana extends AppCompatActivity {
    Common common;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;

    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    private int stat=0;
    private Button btnAdd,btnSave,btnType,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    TextView bt_back;
    TextView btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id ,txt_timer,tv_timer ;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    private final String[] d10={"100","110","120","130","140","150","160","170","180","190"};
    private final String[] d11={"110","111","112","113","114","115","116","117","118","119"};
    private final String[] d12={"112","120","122","123","124","125","126","127","128","129"};
    private final String[] d13={"113","123","130","133","134","135","136","137","138","139"};
    private final String[] d14={"114","124","134","140","144","145","146","147","148","149"};
    private final String[] d15={"115","125","135","145","150","155","156","157","158","159"};
    private final String[] d16={"116","126","136","146","156","160","166","167","168","169"};
    private final String[] d17={"117","127","137","147","157","167","170","177","178","179"};
    private final String[] d18={"118","128","138","148","158","168","178","180","188","189"};
    private final String[] d19={"119","129","139","149","159","169","179","189","190","199"};
    private final String[] d20={"120","200","220","230","240","250","260","270","280","290"};
    private final String[] d22={"122","220","223","224","225","226","227","228","229","222"};
    private final String[] d23={"123","230","233","234","235","236","237","238","239","223"};
    private final String[] d24={"124","240","244","245","246","247","248","249","224","234"};
    private final String[] d25={"125","250","255","256","257","258","259","225","235","245"};
    private final String[] d26={"126","260","266","267","268","269","226","236","346","256"};
    private final String[] d27={"127","270","277","278","279","227","237","247","257","267"};
    private final String[] d28={"128","280","288","289","228","238","248","258","268","278"};
    private final String[] d29={"129","290","299","229","239","249","259","269","279","289"};
    private final String[] d30={"130","230","300","330","340","350","360","370","380","390"};
    private final String[] d34={"134","234","334","340","344","345","346","347","348","349"};
    private final String[] d35={"135","350","355","335","345","235","356","357","358","359"};
    private final String[] d36={"136","360","366","336","346","356","367","368","369","236"};
    private final String[] d37={"137","370","377","337","347","357","367","378","379","237"};
    private final String[] d38={"138","380","388","238","338","348","358","368","378","389"};
    private final String[] d39={"139","390","399","349","359","369","379","389","239","339"};
    private final String[] d40={"140","240","340","400","440","450","460","470","480","490"};
    private final String[] d44={"144","244","344","440","449","445","446","447","448","444"};
    private final String[] d45={"145","245","345","450","456","457","458","459","445","455"};
    private final String[] d46={"146","460","446","467","468","469","246","346","456","466"};
    private final String[] d47={"147","470","447","478","479","247","347","457","467","477"};
    private final String[] d48={"148","480","489","248","348","448","488","458","468","478"};
    private final String[] d49={"149","490","499","449","459","469","479","489","249","349"};
    private final String[] d50={"500","550","150","250","350","450","560","570","580","590"};
    private final String[] d55={"155","556","557","558","559","255","355","455","555","550"};
    private final String[] d56={"156","556","567","568","569","356","256","456","560","566"};
    private final String[] d57={"157","257","357","457","557","578","579","570","567","577"};
    private final String[] d58={"158","558","568","578","588","589","580","258","358","458"};
    private final String[] d59={"159","259","359","459","559","569","579","589","590","599"};
    private final String[] d60={"600","160","260","360","460","560","660","670","680","690"};
    private final String[] d66={"660","667","668","669","666","166","266","366","466","566"};
    private final String[] d67={"670","167","267","367","467","567","667","678","679","677"};
    private final String[] d68={"680","688","668","678","168","268","368","468","568","689"};
    private final String[] d69={"690","169","269","369","469","569","669","679","689","699"};
    private final String[] d70={"700","170","270","370","470","570","670","770","780","790"};
    private final String[] d77={"770","177","277","377","477","577","677","778","779","777"};
    private final String[] d78={"178","278","378","478","578","678","778","788","789","780"};
    private final String[] d79={"179","279","379","479","579","679","779","789","799","790"};
    private final String[] d80={"180","280","380","480","580","680","780","880","800","890"};
    private final String[] d88={"188","288","388","488","588","688","788","889","888","880"};
    private final String[] d89={"189","289","389","489","589","689","789","889","890","899"};
    private final String[] d90={"900","190","290","390","490","590","690","790","890","990"};

    private final String[] d99={"199","299","399","499","599","699","799","899","990","999"};

    private final String[][] main=new String[][]{d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d22,d23,d24,d25,d26,d27
            ,d28,d29,d30,d34,d35};
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
    // private ListView lstView;
    AutoCompleteTextView editText;
    private String game_id ,bet_type;
    private String m_id;
    private String start_time,end_time;

    private TextView txtWallet_amount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cycle_pana );
        common=new Common(CyclePana.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
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
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);

        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        editText=(AutoCompleteTextView) findViewById(R.id.etSingleDigit);
        progressDialog=new LoadingBar(CyclePana.this);


        txtMatka.setSelected(true);
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);

        bt_back=(TextView)findViewById(R.id.txtBack);


//        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(CyclePana.this,android.R.layout.simple_list_item_1, sp_input_data.cycle_pana_array);
        editText.setAdapter(adapter);
        txtMatka.setText(dashName.toString()+"- Cycle Patti Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

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
//                    details.setBetTypeTo(CyclePana.this,dialog,start_time,end_time,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();

            }
        });

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.clear();
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
//                else if(dData.length()!=3)
//                {
//                    editText.setError("Please enter digitin right format");
//                    editText.requestFocus();
//                    return;
//                }
//                else if(!Arrays.asList(sp_input_data.panel_group_array).contains(dData))
//                {
//                    errorMessageDialog(CyclePana.this,"Invalid Panna");
//                    editText.setText("");
//                    etPoints.setText("");
//                    editText.requestFocus();
//
//                }


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
                            txt_timer.setVisibility(View.VISIBLE);
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
                        switch (d)
                        {
                            case "10" :
                                list.clear();
                                setArray( d10,p,th );
                                break;

                            case "11":
                                list.clear();
                                setArray( d11,p,th );
                                break;
                            case "12" :
                                list.clear();
                                setArray( d12,p,th );
                                break;
                            case "13" :
                                list.clear();
                                setArray( d13,p,th );
                                break;
                            case "14" :
                                list.clear();
                                setArray( d14,p,th );
                                break;
                            case "15" :
                                list.clear();
                                setArray( d15,p,th );
                                break;
                            case "16" :
                                list.clear();
                                setArray( d16,p,th );
                                break;
                            case "17" :
                                list.clear();
                                setArray( d17,p,th );
                                break;
                            case "18" :
                                list.clear();
                                setArray( d18,p,th );
                                break;
                            case "19" :
                                list.clear();
                                setArray( d19,p,th );
                                break;
                            case "20" :
                                list.clear();
                                setArray( d20,p,th );
                                break;
                            case "22" :
                                list.clear();
                                setArray( d22,p,th );
                                break;
                            case "23" :
                                list.clear();
                                setArray( d23,p,th );
                                break;
                            case "24" :
                                list.clear();
                                setArray( d24,p,th );
                                break;
                            case "25" :
                                list.clear();
                                setArray( d25,p,th );
                                break;
                            case "26" :
                                list.clear();
                                setArray( d26,p,th );
                                break;

                            case "27" :
                                list.clear();
                                setArray( d27,p,th );
                                break;

                            case "28" :
                                list.clear();
                                setArray( d28,p,th );
                                break;

                            case "29" :
                                list.clear();
                                setArray( d29,p,th );
                                break;

                            case "30" :
                                list.clear();
                                setArray( d30,p,th );
                                break;

                            case "34" :
                                list.clear();
                                setArray( d34,p,th );
                                break;

                            case "35" :
                                list.clear();
                                setArray( d35,p,th );
                                break;

                            case "36" :
                                list.clear();
                                setArray( d36,p,th );
                                break;

                            case "37" :
                                list.clear();
                                setArray( d37,p,th );
                                break;

                            case "38" :
                                list.clear();
                                setArray( d38,p,th );
                                break;

                            case "39" :
                                list.clear();
                                setArray( d39,p,th );
                                break;

                            case "40" :
                                list.clear();
                                setArray( d40,p,th );
                                break;

                            case "44" :
                                list.clear();
                                setArray( d44,p,th );
                                break;

                            case "45" :
                                list.clear();
                                setArray( d45,p,th );
                                break;

                            case "46" :
                                list.clear();
                                setArray( d46,p,th );
                                break;

                            case "47" :
                                list.clear();
                                setArray( d47,p,th );
                                break;
                            case "48" :
                                list.clear();
                                setArray( d48,p,th );
                                break;

                            case "49" :
                                list.clear();
                                setArray( d49,p,th );
                                break;

                            case "50" :
                                list.clear();
                                setArray( d50,p,th );
                                break;
                            case "55" :
                                list.clear();
                                setArray( d55,p,th );
                                break;
                            case "56" :
                                list.clear();
                                setArray( d56,p,th );
                                break;

                            case "57" :
                                list.clear();
                                setArray( d57,p,th );
                                break;

                            case "58" :
                                list.clear();
                                setArray( d58,p,th );
                                break;
                            case "59" :
                                list.clear();
                                setArray( d59,p,th );
                                break;
                            case "60" :
                                list.clear();
                                setArray( d60,p,th );
                                break;
                            case "66" :
                                list.clear();
                                setArray( d66,p,th );
                                break;
                            case "67" :
                                list.clear();
                                setArray( d67,p,th );
                                break;
                            case "68" :
                                list.clear();
                                setArray( d68,p,th );
                                break;

                            case "69" :
                                list.clear();
                                setArray( d69,p,th );
                                break;

                            case "70" :
                                list.clear();
                                setArray( d70,p,th );
                                break;

                            case "77" :
                                list.clear();
                                setArray( d77,p,th );
                                break;

                            case "78" :
                                list.clear();
                                setArray( d78,p,th );
                                break;

                            case "79" :
                                list.clear();
                                setArray( d79,p,th );
                                break;

                            case "80" :
                                list.clear();
                                setArray( d80,p,th );
                                break;
                            case "88" :
                                list.clear();
                                setArray( d88,p,th );
                                break;

                            case "89" :
                                list.clear();
                                setArray( d89,p,th );
                                break;

                            case "90" :
                                list.clear();
                                setArray( d90,p,th );
                                break;

                            case "99" :
                                list.clear();
                                setArray( d99,p,th );
                                break;

                        }


//
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
//                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
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
                    btnGameType.setText(s_dt+" Bet" + bet_type.toUpperCase());
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

//
public void setArray(String[] array ,String p, String th)
{
    for (int i =0 ;i<array.length ; i++) {
        common.addData(  array[i], p, th, list, tableAdaper, list_table, btnSave );
        editText.setText("");
        etPoints.setText("");

        editText.requestFocus();
    }

}

}

