package in.games.OnlineMatka;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView open_single_p , close_single_p , open_double,close_double ,open_triple , close_triple,open_cylce,close_cylce,halfsngm,fullsngm,jodi ,open_single,close_single;
    TextView bt_back ,txtMatkaName;
    private String dashName;

    private Toolbar toolbar;

    private String m_id ,start_time ,end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        m_id=getIntent().getStringExtra("m_id");
        dashName=getIntent().getStringExtra("matkaName");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        //   bet_type=getIntent().getStringExtra("bet");
        Log.e("game",m_id+"\n"+dashName);

        bt_back=(TextView)findViewById(R.id.txtBack);
        open_cylce = findViewById(R.id.opencyclepatti);
        open_double = findViewById(R.id.opendoublepatti);
        open_single_p= findViewById(R.id.opensinglepatti);
        open_single= findViewById(R.id.opensingle);
        open_triple = findViewById(R.id.opentriplepatti);
        close_cylce = findViewById(R.id.closecyclepatti);
        close_double = findViewById(R.id.closedoublepatti);
        close_single_p = findViewById(R.id.closesinglepatti);
        close_single = findViewById(R.id.closesingle);
        close_triple = findViewById(R.id.closetriplepatti);
        halfsngm = findViewById(R.id.halfsangm);
        fullsngm= findViewById(R.id.fullsangm);
        jodi = findViewById(R.id.jodi);

        toolbar=findViewById(R.id.toolbar);
        txtMatkaName=findViewById(R.id.board);


        txtMatkaName.setText(dashName+ " DASHBOARD");
        txtMatkaName.setSelected(true);

        bt_back.setOnClickListener(this);
        open_double.setOnClickListener(this);
        open_cylce.setOnClickListener(this);
        open_single.setOnClickListener(this);
        open_single_p.setOnClickListener(this);
        open_triple.setOnClickListener(this);
        close_triple.setOnClickListener(this);
        close_single.setOnClickListener(this);
        close_single_p.setOnClickListener(this);
        close_double.setOnClickListener(this);
        close_cylce.setOnClickListener(this);
        halfsngm.setOnClickListener(this);
        fullsngm.setOnClickListener(this);
        jodi.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id ==R.id.opensinglepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,SinglePannaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","7");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
          intent.putExtra("m_type","open");
            startActivity(intent);
        }
        else if (id==R.id.opensingle)
        {
            Intent intent=new Intent(NewGameActivity.this,NewSingleDigit.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","2");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
             intent.putExtra("m_type","open");
             startActivity(intent);
        }
        else if (id==R.id.closesingle)
        {
            Intent intent=new Intent(NewGameActivity.this,NewSingleDigit.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","2");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
               intent.putExtra("m_type","close");
            startActivity(intent);
        }
        else if (id==R.id.jodi)
        {
            Intent intent=new Intent(NewGameActivity.this,NewJodi.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","3");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
           intent.putExtra("m_type","close");
            startActivity(intent);
        }
        else if (id ==R.id.opendoublepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,DoublePanaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","8");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
           intent.putExtra("m_type","open");
            startActivity(intent);
        }
        else if(id ==R.id.opentriplepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,TriplePanaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","9");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
              intent.putExtra("m_type","open");
            startActivity(intent);
        }
        else if (id ==R.id.opencyclepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,CyclePana.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","14");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
            intent.putExtra("m_type","open");
            startActivity(intent);}
        else if(id ==R.id.halfsangm)
        {
            Intent intent=new Intent(NewGameActivity.this,HalfSangamActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","12");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
            //intent.putExtra("m_type",bet_type);
            startActivity(intent);
        }
        else if (id ==R.id.closedoublepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,DoublePanaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","8");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
            intent.putExtra("m_type","close");
            startActivity(intent);
        }
        else if (id ==R.id.closesinglepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,SinglePannaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","7");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
            intent.putExtra("m_type","close");
            startActivity(intent);
        }

        else if (id ==R.id.closetriplepatti)
        {
            Intent intent=new Intent(NewGameActivity.this,TriplePanaActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","9");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
           intent.putExtra("m_type","close");
            startActivity(intent);
        }
        else if (id ==R.id.closecyclepatti)
        {

            Intent intent=new Intent(NewGameActivity.this,CyclePana.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","14");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
           intent.putExtra("m_type","close");
            startActivity(intent);
        }
        else if (id==R.id.fullsangm)
        {
            Intent intent=new Intent(NewGameActivity.this,FullSangamActivity.class);
            intent.putExtra("matkaName",dashName.toString());
            intent.putExtra("game_id","13");
            intent.putExtra("m_id",m_id.toString());
            intent.putExtra("end_time",end_time);
            intent.putExtra("start_time",start_time);
            //intent.putExtra("m_type",bet_type);
            startActivity(intent);
        }
        else if (id == R.id.txtBack)
        {
            finish();
        }

    }
}
