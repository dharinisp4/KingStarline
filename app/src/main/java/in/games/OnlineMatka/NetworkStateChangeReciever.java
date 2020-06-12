package in.games.OnlineMatka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

public class NetworkStateChangeReciever extends BroadcastReceiver {
    public static final String NETWORK_AVAILABLE_ACTION="in.binplus.NetworkAvailable";
    public static final String IS_NETWORK_AVAILABLE="isNetworkAvailable";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent networkStateIntent=new Intent(NETWORK_AVAILABLE_ACTION);
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE,isConnectedToNetwork(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);


    }

    private boolean isConnectedToNetwork(Context context) {
        try {
            if(context!=null)
            {
                ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

                return networkInfo !=null && networkInfo.isConnected();

            }
            return false;

        }
        catch (Exception ex)
        {
            Log.e(NetworkStateChangeReciever.class.getName(), ex.getMessage());
            return false;
        }


    }
}
