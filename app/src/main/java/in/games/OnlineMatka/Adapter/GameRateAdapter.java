package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.GameRateModel;
import in.games.OnlineMatka.R;

public class GameRateAdapter extends RecyclerView.Adapter<GameRateAdapter.ViewHolder> {
    ArrayList<GameRateModel> ratelist ;
    Context context ;

    public GameRateAdapter(ArrayList<GameRateModel> ratelist, Context context) {
        this.ratelist = ratelist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context ).inflate( R.layout.row_rates ,null);
        ViewHolder viewHolder = new ViewHolder( view );
        return  viewHolder;

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
