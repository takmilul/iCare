package batch9.upcomingdevelopers.com.icare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class GeneralInfoActivity extends AppCompatActivity {

    String[] generalInfoHeader;
    String[] generalInfoBody;

    ArrayList<String[]> generalInfoArrayList;
    ListView generalInfoLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);
        generalInfoHeader = getResources().getStringArray(R.array.general_info_header);
        generalInfoBody = getResources().getStringArray(R.array.general_info_body);


        ArrayList<GeneralInfo> generalInfoArrayList = new ArrayList<>();
        for (int i = 0; i < generalInfoHeader.length; i++) {
            generalInfoArrayList.add(new GeneralInfo(generalInfoHeader[i], generalInfoBody[i]));
        }

        generalInfoLv = (ListView) findViewById(R.id.listViewGeneralInfo);

        GeneralInfoCustomAdapter generalInfoCustomAdapter = new GeneralInfoCustomAdapter(getApplicationContext(), generalInfoArrayList);
        generalInfoLv.setAdapter(generalInfoCustomAdapter);
    }
}
