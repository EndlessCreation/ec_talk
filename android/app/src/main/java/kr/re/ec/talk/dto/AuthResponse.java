package kr.re.ec.talk.dto;

/**
 * POJO object for handling Authentication.
 * @author Taehee Kim 2016-09-17
 */
public final class AuthResponse extends ResponseBase {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "AuthResponse [nickname=" + nickname + ", getSuccess()="
                + getSuccess() + ", getMessage()=" + getMessage() + "]";
    }
}
