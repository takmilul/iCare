package batch9.upcomingdevelopers.com.icare;

public class UserProfileModel {

    private int userId;
    private String userName;
    private String userAge;
    private String userHeight;
    private String userWeight;

    public UserProfileModel(String userName, String userAge, String userHeight, String userWeight) {
        this.userName = userName;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

    public UserProfileModel(int userId, String userName, String userAge, String userHeight, String userWeight) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }
}
