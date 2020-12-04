package com.ms.icbc.security.bean;

public class FingerField {
	public String st;
	public String ua;
	public String resolution;
	public String can;
	public String gi;
	public boolean hlb;
	public boolean hlr;
	public String np;
	public String timeZone;
	public String language;
	public String rp;
	public boolean ce;
	public int hc;
	public int pr;
	public int cd;
	public String cpt;
	public boolean ls;
	public boolean ss;
	public boolean ind;
	public String web;
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		if(null == st) {
			throw new IllegalArgumentException("param is null");
		}

		this.st = st;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		if(null == ua) {
			throw new IllegalArgumentException("param is null");
		}

		this.ua = ua;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		if(null == resolution) {
			throw new IllegalArgumentException("param is null");
		}

		this.resolution = resolution;
	}
	public String getCan() {
		return can;
	}
	public void setCan(String can) {
		if(null == can) {
			throw new IllegalArgumentException("param is null");
		}

		this.can = can;
	}
	public String getGi() {
		return gi;
	}
	public void setGi(String gi) {
		if(null == gi) {
			throw new IllegalArgumentException("param is null");
		}

		this.gi = gi;
	}
	public boolean isHlb() {
		return hlb;
	}
	public void setHlb(boolean hlb) {
		

		this.hlb = hlb;
	}
	public boolean isHlr() {
		return hlr;
	}
	public void setHlr(boolean hlr) {
		this.hlr = hlr;
	}
	public String getNp() {
		return np;
	}
	public void setNp(String np) {
		if(null == np) {
			throw new IllegalArgumentException("param is null");
		}

		this.np = np;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		if(null == timeZone) {
			throw new IllegalArgumentException("param is null");
		}

		this.timeZone = timeZone;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		if(null == language) {
			throw new IllegalArgumentException("param is null");
		}

		this.language = language;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		if(null == rp) {
			throw new IllegalArgumentException("param is null");
		}

		this.rp = rp;
	}
	public boolean isCe() {
		return ce;
	}
	public void setCe(boolean ce) {
		this.ce = ce;
	}
	public int getHc() {
		return hc;
	}
	public void setHc(int hc) {
		this.hc = hc;
	}
	public int getPr() {
		return pr;
	}
	public void setPr(int pr) {
		this.pr = pr;
	}
	public String getCpt() {
		return cpt;
	}
	public void setCpt(String cpt) {
		if(null == cpt) {
			throw new IllegalArgumentException("param is null");
		}

		this.cpt = cpt;
	}
	public boolean isLs() {
		return ls;
	}
	public void setLs(boolean ls) {
		this.ls = ls;
	}
	public boolean isSs() {
		return ss;
	}
	public void setSs(boolean ss) {
		this.ss = ss;
	}
	public boolean isInd() {
		return ind;
	}
	public void setInd(boolean ind) {
		this.ind = ind;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		if(null == web) {
			throw new IllegalArgumentException("param is null");
		}

		this.web = web;
	}

	
	
	
	
}
