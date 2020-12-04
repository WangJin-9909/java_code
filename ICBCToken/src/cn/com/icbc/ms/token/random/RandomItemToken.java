package cn.com.icbc.ms.token.random;

public class RandomItemToken extends RandomItem{
	private  String token;
	
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		if(null == token) {
			throw new IllegalArgumentException("token is null");
		}
		
		this.token = token;
	}
	
	public void setTokenWithNull() {
		
		this.token = null;
	}

}
