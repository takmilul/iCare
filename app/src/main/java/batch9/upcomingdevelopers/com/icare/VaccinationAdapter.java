package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class VaccinationAdapter extends ArrayAdapter<VaccinationModel> {

    public VaccinationAdapter(Context context, List<VaccinationModel> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        VaccinationModel vaccinationModel = getItem(position);
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_vaccination_chart_row, parent, false);

            viewHolder.vaccineName = (TextView) convertView.findViewById(R.id.nameViewVaccineTv);
            viewHolder.vaccineDate = (TextView) convertView.findViewById(R.id.dateViewVaccineTv);
            viewHolder.vaccineTime = (TextView) convertView.findViewById(R.id.timeViewVaccineTv);
            viewHolder.vaccineDetail = (TextView) convertView.findViewById(R.id.detailViewVaccineTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.vaccineName.setText(vaccinationModel.getVaccineName());
        viewHolder.vaccineDate.setText(vaccinationModel.getVaccineDate());
        viewHolder.vaccineTime.setText(vaccinationModel.getVaccineTime());
        viewHolder.vaccineDetail.setText(vaccinationModel.getVaccineDetail());
        return convertView;
    }

    public static class ViewHolder {
        TextView vaccineName;
        TextView vaccineDate;
        TextView vaccineTime;
        TextView vaccineDetail;
    }
}
