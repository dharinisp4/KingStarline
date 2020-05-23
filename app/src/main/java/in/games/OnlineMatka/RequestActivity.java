package in.games.OnlineMatka;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

import android.widget.TextView;

public class RequestActivity extends MyBaseActivity {
  Common common;
    EditText etPoints;
 LoadingBar progressDialog;
    private TextView bt_back,txtMatka;
    Button btnRequest;
    private TextView txtWallet_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        txtMatka=(TextView)findViewById(R.id.board);
        etPoints=(EditText)findViewById(R.id.etRequstPoints);
        btnRequest=(Button)findViewById(R.id.add_Request);
        progressDialog=new LoadingBar(RequestActivity.this);

        bt_back=(TextView)findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
   common=new Common(RequestActivity.this);

        txtMatka.setText("FUNDS");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int points=Integer.parseInt(etPoints.getText().toString().trim());

                if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Enter Some Points");
                    return;
                }
                else
                {
                    if(points<200)
                    {
                        common.errorMessageDialog("Minimum Range for points is 200");

                    }
                    else
                    {
                        Intent intent = new Intent(RequestActivity.this,UploadScreenshotActivity.class);
                        intent.putExtra("points",String.valueOf(points));
                        intent.putExtra("status","pending");
                        startActivity(intent);
                        String user_id= Prevalent.currentOnlineuser.getId();
                        String p=String.valueOf(points);
                        String st="pending";
//                       saveInfoIntoDatabase(user_id,p,st);
                    }
                }






            }
        });

//        AddPointsFragment searchFragment=new AddPointsFragment();
//        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.flMain,searchFragment);
//        //  fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


    }





    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(RequestActivity.this);
        common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
    }
}
