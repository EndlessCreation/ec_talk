package kr.re.ec.talk.dto;

/**
 * User Value Object
 * @author Taehee Kim 2016-09-16
 */
public class User {
	public static final int ID_NOT_SET = -1;
	
	private int id;
	private String token;
	private String nickname;
	private String deviceId;
	
	public User(int id, String token, String nickname, String deviceId) {
		this.id = id;
		this.token = token;
		this.nickname = nickname;
		this.deviceId = deviceId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", token=" + token + ", nickname=" + nickname
				+ ", deviceId=" + deviceId + "]";
	}
}
