package in.games.OnlineMatka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

import static in.games.OnlineMatka.URLs.URL_MPIN;

public class DrawerGenMpinActivity extends MyBaseActivity {
 LoadingBar progressDialog;
    private Dialog dialog;
    Common common;
    private TextView btn_back;
    private CardView cvGenPin,cvForPin;
    private Button dialog_btnMpin;

    EditText dialog_etEmail;
    private Button btnDGenPin,btnDForPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_gen_mpin);
        common=new Common(DrawerGenMpinActivity.this);
        cvGenPin=(CardView)findViewById(R.id.cvGenPin);
        cvForPin=(CardView)findViewById(R.id.cvForPin);
        progressDialog=new LoadingBar(DrawerGenMpinActivity.this);

        btn_back=(TextView)findViewById(R.id.txt_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cvGenPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(DrawerGenMpinActivity.this);
                builder.setTitle("Generate MPIN")
                        .setMessage("Please Press ok to generate MPIN")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String email = Prevalent.currentOnlineuser.getEmail().toString().trim();
                                getMPINNumber(email);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                builder.show();
            }
        });

        cvForPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerGenMpinActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.dialog_foraget_mpin_layout);
                dialog_btnMpin=(Button)dialog.findViewById(R.id.forget_mpin);
                dialog_etEmail=(EditText)dialog.findViewById(R.id.etForget_email);

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


                dialog_btnMpin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(TextUtils.isEmpty(dialog_etEmail.getText().toString()))
                        {
                            dialog_etEmail.setError("Enter registered Email Id");
                            dialog_etEmail.requestFocus();
                            return;
                        }
                        else
                        {
                            String mail=dialog_etEmail.getText().toString().trim();
                            getForgotMpin(mail);
                        }

                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        common.setSessionTimeOut(DrawerGenMpinActivity.this);
    }

    private void getForgotMpin(final String mail) {


        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_forgot_mpin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerGenMpinActivity.this, "Mail sent to your email address!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerGenMpinActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerGenMpinActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerGenMpinActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("email",mail);
                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }


    private void getMPINNumber(final String email) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MPIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("success"))
                            {

                                String mpin=jsonObject.getString("ans");
                                progressDialog.dismiss();

                                AlertDialog.Builder alertDialog=new AlertDialog.Builder(DrawerGenMpinActivity.this);
                                alertDialog.setMessage(mpin)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();

                                            }
                                        });
                                alertDialog.show();
                               // builDialog(DrawerGenMpinActivity.this,mpin);


                            }
                            else if(status.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String mpin=jsonObject.getString("ans");
                                common.errorMessageDialog(""+mpin);
                            }
                            else
                            {

                                progressDialog.dismiss();
                                Toast.makeText(DrawerGenMpinActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        catch (Exception ex)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DrawerGenMpinActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(DrawerGenMpinActivity.this,"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("email",email);



                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public AlertDialog.Builder builDialog(Context c, String key)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        //builder.setTitle("No Internet Connection");
        builder.setMessage("MPIN is : "+key);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        return builder;
    }
}
