package in.games.OnlineMatka.Model;

public class PGObjects {

    String message,time,number;
    int image;

    public PGObjects(String message, String time, String number,int image) {
        this.message = message;
        this.time = time;
        this.number = number;
        this.image=image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
