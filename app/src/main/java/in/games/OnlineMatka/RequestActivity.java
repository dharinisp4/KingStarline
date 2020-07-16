package in.games.OnlineMatka;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.ConnectivityReceiver;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static in.games.OnlineMatka.splash_activity.min_add_amount;

public class RequestActivity extends AppCompatActivity implements PaymentStatusListener {
  Common common;
    EditText etPoints;
 LoadingBar progressDialog;
    private TextView bt_back,txtMatka;
    Button btnRequest;
    private TextView txtWallet_amount;
    int min_amount,amt_limit=0 ;
    String upi="",upi_name="",upi_desc="";
   CardView cardView1;
    String transactionId="";
    final int UPI_PAYMENT = 0;
    private EasyUpiPayment mEasyUpiPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        txtMatka=(TextView)findViewById(R.id.board);
        etPoints=(EditText)findViewById(R.id.etRequstPoints);
        cardView1=(CardView) findViewById(R.id.cardView1);
        btnRequest=(Button)findViewById(R.id.add_Request);
        progressDialog=new LoadingBar(RequestActivity.this);

        bt_back=(TextView)findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
   common=new Common(RequestActivity.this);
   min_amount = Integer.parseInt(min_add_amount);

        txtMatka.setText("Add Points");
        getApiData();
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
                    if(points<amt_limit)
                    {
                        common.errorMessageDialog("Minimum Range for points is "+ amt_limit);

                    }
                    else
                    {

                        String p=String.valueOf(points);
                        String st="pending";
                         transactionId = "TXN" + System.currentTimeMillis();
                        String payeeVpa = upi;
                        String payeeName = upi_name;
                        String transactionRefId = transactionId;
                        String description = upi_desc;
                        String amount = p.toString()+".00";
                        payViaUpi(transactionId,payeeVpa,payeeName,transactionRefId,description,amount);
//                        saveInfoIntoDatabase(user_id,p,st);
                    }
                }






            }
        });

//        AddPointsFragment searchFragment=new AddPointsFragment();
//        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.flMain,searchFragment);
//        //  fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RequestActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void payViaUpi(String transactionId, String payeeVpa, String payeeName, String transactionRefId, String description, String amount) {
        // START PAYMENT INITIALIZATION
        mEasyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                .setPayeeVpa(payeeVpa)
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setDescription(description)
                .setAmount(amount)
                .build();

        // Register Listener for Events
        mEasyUpiPayment.setPaymentStatusListener(this);


//        switch (paymentAppChoice.getId()) {
//            case R.id.app_default:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.NONE);
//                break;
//            case R.id.app_amazonpay:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.AMAZON_PAY);
//                break;
//            case R.id.app_bhim_upi:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
//                break;
//            case R.id.app_google_pay:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
//                break;
//            case R.id.app_phonepe:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.PHONE_PE);
//                break;
//            case R.id.app_paytm:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.PAYTM);
//                break;
//        }

        mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.NONE);

        // Check if app exists or not
        if (mEasyUpiPayment.isDefaultAppExist()) {
            onAppNotFound();
            return;
        }
        // END INITIALIZATION

        // START PAYMENT
        mEasyUpiPayment.startPayment();
    }


    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(RequestActivity.this);
        common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
    }
    private void saveInfoIntoDatabase(final String user_id, final String points, final String st) {

       progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_data_insert_req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                      progressDialog.dismiss();
                        Toast.makeText(RequestActivity.this,"successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RequestActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }
                    else
                    {
                        progressDialog.dismiss();

                        Toast.makeText(RequestActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                        return;
                    }


                }
                catch (Exception ex)
                {
                   progressDialog.dismiss();

                    Toast.makeText(RequestActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                      progressDialog.dismiss();

                        Toast.makeText(RequestActivity.this,"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
                        return;

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("user_id",user_id);
                params.put("points",points);
                params.put("request_status",st);
                params.put("type","Add");

                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(RequestActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getApiData() {
        progressDialog.show();
        String json_tag="json_splash_request";
        final HashMap<String,String> params=new HashMap<String, String>();
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.GET, URLs.URL_INDEX, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.e("asdasd",""+response.toString());
                progressDialog.dismiss();
                try
                {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        JSONObject dataObj=response.getJSONObject("data");
                          upi=dataObj.getString("upi");
                          upi_name=dataObj.getString("upi_name");
                          upi_desc=dataObj.getString("upi_desc");
                          amt_limit=Integer.parseInt(dataObj.getString("fund_amount").toString());

                    }
                    else
                    {
                        common.showToast(response.getString("message"));
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(RequestActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);


    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//    common.showToast("transactionDetails -"+transactionDetails.toString());
    Log.e("transactionDetails",""+transactionDetails);
    if(transactionDetails.getStatus().equalsIgnoreCase("success"))
    {
        String user_id= Prevalent.currentOnlineuser.getId();
        addRequest(user_id,transactionDetails.getAmount().toString(),"pending",transactionDetails.getTransactionId().toString());

    }
    else
    {
      common.showToast("Payment Failed.");
    }

    }

    @Override
    public void onTransactionSuccess() {

//        common.showToast("success");
    }

    @Override
    public void onTransactionSubmitted() {
//    common.showToast("submitteddd");
    }

    @Override
    public void onTransactionFailed() {
   common.showToast("Failed");
    }

    @Override
    public void onTransactionCancelled() {
  common.showToast("Cancelled");
    }

    @Override
    public void onAppNotFound() {
  common.showToast("App Not Found");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEasyUpiPayment.detachListener();
    }

    public void addRequest(String user_id,String points,String status,String txn_id)
    {
        progressDialog.show();
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        params.put("points",points);
        params.put("request_status",status);
        params.put("type","Add");
        params.put("txn_id",txn_id);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.Url_data_insert_req, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RequestActivity.this,"successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RequestActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }
                    else
                    {
                        progressDialog.dismiss();

                        Toast.makeText(RequestActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                        return;
                    }


                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();

                    Toast.makeText(RequestActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);
    }
}
