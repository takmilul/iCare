package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GeneralInfoCustomAdapter extends ArrayAdapter<GeneralInfo> {
    private LayoutInflater layoutInflater;


    public GeneralInfoCustomAdapter(Context context, List<GeneralInfo> objects) {
        super(context, 0, objects);
        this.layoutInflater = LayoutInflater.from(context);
    }




    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;


        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.general_info_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.headerTv = (TextView) convertView.findViewById(R.id.generalInfoHeaderTv);
            viewHolder.bodyTv = (TextView) convertView.findViewById(R.id.generalInfoBodyTv);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GeneralInfo generalInfo = getItem(position);

        viewHolder.headerTv.setText(generalInfo.getHeader());
        viewHolder.bodyTv.setText(generalInfo.getInfo());
//        viewHolder.headerTv.setText(userProfileModel.get(position).getProductCode());

        return convertView;
    }

    public static class ViewHolder {
        TextView headerTv;
        TextView bodyTv;
    }
}
