package project.ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import project.bean.*;
import project.db.ShopMgr;

import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Font;

public class Shopcart extends JFrame implements ChangeListener, ActionListener, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Shopcart sc;
	private JScrollPane sp;
	ImageIcon icon;
	JPanel dataPanel;
	ShopMgr sm;
	String ImgName = "./IMG\\";
	private JLabel noData;
	private MemberBean mem;
	private JLabel subTitle;
	private Vector<CartBean> cartBV = new Vector<>();
	private CustomMethod cm = new CustomMethod();
	private JLabel totalPriceLb;
	public int prePage;
	private long totalPrice;
	private boolean isPopUp;
	private JPanel background;
	private JPanel panelPopUp;
	private JLabel lbPopUpGrade;
	private JLabel lbPopUpPoint;
	private JButton btnMyPage;
	private JLabel lbPopUpName;
	private JButton btnClosePopUp;

	public static Shopcart getInstance() {
		if (sc == null) {
			sc = new Shopcart(new MemberBean());
		}
		return sc;
	}

	public void refresh(MemberBean mem, int prePage) {
		this.mem = mem;
		this.prePage = prePage;
		setTitle(mem.getName() + "님의 장바구니 페이지");
		subTitle.setText(mem.getName() + "님의 장바구니 페이지");
		lbPopUpName.setText(mem.getName() + "님");
		lbPopUpGrade.setText("등급 : " + mem.getGrade());
		lbPopUpPoint.setText("포인트 : " + mem.getPoint());
		sm.updateMember(mem);
		addData();
	}

	private Shopcart(MemberBean mem) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420, 665);
		setVisible(true);
		setLocationRelativeTo(null);
		// DB연결
		sm = ShopMgr.getInstance();

		this.mem = mem;
		setTitle(mem.getName() + "님의 장바구니 페이지");
		icon = new ImageIcon(ImgName + "CartBack.PNG");

		// 배경 Panel 생성후 컨텐츠페인으로 지정
		background = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {

				g.drawImage(icon.getImage(), 0, 0, null);

				setOpaque(false);
				super.paintComponent(g);
			}
		};
		background.setBackground(SystemColor.menu);
		background.setForeground(Color.LIGHT_GRAY);
		background.setLayout(null);

		panelPopUp = new JPanel();
		panelPopUp.setBounds(405, 0, 150, 635);
		panelPopUp.setBackground(new Color(200, 200, 200, 240));

		background.add(panelPopUp);
		panelPopUp.setLayout(null);

		btnClosePopUp = new JButton("");
		btnClosePopUp.setContentAreaFilled(false);
		btnClosePopUp.setBorderPainted(false);
		btnClosePopUp.setBounds(12, 10, 30, 30);
		btnClosePopUp.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
		btnClosePopUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClosePopUp.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\XFill.png"), 30, 30));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnClosePopUp.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
			}
		});
		btnClosePopUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnClosePopUp.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
				togglePopUp();
			}
		});
		panelPopUp.add(btnClosePopUp);

		lbPopUpName = new JLabel("New label");
		lbPopUpName.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbPopUpName.setBounds(12, 44, 126, 30);
		panelPopUp.add(lbPopUpName);

		lbPopUpGrade = new JLabel("New label");
		lbPopUpGrade.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbPopUpGrade.setBounds(12, 84, 126, 30);
		panelPopUp.add(lbPopUpGrade);

		lbPopUpPoint = new JLabel("New label");
		lbPopUpPoint.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbPopUpPoint.setBounds(12, 124, 126, 30);
		panelPopUp.add(lbPopUpPoint);

		btnMyPage = new JButton("마이페이지");
		btnMyPage.setContentAreaFilled(false);
		btnMyPage.setBounds(12, 164, 126, 30);
		btnMyPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyPage myp = MyPage.getInstance();
				myp.refresh(sc.mem, Constant.PREPAGECART);
				myp.setLocationRelativeTo(sc);
				myp.setVisible(true);
				togglePopUp();
				dispose();
			}
		});
		panelPopUp.add(btnMyPage);

		noData = new JLabel();
		noData.setBounds(52, 172, 300, 300);
		ImageIcon ic = new ImageIcon(ImgName + "NoProduct.png");
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		noData.setIcon(ic);
		noData.setOpaque(true);
		noData.setVisible(false);
		background.add(noData);

//		scrollPane = new JScrollPane(background);
		totalPriceLb = new JLabel("0");
		totalPriceLb.setBounds(0, 530, 400, 30);
		totalPriceLb.setBackground(Color.WHITE);
		totalPriceLb.setOpaque(true);
		totalPriceLb.setFont(new Font("돋움체", Font.BOLD, 20));
		totalPriceLb.setHorizontalAlignment(JLabel.RIGHT);
		background.add(totalPriceLb);

		JButton btnMyAc = new JButton("");
		btnMyAc.setBorderPainted(false);
		btnMyAc.setContentAreaFilled(false);
		btnMyAc.setIcon(resizeIcon(new ImageIcon("./IMG\\\\MyAcNull.png"), 40, 40));
		btnMyAc.setBounds(350, 10, 40, 40);
		btnMyAc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMyAc.setIcon(resizeIcon(new ImageIcon("./IMG\\\\MyAcFill.png"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMyAc.setIcon(resizeIcon(new ImageIcon("./IMG\\\\MyAcNull.png"), 40, 40));
			}
		});
		btnMyAc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePopUp();
			}
		});
		background.add(btnMyAc);

		JButton home = new JButton("");
		home.setIcon(resizeIcon(new ImageIcon(ImgName + "HomeNull.PNG"), 40, 40));
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				home.setIcon(resizeIcon(new ImageIcon(ImgName + "HomeNull.PNG"), 40, 40));
				Mainpage mp = Mainpage.getInstance();
				mp.refresh(sc.mem);
				mp.setLocationRelativeTo(sc);
				mp.setVisible(true);
				dispose();
			}
		});
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				home.setIcon(resizeIcon(new ImageIcon(ImgName + "HomeFill.PNG"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				home.setIcon(resizeIcon(new ImageIcon(ImgName + "HomeNull.PNG"), 40, 40));
			}
		});
		home.setBounds(250, 10, 40, 40);
		home.setOpaque(true);
		home.setContentAreaFilled(false);
		home.setBorderPainted(false);
		background.add(home);

		JButton logout = new JButton("");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout.setIcon(resizeIcon(new ImageIcon(ImgName + "LogOutNull.PNG"), 40, 40));
				LoginPage lp = LoginPage.getInstance();
				lp.setLocationRelativeTo(sc);
				lp.setVisible(true);
				dispose();
			}
		});
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logout.setIcon(resizeIcon(new ImageIcon(ImgName + "LogOutFill.PNG"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logout.setIcon(resizeIcon(new ImageIcon(ImgName + "LogOutNull.PNG"), 40, 40));
			}
		});
		logout.setIcon(resizeIcon(new ImageIcon(ImgName + "LogOutNull.PNG"), 40, 40));
		logout.setBounds(300, 10, 40, 40);
		logout.setContentAreaFilled(false);
		logout.setBorderPainted(false);
		background.add(logout);

		dataPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		dataPanel.setBackground(Color.WHITE);

		sp = new JScrollPane(dataPanel);
		sp.setBounds(0, 120, 404, 404);
		sp.getVerticalScrollBar().setUnitIncrement(16);
		background.add(sp);

		subTitle = new JLabel(mem.getName() + "님의 장바구니");
		subTitle.setFont(new Font("돋움체", Font.PLAIN, 18));
		subTitle.setHorizontalAlignment(JLabel.CENTER);
		subTitle.setBounds(0, 84, 405, 33);
		background.add(subTitle);

		addData();

		JButton immediatePurchase = new JButton("");
		immediatePurchase.setIcon(new ImageIcon(ImgName + "주문하기.png"));
		immediatePurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(sc, "장바구니에 있는 모든 상품을 주문합니다.", "안내",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					boolean flag = false;
					if (sc.mem.getPoint() > 0) {
						flag = (JOptionPane.showConfirmDialog(sc, sc.mem.getPoint() + " 포인트가 있습니다. 사용하시겠습니까?", "안내",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
					}
					confirmOrder(flag);
				} else {
					JOptionPane.showMessageDialog(sc, "결재를 취소했습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		immediatePurchase.setBounds(0, 570, 405, 58);
		background.add(immediatePurchase);

		JButton btn_back = new JButton("");
		btn_back.setIcon(resizeIcon(new ImageIcon("./IMG\\BACK.png"), 40, 40));
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sc.prePage == Constant.PREPAGEMAIN) {
					Mainpage mp = Mainpage.getInstance();
					mp.refresh(sc.mem);
					mp.setLocationRelativeTo(sc);
					mp.setVisible(true);
					dispose();
				} else if (sc.prePage == Constant.PREPAGEPRODUCTINFO) {
					ProductInfo pi = ProductInfo.getInstance();
					pi.refresh(pi.pb, sc.mem);
					pi.setLocationRelativeTo(sc);
					pi.setVisible(true);
					dispose();
				}
			}
		});
		btn_back.setContentAreaFilled(false);
		btn_back.setBounds(10, 10, 40, 40);
		background.add(btn_back);

//		setContentPane(scrollPane);
		setContentPane(background);
		validate();
	}

	public Icon resizeIcon(ImageIcon ii, int w, int h) {
		ImageIcon ic = ii;
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		return ic;
	}

	public JPanel createData(String proName, int proPrice, int proIdx, int quantity) {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		CartBean cb = new CartBean();

		JLabel imgLb = new JLabel("NO IMG");
		ImageIcon ic = new ImageIcon(ImgName + "product" + proIdx + ".png");
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		imgLb.setIcon(ic);
		imgLb.setBounds(10, 10, 180, 180);
		panel.add(imgLb);

		JLabel nameLb = new JLabel(proName);
		nameLb.setBounds(200, 30, 160, 60);
		panel.add(nameLb);

		JLabel priceLb = new JLabel(toWon(proPrice));
		priceLb.setBounds(200, 90, 160, 60);
		panel.add(priceLb);

		JLabel quanLb = new JLabel("수량");
		quanLb.setBounds(200, 150, 40, 40);
		panel.add(quanLb);

		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999999999, 1);
		JSpinner quanSp = new JSpinner(model);
		quanSp.setBounds(230, 150, 90, 40);
		quanSp.setFont(new Font("돋움체", Font.BOLD, 20));
		quanSp.setValue(quantity);
		quanSp.addChangeListener(this);
		panel.add(quanSp);

		JButton btn = new JButton();
		btn.setIcon(cm.resizeIcon(new ImageIcon(ImgName + "XNull.PNG"), 30, 30));
		btn.setBounds(355, 0, 30, 30);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btn.setIcon(cm.resizeIcon(new ImageIcon(ImgName + "XNull.PNG"), 30, 30));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setIcon(cm.resizeIcon(new ImageIcon(ImgName + "XFill.PNG"), 30, 30));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn.setIcon(cm.resizeIcon(new ImageIcon(ImgName + "XNull.PNG"), 30, 30));
			}
		});
		btn.addActionListener(this);
		panel.add(btn);

		cb.setMb(mem);
		cb.setProIdx(proIdx);
		cb.setProName(proName);
		cb.setProPrice(proPrice);
		cb.setQuantity(quantity);
		cb.setPayment(cb.getProPrice() * cb.getQuantity());
		cb.setJsp(quanSp);
		cb.setBtn(btn);
		cartBV.add(cb);
		totalPrice += cb.getProPrice() * cb.getQuantity();
		totalPriceLb.setText("합계 : " + toWon(totalPrice));

		return panel;
	}

	public void addData() {
		dataPanel.removeAll();
		totalPrice = 0;
		HashMap<Integer, CartBean> cartMap = sm.selectCart(mem.getIdx(), Constant.CARTORDER);
		if (cartMap.size() > 0) {
			noData.setVisible(false);
			for (int key : cartMap.keySet()) {
				String proName = cartMap.get(key).getProName();
				int proPrice = cartMap.get(key).getProPrice();
				int proIdx = cartMap.get(key).getProIdx();
				int quantity = cartMap.get(key).getQuantity();
				JPanel p = createData(proName, proPrice, proIdx, quantity);
				p.setPreferredSize(new Dimension(370, 200));
				dataPanel.add(p);
			}
		} else {
			noData.setVisible(true);
			totalPriceLb.setText("합계 : 0원");
		}
		repaint();
	}

	public String toWon(long Amount) {
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (Amount > (long) Math.pow(10, 12)) {
				sb.append((long) Math.floor(Amount / (long) Math.pow(10, 12)) + "조");
				Amount %= (long) Math.pow(10, 12);
			} else if (Amount > (long) Math.pow(10, 8)) {
				sb.append((long) Math.floor(Amount / (long) Math.pow(10, 8)) + "억");
				Amount %= (long) Math.pow(10, 8);
			} else if (Amount > (long) Math.pow(10, 4)) {
				sb.append((long) Math.floor(Amount / (long) Math.pow(10, 4)) + "만");
				Amount %= (long) Math.pow(10, 4);
			} else {
				if (Amount == 0) {
					sb.append("원");
				} else {
					sb.append(Amount + "원");
				}
				break;
			}
		}
		return sb.toString();
	}

	private void confirmOrder(boolean usePoint) {
		if (cartBV.size() > 0) {
			int paymentOfAmount = 0;
			while (cartBV.size() != 0) {
				cartBV.get(0).setMb(mem);
				int temp = 0;
				if (usePoint && mem.getPoint() > 0) {
					if (cartBV.get(0).getPayment() <= mem.getPoint()) {
						temp = cartBV.get(0).getPayment();
						cartBV.get(0).setPayment(0);
						mem.setPoint(mem.getPoint() - temp);
					} else {
						temp = mem.getPoint();
						mem.setPoint(0);
						cartBV.get(0).setPayment(cartBV.get(0).getPayment() - temp);
					}
				}
				paymentOfAmount += cartBV.get(0).getPayment();
				if (!sm.updateCartFromPro(cartBV.get(0), Constant.CARTORDERCOMPLETE)) {
					JOptionPane.showMessageDialog(sc, "결제 중 문제가 발생하였습니다.\n다시 한번 확인해주세요.", "안내",
							JOptionPane.ERROR_MESSAGE);
					mem.setPoint(mem.getPoint() + temp);
					break;
				}
				cartBV.remove(0);
			}
			if (cartBV.size() == 0) {
				if (usePoint) {
					JOptionPane.showMessageDialog(sc, "상품 주문 및 결제가 완료되었습니다.\n 결제 금액 : " + toWon(paymentOfAmount), "안내",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					long pointTemp = 0;
					if (sc.mem.getGrade().equals(Constant.BRONZE)) {
						pointTemp = (long) (paymentOfAmount * (Constant.BRONZEPERPOINT));
					} else if (sc.mem.getGrade().equals(Constant.SILVER)) {
						pointTemp = (long) (paymentOfAmount * (Constant.SILVERPERPOINT));
					} else if (sc.mem.getGrade().equals(Constant.GOLD)) {
						pointTemp = (long) (paymentOfAmount * (Constant.GOLDPERPOINT));
					} else if (sc.mem.getGrade().equals(Constant.PLATINUM)) {
						pointTemp = (long) (paymentOfAmount * (Constant.PLATINUMPERPOINT));
					} else if (sc.mem.getGrade().equals(Constant.DIAMOND)) {
						pointTemp = (long) (paymentOfAmount * (Constant.DIAMONDPERPOINT));
					}
					this.mem.setPoint(this.mem.getPoint() + (int) pointTemp);
					JOptionPane.showMessageDialog(sc,
							"상품 주문 및 결제가 완료되었습니다.\n 결제 금액 : " + toWon(paymentOfAmount) + "\n" + pointTemp + "포인트 적립",
							"안내", JOptionPane.INFORMATION_MESSAGE);
				}
				addData();
			}
		} else {
			JOptionPane.showMessageDialog(sc, "결제 할 상품이 없습니다.\n다시 한번 확인해주세요.", "안내", JOptionPane.ERROR_MESSAGE);
		}
		gradeCheck(mem);
	}
	
	public void gradeCheck(MemberBean mb) {
		if (this.mem.getIdx() != 0) {
			String oldGrade = this.mem.getGrade();
			long Amount = sm.selectConsumption(mb.getIdx());
			if (Amount >= Constant.GRADEDIAMOND) {
				mb.setGrade(Constant.DIAMOND);
			} else if (Amount >= Constant.GRADEPLATINUM) {
				mb.setGrade(Constant.PLATINUM);
			} else if (Amount >= Constant.GRADEGOLD) {
				mb.setGrade(Constant.GOLD);
			} else if (Amount >= Constant.GRADESILVER) {
				mb.setGrade(Constant.SILVER);
			} else {
				mb.setGrade(Constant.BRONZE);
			}
			if (!mb.getGrade().equals(oldGrade)) {
				JOptionPane.showMessageDialog(sc, "회원 등급이 상승하였습니다. 축하합니다.\n"+oldGrade+"->"+mb.getGrade());
			}
		} refresh(mb, prePage);
	}

//	public static void main(String[] args) {
//		Shopcart frame = new Shopcart("서수정",1);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(420, 665);
//		frame.setVisible(true);
//	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner jsp = (JSpinner) e.getSource();
		CartBean cb = null;
		int idx = -1;
		for (int i = 0; i < cartBV.size(); i++) {
			if (cartBV.get(i).getJsp() == jsp) {
				cb = cartBV.get(i);
				idx = i;
				break;
			}
		}
		long subtract = (int) jsp.getValue() - cb.getQuantity();
		cartBV.get(idx).setQuantity((int) jsp.getValue());
		cartBV.get(idx).setPayment(cartBV.get(idx).getQuantity() * cartBV.get(idx).getProPrice());
		totalPrice += cb.getProPrice() * subtract;
		totalPriceLb.setText("합계 : " + toWon(totalPrice));
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		CartBean cb = null;
		int idx = -1;
		for (int i = 0; i < cartBV.size(); i++) {
			if (btn == cartBV.get(i).getBtn()) {
				cb = cartBV.get(i);
				idx = i;
				break;
			}
		}
		if (sm.updateCartFromPro(cb, 2)) {
			JOptionPane.showMessageDialog(sc, "장바구니에서 해당 상품이 삭제되었습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
			cartBV.remove(idx);
			addData();
		} else {
			JOptionPane.showMessageDialog(sc, "상품삭제 중 문제가 발생하였습니다.\n다시 한번 확인해주세요.", "안내", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void togglePopUp() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		for (int i = 0; i < cartBV.size(); i++) {
			cartBV.get(i).getBtn().setVisible(isPopUp);
			cartBV.get(i).getJsp().setEnabled(isPopUp);
		}
		Component[] components = background.getComponents();
		for (Component component : components) {
		    component.setEnabled(isPopUp);
		}
		if (isPopUp) {
			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else {
			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		}
		try {
			if (isPopUp) {
				isPopUp = !isPopUp;
				btnClosePopUp.setEnabled(false);
				for (int i = 0; i < 150; i++) {
					int x = panelPopUp.getX() + 1;
					panelPopUp.setLocation(x, panelPopUp.getY());
					Thread.sleep((long) Math.pow(i / 50, 2));
				}
			} else {
				isPopUp = !isPopUp;
				for (int i = 0; i < 150; i++) {
					int x = panelPopUp.getX() - 1;
					panelPopUp.setLocation(x, panelPopUp.getY());
					Thread.sleep((long) Math.pow(i / 50, 2));
				}
				btnClosePopUp.setEnabled(true);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
