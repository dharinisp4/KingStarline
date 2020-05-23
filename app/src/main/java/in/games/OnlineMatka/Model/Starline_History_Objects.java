package in.games.OnlineMatka.Model;

public class Starline_History_Objects {

    String id,user_id,matka_id,points,digits,date,time,bet_type,game_id,status,s_game_time,play_for,play_on,day;


    public Starline_History_Objects() {
    }


    public Starline_History_Objects(String id, String user_id, String matka_id, String points, String digits, String date, String time, String bet_type, String game_id, String status, String s_game_time, String play_for, String play_on, String day) {
        this.id = id;
        this.user_id = user_id;
        this.matka_id = matka_id;
        this.points = points;
        this.digits = digits;
        this.date = date;
        this.time = time;
        this.bet_type = bet_type;
        this.game_id = game_id;
        this.status = status;
        this.s_game_time = s_game_time;
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

    public String getS_game_time() {
        return s_game_time;
    }

    public void setS_game_time(String s_game_time) {
        this.s_game_time = s_game_time;
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
