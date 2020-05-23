package in.games.OnlineMatka.Adapter;

public class details {

//   // updateWalletAmount(id,up_amt);
//
//
//
//
//
//
//
//
//    public static void setBetType(Context context, Dialog dialog, TextView txtOpen, TextView txtClose, String m_id, final Button btnType, LoadingBar progressDialog)
//    {
//
//
//
//        dialog=new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_bettype);
//
//
//
//
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        txtOpen=(TextView)dialog.findViewById(R.id.typeOpening);
//        txtClose=(TextView)dialog.findViewById(R.id.typeClosing);
//
//        //openBetTypeDialog();
//        setData(txtOpen,txtClose,m_id,progressDialog,context);
//
//        final Dialog finalDialog = dialog;
//        final TextView finalTxtOpen = txtOpen;
//        txtOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtOpen.getText().toString());
//               finalDialog.dismiss();
//            }
//        });
//
//        final TextView finalTxtClose = txtClose;
//        final Dialog finalDialog1 = dialog;
//        txtClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtClose.getText().toString());
//                finalDialog1.dismiss();
//            }
//        });
//    }
//
//
//
//
//    //Function for currrent date with btnType
//
//    public static void setBetTypeTo(Context context, Dialog dialog, final String s_time, final String e_time, final TextView txt_timer, TextView txtOpen, TextView txtClose, String m_id, final Button btnType,LoadingBar progressDialog, final String c_date)
//    {
//        dialog=new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_bettype);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        txtOpen=(TextView)dialog.findViewById(R.id.typeOpening);
//        txtClose=(TextView)dialog.findViewById(R.id.typeClosing);
//
//        //openBetTypeDialog();
//        setDataTo(txtOpen,txtClose,m_id,progressDialog,context,c_date);
//
//        final Dialog finalDialog = dialog;
//        final TextView finalTxtOpen = txtOpen;
//        txtOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtOpen.getText().toString());
//               startTimer(s_time,txt_timer,"open");
//                finalDialog.dismiss();
//            }
//        });
//
//        final TextView finalTxtClose = txtClose;
//        final Dialog finalDialog1 = dialog;
//        txtClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtClose.getText().toString());
//                startTimer(e_time,txt_timer,"close");
//                finalDialog1.dismiss();
//            }
//        });
//    }
//    public static void setBetTypeToo(Context context, Dialog dialog, final String s_time, final String e_time, final TextView txt_timer, final CountDownTimer timer, TextView txtOpen, TextView txtClose, String m_id, final Button btnType, LoadingBar progressDialog, final String c_date)
//    {
//        dialog=new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_bettype);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        txtOpen=(TextView)dialog.findViewById(R.id.typeOpening);
//        txtClose=(TextView)dialog.findViewById(R.id.typeClosing);
//
//        //openBetTypeDialog();
//        setDataTo(txtOpen,txtClose,m_id,progressDialog,context,c_date);
//
//        final Dialog finalDialog = dialog;
//        final TextView finalTxtOpen = txtOpen;
//        txtOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtOpen.getText().toString());
//
//                finalDialog.dismiss();
//            }
//        });
//
//        final TextView finalTxtClose = txtClose;
//        final Dialog finalDialog1 = dialog;
//        txtClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtClose.getText().toString());
//
//                finalDialog1.dismiss();
//            }
//        });
//    }
//
//
//
//
//
//
//    //userid, asd, DoublePanaActivity.this, jsonArray, URLs.Url_data_insert, game_id, progressDialog
////    public static void updateWalletAmount(final Context context,final JSONArray jsonArray,final LoadingBar progressDialog,final String matka_name,final String m_id )
////    {
////        final String data=String.valueOf(jsonArray);
////        progressDialog.show();
////        RequestQueue requestQueue_insert=null;
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.URL_INSERT_DATA, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////                try
////                {
////                    Log.d("insert_data",response.toString());
////                    JSONObject jsonObject=new JSONObject(response);
////                    // Toast.makeText(context,""+response,Toast.LENGTH_LONG).show();
////                    final String status=jsonObject.getString("status");
////                    progressDialog.dismiss();
////                    if(status.equals("success"))
////                    {
////
////                        //updateWalletAmount(id,amt,context);
////                        Intent intent=new Intent(context,GameActivity.class);
////                        intent.putExtra("matkaName",matka_name);
////                        intent.putExtra("m_id",m_id);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////
////                        context.startActivity(intent);
////                        Toast.makeText(context,"successfulll..",Toast.LENGTH_LONG).show();
////                    }
////                    else if(status.equals("failed"))
////                    {
////                        String sd=status.toString();
////                        errorMessageDialog(context,sd.toString());
////                        // Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
////                    }
////                    else if(status.equals("timeout"))
////                    {
////
////                        final Dialog myDialog=new Dialog(context);
////                        myDialog.setContentView(R.layout.dialog_error_message_dialog);
////                        TextView txtmessage=(TextView)myDialog.findViewById(R.id.txtmessage);
////                        Button btnOK=(Button) myDialog.findViewById(R.id.btnOK);
////
////                        txtmessage.setText("TimeOut");
////                        btnOK.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////
////                                myDialog.dismiss();
////
////                                String sd=status.toString();
////                                //errorMessageDialog(context,sd.toString());
////                                Intent intent=new Intent(context,HomeActivity.class);
////                                context.startActivity(intent);
////                            }
////                        });
////
////                        myDialog.show();
//////                        String sd=status.toString();
//////                        errorMessageDialog(context,sd.toString());
//////                        Intent intent=new Intent(context,HomeActivity.class);
//////                        context.startActivity(intent);
////
////
////                    }
////
////
////
////
////                }
////                catch (Exception ex)
////                {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
////                }
////
////
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                progressDialog.dismiss();
////                Toast.makeText(context,""+error.getMessage(),Toast.LENGTH_LONG).show();
////
////            }
////        })
////        {
////
////            @Override
////            public HashMap<String, String> getParams() {
////                HashMap<String,String> params=new HashMap<String, String>();
////                params.put("data",data);
////                return params;
////            }
////
////
////        };
////
////        requestQueue_insert= Volley.newRequestQueue(context);
////        requestQueue_insert.add(stringRequest);
////
////
////
////
////
////
////
////    }
//
//    public static void updateWalletAmount(final Context context,final JSONArray jsonArray,final LoadingBar progressDialog,final String matka_name,final String m_id )
//    {
//        final String data=String.valueOf(jsonArray);
//        String json_request_tag="json_insert_request";
//        HashMap<String,String> params=new HashMap<String, String>();
//        params.put("data",data);
//
//     //  Toast.makeText(context,""+data,Toast.LENGTH_LONG).show();
//        if(progressDialog.isShowing())
//        {
//            progressDialog.dismiss();
//        }
//        progressDialog.show();
//
//
//        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_INSERT_DATA, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try
//                {
//                    Log.d("insert_data",response.toString());
//                    JSONObject jsonObject=response;
//                    // Toast.makeText(context,""+response.toString(),Toast.LENGTH_LONG).show();
//                    final String status=jsonObject.getString("status");
//                    progressDialog.dismiss();
//                    if(status.equals("success"))
//                    {
//
//                        //updateWalletAmount(id,amt,context);
//                        Intent intent=new Intent(context, GameActivity.class);
//                        intent.putExtra("matkaName",matka_name);
//                        intent.putExtra("m_id",m_id);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                        context.startActivity(intent);
//                        Toast.makeText(context,"Bid Added Successfully.",Toast.LENGTH_LONG).show();
//                    }
//                    else if(status.equals("failed"))
//                    {
//                        String sd=status.toString();
//                        errorMessageDialog(context,sd.toString());
//                        // Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
//                    }
//                    else if(status.equals("timeout"))
//                    {
//
//                        final Dialog myDialog=new Dialog(context);
//                        myDialog.setContentView(R.layout.dialog_error_message_dialog);
//                        TextView txtmessage=(TextView)myDialog.findViewById(R.id.txtmessage);
//                        Button btnOK=(Button) myDialog.findViewById(R.id.btnOK);
//
//                        txtmessage.setText("Biding closed for this date");
//                        btnOK.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                myDialog.dismiss();
//
//                                String sd=status.toString();
//                                //errorMessageDialog(context,sd.toString());
//                                Intent intent=new Intent(context, HomeActivity.class);
//                                context.startActivity(intent);
//                            }
//                        });
//
//                        myDialog.show();
////                        String sd=status.toString();
////                        errorMessageDialog(context,sd.toString());
////                        Intent intent=new Intent(context,HomeActivity.class);
////                        context.startActivity(intent);
//
//
//                    }
//
//
//
//
//                }
//                catch (Exception ex)
//                {
//                    progressDialog.dismiss();
//                    Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Module module=new Module();
//                String msg=module.VolleyErrorMessage(error);
//                errorMessageDialog(context,msg);
//
//
//            }
//        });
//        customJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
//                500000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
//        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
//
//
//
//
//    }
//// public static void updateWalletAmount(final String id,final String amt,final Context context,final JSONArray jsonArray,final String api_url,final String game_id,final ProgressDialog progressDialog)
////    {
////        progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_Wallet_Update, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////                if(response.isEmpty() && response.equals(null))
////                {
////
////                   progressDialog.dismiss();
////                    Toast.makeText(context,"Empty",Toast.LENGTH_LONG).show();
////                    return;
////                }
////                else
////                {
////                    try
////                    {
////                        JSONObject jsonObject=new JSONObject(response);
////                        String status=jsonObject.getString("status");
////                        if(status.equals("success"))
////                        {
////
////                            saveGameDataToDatabase(jsonArray,id,amt,URLs.Url_data_insert,game_id,progressDialog,context);
////                            progressDialog.dismiss();
////                           // Toast.makeText(context,"Updated:",Toast.LENGTH_LONG).show();
////                            return;
////                        }
////                        else
////                        {
////
////                            progressDialog.dismiss();
////                            Toast.makeText(context,"Failed:",Toast.LENGTH_LONG).show();
////                            return;
////                        }
////                    }
////                    catch (Exception ex)
////                    {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Exce:"+ex.getMessage(),Toast.LENGTH_LONG).show();
////                        return;
////                    }
////                }
////
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss();
////                         if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("points",amt);
////                params.put("user_id",id);
////
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////    }
//
////    public static void setWallet_Amount(final TextView txt, final ProgressDialog progressDialog,final String mid, final Context context) {
////     progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_Wallet,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////
////                        if(response.equals(null))
////                        {
////                            progressDialog.dismiss();
////                            Toast.makeText(context,"You have no money in wallet",Toast.LENGTH_LONG).show();
////                            return;
////                        }
////                        else
////                        {
////                            try
////                            {
////
////                                JSONObject jsonObject=new JSONObject(response);
////
////                                String status=jsonObject.getString("status");
////
////                                if(status.equals("success"))
////                                {
////                                    JSONObject object=jsonObject.getJSONObject("message");
////                                    WalletObjects walletObjects=new WalletObjects();
////                                    walletObjects.setUser_id(object.getString("user_id"));
////                                    walletObjects.setWallet_points(object.getString("wallet_points"));
////                                    walletObjects.setWallet_id(object.getString("wallet_id"));
////                                    progressDialog.dismiss();
////                                    txt.setText(walletObjects.getWallet_points());
////                                }
////                                else if(status.equals("failed"))
////                                {
////                                    progressDialog.dismiss();
////                                    txt.setText("0");
////                                    Toast.makeText(context,"You have no points",Toast.LENGTH_LONG).show();
////                                    return;
////                                }
////                                else
////                                {
////                                    progressDialog.dismiss();
////                                    txt.setText("0");
////                                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
////                                    return;
////                                }
////
////
////
////
////
////                            }
////                            catch(Exception ex)
////                            {
////                                progressDialog.dismiss();
////                                Toast.makeText(context,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
////                                return;
////
////                            }
////
////                        }
////
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////
////                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////
////
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("user_id",mid);
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////        };
////
////
////
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////    }
//
//
//
////    public static void saveGameDataToDatabase(final JSONArray jsonArray,final String userid,final String asd,final String api_url,final ProgressDialog progressDialog,final Context context)
////    {
////        progressDialog.show();
////
////        final String data=String.valueOf(jsonArray);
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, api_url, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////                if(response.isEmpty() && response.equals(null))
////                {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"empty",Toast.LENGTH_LONG).show();
////                }
////                else
////                {
////                    try
////                    {
////                        JSONObject jsonObject=new JSONObject(response);
////                        String status=jsonObject.getString("status");
////
////                        if(status.equals("success"))
////                        {
////
////                            updateWalletAmount(userid,asd,context);
////                            // updateWallet(userid,asd);
////                            progressDialog.dismiss();
////                            Toast.makeText(context,"successful..",Toast.LENGTH_LONG).show();
//////                       String id=Prevalent.currentOnlineuser.getId().toString().trim();
//////                       updateWalletAmount(id,amt,OddEvenActivity.this,progressDialog);
////                            Intent intent=new Intent(context, HomeActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            context.startActivity(intent);
////                            ((Activity)context).finish();
////                        }
////                        else
////                        {
////                            progressDialog.dismiss();
////                            Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
////                        }
////
////                    }
////                    catch (Exception ex)
////                    {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
////                    }
////                }
////
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Err"+error.getMessage(),Toast.LENGTH_LONG).show();
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("data",data);
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////    }
//
//    public static void saveGameDataToDatabase(final JSONArray jsonArray,final String userid,final String asd,final String api_url,final String game_id,final LoadingBar progressDialog,final Context context)
//    {
//        //progressDialog.show();
//
//        final String data=String.valueOf(jsonArray);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, api_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if(response.isEmpty() && response.equals(null))
//                {
//                  //  progressDialog.dismiss();
//                    Toast.makeText(context,"empty",Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    try
//                    {
//                        JSONObject jsonObject=new JSONObject(response);
//                        String status=jsonObject.getString("status");
//                        if(status.equals("success"))
//                        {
//                          //updateWalletAmount(id,amt,context);
//                          Intent intent=new Intent(context,HomeActivity.class);
//                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                          context.startActivity(intent);
//                         Toast.makeText(context,"successfulll..",Toast.LENGTH_LONG).show();
//                        }
//                        else if(status.equals("failed"))
//                        {
//                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            String sd=status.toString();
//                            errorMessageDialog(context,sd.toString());
//                        }
//
//
//
//                        //Toast.makeText(context,""+status,Toast.LENGTH_LONG).show();
//
//                       // ActivityCompat.finishAffinity();
////                        if(status.equals("success"))
////                        {
////
////                           // updateWalletAmount(userid,asd,context);
////
////                          //  progressDialog.dismiss();
////                            Toast.makeText(context,"successful.."+status,Toast.LENGTH_LONG).show();
////                            Intent intent=new Intent(context, HomeActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            context.startActivity(intent);
////                            ((Activity)context).finish();
////                        }
////                        else
////                        {
////                            progressDialog.dismiss();
////                          Toast.makeText(context,"success",Toast.LENGTH_LONG).show();
////                            Intent intent=new Intent(context, HomeActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            context.startActivity(intent);
////                            ((Activity)context).finish();
////                        }
//
//                    }
//                    catch (Exception ex)
//                    {
//                   //     progressDialog.dismiss();
//                        Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Module module = new Module();
//                        String msg = module.VolleyErrorMessage(error);
//                        errorMessageDialog(context, msg);
//
//                    }
//                    })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("data",data);
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//    }
//
//
//    // Function for Half And Sangum insert Data and Update Wallet
//
//
//
//    public static void updateWalletAmountSangum(final Context context, final JSONArray jsonArray, final LoadingBar progressDialog, final String matka_name, final String m_id)
//    {
//        final String data=String.valueOf(jsonArray);
//        String json_request_tag="json_sangam_tag";
//        HashMap<String, String> params=new HashMap<String, String>();
//        params.put("data",data);
//        progressDialog.show();
//
//
//        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_INSERT_SANGAM, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try
//                {
//                    JSONObject jsonObject=response;
//                    String status=jsonObject.getString("status");
//
//                    if(status.equals("success"))
//                    {
//
//                        // updateWalletAmount(userid,asd,context);
//
//                          progressDialog.dismiss();
//                        Toast.makeText(context,"Bid Added Successfully.",Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(context, GameActivity.class);
//                        intent.putExtra("matkaName",matka_name);
//                        intent.putExtra("m_id",m_id);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        context.startActivity(intent);
//                        ((Activity)context).finish();
//                    }
//                    else if(status.equals("timeout"))
//                    {
//                        progressDialog.dismiss();
//                        errorMessageDialog(context,"Biding closed for this date");
//                    }
//                    else
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
////                            Intent intent=new Intent(context, HomeActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            context.startActivity(intent);
////                            ((Activity)context).finish();
//                    }
//
//                }
//                catch (Exception ex)
//                {
//                         progressDialog.dismiss();
//                    Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                progressDialog.dismiss();
//                Module module=new Module();
//                String msg=module.VolleyErrorMessage(error);
//                errorMessageDialog(context,msg);
//
//
//            }
//        });
//        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
//
//
//
//    }
//
//    public static void saveGameDataToDatabaseSangum(final JSONArray jsonArray,final String userid,final String asd,final String api_url,final String game_id,final LoadingBar progressDialog,final Context context)
//    {
//        //progressDialog.show();
//
//        final String data=String.valueOf(jsonArray);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, api_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if(response.isEmpty() && response.equals(null))
//                {
//                    //  progressDialog.dismiss();
//                    Toast.makeText(context,"empty",Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    try
//                    {
//                        JSONObject jsonObject=new JSONObject(response);
//                        String status=jsonObject.getString("status");
//
//                        if(status.equals("success"))
//                        {
//
//                            // updateWalletAmount(userid,asd,context);
//
//                            //  progressDialog.dismiss();
//                            Toast.makeText(context,"successful..",Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(context, HomeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            context.startActivity(intent);
//                            ((Activity)context).finish();
//                        }
//                        else
//                        {
//                            progressDialog.dismiss();
//                            Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
////                            Intent intent=new Intent(context, HomeActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            context.startActivity(intent);
////                            ((Activity)context).finish();
//                        }
//
//                    }
//                    catch (Exception ex)
//                    {
//                        //     progressDialog.dismiss();
//                        Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Module module=new Module();
//                        String msg=module.VolleyErrorMessage(error);
//                        errorMessageDialog(context,msg);
//                    }
//                })  {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("data",data);
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//    }
//
//
//    private static void setData(final TextView txtOpen, final TextView txtClose, final String m_id, final LoadingBar progressDialog, final Context context) {
//        progressDialog.show();
//
//        String json_tag="json_setdate";
//        HashMap<String,String> params=new HashMap<>();
//        params.put("id",m_id);
//
//        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.Url_matka_with_id, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try
//                {
//                    JSONObject jsonObject=response;
//                    String status=jsonObject.getString("status");
//                    if(status.equals("success"))
//                    {
//                        JSONObject object=jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects=new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start=matkasObjects.getBid_start_time();
//                        Date current_time=new Date();
//                        SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
//                        //Date time_start=sformat.parse(bid_start);
//                        String c_date=sformat.format(current_time);
//
//                        // int flag=time_start.compareTo(current_time);
//                        //txtOpen.setText(""+d+"\n"+current_time);
//                        // txtClose.setText(current_time.toString());
//
//                        String startTimeSplliting[]=bid_start.split(":");
//                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
//                        int s_min=Integer.parseInt(startTimeSplliting[1]);
//                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
//                        String currentTimeSplitting[]=c_date.split(":");
//                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
//                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
//                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);
//
//                        int flag=0;
//                        if(s_hours>c_hours)
//                        {
//                            flag=1;
//                        }
//                        else if(s_hours==c_hours)
//                        {
//                            if(s_min>c_min)
//                            {
//                                flag=1;
//                            }
//                            else if(s_min==c_min)
//                            {
//                                if(s_sec>c_sec)
//                                {
//                                    flag=1;
//                                }
//                                else
//                                {
//                                    flag=0;
//                                }
//                                flag=0;
//                            }
//                            else
//                            {
//                                flag=0;
//                            }
//                        }
//                        else
//                        {
//                            flag=0;
//                        }
//
//
//
//                        if(flag==1)
//                        {
//                            txtOpen.setText("Open Bet");
//                            txtClose.setText("Close Bet");
//                            //  txtOpen.setText("Open Bet");
////                            txtOpen.setVisibility(View.GONE);
////                            txtClose.setText("Close Bet");
//                        }
//                        else if(flag==0)
//                        {
//                            txtOpen.setVisibility(View.GONE);
//                            txtClose.setText("Close Bet");
//
//                        }
//
////String data="Hours : "+s_hours+"\n min : "+s_min+"\n sec : "+s_sec+"\n hours :"+c_hours+"\n minn :"+c_min+"\n seccc :"+c_sec;
//
//                        progressDialog.dismiss();
//                        // Toast.makeText(DoublePanaActivity.this,data,Toast.LENGTH_LONG).show();
//
//
//                    }
//                    else
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"Something ",Toast.LENGTH_LONG).show();
//
//                    }
//                }
//                catch(Exception ex)
//                {
//                    progressDialog.dismiss();
//                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//
//                Module module=new Module();
//                String msg=module.VolleyErrorMessage(error);
//                errorMessageDialog(context,msg);
//
//            }
//        });
//        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
//
//    }
//
//
////    private static void setDataTo(final TextView txtOpen, final TextView txtClose, final String m_id, final ProgressDialog progressDialog, final Context context,final String date_cuurent) {
////        progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////                try
////                {
////                    JSONObject jsonObject=new JSONObject(response);
////                    String status=jsonObject.getString("status");
////                    if(status.equals("success"))
////                    {
////                        JSONObject object=jsonObject.getJSONObject("data");
////                        MatkasObjects matkasObjects=new MatkasObjects();
////                        matkasObjects.setId(jsonObject.getString("id"));
////                        matkasObjects.setName(jsonObject.getString("name"));
////                        matkasObjects.setStart_time(jsonObject.getString("start_time"));
////                        matkasObjects.setEnd_time(jsonObject.getString("end_time"));
////                        matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
////                        matkasObjects.setNumber(jsonObject.getString("number"));
////                        matkasObjects.setEnd_num(jsonObject.getString("end_num"));
////                        matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
////                        matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
////                        matkasObjects.setCreated_at(jsonObject.getString("created_at"));
////                        matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
////                        matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
////                        matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
////                        matkasObjects.setStatus(jsonObject.getString("status"));
////
////
////                        String dt=new SimpleDateFormat("EEEE").format(new Date());
////                        String bid_start="";
////                        if(dt.equals("Saturday"))
////                        {
////                           bid_start=matkasObjects.getSat_start_time();
////                        }
////                        else
////                        {
////                            bid_start=matkasObjects.getBid_start_time();
////
////                        }
////                    if(dt.equals("Sunday"))
////                        {
////                           bid_start=matkasObjects.getStart_time();
////                        }
////                        else
////                        {
////                            bid_start=matkasObjects.getBid_start_time();
////
////                        }
////
////
////                        Date current_time=new Date();
////                        SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
////                        //Date time_start=sformat.parse(bid_start);
////                        String c_date=sformat.format(current_time);
////
////                        // int flag=time_start.compareTo(current_time);
////                        //txtOpen.setText(""+d+"\n"+current_time);
////                        // txtClose.setText(current_time.toString());
////
////                        String startTimeSplliting[]=bid_start.split(":");
////                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
////                        int s_min=Integer.parseInt(startTimeSplliting[1]);
////                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
////                        String currentTimeSplitting[]=c_date.split(":");
////                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
////                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
////                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);
////
////                        int flag=0;
////                        if(s_hours>c_hours)
////                        {
////                            flag=1;
////                        }
////                        else if(s_hours==c_hours)
////                        {
////                            if(s_min>c_min)
////                            {
////                                flag=1;
////                            }
////                            else if(s_min==c_min)
////                            {
////                                if(s_sec>c_sec)
////                                {
////                                    flag=1;
////                                }
////                                else
////                                {
////                                    flag=0;
////                                }
////                                flag=0;
////                            }
////                            else
////                            {
////                                flag=0;
////                            }
////                        }
////                        else
////                        {
////                            flag=0;
////                        }
////
////SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
////
////                        Date d1=simpleDateFormat.parse(date_cuurent);
////                        Date date=new Date();
////                        SimpleDateFormat dateFormat1=new SimpleDateFormat("dd/MM/yyyy");
////                        String s1=dateFormat1.format(date);
////                        Date d2=simpleDateFormat.parse(s1);
//////Toast.makeText(context,"d1 :-  "+d1.toString()+"\n d2   :-"+d2.toString(),Toast.LENGTH_LONG).show();
////                        if(d1.compareTo(d2)==0)
////                        {
////                            if(flag==1)
////                            {
////                                txtOpen.setText("Open Bet");
////                                txtClose.setText("Close Bet");
////                                //  txtOpen.setText("Open Bet");
//////                            txtOpen.setVisibility(View.GONE);
//////                            txtClose.setText("Close Bet");
////                            }
////                            else if(flag==0)
////                            {
////                                txtOpen.setVisibility(View.GONE);
////                                txtClose.setText("Close Bet");
////
////                            }
////
////                        }
////                        else
////                        {
////                            txtOpen.setText("Open Bet");
////                            txtClose.setText("Close Bet");
////                        }
////
////
//////String data="Hours : "+s_hours+"\n min : "+s_min+"\n sec : "+s_sec+"\n hours :"+c_hours+"\n minn :"+c_min+"\n seccc :"+c_sec;
////
////                        progressDialog.dismiss();
////                        // Toast.makeText(DoublePanaActivity.this,data,Toast.LENGTH_LONG).show();
////
////
////                    }
////                    else
////                    {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Something ",Toast.LENGTH_LONG).show();
////
////                    }
////                }
////                catch(Exception ex)
////                {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();
////
////                }
////
////
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("id",m_id);
////
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////    }
//
//
//
//
//
//    public static String comapareTimeBids(String bid_start,String c_date)
//    {
//        String ret;
//        String startTimeSplliting[]=bid_start.split(":");
//        int s_hours=Integer.parseInt(startTimeSplliting[0]);
//        int s_min=Integer.parseInt(startTimeSplliting[1]);
//        int s_sec=Integer.parseInt(startTimeSplliting[2]);
//        String currentTimeSplitting[]=c_date.split(":");
//        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
//        int c_min=Integer.parseInt(currentTimeSplitting[1]);
//        int c_sec=Integer.parseInt(currentTimeSplitting[2]);
//
//        int flag=0;
//        if(s_hours>c_hours)
//        {
//            flag=1;
//        }
//        else if(s_hours==c_hours)
//        {
//            if(s_min>c_min)
//            {
//                flag=1;
//            }
//            else if(s_min==c_min)
//            {
//                if(s_sec>c_sec)
//                {
//                    flag=1;
//                }
//                else
//                {
//                    flag=0;
//                }
//                flag=0;
//            }
//            else
//            {
//                flag=0;
//            }
//        }
//        else
//        {
//            flag=0;
//        }
//
//        if(flag==0)
//        {
//            ret="before";
//        }
//        else
//        {
//            ret="after";
//        }
//        return ret;
//    }
//
////    public static void getDateData(final Context context, final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate, final ProgressDialog progressDialog)
////    {
////
////        progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    String status = jsonObject.getString("status");
////                    if (status.equals("success")) {
////                        JSONObject object = jsonObject.getJSONObject("data");
////                        MatkasObjects matkasObjects = new MatkasObjects();
////                        matkasObjects.setId(object.getString("id"));
////                        matkasObjects.setName(object.getString("name"));
////                        matkasObjects.setStart_time(object.getString("start_time"));
////                        matkasObjects.setEnd_time(object.getString("end_time"));
////                        matkasObjects.setStarting_num(object.getString("starting_num"));
////                        matkasObjects.setNumber(object.getString("number"));
////                        matkasObjects.setEnd_num(object.getString("end_num"));
////                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
////                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
////                        matkasObjects.setSat_start_time(object.getString("sat_start_time"));
////                        matkasObjects.setSat_end_time(object.getString("sat_end_time"));
////                        matkasObjects.setCreated_at(object.getString("created_at"));
////                        matkasObjects.setUpdated_at(object.getString("updated_at"));
////                        matkasObjects.setStatus(object.getString("status"));
////
////                        String bid_start="";
////                        String bid_end="";
////                        String dt=new SimpleDateFormat("EEEE").format(new Date());
////
////                        if(dt.equals("Wednesday"))
////                        {
////                            String st_time=matkasObjects.getStart_time();
////                            if(st_time.equals("") && st_time.equals("null"))
////                            {
////
////                                Date c_dat=new Date();
////                                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                                String s_dt=dateFormat.format(c_dat);
////                                String n_dt= getNextDate(s_dt);
////                                String a_dt= getNextDate(n_dt);
////                                txtCurrentDate.setText(s_dt+" Bet Close");
////                                txtNextDate.setText(n_dt+" Bet Open");
////                                txtAfterNextDate.setText(a_dt+" Bet Open");
////
////                                //  Toast.makeText(context,"Somtehin",Toast.LENGTH_LONG).show();
////                            }
////                            else
////                            {
//////                                Toast.makeText(context,""+matkasObjects.getSat_start_time(),Toast.LENGTH_LONG).show();
////
////                                bid_start = matkasObjects.getStart_time();
////                                bid_end=matkasObjects.getEnd_time().toString();
////                            }
////                        }
////                        else if(dt.equals("Saturday"))
////                        {
////                            String st_time=matkasObjects.getSat_start_time();
////                            if(st_time.equals("") && st_time.equals("null"))
////                            {
////
////                                Date c_dat=new Date();
////                                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                                String s_dt=dateFormat.format(c_dat);
////                                String n_dt= getNextDate(s_dt);
////                                String a_dt= getNextDate(n_dt);
////                                txtCurrentDate.setText(s_dt+" Bet Close");
////                                txtNextDate.setText(n_dt+" Bet Open");
////                                txtAfterNextDate.setText(a_dt+" Bet Open");
////
////                                //  Toast.makeText(context,"Somtehin",Toast.LENGTH_LONG).show();
////                            }
////                            else
////                            {
//////                                Toast.makeText(context,""+matkasObjects.getSat_start_time(),Toast.LENGTH_LONG).show();
////
////                                bid_start = matkasObjects.getSat_start_time();
////                               bid_end=matkasObjects.getSat_end_time().toString();
////                            }
////                        }
////                        else
////                        {
////                            bid_start = matkasObjects.getBid_start_time();
////                            bid_end=matkasObjects.getBid_end_time().toString();
////                        }
////
////
////                        String time1 = bid_start.toString();
////                        String time2 = bid_end.toString();
////
////                        Date cdate=new Date();
////
////
////
////                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
////                        String time3=format.format(cdate);
////                        Date date1 = null;
////                        Date date2=null;
////                        Date date3=null;
////                        try {
////                            date1 = format.parse(time1);
////                            date2 = format.parse(time2);
////                            date3=format.parse(time3);
////                        } catch (ParseException e1) {
////                            e1.printStackTrace();
////                        }
////
////                        long difference = date3.getTime() - date1.getTime();
////                        long as=(difference/1000)/60;
////
////                        long diff_close=date3.getTime()-date2.getTime();
////                        long c=(diff_close/1000)/60;
////
////                        Date c_dat=new Date();
////                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                        String s_dt=dateFormat.format(c_dat);
////                        String n_dt= getNextDate(s_dt);
////                        String a_dt= getNextDate(n_dt);
////                        if(as<0)
////                        {
////                            progressDialog.dismiss();
////                            //btn.setText(s_dt+" Bet Open");
////                            txtCurrentDate.setText(s_dt+" Bet Open");
////                            txtNextDate.setText(n_dt+" Bet Open");
////                            txtAfterNextDate.setText(a_dt+" Bet Open");
////
////                            //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
////                        }
////                        else if(c>0)
////                        {progressDialog.dismiss();
////                            txtCurrentDate.setText(s_dt+" Bet Close");
////                            txtNextDate.setText(n_dt+" Bet Open");
////                            txtAfterNextDate.setText(a_dt+" Bet Open");
////
////                            // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
////                        }
////                        else
////                        {
////                            progressDialog.dismiss();
////                            //btn.setText(s_dt+" Bet Open");
////                            txtCurrentDate.setText(s_dt+" Bet Open");
////                            txtNextDate.setText(n_dt+" Bet Open");
////                            txtAfterNextDate.setText(a_dt+" Bet Open");
////                        }
////
////
////                    } else {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Something erong",Toast.LENGTH_LONG).show();
////
////
////                    }
////                } catch (Exception ex) {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"Something erong"+ex.getMessage(),Toast.LENGTH_LONG).show();
////
////                }
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("id",m_id);
////
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////    }
////
//
//
//
//    // Function for onStart set betGameType Data
//
//
////    public static void setBetDateDay(final Context context, final String m_id, final Button btnGameType, final ProgressDialog progressDialog) {
////
////
////
////        progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    String status = jsonObject.getString("status");
////                    if (status.equals("success")) {
////                        JSONObject object = jsonObject.getJSONObject("data");
////                        MatkasObjects matkasObjects = new MatkasObjects();
////                        matkasObjects.setId(jsonObject.getString("id"));
////                        matkasObjects.setName(jsonObject.getString("name"));
////                        matkasObjects.setStart_time(jsonObject.getString("start_time"));
////                        matkasObjects.setEnd_time(jsonObject.getString("end_time"));
////                        matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
////                        matkasObjects.setNumber(jsonObject.getString("number"));
////                        matkasObjects.setEnd_num(jsonObject.getString("end_num"));
////                        matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
////                        matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
////                        matkasObjects.setCreated_at(jsonObject.getString("created_at"));
////                        matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
////                        matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
////                        matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
////                        matkasObjects.setStatus(jsonObject.getString("status"));
////
////                        String dt=new SimpleDateFormat("EEEE").format(new Date());
////                        if(dt.equals("Wednesday"))
////                        {
////                            Date c_dat=new Date();
////                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                            String s_dt=dateFormat.format(c_dat);
////                            String n_dt= getNextDate(s_dt);
////                            String a_dt= getNextDate(n_dt);
////
////                            progressDialog.dismiss();
////                            btnGameType.setText(s_dt+" Bet Close");
////                        }
////
////                        else if(dt.equals("Thursday"))
////                        {
////                            Date c_dat=new Date();
////                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                            String s_dt=dateFormat.format(c_dat);
////                            String n_dt= getNextDate(s_dt);
////                            String a_dt= getNextDate(n_dt);
////
////                            progressDialog.dismiss();
////                            btnGameType.setText(s_dt+" Bet Close");
////                        }
////                        else
////                        {
////                            String bid_start = matkasObjects.getBid_start_time();
////                            String bid_end=matkasObjects.getBid_end_time().toString();
////
////
////                            String time1 = bid_start.toString();
////                            String time2 = bid_end.toString();
////
////                            Date cdate=new Date();
////
////
////
////                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
////                            String time3=format.format(cdate);
////                            Date date1 = null;
////                            Date date2=null;
////                            Date date3=null;
////                            try {
////                                date1 = format.parse(time1);
////                                date2 = format.parse(time2);
////                                date3=format.parse(time3);
////                            } catch (ParseException e1) {
////                                e1.printStackTrace();
////                            }
////
////                            long difference = date3.getTime() - date1.getTime();
////                            long as=(difference/1000)/60;
////
////                            long diff_close=date3.getTime()-date2.getTime();
////                            long c=(diff_close/1000)/60;
////
////                            Date c_dat=new Date();
////                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                            String s_dt=dateFormat.format(c_dat);
////                            String n_dt= getNextDate(s_dt);
////                            String a_dt= getNextDate(n_dt);
////
////                            if(c>0)
////                            {progressDialog.dismiss();
////                                btnGameType.setText(s_dt+" Bet Close");
////
////
////                                // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
////                            }
////                            else
////                            {
////                                progressDialog.dismiss();
////                                btnGameType.setText(s_dt+" Bet Open");
////
////                            }
////
////
////                        }
////
////
////                    } else {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Something wrong",Toast.LENGTH_LONG).show();
////
////
////                    }
////                } catch (Exception ex) {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"Something wrong"+ex.getMessage(),Toast.LENGTH_LONG).show();
////
////                }
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("id",m_id);
////
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////
////    }
//
//
//
//
//////New Demo function for onStart
////
////    public static void setBetDemoDay(final Context context, final String m_id, final Button btnGameType, final ProgressDialog progressDialog) {
////
////
////
////        progressDialog.show();
////
////        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////
////
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    String status = jsonObject.getString("status");
////                    if (status.equals("success")) {
////                        JSONObject object = jsonObject.getJSONObject("data");
////                        MatkasObjects matkasObjects = new MatkasObjects();
////                        matkasObjects.setId(jsonObject.getString("id"));
////                        matkasObjects.setName(jsonObject.getString("name"));
////                        matkasObjects.setStart_time(jsonObject.getString("start_time"));
////                        matkasObjects.setEnd_time(jsonObject.getString("end_time"));
////                        matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
////                        matkasObjects.setNumber(jsonObject.getString("number"));
////                        matkasObjects.setEnd_num(jsonObject.getString("end_num"));
////                        matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
////                        matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
////                        matkasObjects.setCreated_at(jsonObject.getString("created_at"));
////                        matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
////                        matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
////                        matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
////                        matkasObjects.setStatus(jsonObject.getString("status"));
////
////                        String dt=new SimpleDateFormat("EEEE").format(new Date());
////                        String bid_start = "";
////                        String bid_end="";
//////                        String bid_start = matkasObjects.getBid_start_time();
//////                        String bid_end=matkasObjects.getBid_end_time().toString();
////
////                        if(dt.equals("Thursday"))
////                        {
////                            bid_start=matkasObjects.getStart_time();
////                            bid_end=matkasObjects.getEnd_time();
////                        }
////                        else if(dt.equals("Friday"))
////                        {
////                            bid_start=matkasObjects.getSat_start_time();
////                            bid_end=matkasObjects.getSat_end_time();
////
////                        }
////                        else
////                        {
////                            bid_start=matkasObjects.getBid_start_time();
////                            bid_end=matkasObjects.getBid_end_time();
////
////                        }
////
////
////                            String time1 = bid_start.toString();
////                            String time2 = bid_end.toString();
////
////                            Date cdate=new Date();
////
////
////
////                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
////                            String time3=format.format(cdate);
////                            Date date1 = null;
////                            Date date2=null;
////                            Date date3=null;
////                            try {
////                                date1 = format.parse(time1);
////                                date2 = format.parse(time2);
////                                date3=format.parse(time3);
////                            } catch (ParseException e1) {
////                                e1.printStackTrace();
////                            }
////
////                            long difference = date3.getTime() - date1.getTime();
////                            long as=(difference/1000)/60;
////
////                            long diff_close=date3.getTime()-date2.getTime();
////                            long c=(diff_close/1000)/60;
////
////                            Date c_dat=new Date();
////                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
////                            String s_dt=dateFormat.format(c_dat);
////                            String n_dt= getNextDate(s_dt);
////                            String a_dt= getNextDate(n_dt);
////
////                            if(c>0)
////                            {progressDialog.dismiss();
////                                btnGameType.setText(s_dt+" Bet Close");
////
////
////                                // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
////                            }
////                            else
////                            {
////                                progressDialog.dismiss();
////                                btnGameType.setText(s_dt+" Bet Open");
////
////                            }
////
////
//////                        }
////
////
////                    } else {
////                        progressDialog.dismiss();
////                        Toast.makeText(context,"Something wrong",Toast.LENGTH_LONG).show();
////
////
////                    }
////                } catch (Exception ex) {
////                    progressDialog.dismiss();
////                    Toast.makeText(context,"Some"+ex.getMessage(),Toast.LENGTH_LONG).show();
////
////                }
////            }
////        },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
////                            ConnectivityManager cm = (ConnectivityManager)context.getApplicationContext()
////                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
////                            NetworkInfo activeNetwork = null;
////                            if (cm != null) {
////                                activeNetwork = cm.getActiveNetworkInfo();
////                            }
////                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
////                                Toast.makeText(context, "Server is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            } else {
////                                Toast.makeText(context, "Your device is not connected to internet.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("connection"))){
////                            Toast.makeText(context, "Your device is not connected to internet.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof MalformedURLException){
////                            Toast.makeText(context, "Bad Request.", Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
////                                || error.getCause() instanceof JSONException
////                                || error.getCause() instanceof XmlPullParserException){
////                            Toast.makeText(context, "Parse Error (because of invalid json or xml).",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error.getCause() instanceof OutOfMemoryError){
////                            Toast.makeText(context, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof AuthFailureError){
////                            Toast.makeText(context, "server couldn't find the authenticated request.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
////                            Toast.makeText(context, "Server is not responding.", Toast.LENGTH_SHORT).show();
////                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
////                                || error.getCause() instanceof ConnectTimeoutException
////                                || error.getCause() instanceof SocketException
////                                || (error.getCause().getMessage() != null
////                                && error.getCause().getMessage().contains("Connection timed out"))) {
////                            Toast.makeText(context, "Connection timeout error",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(context, "An unknown error occurred.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////
////                params.put("id",m_id);
////
////                // params.put("phonepay",phonepaynumber);
////
////
////                return params;
////            }
////
////        };
////
////        RequestQueue requestQueue= Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////
////
////
////    }
////
////
////
//
//
//
//
//    public static void getMatkaDateData(final Context context, final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate, final LoadingBar progressDialog)
//    {
//
//        progressDialog.show();
//
//        String json_tag="json_matka_id";
//        HashMap<String,String> params=new HashMap<String, String>();
//        params.put("id",m_id);
//
//        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONObject jsonObject = response;
//                    String status = jsonObject.getString("status");
//                    if (status.equals("success")) {
//                        JSONObject object = jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects = new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setEnd_time(object.getString("end_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setSat_start_time(object.getString("sat_start_time"));
//                        matkasObjects.setSat_end_time(object.getString("sat_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start = "";
//                        String bid_end = "";
//                        String dt = new SimpleDateFormat("EEEE").format(new Date());
//
//                        String st_time = "";
//                        String st_time1 = "";
//                        String st_time2 = "";
//
//                        if (dt.equals("Saturday")) {
//                            st_time = matkasObjects.getSat_start_time();
//                        } else if (dt.equals("Sunday")) {
//                            st_time = matkasObjects.getStart_time();
//                        } else {
//                            st_time = matkasObjects.getBid_start_time();
//                        }
//
//                        String dt1 = getNextDay(dt);
//                        if (dt1.equals("Saturday")) {
//                            st_time1 = matkasObjects.getSat_start_time();
//                        } else if (dt1.equals("Sunday")) {
//                            st_time1 = matkasObjects.getStart_time();
//                        } else {
//                            st_time1 = matkasObjects.getBid_start_time();
//                        }
//
//                        String dt2 = getNextDay(dt1);
//                        if (dt2.equals("Saturday")) {
//                            st_time2 = matkasObjects.getSat_start_time();
//                        } else if (dt2.equals("Sunday")) {
//                            st_time2 = matkasObjects.getStart_time();
//                        } else {
//                            st_time2 = matkasObjects.getBid_start_time();
//                        }
//
//
//                        String nd="";
//                        String and="";
//                        String cd="";
//
//
//                        if (st_time.equals("") && st_time.equals("null")) {
//
//                            txtCurrentDate.setText(dt + " Bet Close");
//                            cd="c";
//
//                            if (st_time1.equals("") && st_time1.equals("null")) {
//                                txtNextDate.setText(dt1 + " Bet Close");
//                                nd="c";
//                            } else {
//                                txtNextDate.setText(dt1 + " Bet Open");
//                                nd="o";
//                            }
//                            if (st_time2.equals("") && st_time2.equals("null")) {
//                                txtAfterNextDate.setText(dt2 + " Bet Close");
//                                and="c";
//                            } else {
//                                txtAfterNextDate.setText(dt2 + " Bet Open");
//                                and="o";
//                            }
//
//
//                            //  Toast.makeText(context,"Somtehin",Toast.LENGTH_LONG).show();
//                        } else {
////                                Toast.makeText(context,""+matkasObjects.getSat_start_time(),Toast.LENGTH_LONG).show();
//
//                            bid_start = st_time;
//                            bid_end = matkasObjects.getBid_end_time().toString();
//
//                            String time1 = bid_start.toString();
//                            String time2 = bid_end.toString();
//
//                            Date cdate = new Date();
//
//
//                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//                            String time3 = format.format(cdate);
//                            Date date1 = null;
//                            Date date2 = null;
//                            Date date3 = null;
//                            try {
//                                date1 = format.parse(time1);
//                                date2 = format.parse(time2);
//                                date3 = format.parse(time3);
//                            } catch (ParseException e1) {
//                                e1.printStackTrace();
//                            }
//
//                            long difference = date3.getTime() - date1.getTime();
//                            long as = (difference / 1000) / 60;
//
//                            long diff_close = date3.getTime() - date2.getTime();
//                            long c = (diff_close / 1000) / 60;
//
//                            Date c_dat = new Date();
//                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy EEEE");
//                            String s_dt = dateFormat2.format(c_dat);
//                            String n_dt = getNextDate(s_dt);
//                            String a_dt = getNextDate(n_dt);
//                            if (as < 0) {
//                                progressDialog.dismiss();
//                                //btn.setText(s_dt+" Bet Open");
//                                txtCurrentDate.setText(s_dt + " Bet Open");
//                                if(nd.equals("c"))
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Close");
//
//                                }
//                                else
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Open");
//
//                                }
//
//                                if(and.equals("c"))
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Close");
//
//                                }
//                                else
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Open");
//
//                                }
//
//                                //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
//                            } else if (c > 0) {
//                                progressDialog.dismiss();
//                                txtCurrentDate.setText(dt + " Bet Close");
//                                if(nd.equals("c"))
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Close");
//
//                                }
//                                else
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Open");
//
//                                }
//
//                                if(and.equals("c"))
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Close");
//
//                                }
//                                else
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Open");
//
//                                }
//
//                                // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
//                            } else {
//                                progressDialog.dismiss();
//                                //btn.setText(s_dt+" Bet Open");
//                                txtCurrentDate.setText(dt + " Bet Open");
//                                if(nd.equals("c"))
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Close");
//
//                                }
//                                else if(nd.equals("o"))
//                                {
//                                    txtNextDate.setText(n_dt + " Bet Open");
//
//                                }
//
//                                if(and.equals("c"))
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Close");
//
//                                }
//                                else if(and.equals("o"))
//                                {
//                                    txtAfterNextDate.setText(a_dt + " Bet Open");
//
//                                }
//
//
//                            }
//                        }
//
//  }
//                    } catch(Exception ex){
//                        progressDialog.dismiss();
//                        Toast.makeText(context, "Something erong" + ex.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Module module=new Module();
//                String msg=module.VolleyErrorMessage(error);
//                errorMessageDialog(context,msg);
//
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
//
//
//
//            }
//
//
//

}
