package project.bean;

import javax.swing.JButton;

public class ReviewBean {
	
	private int proIdx;
	private int memIdx;
	private String memName;
	private String comTimeStamp;
	private String comments;
	private int starRating;
	private JButton deleteBtn;
	private JButton modifyBtn;
	private boolean isHimBuy;
	
	public ReviewBean() {
		this.starRating = 1;
	}
	
	public int getProIdx() {
		return proIdx;
	}
	public void setProIdx(int proIdx) {
		this.proIdx = proIdx;
	}
	public int getMemIdx() {
		return memIdx;
	}
	public void setMemIdx(int memIdx) {
		this.memIdx = memIdx;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getComTimeStamp() {
		return comTimeStamp;
	}
	public String getComDate() {
		String date[] = this.comTimeStamp.split(" ");
		return date[0];
	}
	public void setComTimeStamp(String comTimeStamp) {
		this.comTimeStamp = comTimeStamp;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getStarRating() {
		return starRating;
	}
	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
	public JButton getDeleteBtn() {
		return deleteBtn;
	}
	public void setDeleteBtn(JButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}
	public JButton getModifyBtn() {
		return modifyBtn;
	}
	public void setModifyBtn(JButton modifyBtn) {
		this.modifyBtn = modifyBtn;
	}

	public boolean isHimBuy() {
		return isHimBuy;
	}

	public void setHimBuy(boolean isHimBuy) {
		this.isHimBuy = isHimBuy;
	}
	
}
