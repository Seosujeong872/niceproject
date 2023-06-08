package project.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import project.bean.Constant;
import project.bean.CustomMethod;
import project.bean.MemberBean;
import project.db.ShopMgr;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class PasswordChagePage extends JFrame {

	private static PasswordChagePage pcp;
	private JPanel contentPane;
	private JPasswordField tfOldPW;
	private JPasswordField tfNewPw;
	private JPasswordField tfNewPw2;
	private JLabel lbPwCheck;
	private MemberBean mb = new MemberBean();
	private CustomMethod cm = new CustomMethod();
	private ShopMgr sm = ShopMgr.getInstance();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PasswordChagePage frame = new PasswordChagePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static PasswordChagePage getInstance() {
		if (pcp == null) {
			pcp = new PasswordChagePage();
		}
		return pcp;
	}

	public void refresh(MemberBean mb) {
		this.mb = mb;
		setTitle(mb.getName() + "님 마이페이지 - 비밀번호 변경");
	}

	/**
	 * Create the frame.
	 */
	private PasswordChagePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbLOGO = new JLabel("");
		lbLOGO.setBounds(54, 10, 69, 39);
		lbLOGO.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\LOGO.png"), 69, 39));
		contentPane.add(lbLOGO);

		JButton btnBack = new JButton((String) null);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setBounds(12, 10, 30, 30);
		btnBack.setIcon(cm.resizeIcon(new ImageIcon("./IMG\\BACK.png"), 30, 30));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyPage myp = MyPage.getInstance();
				myp.setLocationRelativeTo(pcp);
				myp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnBack);

		JLabel lbOldPW = new JLabel("현재 비밀번호");
		lbOldPW.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbOldPW.setBounds(34, 59, 80, 20);
		contentPane.add(lbOldPW);

		tfOldPW = new JPasswordField();
		tfOldPW.setBounds(144, 59, 265, 20);
		contentPane.add(tfOldPW);
		tfOldPW.setColumns(10);

		JLabel lbNewPW = new JLabel("변경할 비밀번호");
		lbNewPW.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbNewPW.setBounds(34, 110, 98, 20);
		contentPane.add(lbNewPW);

		tfNewPw = new JPasswordField();
		tfNewPw.setColumns(10);
		tfNewPw.setBounds(144, 110, 265, 20);
		tfNewPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pwCheck();
			}
		});
		contentPane.add(tfNewPw);

		JLabel lbNewPW2 = new JLabel("비밀번호 확인");
		lbNewPW2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbNewPW2.setBounds(34, 158, 80, 20);
		contentPane.add(lbNewPW2);

		tfNewPw2 = new JPasswordField();
		tfNewPw2.setColumns(10);
		tfNewPw2.setBounds(144, 158, 265, 20);
		tfNewPw2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pwCheck();
			}
		});
		contentPane.add(tfNewPw2);

		JButton btnConfirm = new JButton("비밀번호 변경");
		btnConfirm.setContentAreaFilled(false);
		btnConfirm.setBounds(127, 221, 180, 30);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (new String(tfOldPW.getPassword()).equals(mb.getPassword())) {
					if (new String(tfNewPw.getPassword()).equals(new String(tfNewPw2.getPassword()))
							&& !(new String(tfNewPw.getPassword()).equals(""))) {
						mb.setPassword(new String(tfNewPw2.getPassword()));
						if (sm.updatePW(mb)) {
							JOptionPane.showMessageDialog(pcp, "성공적으로 비밀변호를 변경했습니다.\n다시 로그인 해 주세요.", "안내",
									JOptionPane.INFORMATION_MESSAGE);
							LoginPage lp = LoginPage.getInstance();
							lp.setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(pcp, "비밀번호 변경중 문제가 발생했습니다.", "경고", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(pcp, "새로운 비밀번호와 비밀번호 확인란이 일치하지 않습니다.", "경고",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(pcp, "현재 비밀번호가 일치하지 않습니다.", "경고", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnConfirm);

		lbPwCheck = new JLabel("비밀번호가 일치하지 않습니다");
		lbPwCheck.setForeground(Color.red);
		lbPwCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lbPwCheck.setHorizontalAlignment(SwingConstants.TRAILING);
		lbPwCheck.setBounds(229, 188, 180, 15);
		contentPane.add(lbPwCheck);
	}

	public void pwCheck() {
		System.out.println(new String(tfNewPw.getPassword()) + "/" + new String(tfNewPw2.getPassword()));
		if (new String(tfNewPw.getPassword()).equals(new String(tfNewPw2.getPassword()))
				&& !(new String(tfNewPw.getPassword()).equals(""))) {
			lbPwCheck.setText("비밀번호가 일치합니다");
			lbPwCheck.setForeground(Color.green);
		} else {
			lbPwCheck.setText("비밀번호가 일치하지 않습니다");
			lbPwCheck.setForeground(Color.red);
		}
	}
}
