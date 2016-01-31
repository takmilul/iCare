package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Mobile App Develop on 12-1-16.
 */
public class MedicalHistoryAdapter extends ArrayAdapter<MedicalHistoryModel> {
    Context context;
    LayoutInflater inflater;
    public MedicalHistoryAdapter(Context context, List<MedicalHistoryModel> objects) {
        super(context, 0, objects);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.view_medical_history_row, parent, false);
            holder.prescriptionViewMedicalHistoryImage = (ImageView) convertView.findViewById(R.id.prescriptionViewMedicalHistoryImage);
            holder.doctorNameViewMedicalHistoryTv = (TextView) convertView.findViewById(R.id.doctorNameViewMedicalHistoryTv);
            holder.detailsViewMedicalHistoryTv = (TextView) convertView.findViewById(R.id.detailsViewMedicalHistoryTv);
            holder.dateViewMedicalHistoryTv = (TextView) convertView.findViewById(R.id.dateViewMedicalHistoryTv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MedicalHistoryModel medicalHistoryModel = getItem(position);

        //Toast.makeText(context, medicalHistoryModel.getMedicalHistoryImageFilePath(), Toast.LENGTH_LONG).show();
        if(medicalHistoryModel.getMedicalHistoryImageFilePath().length() > 0)
            Picasso.with(context).load("file://" + medicalHistoryModel.getMedicalHistoryImageFilePath()).resize(852, 852).centerInside().into(holder.prescriptionViewMedicalHistoryImage);
        //holder.prescriptionViewMedicalHistoryImage.setImageBitmap(BitmapFactory.decodeFile(medicalHistoryModel.getMedicalHistoryImageFilePath()));
        holder.doctorNameViewMedicalHistoryTv.setText(medicalHistoryModel.getMedicalHistoryDoctorName());
        holder.detailsViewMedicalHistoryTv.setText(medicalHistoryModel.getMedicalHistoryDetails());
        holder.dateViewMedicalHistoryTv.setText(medicalHistoryModel.getMedicalHistoryDate());


        return convertView;
    }

    private static class ViewHolder {
        ImageView prescriptionViewMedicalHistoryImage;
        TextView doctorNameViewMedicalHistoryTv;
        TextView detailsViewMedicalHistoryTv;
        TextView dateViewMedicalHistoryTv;
    }
}
