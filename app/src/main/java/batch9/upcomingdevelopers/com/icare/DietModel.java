package batch9.upcomingdevelopers.com.icare;

public class DietModel {
    private int dietId;
    private String dietType;
    private String dietMenu;
    private String dietDate;
    private String DietTime;
    private int dietAlarm;
    private int dietReminder;
    private int dietForeignKey;

    public DietModel(String dietType, String dietMenu, String dietDate, String dietTime, int dietAlarm, int dietReminder, int dietForeignKey) {
        this.dietType = dietType;
        this.dietMenu = dietMenu;
        this.dietDate = dietDate;
        DietTime = dietTime;
        this.dietAlarm = dietAlarm;
        this.dietReminder = dietReminder;
        this.dietForeignKey = dietForeignKey;
    }

    public DietModel(int dietId, String dietType, String dietMenu, String dietDate, String dietTime, int dietAlarm, int dietReminder, int dietForeignKey) {
        this.dietId = dietId;
        this.dietType = dietType;
        this.dietMenu = dietMenu;
        this.dietDate = dietDate;
        DietTime = dietTime;
        this.dietAlarm = dietAlarm;
        this.dietReminder = dietReminder;
        this.dietForeignKey = dietForeignKey;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public void setDietMenu(String dietMenu) {
        this.dietMenu = dietMenu;
    }

    public void setDietDate(String dietDate) {
        this.dietDate = dietDate;
    }

    public void setDietTime(String dietTime) {
        this.DietTime = dietTime;
    }

    public void setDietAlarm(int dietAlarm) {
        this.dietAlarm = dietAlarm;
    }

    public void setDietReminder(int dietReminder) {
        this.dietReminder = dietReminder;
    }

    public int getDietId() {
        return dietId;
    }

    public String getDietType() {
        return dietType;
    }

    public String getDietMenu() {
        return dietMenu;
    }

    public String getDietDate() {
        return dietDate;
    }

    public String getDietTime() {
        return DietTime;
    }

    public int isDietAlarm() {
        return dietAlarm;
    }

    public int isDietReminder() {
        return dietReminder;
    }

    public int getDietForeignKey() {
        return dietForeignKey;
    }
}
