package in.games.OnlineMatka;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



class CheckNetwork {
    Context context;
    public CheckNetwork(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();
    }
}
