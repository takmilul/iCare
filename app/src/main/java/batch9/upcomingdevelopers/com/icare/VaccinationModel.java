package batch9.upcomingdevelopers.com.icare;

public class VaccinationModel {
    private int vaccineId;
    private String vaccineName;
    private String vaccineDate;
    private String vaccineTime;
    private String vaccineDetail;
    private int vaccineReminder;
    private int vaccineForeignKey;

    public VaccinationModel(String vaccineName, String vaccineDate, String vaccineTime, String vaccineDetail, int vaccineReminder, int vaccineForeignKey) {
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.vaccineTime = vaccineTime;
        this.vaccineDetail = vaccineDetail;
        this.vaccineReminder = vaccineReminder;
        this.vaccineForeignKey = vaccineForeignKey;
    }

    public VaccinationModel(int vaccineId, String vaccineName, String vaccineDate, String vaccineTime, String vaccineDetail, int vaccineReminder, int vaccineForeignKey) {
        this.vaccineId = vaccineId;
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.vaccineTime = vaccineTime;
        this.vaccineDetail = vaccineDetail;
        this.vaccineReminder = vaccineReminder;
        this.vaccineForeignKey = vaccineForeignKey;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public void setVaccineTime(String vaccineTime) {
        this.vaccineTime = vaccineTime;
    }

    public void setVaccineDetail(String vaccineDetail) {
        this.vaccineDetail = vaccineDetail;
    }

    public void setVaccineReminder(int vaccineReminder) {
        this.vaccineReminder = vaccineReminder;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public String getVaccineTime() {
        return vaccineTime;
    }

    public String getVaccineDetail() {
        return vaccineDetail;
    }

    public int getVaccineReminder() {
        return vaccineReminder;
    }

    public int getVaccineForeignKey() {
        return vaccineForeignKey;
    }
}
