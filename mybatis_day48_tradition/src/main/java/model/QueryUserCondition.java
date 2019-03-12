package model;

import java.util.Date;

public class QueryUserCondition {
    User user;
    int id_start;
    int id_end;

    @Override
    public String toString() {
        return "QueryUserCondition{" +
                "user=" + user +
                ", id_start=" + id_start +
                ", id_end=" + id_end +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId_start() {
        return id_start;
    }

    public void setId_start(int id_start) {
        this.id_start = id_start;
    }

    public int getId_end() {
        return id_end;
    }

    public void setId_end(int id_end) {
        this.id_end = id_end;
    }

    public QueryUserCondition() {
    }

    public QueryUserCondition(User user, int id_start, int id_end) {
        this.user = user;
        this.id_start = id_start;
        this.id_end = id_end;
    }
}
