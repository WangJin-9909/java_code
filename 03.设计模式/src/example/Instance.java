package example;
/**
 * µ¥ÀýÄ£Ê½
 * @author wangJin
 *
 */
public class Instance {
	private volatile static Instance instane;
	private Instance() {}
	public static Instance getInstance() {
		synchronized (Instance.class) {
			if(instane == null) {
				instane = new Instance();
			}
		}
		
		return instane;
	}
	
}
