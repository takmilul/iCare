package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by acer on 21-Jan-16.
 */
public class HospitalAdapter extends ArrayAdapter<HealthCenters> {

    LayoutInflater inflater;

    public HospitalAdapter(Context context, List<HealthCenters> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.hospital_row, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.nameHospitalTV);
            holder.address = (TextView) convertView.findViewById(R.id.addressHospitalTV);
            holder.phone = (TextView) convertView.findViewById(R.id.phoneHospitalTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HealthCenters healthCenters = getItem(position);
        holder.name.setText(healthCenters.getName());
        holder.address.setText(healthCenters.getAddress());
        holder.phone.setText(healthCenters.getPhone());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView address;
        TextView phone;
    }
}