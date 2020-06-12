package in.games.OnlineMatka.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.GameRateModel;
import in.games.OnlineMatka.R;

public class Notice_RateAdapter extends RecyclerView.Adapter<Notice_RateAdapter.ViewHolder> {
    ArrayList<GameRateModel> ratelist ;
    Context context ;

    public Notice_RateAdapter(ArrayList<GameRateModel> ratelist, Context context) {
        this.ratelist = ratelist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.row_notice_rates ,null);
       ViewHolder holder = new ViewHolder( view );
       return  holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GameRateModel rlist = ratelist.get( i );
        viewHolder.game_rate.setText(rlist.getRate_range()+" : " +rlist.getRate());
        viewHolder.game_name.setText( rlist.getName() );

    }

    @Override
    public int getItemCount() {
        return ratelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView game_name ,game_range ,game_rate ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            game_name = itemView.findViewById( R.id.gamename );
            game_rate=itemView.findViewById( R.id.gamerate );
        }
    }
}
