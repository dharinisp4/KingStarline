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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class FullSangamActivity extends MyBaseActivity {

    Common common;
    private int stat=0;
    private TextView txtDigit,txtPoint,txtType,btnDelete;
    private int val_p=0;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Dialog dialog;
    private final String[] singlePaana={"137","128","146","236","245","290","380","470","489","560","678","579",
            "119","155","227","335","344","399","588","669","777","100","129","138","147","156","237","246",
            "345","390","480","570","589","679","110","228","255","336","499","660","778","200","444",
            "120","139","148","157","238","247","256","346","490","580","670","689","779","788","300","111",
            "130","149","158","167","239","248","257","347","356","590","680","789","699","770","400","888",
            "140","159","168","230","249","258","267","348","357","456","690","780","113","122","177","339",
            "366","447","799","889","500","555",
            "123","150","169","178","240","259","268","349","358","367","457","790","114","277","330","448",
            "466","556","880","899","600","222",
            "124","160","179","250","269","278","340","359","368","458","467","890","115","133","188","223","377",
            "449","557","566","700","999",
            "125","134","170","189","260","279","350","369","378","459","468","567","116","224","233","288","440",
            "477","558","666", "126","135","180","235","270","289","360","379","450","469","478",
            "568","117","144","199","225","388","559","577","667","900","333",
            "127","136","145","190","234","280","370","389","460","479","569","578","118","226","244","299","334","488",
            "668","677","000","550",
            "688",
            "166","229","337","355","445","599","112","220","266",
            "338","446","455",
            "800","990"};


    private Button btnAdd,btnSave,btnType,btnGameType;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    TextView bt_back;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;

    AutoCompleteTextView etOpenPana,etClosePana;
    private String game_id;
    private String m_id ,start_time,end_time;
    private TextView txtWallet_amount,txt_timer,tv_timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sangam);
        common=new Common(FullSangamActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(FullSangamActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
      //  progressDialog.setTitle("Please wait");

        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        txt_timer = findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        txtMatka.setSelected(true);
        common.setSessionTimeOut(FullSangamActivity.this);

        bt_back=(TextView)findViewById(R.id.txtBack);

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        etOpenPana=findViewById(R.id.et_OpenPanna);
        etClosePana=findViewById(R.id.et_ClosePanna);


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



//btnGameType.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//        details.setDateAndBetTpeTo(FullSangamActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//
//    }
//});

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(FullSangamActivity.this,android.R.layout.simple_list_item_1,singlePaana);
        etOpenPana.setAdapter(adapter);
        etClosePana.setAdapter(adapter);
        txtMatka.setText(dashName.toString()+"- Full Sangam Board");

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());
        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_b=btnGameType.getText().toString().trim();
                String b[]=date_b.split(" ");
                String vt=b[3];

                if(vt.equals("Open")) {
                    String open_pana = etOpenPana.getText().toString().trim();
                    String close_pana = etClosePana.getText().toString().trim();
                    String points = etPoints.getText().toString().trim();

                    if (TextUtils.isEmpty(open_pana)) {
                        etOpenPana.setError("Enter open pana");
                        etOpenPana.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(close_pana)) {
                        etClosePana.setError("Enter close pana");
                        etClosePana.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(points)) {
                        etPoints.setError("Enter points");
                        etPoints.requestFocus();
                        return;
                    } else if(!Arrays.asList(singlePaana).contains(open_pana))
                    {
                        Toast.makeText(FullSangamActivity.this,"This is invalid pana",Toast.LENGTH_LONG).show();
                        etOpenPana.setText("");
                        etOpenPana.requestFocus();
                        return;

                    }else if(!Arrays.asList(singlePaana).contains(close_pana))
                    {
                        Toast.makeText(FullSangamActivity.this,"This is invalid pana",Toast.LENGTH_LONG).show();
                        etClosePana.setText("");
                        etClosePana.requestFocus();
                        return;

                    }
                    else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 1");
                            etPoints.requestFocus();
                            return;


                        } else {
                          //  setTableRowData(open_pana, close_pana, points);
                            common.addData(open_pana+"-"+close_pana,points,"Full Sangam",list,tableAdaper,list_table,btnSave);

                            clearCtrls();
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

                int er = list.size();
                if (er <= 0) {
                    String message = "Please Add Some Bids";
                    common.errorMessageDialog(message);
                    return;
                } else {

                    try {
                        int amt = 0;
                        ArrayList list_digits = new ArrayList();
                        ArrayList list_type = new ArrayList();
                        ArrayList list_points = new ArrayList();
                        int rows = list.size();


                        for (int i = 0; i < rows; i++) {


                             TableModel tableModel=list.get(i);
                            String asd = tableModel.getDigits().toString();
                            String d_all[]=asd.split("-");
                            String d0=d_all[0].toString();
                            String d1=d_all[1].toString();

                            String asd1 = tableModel.getPoints().toString();
                            String asd2 = tableModel.getType().toString();
                            int b = 1;
                            if (asd2.equals("Full Sangam")) {
                                b = 0;
                            } else {
                                b = 0;
                            }


                            amt = amt + Integer.parseInt(asd1);

                            char quotes='"';
                            list_digits.add(quotes+d0+quotes);
                            list_points.add(asd1);
                            list_type.add(quotes+d1+quotes);



                            // String sd=list_digits.add();
                        }



                        String id = Prevalent.currentOnlineuser.getId().toString().trim();
                        String matka_id = m_id.toString().trim();
                        String date = "15/02/2020";
                        String dt=btnGameType.getText().toString().trim();
                        String d[]=dt.split(" ");

                        String c=d[0].toString();

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("points", list_points);
                        jsonObject.put("digits", list_digits);
                        jsonObject.put("bettype", list_type);
                        jsonObject.put("user_id", id);
                        jsonObject.put("matka_id", matka_id);
                        jsonObject.put("date", c);
                        jsonObject.put("game_id", game_id);

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject);


                       // Toast.makeText(FullSangamActivity.this,"data"+jsonArray,Toast.LENGTH_LONG).show();

                        //      Object o1=jsonArray_digits;

                       String w = txtWallet_amount.getText().toString().trim();
                        int wallet_amount = Integer.parseInt(w);
                        if (wallet_amount < amt) {

                            String message="Insufficient Amount";
                            common.errorMessageDialog(message);
                            return;

                        } else {
                            int up_amt = wallet_amount - amt;
                            String asd = String.valueOf(up_amt);
                            String userid = Prevalent.currentOnlineuser.getId();
                            btnSave.setEnabled(false);
                            common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
//                            updateWalletAmountSangum( FullSangamActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);

                        }
//

                    } catch (Exception ex) {
                        Toast.makeText(FullSangamActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }
        });



    }



    public void clearCtrls()
    {
        etOpenPana.setText("");
        etClosePana.setText("");
        etPoints.setText("");
    etOpenPana.requestFocus();

    }

    @Override
    protected void onStart() {
        super.onStart();
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
            common.setBetDateDay(m_id,btnGameType,progressDialog);

        }

    }



}
