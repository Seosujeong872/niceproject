package project.bean;

public class ProductBean {
	private int proIdx;
	private String proName;
	private int cateIdx;
	private String cateName;
	private int price;
	private String imgAddress;
	private int inventory;
	
	public int getProIdx() {
		return proIdx;
	}
	public void setProIdx(int proIdx) {
		this.proIdx = proIdx;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public int getCateIdx() {
		return cateIdx;
	}
	public void setCateIdx(int cateIdx) {
		this.cateIdx = cateIdx;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
}
