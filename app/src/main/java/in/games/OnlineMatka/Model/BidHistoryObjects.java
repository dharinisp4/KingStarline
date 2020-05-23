package in.games.OnlineMatka.Model;

public class BidHistoryObjects {

    String id,user_id,matka_id,points,digits,date,time,bet_type,name,game_id,status,play_for,play_on,day;

    public BidHistoryObjects() {
    }

    public BidHistoryObjects(String id, String user_id, String matka_id, String points, String digits, String date, String time, String bet_type, String name, String game_id, String status, String play_for, String play_on, String day) {
        this.id = id;
        this.user_id = user_id;
        this.matka_id = matka_id;
        this.points = points;
        this.digits = digits;
        this.date = date;
        this.time = time;
        this.bet_type = bet_type;
        this.name = name;
        this.game_id = game_id;
        this.status = status;
        this.play_for = play_for;
        this.play_on = play_on;
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMatka_id() {
        return matka_id;
    }

    public void setMatka_id(String matka_id) {
        this.matka_id = matka_id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBet_type() {
        return bet_type;
    }

    public void setBet_type(String bet_type) {
        this.bet_type = bet_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlay_for() {
        return play_for;
    }

    public void setPlay_for(String play_for) {
        this.play_for = play_for;
    }

    public String getPlay_on() {
        return play_on;
    }

    public void setPlay_on(String play_on) {
        this.play_on = play_on;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
