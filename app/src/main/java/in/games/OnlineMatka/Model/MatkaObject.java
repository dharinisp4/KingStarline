package in.games.OnlineMatka.Model;

public class MatkaObject {

    int image;
    String time,name,number,status;

    public MatkaObject(int image, String time, String name, String number, String status) {
        this.image = image;
        this.time = time;
        this.name = name;
        this.number = number;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
