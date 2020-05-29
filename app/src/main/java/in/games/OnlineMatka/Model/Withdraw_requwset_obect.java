package in.games.OnlineMatka.Model;

public class Withdraw_requwset_obect {

    String id,withdraw_points,withdraw_status,user_id,time ,type;


    public Withdraw_requwset_obect() {
    }



    public Withdraw_requwset_obect(String id, String withdraw_points, String withdraw_status, String user_id, String time, String type) {
        this.id = id;
        this.withdraw_points = withdraw_points;
        this.withdraw_status = withdraw_status;
        this.user_id = user_id;
        this.time = time;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWithdraw_points() {
        return withdraw_points;
    }

    public void setWithdraw_points(String withdraw_points) {
        this.withdraw_points = withdraw_points;
    }

    public String getWithdraw_status() {
        return withdraw_status;
    }

    public void setWithdraw_status(String withdraw_status) {
        this.withdraw_status = withdraw_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
