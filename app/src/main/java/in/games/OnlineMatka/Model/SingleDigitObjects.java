package in.games.OnlineMatka.Model;

public class SingleDigitObjects {

    String digits,points,bettype;
    public SingleDigitObjects() {
    }

    public SingleDigitObjects(String digits, String points, String bettype) {
        this.digits = digits;
        this.points = points;
        this.bettype = bettype;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getBettype() {
        return bettype;
    }

    public void setBettype(String bettype) {
        this.bettype = bettype;
    }
}
