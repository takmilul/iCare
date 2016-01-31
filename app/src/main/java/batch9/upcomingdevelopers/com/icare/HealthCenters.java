package batch9.upcomingdevelopers.com.icare;

import java.util.ArrayList;

/**
 * Created by acer on 21-Jan-16.
 */
public class HealthCenters {
    String name;
    String address;
    String phone;
    double lat;
    double lon;


    public HealthCenters() {
    }


    public HealthCenters(String name, String address, String phone, double lat, double lon) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
    }

    public static ArrayList<HealthCenters> getAllHealthCenters(){
        ArrayList<HealthCenters> healthCenterses = new ArrayList<>();
        healthCenterses.add(new HealthCenters("United Hospital","Plot 15, Rd No 71, Dhaka 1212","+880 2-8836000", 23.8045603, 90.4156108));
        healthCenterses.add(new HealthCenters("Square Hospitals Limited", "18F, Bir Uttam Qazi Nuruzzaman Sarak, West Panthapath, Dhaka 1205","+880 1713141447", 23.7529968, 90.3814611));
        healthCenterses.add(new HealthCenters("Apollo Hospitals", "Plot: 81, Block: E, Bashundhara R/A, Dhaka 1229", "+880 1729-276556",23.809876, 90.431202));
        healthCenterses.add(new HealthCenters("Ahsania Mission Cancer Hospital", "Mirpur Rd, Dhaka", "+880 2-8051618", 23.8034963, 90.3772263));
        healthCenterses.add(new HealthCenters("National Heart Foundation Hospital & Research Institute", "Plot-7/2, Section-2, Mirpur, Dhaka-1216", "9033442-6", 23.8037449, 90.3619551));
        healthCenterses.add(new HealthCenters("National Institute of Neuro-Sciences Hospital", "ShahidShahabuddinShorok, Dhaka 1207", ":+88-02-9137305", 23.7761345, 90.3707896));
        healthCenterses.add(new HealthCenters("BIRDEM", "122,KaziNazrul Islam Avenue,Shahbagh,Dhaka 1000", "+880 2-8616644", 23.7387909, 90.3964691));
        healthCenterses.add(new HealthCenters("IspahaniIslamia Eye Institute and Hospital", "KrishiKhamar Rd, Dhaka 1215", "+880 2-8112856", 23.7585286, 90.3852829));
        healthCenterses.add(new HealthCenters("City Hospital Ltd", "1/8 Block E Lalmatia, Satmasjid Road, Dhaka 1207", "+880 2-8143312", 23.7540245, 90.3654782));
        healthCenterses.add(new HealthCenters("Central Hospital Ltd", "House#2, Rd No. 5, Dhaka", "+880 2-9660015", 23.7433287, 90.3841728));
        healthCenterses.add(new HealthCenters("Japan Bangladesh Friendship Hospital", "55 Satmasjid Road, Dhaka 1209", ":+880 2-9672277", 23.7395673, 90.3751042));
        healthCenterses.add(new HealthCenters("Bangladesh Eye Hospital", "Satmasjid Rd, Dhaka", ":+880 1755-660041", 23.7516582, 90.3672843));
        healthCenterses.add(new HealthCenters("Holy Family Red Crescent Medical College and Hospital", "1, Eskatan Garden Rd, Dhaka 1000", "+88-02-9353031", 23.7467686, 90.4033885));
        healthCenterses.add(new HealthCenters("Samorita Hospital", "Panthapath, Dhaka 1205", "+880 2-9131901", 23.7525326, 90.3850989));
        healthCenterses.add(new HealthCenters("Ibrahim Cardiac Hospital & Research Institute", "Ramna, Dhaka", "+880 2-9671141", 23.738815, 90.396403));
        healthCenterses.add(new HealthCenters("Bangabandhu Sheikh Mujib Medical University", "Shahbagh Rd, Dhaka", "+880 2-8614001", 23.7389142, 90.3947783));
        healthCenterses.add(new HealthCenters("Labaid Cardiac Hospital", "1 and 3, Rd No 4, Dhaka 1205", "+880 2-9676356", 23.74161, 90.3834754));
        healthCenterses.add(new HealthCenters("IBN Sina Hospital", "Rd No. 15A, Dhaka 1207", "8119514-5",  23.7515336, 90.369072));
        healthCenterses.add(new HealthCenters("Care Hospital and Diagnostic Pvt Ltd.", "2/1-A Iqbal Road, Dhaka 1207", "+880 2-9134407",23.7689089, 90.3687776));
        healthCenterses.add(new HealthCenters("Health & Hope Hospital", "152/1/H Panthapath, Dhaka 1205", "+880 2-9137076", 23.7509236, 90.3867437));
        healthCenterses.add(new HealthCenters("Dhaka Shishu Hospital", "Syed MahbubMorshed Ave, Dhaka 1207", "+880 2-8116061", 23.7730713, 90.3688037));
        return healthCenterses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
