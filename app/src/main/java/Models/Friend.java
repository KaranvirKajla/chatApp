package Models;

public class Friend {
    String email;
    long seconds;

    public Friend() {
    }

    public Friend(String email, long seconds) {
        this.email = email;
        this.seconds = seconds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}
