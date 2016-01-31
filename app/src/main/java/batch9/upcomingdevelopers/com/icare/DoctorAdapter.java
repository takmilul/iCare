package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class DoctorAdapter extends ArrayAdapter<DoctorModel> {
   public DoctorAdapter(Context context, List<DoctorModel> objects) {
      super(context, 0, objects);
   }

   public View getView(int position, View convertView, ViewGroup parent) {

      final DoctorModel doctorModel = getItem(position);
      ViewHolder viewHolder = new ViewHolder();

      if (convertView == null) {
         LayoutInflater inflater = LayoutInflater.from(getContext());
         convertView = inflater.inflate(R.layout.view_doctors_chart_row, parent, false);

         viewHolder.nameViewDoctorTv = (TextView) convertView.findViewById(R.id.nameViewDoctorTv);
         viewHolder.detailViewDoctorTv = (TextView) convertView.findViewById(R.id.detailViewDoctorTv);
         viewHolder.dateViewDoctorTv = (TextView) convertView.findViewById(R.id.dateViewDoctorTv);
         viewHolder.phoneViewDoctorTv = (TextView) convertView.findViewById(R.id.phoneViewDoctorTv);
         viewHolder.emailViewDoctorTv = (TextView) convertView.findViewById(R.id.emailViewDoctorTv);
         convertView.setTag(viewHolder);
      } else {
         viewHolder = (ViewHolder) convertView.getTag();
      }

      viewHolder.nameViewDoctorTv.setText(doctorModel.getDoctorName());
      viewHolder.detailViewDoctorTv.setText(doctorModel.getDoctorDetail());
      viewHolder.dateViewDoctorTv.setText(doctorModel.getDoctorAppointment());
      viewHolder.phoneViewDoctorTv.setText(doctorModel.getDoctorPhone());
      viewHolder.emailViewDoctorTv.setText(doctorModel.getDoctorEmail());
      return convertView;
   }

   public static class ViewHolder {
      TextView nameViewDoctorTv;
      TextView detailViewDoctorTv;
      TextView dateViewDoctorTv;
      TextView phoneViewDoctorTv;
      TextView emailViewDoctorTv;
   }
}
