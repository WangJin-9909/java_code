/**
* @author Dabing
* @E-mail chenbinging@outlook.com
* @date 2018-10-26涓婂崍9:50:00
* 鍥剧墖鍒嗗潡绫�
*/
package example;

public class LatticeSegmentationBean {

	private int m_xRangePoint;
	private int m_yRangePoint;
	private int m_isMark;
	private char m_txt;
	
	public LatticeSegmentationBean(){}
	public LatticeSegmentationBean(int x, int y){
		this.m_xRangePoint = x;
		this.m_yRangePoint = y;
	}
	public int getM_xRangePoint() {
		return m_xRangePoint;
	}
	public void setM_xRangePoint(int m_xRangePoint) {
		this.m_xRangePoint = m_xRangePoint;
	}
	public int getM_yRangePoint() {
		return m_yRangePoint;
	}
	public void setM_yRangePoint(int m_yRangePoint) {
		this.m_yRangePoint = m_yRangePoint;
	}
	public int getM_isMark() {
		return m_isMark;
	}
	public void setM_isMark(int m_isMark) {
		this.m_isMark = m_isMark;
	}
	public char getM_txt() {
		return m_txt;
	}
	public void setM_txt(char m_txt) {
		this.m_txt = m_txt;
	}
	
}
