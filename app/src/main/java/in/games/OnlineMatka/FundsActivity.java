package in.games.OnlineMatka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FundsActivity extends MyBaseActivity {

    RelativeLayout cvAdd_Funds,cvWithDraw_Funds,cvFundReq_history;
    TextView bt_back ,tv_title;
    String type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);
        type = getIntent().getStringExtra("type");
        cvAdd_Funds=findViewById(R.id.cvAddFunds);
        cvWithDraw_Funds=findViewById(R.id.cvWithdrawFunds);
        cvFundReq_history=findViewById(R.id.cvFund_req_history);
        bt_back = (TextView) findViewById(R.id.txt_back);
        tv_title = (TextView) findViewById(R.id.title);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        if (type.equalsIgnoreCase("add"))
        {
            cvWithDraw_Funds.setVisibility(View.GONE);
            tv_title.setText("Add Points");
        }
        else if (type.equalsIgnoreCase("withdraw"))
        {
            cvAdd_Funds.setVisibility(View.GONE);
            tv_title.setText("Withdraw Points");
        }
        cvAdd_Funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FundsActivity.this,RequestActivity.class);
                startActivity(intent);

            }
        });

        cvWithDraw_Funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(FundsActivity.this,WithdrawalActivity.class);
                startActivity(intent);

            }
        });

        cvFundReq_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FundsActivity.this,Fund_RequestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(FundsActivity.this);
    }
}
