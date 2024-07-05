package YahiaLakrikba.GestioneEventi.auth;


public class UserLoginSuccessDTO {

    private String token;

    public UserLoginSuccessDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

