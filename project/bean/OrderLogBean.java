package project.bean;

import javax.swing.JButton;
import javax.swing.JSpinner;

public class OrderLogBean {
	private int memIdx;
	private String memName;
	private ProductBean pb;
	private int quantity;
	private int status;
	private int payment;
	private String orderTimeStamp;
	private String finalTimeStamp;
	private JButton btn;
	
	public OrderLogBean() {
		this.memIdx = -1;
		this.pb = new ProductBean();
		this.btn = new JButton();
	}

	public int getMemIdx() {
		return memIdx;
	}

	public void setMemIdx(int memIdx) {
		this.memIdx = memIdx;
	}

	public ProductBean getPb() {
		return pb;
	}

	public void setPb(ProductBean pb) {
		this.pb = pb;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderTimeStamp() {
		return orderTimeStamp;
	}
	
	public String getOrderDate() {
		String date[] = this.orderTimeStamp.split(" ");
		return date[0];
	}

	public void setOrderTimeStamp(String orderTimeStamp) {
		this.orderTimeStamp = orderTimeStamp;
	}

	public String getFinalTimeStamp() {
		return finalTimeStamp;
	}
	
	public String getFinalDate() {
		String date[] = this.finalTimeStamp.split(" ");
		return date[0];
	}

	public void setFinalTimeStamp(String finalTimeStamp) {
		this.finalTimeStamp = finalTimeStamp;
	}

	public JButton getBtn() {
		return btn;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}
}
