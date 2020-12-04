package cn.com.icbc.ms.token.random;

public class RandomItem {

	private  String randomStrA;
	private  String randomStrB;
	private  String timeStamp;
	
	
	
	



	public String getRandomStrA() {
		return randomStrA;
	}

	public void setRandomStrA(String randomStrA) {
		if(null == randomStrA) {
			throw new IllegalArgumentException("randomA is null");
		}
		this.randomStrA = randomStrA;
	}

	public String getRandomStrB() {
		return randomStrB;
	}

	public void setRandomStrB(String randomStrB) {
		if(null == randomStrB) {
			throw new IllegalArgumentException("randomB is null");
		}
		this.randomStrB = randomStrB;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		if(null == timeStamp) {
			throw new IllegalArgumentException("timeStamp is null");
		}
		this.timeStamp = timeStamp;
	}

	public RandomItem(String randomStrA, String randomStrB, String timeStamp) {
		if(null == timeStamp || null == randomStrA || null == randomStrB) {
			//throw new IllegalArgumentException("timeStamp is null");
			
		}
		this.randomStrA = randomStrA;
		this.randomStrB = randomStrB;
		this.timeStamp = timeStamp;

	}

	public RandomItem() {
		// TODO Auto-generated constructor stub
		this(null, null, null);
	}

}
