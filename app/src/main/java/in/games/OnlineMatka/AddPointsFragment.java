package in.games.OnlineMatka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPointsFragment extends Fragment {

    private Button btnFragement;

    public AddPointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_points, container, false);

        btnFragement=(Button)view.findViewById(R.id.add_Request);

        btnFragement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                WithdrawalRequest withdrawalRequest=new WithdrawalRequest();
//
//                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.flMain,withdrawalRequest);
//                fragmentTransaction.commit();


            }
        });

        return view;
    }

}
