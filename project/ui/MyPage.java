package project.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import project.bean.Constant;
import project.bean.CustomMethod;
import project.bean.MemberBean;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPage extends JFrame {

	private static MyPage myp;
	public int prePage;
	private JPanel contentPane;
	private MemberBean mb = new MemberBean();
	private CustomMethod cm = new CustomMethod();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MyPage frame = new MyPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static MyPage getInstance() {
		if (myp == null) {
			myp = new MyPage();
		} return myp;
	}
	
	public void refresh(MemberBean mb, int prePage) {
		this.mb = mb;
		this.prePage = prePage;
		setTitle(mb.getName()+"님 마이페이지");
	}
	
	/**
	 * Create the frame.
	 */
	private MyPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 405);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("");
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setBounds(12, 10, 30, 30);
		btnBack.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\BACK.png"), 30, 30));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myp.prePage == Constant.PREPAGEMAIN) {
					Mainpage mp = Mainpage.getInstance();
					mp.setLocationRelativeTo(myp);
					mp.refresh(myp.mb);
					mp.setVisible(true);
					dispose();
				} else if (myp.prePage == Constant.PREPAGEPRODUCTINFO) {
					ProductInfo pi = ProductInfo.getInstance();
					pi.setLocationRelativeTo(myp);
					pi.refresh(pi.pb, myp.mb);
					pi.setVisible(true);
					dispose();
				} else if (myp.prePage == Constant.PREPAGECART) {
					Shopcart sc = Shopcart.getInstance();
					sc.setLocationRelativeTo(myp);
					sc.refresh(myp.mb, sc.prePage);
					sc.setVisible(true);
					dispose();
				}
			}
		});
		contentPane.add(btnBack);
		
		JButton btnLogOut = new JButton("");
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setBorderPainted(false);
		btnLogOut.setBounds(442, 10, 30, 30);
		btnLogOut.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 30, 30));
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogOut.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\LogOutFill.png"), 30, 30));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogOut.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 30, 30));
			}
		});
		btnLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogOut.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\LogOutNull.png"), 30, 30));
				LoginPage lp = LoginPage.getInstance();
				lp.setLocationRelativeTo(null);
				lp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnLogOut);
		
		JButton btnHome = new JButton("");
		btnHome.setContentAreaFilled(false);
		btnHome.setBorderPainted(false);
		btnHome.setBounds(400, 10, 30, 30);
		btnHome.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 30, 30));
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHome.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\HomeFill.png"), 30, 30));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHome.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 30, 30));
			}
		});
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHome.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 30, 30));
				Mainpage mp = Mainpage.getInstance();
				mp.setLocationRelativeTo(myp);
				mp.refresh(myp.mb);
				mp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnHome);
		
		JButton btnCart = new JButton("");
		btnCart.setContentAreaFilled(false);
		btnCart.setBorderPainted(false);
		btnCart.setBounds(358, 10, 30, 30);
		btnCart.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 30, 30));
		btnCart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCart.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\CartFill.png"), 30, 30));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCart.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 30, 30));
			}
		});
		btnCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCart.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\CartNull.png"), 30, 30));
				Shopcart sc = Shopcart.getInstance();
				sc.refresh(myp.mb, Constant.PREPAGEMAIN);
				sc.setLocationRelativeTo(myp);
				sc.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnCart);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setBounds(54, 10, 69, 39);
		lbLogo.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\LOGO.png"), 69, 39));
		contentPane.add(lbLogo);
		
		JButton btnChangePw = new JButton("");
		btnChangePw.setContentAreaFilled(false);
		btnChangePw.setBorderPainted(false);
		btnChangePw.setBounds(28, 68, 200, 200);
		btnChangePw.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\PWNull.png"), 150, 150));
		btnChangePw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnChangePw.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\PWFill.png"), 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnChangePw.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\PWNull.png"), 150, 150));
			}
		});
		btnChangePw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnChangePw.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\PWNull.png"), 150, 150));
				PasswordChagePage pcp = PasswordChagePage.getInstance();
				pcp.setLocationRelativeTo(myp);
				pcp.refresh(myp.mb);
				pcp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnChangePw);
		
		JButton btnSearchOrder = new JButton("");
		btnSearchOrder.setContentAreaFilled(false);
		btnSearchOrder.setBorderPainted(false);
		btnSearchOrder.setBounds(256, 68, 200, 200);
		btnSearchOrder.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\OrderLogNull.png"), 150, 150));
		btnSearchOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSearchOrder.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\OrderLogFill.png"), 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSearchOrder.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\OrderLogNull.png"), 150, 150));
			}
		});
		btnSearchOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSearchOrder.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\\\OrderLogNull.png"), 150, 150));
				OrderLogPage olp = OrderLogPage.getInstance();
				olp.refresh(myp.mb);
				olp.setLocationRelativeTo(myp);
				olp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnSearchOrder);
		
		JLabel lblNewLabel = new JLabel("비밀번호 변경");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 267, 200, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("주문내역 확인");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(256, 267, 200, 30);
		contentPane.add(lblNewLabel_1);
	}

}
