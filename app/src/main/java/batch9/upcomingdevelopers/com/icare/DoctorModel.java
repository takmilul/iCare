package batch9.upcomingdevelopers.com.icare;

public class DoctorModel {
    private int doctorId;
    private String doctorName;
    private String doctorDetail;
    private String doctorAppointment;
    private String doctorPhone;
    private String doctorEmail;
    private int doctorForeignKey;

    public DoctorModel(String doctorName, String doctorDetail, String doctorAppointment, String doctorPhone, String doctorEmail, int doctorForeignKey) {
        this.doctorName = doctorName;
        this.doctorDetail = doctorDetail;
        this.doctorAppointment = doctorAppointment;
        this.doctorPhone = doctorPhone;
        this.doctorEmail = doctorEmail;
        this.doctorForeignKey = doctorForeignKey;
    }

    public DoctorModel(int doctorId, String doctorName, String doctorDetail, String doctorAppointment, String doctorPhone, String doctorEmail, int doctorForeignKey) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorDetail = doctorDetail;
        this.doctorAppointment = doctorAppointment;
        this.doctorPhone = doctorPhone;
        this.doctorEmail = doctorEmail;
        this.doctorForeignKey = doctorForeignKey;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorDetail(String doctorDetail) {
        this.doctorDetail = doctorDetail;
    }

    public void setDoctorAppointment(String doctorAppointment) {
        this.doctorAppointment = doctorAppointment;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorDetail() {
        return doctorDetail;
    }

    public String getDoctorAppointment() {
        return doctorAppointment;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public int getDoctorForeignKey() {
        return doctorForeignKey;
    }
}
