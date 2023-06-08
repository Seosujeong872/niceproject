package project.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import javax.swing.border.EmptyBorder;

import project.bean.CustomMethod;
import project.bean.MemberBean;
import project.db.ShopMgr;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame implements ActionListener{

	private static LoginPage lp;
	private JFrame frame;
	private JTextField tfId;
	private JPasswordField tfPw;
	private JButton btnLogin;
	private ShopMgr sm = ShopMgr.getInstance();
	private CustomMethod cm = new CustomMethod();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginPage window = new LoginPage();
//					window.setVisible(true);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static LoginPage getInstance() {
		if (lp == null) {
			lp = new LoginPage();
		}
		return lp;
	}

	/**
	 * Create the application.
	 */
	private LoginPage() {
		setTitle("NICE");
		setBounds(100, 100, 400, 623);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JLayeredPane panel = new JLayeredPane();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setBounds(0, 0, 384, 590);
		getContentPane().add(panel);
		panel.setLayout(null);

		tfId = new JTextField("");
		tfId.setOpaque(false);
		tfId.setFont(new Font("굴림", Font.PLAIN, 23));
		tfId.setBounds(39, 151, 306, 43);
		tfId.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tfId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfPw.requestFocus();
			}
		});
		panel.add(tfId);
		tfId.setColumns(10);

		tfPw = new JPasswordField();
		tfPw.setOpaque(false);
		tfPw.setFont(new Font("굴림체", Font.PLAIN, 23));
		tfPw.setBounds(39, 269, 306, 43);
		tfPw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tfPw.addActionListener(this);
		panel.add(tfPw);

		// 로그인 버튼
		btnLogin = new JButton("");
		ImageIcon bl = new ImageIcon("./img\\newlogin1.png");
		Image login = bl.getImage();
		login = login.getScaledInstance(318, 59, Image.SCALE_SMOOTH);
		bl = new ImageIcon(login);
		btnLogin.setIcon(bl);

		btnLogin.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				ImageIcon bl = new ImageIcon("./img\\newlogin2.png");
				Image imgBtnLogin = bl.getImage();
				imgBtnLogin = imgBtnLogin.getScaledInstance(318, 59, Image.SCALE_SMOOTH);
				bl = new ImageIcon(imgBtnLogin);
				btnLogin.setIcon(bl);

			}

			@Override
			public void mouseExited(MouseEvent e) {

				ImageIcon bl = new ImageIcon("./img\\newlogin1.png");
				Image imgBtnLogin = bl.getImage();
				imgBtnLogin = imgBtnLogin.getScaledInstance(318, 59, Image.SCALE_SMOOTH);
				bl = new ImageIcon(imgBtnLogin);
				btnLogin.setIcon(bl);

			}
		});

		btnLogin.addActionListener(this);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setFocusPainted(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setFont(new Font("돋움", Font.BOLD, 22));
		btnLogin.setBackground(new Color(52, 47, 253));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(39, 466, 320, 60);
		panel.add(btnLogin);

		// 회원가입 버튼
		JButton btnJoin = new JButton("");

		ImageIcon bj = new ImageIcon("./img\\join1.png");
		Image Join = bj.getImage();
		Join = Join.getScaledInstance(120, 37, Image.SCALE_SMOOTH);
		bj = new ImageIcon(Join);
		btnJoin.setIcon(bj);
		btnJoin.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				ImageIcon bj = new ImageIcon("./img\\join2.png");
				Image imgBtnJoin = bj.getImage();
				imgBtnJoin = imgBtnJoin.getScaledInstance(120, 37, Image.SCALE_SMOOTH);
				bj = new ImageIcon(imgBtnJoin);
				btnJoin.setIcon(bj);

			}

			@Override
			public void mouseExited(MouseEvent e) {

				ImageIcon bj = new ImageIcon("./img\\join1.png");
				Image imgBtnJoin = bj.getImage();
				imgBtnJoin = imgBtnJoin.getScaledInstance(120, 37, Image.SCALE_SMOOTH);
				bj = new ImageIcon(imgBtnJoin);
				btnJoin.setIcon(bj);

			}

		});

		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon bj = new ImageIcon("./img\\join1.png");
				Image imgBtnJoin = bj.getImage();
				imgBtnJoin = imgBtnJoin.getScaledInstance(120, 37, Image.SCALE_SMOOTH);
				bj = new ImageIcon(imgBtnJoin);
				btnJoin.setIcon(bj);
				SignUpPage sup = SignUpPage.getInstance();
				sup.setLocationRelativeTo(lp);
				sup.setVisible(true);
				dispose();
			}
		});
		btnJoin.setContentAreaFilled(false);
		btnJoin.setForeground(Color.BLACK);
		btnJoin.setBounds(230, 331, 120, 37);
		btnJoin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(btnJoin);

		// 로고
		JLabel imgLogo = new JLabel("");
		imgLogo.setIcon(new ImageIcon("./IMG\\logo.png"));
		imgLogo.setBounds(226, 10, 158, 83);
		panel.add(imgLogo);

		// 회원가입
//		JButton btnJoin = new JButton("");
//		
//		btnJoin.setContentAreaFilled(false);
//		ImageIcon bj = new ImageIcon("./img\\joinbtn2.png");
//		Image Join = bj.getImage();
//		Join =Join.getScaledInstance( 120, 37, Image.SCALE_SMOOTH);
//		bj = new ImageIcon(Join);
//		btnJoin.setIcon(bj);
//		btnJoin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		btnJoin.setBounds(248, 333, 120,37);
//		btnJoin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		panel.add(btnJoin);
//		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("./IMG\\login33png.png"));
		lblNewLabel.setBounds(0, 0, 384, 590);
		getContentPane().add(lblNewLabel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ImageIcon bl = new ImageIcon("./img\\newlogin1.png");
		Image imgBtnLogin = bl.getImage();
		imgBtnLogin = imgBtnLogin.getScaledInstance(318, 59, Image.SCALE_SMOOTH);
		bl = new ImageIcon(imgBtnLogin);
		btnLogin.setIcon(bl);
		if (sm.login(tfId.getText(), new String(tfPw.getPassword()))) {
			MemberBean mb = new MemberBean();
			mb = sm.selectMember(tfId.getText(), new String(tfPw.getPassword()));
			JOptionPane.showMessageDialog(lp, "로그인 성공");
			Mainpage mp = Mainpage.getInstance();
			mp.refresh(mb);
			mp.setLocationRelativeTo(lp);
			mp.setVisible(true);
			tfPw.setText("");
			dispose();
		} else {
			JOptionPane.showMessageDialog(lp, "로그인 실패");
		}
	}
}
