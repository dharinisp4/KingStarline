package in.games.OnlineMatka.Objects;

public class Fund_Request_HistoryObject {
    String request_id,request_points,time,user_id,request_status;
    public Fund_Request_HistoryObject() {
    }

    public Fund_Request_HistoryObject(String request_id, String request_points, String time, String user_id, String request_status) {
        this.request_id = request_id;
        this.request_points = request_points;
        this.time = time;
        this.user_id = user_id;
        this.request_status = request_status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_points() {
        return request_points;
    }

    public void setRequest_points(String request_points) {
        this.request_points = request_points;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}

