package project.ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Vector;
import java.util.spi.CurrencyNameProvider;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import project.bean.Constant;
import project.bean.MainProductBean;
import project.bean.MemberBean;
import project.db.ShopMgr;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Mainpage extends JFrame implements ActionListener,Runnable {

	private static Mainpage mp;
	private JPanel contentPane;
	private JTextField textFieldSearch;
	private MemberBean mem;
	private JPanel dataPanel;
	private JPanel panel;
	private JLabel lbSearchResult;
	private JLayeredPane layeredPane;
	private JButton btnPagePre;
	private JButton btnPageNext;
	private JLabel lbCurrentPage;
	private JButton btnSearch;
	private JComboBox comboBoxCategory;
	private JScrollPane scrollPane;
	private JLabel lbMainTitle;
	private int currentPage = 1;
	private int firstPage = 1;
	private String ImgName = "./IMG\\";
	private String selectedCate = "전체";
	private JLabel lbNoData;
	private boolean isSearchResult = false;
	private Vector<MainProductBean> mpbv = new Vector<>();
	private int pageBtnPanelX[] = { 242, 217, 192 };
	private int pageBtnPanelW[] = { 40, 90, 140 };
	private Vector<JButton> pageBtns = new Vector<>();
	private ShopMgr sm = ShopMgr.getInstance();
	private JButton btnCart;
	private JButton btnSetting;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private boolean isPopUp;
	private JPanel panelPopUp;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lbPopUpGrade;
	private JLabel lbPopUpPoint;
	private JButton btnMyPage;
	private JLabel lbPopUpName;
	private JButton btnClosePopUp;

	public static Mainpage getInstance() {
		if (mp == null) {
			mp = new Mainpage(new MemberBean());
		} return mp;
	}
	
	public void refresh(MemberBean mem) {
		this.mem = mem;
		lbMainTitle.setText(mem.getName()+"님 환영합니다.");
		String type = mem.getType().replaceAll(" ", "");
		setTitle("NICE 메인 페이지 - "+mem.getName()+"님");
		lbPopUpName.setText(mem.getName()+"님");
		lbPopUpGrade.setText("등급 : "+mem.getGrade());
		lbPopUpPoint.setText("포인트 : "+mem.getPoint());
		if (type.equals("일반")) {
			btnSetting.setVisible(false);
		} else {
			btnSetting.setVisible(true);
		}
	}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MemberBean mem = new MemberBean();
//					mem.setName("서수정");
//					Mainpage frame = Mainpage.getInstance();
//					frame.refresh(mem);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	private Mainpage(MemberBean mem) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		this.mem = mem;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 851);
		setTitle("NICE 메인 페이지 - "+mem.getName()+"님");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		
		lbNoData = new JLabel("찾으시는 상품이 존재하지 않습니다");
		lbNoData.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbNoData.setHorizontalTextPosition(SwingConstants.CENTER);
		lbNoData.setHorizontalAlignment(SwingConstants.CENTER);
		lbNoData.setOpaque(true);
		lbNoData.setBounds(112, 360, 300, 300);
		lbNoData.setVisible(false);
		
		panelPopUp = new JPanel();
		panelPopUp.setBounds(524, 0, 150, 812);
		panelPopUp.setBackground(new Color(200,200,200,240));
		
		contentPane.add(panelPopUp);
		panelPopUp.setLayout(null);
		
		btnClosePopUp = new JButton("");
		btnClosePopUp.setContentAreaFilled(false);
		btnClosePopUp.setBorderPainted(false);
		btnClosePopUp.setBounds(12, 10, 30, 30);
		btnClosePopUp.setIcon(resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
		btnClosePopUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClosePopUp.setIcon(resizeIcon(new ImageIcon("./IMG\\XFill.png"), 30, 30));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnClosePopUp.setIcon(resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
			}
		});
		btnClosePopUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnClosePopUp.setIcon(resizeIcon(new ImageIcon("./IMG\\XNull.png"), 30, 30));
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
				myp.refresh(mp.mem, Constant.PREPAGEMAIN);
				myp.setLocationRelativeTo(mp);
				myp.setVisible(true);
				togglePopUp();
				dispose();
			}
		});
		panelPopUp.add(btnMyPage);
		contentPane.add(lbNoData);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 524, 812);
		contentPane.add(layeredPane);

		DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<>();
		sm.selectCate(dcbm);
		comboBoxCategory = new JComboBox(dcbm);
		comboBoxCategory.setBounds(12, 193, 86, 32);
		comboBoxCategory.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		comboBoxCategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isSearchResult = false;
				currentPage = 1;
				firstPage = 1;
				selectedCate = (String) comboBoxCategory.getSelectedItem();
				addDataByC(selectedCate);
			}
		});
		layeredPane.add(comboBoxCategory);

		textFieldSearch = new JTextField();
		textFieldSearch.setOpaque(false);
		textFieldSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		textFieldSearch.setBounds(160, 199, 318, 21);
		textFieldSearch.addActionListener(this);
		layeredPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);

		btnSearch = new JButton("");
		btnSearch.setFocusPainted(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setOpaque(false);
		btnSearch.setBounds(129, 198, 29, 23);
		btnSearch.addActionListener(this);
		layeredPane.add(btnSearch);

		lbMainTitle = new JLabel(mem.getName() + "님 환영합니다.");
		lbMainTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbMainTitle.setBounds(0, 68, 524, 59);
		lbMainTitle.setBackground(Color.LIGHT_GRAY);
		lbMainTitle.setOpaque(true);
		layeredPane.add(lbMainTitle);

		lbSearchResult = new JLabel("New label");
		lbSearchResult.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lbSearchResult.setBounds(12, 258, 106, 21);
		lbSearchResult.setBackground(Color.WHITE);
		lbSearchResult.setOpaque(true);
		layeredPane.add(lbSearchResult);

		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2, 10, 10));
		dataPanel.setBorder(new EmptyBorder(0, 15, 0, 0));

		scrollPane = new JScrollPane(dataPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 289, 500, 462);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		layeredPane.add(scrollPane);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(192, 761, 140, 40);
		panel.setLayout(new GridLayout(1, 0, 10, 10));
		for (int i = 1; i <= 3; i++) {
			JButton btn = new JButton(i + "");
			btn.setBorder(new EmptyBorder(0, 0, 0, 0));
			btn.setContentAreaFilled(false);
			btn.setPreferredSize(new Dimension(20, 20));
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			panel.add(btn);
		}
		layeredPane.add(panel);

		btnPagePre = new JButton("<");
		btnPagePre.setContentAreaFilled(false);
		btnPagePre.setBounds(130, 761, 50, 40);
		btnPagePre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePage(-1);
				if (isSearchResult) {
					addDataByS(textFieldSearch.getText());
				} else {
					addDataByC(selectedCate);
				}
			}
		});
		layeredPane.add(btnPagePre);

		btnPageNext = new JButton(">");
		btnPageNext.setContentAreaFilled(false);
		btnPageNext.setBounds(344, 761, 50, 40);
		btnPageNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePage(1);
				if (isSearchResult) {
					addDataByS(textFieldSearch.getText());
				} else {
					addDataByC(selectedCate);
				}
			}
		});
		layeredPane.add(btnPageNext);

		lbCurrentPage = new JLabel("New label");
		lbCurrentPage.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbCurrentPage.setHorizontalAlignment(SwingConstants.CENTER);
		lbCurrentPage.setBounds(137, 249, 250, 30);
		layeredPane.add(lbCurrentPage);
		
		JButton btnMyAc = new JButton("");
		btnMyAc.setBorderPainted(false);
		btnMyAc.setContentAreaFilled(false);
		btnMyAc.setIcon(resizeIcon(new ImageIcon("./IMG\\\\MyAcNull.png"), 40, 40));
		btnMyAc.setBounds(472, 10, 40, 40);
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
		layeredPane.add(btnMyAc);
		
		JButton btnLogOut = new JButton("");
		btnLogOut.setBorderPainted(false);
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setBounds(420, 10, 40, 40);
		btnLogOut.setIcon(resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 40, 40));
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogOut.setIcon(resizeIcon(new ImageIcon("./IMG\\\\LogOutFill.png"), 40, 40));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogOut.setIcon(resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 40, 40));
			}
		});
		btnLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogOut.setIcon(resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 40, 40));
				LoginPage lp = LoginPage.getInstance();
				lp.setLocationRelativeTo(mp);
				lp.setVisible(true);
				dispose();
			}
		});
		layeredPane.add(btnLogOut);
		
		btnCart = new JButton("");
		btnCart.setBorderPainted(false);
		btnCart.setContentAreaFilled(false);
		btnCart.setBounds(368, 10, 40, 40);
		btnCart.setIcon(resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 40, 40));
		btnCart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCart.setIcon(resizeIcon(new ImageIcon("./IMG\\\\CartFill.png"), 40, 40));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCart.setIcon(resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 40, 40));
			}
		});
		btnCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCart.setIcon(resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 40, 40));
				Shopcart sc = Shopcart.getInstance();
				sc.refresh(mp.mem, Constant.PREPAGEMAIN);
				sc.setLocationRelativeTo(mp);
				sc.setVisible(true);
				dispose();
			}
		});
		layeredPane.add(btnCart);
		
		btnSetting = new JButton("");
		btnSetting.setBorderPainted(false);
		btnSetting.setContentAreaFilled(false);
		btnSetting.setBounds(316, 10, 40, 40);
		btnSetting.setIcon(resizeIcon(new ImageIcon("./IMG\\SettingNull.png"), 40, 40));
		btnSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSetting.setIcon(resizeIcon(new ImageIcon("./IMG\\SettingFill.png"), 40, 40));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSetting.setIcon(resizeIcon(new ImageIcon("./IMG\\SettingNull.png"), 40, 40));
			}
		});
		btnSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSetting.setIcon(resizeIcon(new ImageIcon("./IMG\\SettingNull.png"), 40, 40));
				AdminMain am = AdminMain.getinstance();
				am.setLocationRelativeTo(mp);
				am.setVisible(true);
				dispose();
			}
		});
		btnSetting.setVisible(false);
		layeredPane.add(btnSetting);
		
		rdbtnNewRadioButton = new JRadioButton("가격 낮은 순");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.setOpaque(false);
		rdbtnNewRadioButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		rdbtnNewRadioButton.setBounds(420, 159, 96, 23);
		rdbtnNewRadioButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (isSearchResult) {
					addDataByS(textFieldSearch.getText());
				} else {
					addDataByC(selectedCate);
				}
			}
		});
		layeredPane.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("가격 높은 순");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton_1.setOpaque(false);
		rdbtnNewRadioButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		rdbtnNewRadioButton_1.setBounds(320, 159, 96, 23);
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (isSearchResult) {
					addDataByS(textFieldSearch.getText());
				} else {
					addDataByC(selectedCate);
				}
			}
		});
		layeredPane.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("판매 인기 순");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setSelected(true);
		rdbtnNewRadioButton_2.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton_2.setOpaque(false);
		rdbtnNewRadioButton_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		rdbtnNewRadioButton_2.setBounds(220, 159, 96, 23);
		rdbtnNewRadioButton_2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (isSearchResult) {
					addDataByS(textFieldSearch.getText());
				} else {
					addDataByC(selectedCate);
				}
			}
		});
		layeredPane.add(rdbtnNewRadioButton_2);

		JLabel lbBack = new JLabel("");
//		lbBack.setIcon(new ImageIcon("C:\\Java\\eclipse-workspace\\NICE_PROJECT\\IMG\\MainPage.png"));
		lbBack.setIcon(resizeIcon(new ImageIcon("./IMG\\MainPage.png"), 524, 761));
		lbBack.setBounds(0, 0, 524, 761);
		contentPane.add(lbBack);

		addDataByC("전체");
		validate();
	}

	public JPanel createData(MainProductBean mpb) {
		JPanel panel = new JPanel();
//		panel.setBounds(10,y+10,420, 50);
//		panel.setSize(420, 50);
//		panel.setPreferredSize(new Dimension(420, 50));
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		JLabel imgLb = new JLabel("NO IMG");
		imgLb.setHorizontalAlignment(JLabel.CENTER);
		ImageIcon ic = new ImageIcon(mpb.getPb().getImgAddress());
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(210, 210, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		imgLb.setIcon(ic);
		imgLb.setBounds(10, 10, 210, 210);
		imgLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgLb.setBorder(new LineBorder(Color.BLACK));
		panel.add(imgLb);

		JLabel nameLb = new JLabel(mpb.getPb().getProName());
		nameLb.setBounds(10, 220, 210, 30);
		nameLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nameLb.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(nameLb);
		
		JLabel cateLb = new JLabel(mpb.getPb().getCateName());
		cateLb.setBounds(10, 250, 210, 30);
		cateLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cateLb.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(cateLb);

		JLabel priceLb = new JLabel(toWon(mpb.getPb().getPrice()));
		priceLb.setBounds(10, 280, 210, 30);
		priceLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		priceLb.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(priceLb);

		JButton btn = new JButton();
		btn.setBounds(10, 10, 210, 290);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.addActionListener(this);
		panel.add(btn);

		mpb.setBtn(btn);

		return panel;
	}

	public void addDataByC(String cate) {
		dataPanel.removeAll();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
		int mode = -1;
		if (rdbtnNewRadioButton_2.isSelected()) {
			mode = Constant.SORTPOPULARITY;
		} else if (rdbtnNewRadioButton_1.isSelected()) {
			mode = Constant.SORTPRICEDESC;
		} else {
			mode = Constant.SORTPRICEASC;
		}
		mpbv = sm.selectPro(cate, mode);
		lbSearchResult.setText(mpbv.size() + "개 결과");
		int pageAmount = (int) Math.ceil(mpbv.size() / 20.0);
		boolean isLastPage = false;
		int lastIdx = 0;
		pageSetting(pageAmount);
		if (pageAmount <= currentPage) {
			currentPage = pageAmount;
			isLastPage = true;
		}
		if (isLastPage) {
			lastIdx = mpbv.size();
		} else {
			lastIdx = currentPage * 20;
		}
		lbCurrentPage.setText("카테고리 : "+currentPage + "페이지");
		int createCnt = 0;
		for (int i = (currentPage - 1) * 20; i < lastIdx; i++) {
			JPanel p = createData(mpbv.get(i));
			p.setPreferredSize(new Dimension(230, 310));
			dataPanel.add(p);
			createCnt++;
		}
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		if (createCnt < 3) {
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			while (createCnt < 3) {
				JPanel nullPanel = new JPanel();
				nullPanel.setPreferredSize(new Dimension(230, 310));
				dataPanel.add(nullPanel);
				createCnt++;
			}
		}
		repaint();
		validate();
	}

	public void addDataByS(String word) {
		dataPanel.removeAll();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
		String trimWord = word.trim().replaceAll(" ", "");
		int mode = -1;
		if (rdbtnNewRadioButton_2.isSelected()) {
			mode = Constant.SORTPOPULARITY;
		} else if (rdbtnNewRadioButton_1.isSelected()) {
			mode = Constant.SORTPRICEDESC;
		} else {
			mode = Constant.SORTPRICEASC;
		}
		mpbv = sm.selectProBySearch(trimWord, mode);
		lbSearchResult.setText(mpbv.size() + "개 결과");
		if (mpbv.size() > 0) {
			lbNoData.setVisible(false);
			int pageAmount = (int) Math.ceil(mpbv.size() / 20.0);
			boolean isLastPage = false;
			int lastIdx = 0;
			pageSetting(pageAmount);
			if (pageAmount <= currentPage) {
				currentPage = pageAmount;
				isLastPage = true;
			}
			if (isLastPage) {
				lastIdx = mpbv.size();
			} else {
				lastIdx = currentPage * 20;
			}
			lbCurrentPage.setText("검색어 : "+ currentPage + "페이지");
			int createCnt = 0;
			for (int i = (currentPage - 1) * 20; i < lastIdx; i++) {
				JPanel p = createData(mpbv.get(i));
				p.setPreferredSize(new Dimension(230, 310));
				dataPanel.add(p);
				createCnt++;
			}
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			if (createCnt < 3) {
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				while (createCnt < 3) {
					JPanel nullPanel = new JPanel();
					nullPanel.setPreferredSize(new Dimension(230, 310));
					dataPanel.add(nullPanel);
					createCnt++;
				}
			}
		} else {
			lbCurrentPage.setText("검색어 : "+ currentPage + "페이지");
			pageSetting(1);
			lbNoData.setVisible(true);
		}
		repaint();
		validate();
	}

	public void changePage(int values) {
		currentPage += 3 * values;
		currentPage--;
		currentPage /= 3;
		currentPage = currentPage * 3 + 1;
		firstPage = currentPage;
	}

	public void pageSetting(int pageCnt) {
		panel.removeAll();
		pageBtns.removeAllElements();
		btnPageNext.setVisible(false);
		btnPagePre.setVisible(false);
		int setIdx = 0;
		switch (pageCnt - firstPage + 1) {
		case 1:
			setIdx = 0;
			break;
		case 2:
			setIdx = 1;
			break;
		default:
			setIdx = 2;
			btnPageNext.setVisible(true);
			break;
		}
		if (currentPage > 3) {
			btnPagePre.setVisible(true);
		}
		panel.setBounds(pageBtnPanelX[setIdx], 761, pageBtnPanelW[setIdx], 40);
		for (int i = 1; i <= pageCnt; i++) {
			JButton btn = new JButton(i + "");
			btn.setBorder(new EmptyBorder(0, 0, 0, 0));
			btn.setContentAreaFilled(false);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.addActionListener(this);
			pageBtns.add(btn);
			if (firstPage <= i && i <= firstPage + 2) {
				panel.add(btn);
			}
		}
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
	
	public Icon resizeIcon(ImageIcon ii, int w, int h) {
		ImageIcon ic = ii;
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		return ic;
	}
	
	public void togglePopUp() {
		new Thread(this).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int cases = -1;
		if (obj == textFieldSearch || obj == btnSearch) {
			currentPage = 1;
			firstPage = 1;
			if (comboBoxCategory.getSelectedIndex() != 0) {
				comboBoxCategory.setSelectedIndex(0);
			}
			isSearchResult = true;
			addDataByS(textFieldSearch.getText());
		} else {
			JButton btn = (JButton) obj;
			for (int i = 0; i < pageBtns.size(); i++) {
				if (btn == pageBtns.get(i)) {
					cases = 0;
					currentPage = i + 1;
					if (isSearchResult) {
						addDataByS(textFieldSearch.getText());
					} else {
						addDataByC(selectedCate);
					}
				}
			}
			if (cases == -1) {
				MainProductBean mpb = null;
				for (int i = 0; i < mpbv.size(); i++) {
					if (btn == mpbv.get(i).getBtn()) {
						mpb = mpbv.get(i);
						break;
					}
				}
				ProductInfo pi = ProductInfo.getInstance();
				pi.refresh(mpb.getPb(), mem);
				pi.setLocationRelativeTo(mp);
				pi.setVisible(true);
				dispose();
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < mpbv.size(); i++) {
			mpbv.get(i).getBtn().setEnabled(isPopUp);
		}
		for (int i = 0; i < pageBtns.size(); i++) {
			pageBtns.get(i).setEnabled(isPopUp);
		}
		Component[] components = layeredPane.getComponents();
		for (Component component : components) {
		    component.setEnabled(isPopUp);
		}
		if (isPopUp) {
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else {
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		}
		try {
			if (isPopUp) {
				isPopUp = !isPopUp;
				btnClosePopUp.setEnabled(false);
				for (int i = 0; i < 150; i++) {
					int x = panelPopUp.getX() + 1;
					panelPopUp.setLocation(x, panelPopUp.getY());
					Thread.sleep((long) Math.pow(i/50, 2));
				}
			} else {
				isPopUp = !isPopUp;
				for (int i = 0; i < 150; i++) {
					int x = panelPopUp.getX() - 1;
					panelPopUp.setLocation(x, panelPopUp.getY());
					Thread.sleep((long) Math.pow(i/50, 2));
				}
				btnClosePopUp.setEnabled(true);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
