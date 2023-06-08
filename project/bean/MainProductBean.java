package project.bean;

import javax.swing.JButton;
import javax.swing.JSpinner;

public class MainProductBean {
	
	private ProductBean pb = null;
	private JButton btn = new JButton();
	
	public ProductBean getPb() {
		return pb;
	}
	public void setPb(ProductBean pb) {
		this.pb = pb;
	}
	public JButton getBtn() {
		return btn;
	}
	public void setBtn(JButton btn) {
		this.btn = btn;
	}
	
}
