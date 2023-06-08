package project.bean;

import javax.swing.JButton;
import javax.swing.JSpinner;

public class CartBean {
	private int proIdx;
	private String proName;
	private MemberBean mb;
	private int quantity;
	private int proPrice;
	private int payment;
	private JSpinner jsp;
	private JButton btn;
	
	public CartBean() {
		proIdx = -1;
		mb = new MemberBean();
		quantity = -1;
	}
	
	public int getProIdx() {
		return proIdx;
	}

	public void setProIdx(int proIdx) {
		this.proIdx = proIdx;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public int getProPrice() {
		return proPrice;
	}

	public void setProPrice(int proPrice) {
		this.proPrice = proPrice;
	}

	public JSpinner getJsp() {
		return jsp;
	}

	public void setJsp(JSpinner jsp) {
		this.jsp = jsp;
	}

	public JButton getBtn() {
		return btn;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public MemberBean getMb() {
		return mb;
	}

	public void setMb(MemberBean mb) {
		this.mb = mb;
	}
	
}
