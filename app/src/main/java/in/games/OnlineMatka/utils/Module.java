package in.games.OnlineMatka.utils;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 29,November,2019
 */
public class Module {

    public static void startTimer(String time, final TextView txt_timer, final String type) {

        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            long diff_e_s = e_time.getTime() - s_time.getTime();
            Log.e("diff", "" + diff_e_s);
//
            new CountDownTimer(diff_e_s, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                    if (type.equals("open")) {
                        txt_timer.setText("Bid Opens in :" + text);
                    } else if (type.equals("close")) {
                        txt_timer.setText("Bid closes in :" + text);
                    }


                }

                @Override
                public void onFinish() {


                }
            }.start();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void startTimerCounter(CountDownTimer countDownTimer, String time, final TextView txt_timer, final String type, final String c_type) {

        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            final Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            long diff_e_s = e_time.getTime() - s_time.getTime();
            Log.e("diff", "" + diff_e_s);
            countDownTimer = new CountDownTimer(diff_e_s, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);

//                        if (type.equals("open")) {
//                            txt_timer.setText("Bid Opens in :" + text);
//                        }
//                        if (type.equals("close")) {
                    txt_timer.setText(text);
//                        }


                }

                @Override
                public void onFinish() {


                }
            }.start();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }






}
