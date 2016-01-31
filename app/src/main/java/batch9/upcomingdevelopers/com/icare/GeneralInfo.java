package batch9.upcomingdevelopers.com.icare;

/**
 * Created by Mobile App Develop on 21-1-16.
 */
public class GeneralInfo {

    String header;
    String info;

    public GeneralInfo() {
    }



    public GeneralInfo(String header, String info) {
        this.header = header;
        this.info = info;

    }



    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
