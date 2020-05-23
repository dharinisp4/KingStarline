package in.games.OnlineMatka;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.Remote.FileUtil;
import in.games.OnlineMatka.Remote.IUpoladAPI;
import in.games.OnlineMatka.Remote.ProgressRequestBody;
import in.games.OnlineMatka.Remote.RetrofitClient;
import in.games.OnlineMatka.Remote.UploadCallBacks;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;
import in.games.OnlineMatka.utils.Module;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.android.volley.VolleyLog.TAG;

public class UploadScreenshotActivity extends AppCompatActivity implements View.OnClickListener, UploadCallBacks {
    Activity ctx = UploadScreenshotActivity.this;
    Module module;
    double rewards=0;
    TextView tv_acc_details, tv_acc_holder;
    String total_amount;
    String order_total_amount,getvalue;
    String trans_id="";
    ImageView img_qr, img_screenshot;
    EditText et_trans_id;
  LoadingBar loadingBar;
          ProgressDialog dialog;
    Button btn_choose, btn_cancel, btn_save;
    Toolbar toolbar;
    Prevalent prevalent ;
    Uri selectedFileUri=null;
    IUpoladAPI mService;
    String user_id="",getuser_id="",gettime="",trans_image_name="";
    private String getpoints = "";
    private String getstatus = "";
    private double wamt=0;
    private String getdate = "";
    public static final int REQUEST_PERMISSION = 1000;
    private static final int PICK_FILE_REQUEST = 1001;

    private static final int GALLERY_REQUEST_CODE = 1;

    private IUpoladAPI getUPIUpload() {
        return RetrofitClient.getClient(URLs.API_SERVER_URL).create(IUpoladAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_screenshot);
        initViews();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }
        mService = getUPIUpload();
        getScreenshot();

    }




    private void initViews() {
        module=new Module();
        toolbar=findViewById(R.id.toolbar);
       prevalent = new Prevalent();
        tv_acc_details=findViewById(R.id.tv_acc_details);
        tv_acc_holder=findViewById(R.id.tv_acc_holder);
        img_qr=findViewById(R.id.img_qr);
        loadingBar=new LoadingBar(ctx);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pay Now");

        getpoints=getIntent().getStringExtra("points");
        getstatus=getIntent().getStringExtra("status");

       user_id= prevalent.currentOnlineuser.getId();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        img_screenshot=findViewById(R.id.img_screenshot);
        et_trans_id=findViewById(R.id.et_trans_id);
        btn_choose=findViewById(R.id.btn_choose);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_save=findViewById(R.id.btn_save);
        btn_choose.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_choose)
        {
            if(btn_choose.getText().equals(getResources().getString(R.string.btn_choose)))
            {
                openGallery();
            }
            else if(btn_choose.getText().equals(getResources().getString(R.string.btn_upload)))
            {
             uploadImage();
            }
            else
            {
             Toast.makeText(ctx,"You already uploaded a screenshot",Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.btn_cancel)
        {
            setCancel(img_screenshot,trans_image_name,btn_choose);
        }
        else if (view.getId() == R.id.btn_save)
        {
         if(trans_image_name.isEmpty())
         {

             Toast.makeText(ctx,"Please upload screenshot first",Toast.LENGTH_LONG).show();
         }
         else if(et_trans_id.getText().toString().isEmpty())
         {
             et_trans_id.setError("Enter Transaction ID");
             et_trans_id.requestFocus();
         }
         else
         {
             trans_id=et_trans_id.getText().toString();
        makeAddTrsansactionRequest();
         }
        }


    }


    private void uploadImage() {

        if(selectedFileUri !=null)
        {
            dialog=new ProgressDialog(ctx);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("loading...");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();
            String file_nm="";
            File file=null;
            try {
                file= FileUtil.from(ctx,selectedFileUri);
                file_nm=user_id+getRandom()+"_trans"+"."+getFileExtension(file);
                trans_image_name=file_nm;

            } catch (IOException e) {
                e.printStackTrace();
            }
            ProgressRequestBody requestFile=new ProgressRequestBody(file,this);


            //   Toast.makeText(DocUploadActivity.this,""+file.getName()+"\n "+getFileExtension(file),Toast.LENGTH_SHORT).show();
            final MultipartBody.Part body= MultipartBody.Part.createFormData("uploaded_file",file_nm,requestFile);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                                    Log.e("image-data",response.toString());
                                    // Toast.makeText(activity,""+response.body().toString(),Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                    Toast.makeText(ctx, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();                                    btn_choose.setText(getResources().getString(R.string.btn_uploaded));
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(ctx,""+t.getMessage().toString(),Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }).start();
        }

    }

    private void openGallery() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , PICK_FILE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == PICK_FILE_REQUEST)
            {
                if(data != null)
                {
                    selectedFileUri=data.getData();
                    if(selectedFileUri !=null && !selectedFileUri.getPath().isEmpty())
                    {
                        img_screenshot.setImageURI(selectedFileUri);
                        btn_choose.setText(getResources().getString(R.string.btn_upload));
                    }
                    else
                    {
                        Toast.makeText(ctx,"Please select any image",Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {


                    //   }
                }
            }
        }
    }

    public String getRandom()
    {
        Date date=new Date();
        SimpleDateFormat smdf=new SimpleDateFormat("ddMMyyhhmmss");
        String dt=smdf.format(date);
        return dt;
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    @Override
    public void onProgressUpdate(int percentage) {
        dialog.setProgress(percentage);
    }

    public void setCancel(ImageView img,String name,Button btn)
    {
        name="";
        img.setImageDrawable(getResources().getDrawable(R.drawable.upload_docs));
        btn.setText(getResources().getString(R.string.btn_choose));
    }


    private void getScreenshot() {
    loadingBar.show();
        HashMap<String,String> params=new HashMap<>();
        CustomJsonRequest request=new CustomJsonRequest(Request.Method.POST, URLs.ADMIN_ACC_DETALS, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
           try
           {
               Log.e("acc_details",response.toString());
               loadingBar.dismiss();
              String status = response.getString("status");
               if(status.equals("success"))
               {
                   JSONObject object=response.getJSONObject("data");
                   tv_acc_holder.setText("Account Holder Name : "+object.getString("name"));
                   tv_acc_details.setText(Html.fromHtml(object.getString("account")));
                   Glide.with(ctx)
                           .load(URLs.ADMIN_SS_URL + object.getString("qr_code"))
                           .centerCrop()
                           .placeholder(R.drawable.logo)
                           .crossFade()
                           .diskCacheStrategy(DiskCacheStrategy.ALL)
                           .dontAnimate()
                           .into(img_qr);

               }
               else
               {
                   Toast.makeText(ctx, "Contact to Admin for further queries", Toast.LENGTH_LONG).show();
               }

           }
           catch (Exception ex)
           {
               loadingBar.dismiss();
               ex.printStackTrace();
           }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                Toast.makeText(ctx, ""+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void addPaymentRequest() {
    }

    private void saveInfoIntoDatabase(final String user_id, final String points, final String st) {

        loadingBar.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_data_insert_req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                       loadingBar.dismiss();
                        Toast.makeText(ctx,"successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(ctx,HomeActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }
                    else
                    {
                       loadingBar.dismiss();

                        Toast.makeText(ctx,"Something Wrong",Toast.LENGTH_LONG).show();
                        return;
                    }


                }
                catch (Exception ex)
                {
                   loadingBar.dismiss();

                    Toast.makeText(ctx,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loadingBar.dismiss();

                        Toast.makeText(ctx,"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
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

                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeAddTrsansactionRequest() {

        loadingBar.show();
        String tag_json_obj = "json_add_order_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id",user_id);
        params.put("trans_id",trans_id);
        params.put("trans_name",trans_image_name);

        CustomJsonRequest jsonObjReq = new CustomJsonRequest(Request.Method.POST,
               URLs.URL_ADD_TRANSACTION, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("status");
                    if (status) {

                        Toast.makeText(UploadScreenshotActivity.this,""+response.getString("message"),Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                        saveInfoIntoDatabase(user_id,getpoints,getstatus);
                        Intent intent=new Intent(ctx,HomeActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(ctx,"Something went wrong",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    loadingBar.dismiss();
                    Toast.makeText(ctx,""+e.getMessage() ,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();

                    Toast.makeText(ctx,""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
