package batch9.upcomingdevelopers.com.icare;

/**
 * Created by acer on 11-Jan-16.
 */
public class MedicalHistoryModel {
    private int medicalHistoryId;
    private String medicalHistoryImageFilePath;
    private String medicalHistoryDoctorName;
    private String medicalHistoryDetails;
    private String medicalHistoryDate;
    private int medicalHistoryForeignKey;

    public MedicalHistoryModel(String medicalHistoryImageFilePath, String medicalHistoryDoctorName, String medicalHistoryDetails, String medicalHistoryDate, int medicalHistoryForeignKey) {
        this.medicalHistoryImageFilePath = medicalHistoryImageFilePath;
        this.medicalHistoryDoctorName = medicalHistoryDoctorName;
        this.medicalHistoryDetails = medicalHistoryDetails;
        this.medicalHistoryDate = medicalHistoryDate;
        this.medicalHistoryForeignKey = medicalHistoryForeignKey;
    }

    public MedicalHistoryModel(int medicalHistoryId, String medicalHistoryImageFilePath, String medicalHistoryDoctorName, String medicalHistoryDetails, String medicalHistoryDate, int medicalHistoryForeignKey) {
        this.medicalHistoryId = medicalHistoryId;
        this.medicalHistoryImageFilePath = medicalHistoryImageFilePath;
        this.medicalHistoryDoctorName = medicalHistoryDoctorName;
        this.medicalHistoryDetails = medicalHistoryDetails;
        this.medicalHistoryDate = medicalHistoryDate;
        this.medicalHistoryForeignKey = medicalHistoryForeignKey;
    }

    public void setMedicalHistoryImageFilePath(String medicalHistoryImageFilePath) {
        this.medicalHistoryImageFilePath = medicalHistoryImageFilePath;
    }

    public void setMedicalHistoryDoctorName(String medicalHistoryDoctorName) {
        this.medicalHistoryDoctorName = medicalHistoryDoctorName;
    }

    public void setMedicalHistoryDetails(String medicalHistoryDetails) {
        this.medicalHistoryDetails = medicalHistoryDetails;
    }

    public void setMedicalHistoryDate(String medicalHistoryDate) {
        this.medicalHistoryDate = medicalHistoryDate;
    }

    public int getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public String getMedicalHistoryImageFilePath() {
        return medicalHistoryImageFilePath;
    }

    public String getMedicalHistoryDoctorName() {
        return medicalHistoryDoctorName;
    }

    public String getMedicalHistoryDetails() {
        return medicalHistoryDetails;
    }

    public String getMedicalHistoryDate() {
        return medicalHistoryDate;
    }

    public int getMedicalHistoryForeignKey() {
        return medicalHistoryForeignKey;
    }
}
