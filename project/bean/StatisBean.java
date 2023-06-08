package project.bean;

public class StatisBean {
	private String cName;
	private int purCountRatio;
	private int year;
	private int month;
	private int purCount;
	
	public StatisBean() {
		cName = "기본값";
		purCountRatio = 0;
		year = 0;
		month = 0;
		purCount = 0;
	}
	
	public void purCountPlus(int i) {
		this.purCount += i;
	}
	
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getPurCountRatio() {
		return purCountRatio;
	}

	public void setPurCountRatio(int purCountRatio) {
		this.purCountRatio = purCountRatio;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getPurCount() {
		return purCount;
	}

	public void setPurCount(int purCount) {
		this.purCount = purCount;
	}
	
	
}
