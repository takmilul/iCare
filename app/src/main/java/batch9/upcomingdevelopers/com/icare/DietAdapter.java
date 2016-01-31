package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class DietAdapter extends ArrayAdapter<DietModel> {
    public DietAdapter(Context context, List<DietModel> objects) {
        super(context, 0, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        DietModel dietModel = getItem(position);
        ViewHolder viewHolder = new ViewHolder();

        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_diet_chart_row, parent, false);

            viewHolder.typeViewDietTv = (TextView) convertView.findViewById(R.id.typeViewDietTv);
            viewHolder.menuViewDietTv = (TextView) convertView.findViewById(R.id.menuViewDietTv);
            viewHolder.dateViewDietTv = (TextView) convertView.findViewById(R.id.dateViewDietTv);
            viewHolder.timeViewDietTv = (TextView) convertView.findViewById(R.id.timeViewDietTv);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.typeViewDietTv.setText(dietModel.getDietType());
        viewHolder.menuViewDietTv.setText(dietModel.getDietMenu());
        viewHolder.dateViewDietTv.setText(dietModel.getDietDate());
        viewHolder.timeViewDietTv.setText(dietModel.getDietTime());
        return convertView;
    }

    public static class ViewHolder{
        TextView todaysChartViewDietTv;
        TextView typeViewDietTv;
        TextView menuViewDietTv;
        TextView dateViewDietTv;
        TextView timeViewDietTv;
    }
}
