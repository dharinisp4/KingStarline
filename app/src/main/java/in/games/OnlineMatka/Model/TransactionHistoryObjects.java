package in.games.OnlineMatka.Model;

public class TransactionHistoryObjects {

    String id,matka_id,user_id,game_id,digits,amt,bid_id,time,type,date,bet_type,name;

    public TransactionHistoryObjects() {
    }

    public TransactionHistoryObjects(String id, String matka_id, String user_id, String game_id, String digits, String amt, String bid_id, String time, String type, String date, String bet_type, String name) {
        this.id = id;
        this.matka_id = matka_id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.digits = digits;
        this.amt = amt;
        this.bid_id = bid_id;
        this.time = time;
        this.type = type;
        this.date = date;
        this.bet_type = bet_type;
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatka_id() {
        return matka_id;
    }

    public void setMatka_id(String matka_id) {
        this.matka_id = matka_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
