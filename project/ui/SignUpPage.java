package project.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import project.bean.CustomMethod;
import project.db.ShopMgr;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SignUpPage extends JFrame{

	private static SignUpPage sup;
	private CustomMethod cm = new CustomMethod();
	private JTextField idText;
	private JPasswordField pwCheck;
	private JPasswordField pwText;
	private JTextField nameText;
	private ShopMgr sm = ShopMgr.getInstance();
	private JLabel PwCheckLb = new JLabel("");
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUpPage window = new SignUpPage();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static SignUpPage getInstance() {
		if (sup == null) {
			sup = new SignUpPage();
		} return sup;
	}
	
	/**
	 * Create the application.
	 */
	private SignUpPage() {
		setBounds(100, 100, 540, 850);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLayeredPane panel = new JLayeredPane();
		panel.setBounds(-5, 0, 535, 800);
		getContentPane().add(panel);

		idText = new JTextField();
		idText.setOpaque(false);
		idText.setBorder(new EmptyBorder(0, 0, 0, 0));
		idText.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		idText.setBounds(60, 168, 433, 63);
		panel.add(idText);
		idText.setColumns(10);

		pwCheck = new JPasswordField();
		pwCheck.setOpaque(false);
		pwCheck.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		pwCheck.setBounds(60, 407, 433, 63);
		pwCheck.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pwCheck();
			}
		});
		panel.add(pwCheck);

		pwText = new JPasswordField();
		pwText.setOpaque(false);
		pwText.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwText.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		pwText.setBounds(60, 313, 433, 63);
		pwText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pwCheck();
			}
		});
		panel.add(pwText);

		JLabel lbId = new JLabel("아이디");
		lbId.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbId.setBounds(50, 147, 57, 15);
		panel.add(lbId);

		JLabel lbPw = new JLabel("비밀번호");
		lbPw.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbPw.setBounds(50, 288, 57, 15);
		panel.add(lbPw);

		JLabel lbPwCheck = new JLabel("비밀번호 확인");
		lbPwCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbPwCheck.setBounds(50, 382, 109, 15);
		
		panel.add(lbPwCheck);

		JLabel lbName = new JLabel("이름");
		lbName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lbName.setBounds(50, 504, 57, 15);
		panel.add(lbName);

		JButton btn_Back = new JButton("");
		btn_Back.setContentAreaFilled(false);
		btn_Back.setBorderPainted(false);
		btn_Back.setIcon(resizeIcon(new ImageIcon("./IMG\\back2.png"), 40, 40));
		btn_Back.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Back.setIcon(resizeIcon(new ImageIcon("./IMG\\BACK.png"), 40, 40));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Back.setIcon(resizeIcon(new ImageIcon("./IMG\\back2.png"), 40, 40));
			}
			
			
			
		});
		btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage lp = LoginPage.getInstance();
				lp.setVisible(true);
				dispose();
			}
		});
	
		
		btn_Back.setBounds(20, 20, 40, 40);
		panel.add(btn_Back);

		nameText = new JTextField();
		nameText.setBorder(new EmptyBorder(0, 0, 0, 0));
		nameText.setOpaque(false);
		nameText.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		nameText.setColumns(10);
		nameText.setBounds(60, 522, 433, 63);
		panel.add(nameText);

		JCheckBox idCheck = new JCheckBox("아이디 중복확인");
		idCheck.setBounds(371, 237, 115, 23);
		idCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (idCheck.isSelected()) {
					if(idText.getText().equals("") || sm.idCheck(idText.getText())) {
						idCheck.setSelected(false);
						JOptionPane.showMessageDialog(null, "다른 아이디를 입력해주세요.");
					}else {
						JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다.");
						idText.setEditable(false);
					}
				} else {
					idText.setEditable(true);
				}
			}
		});
		panel.add(idCheck);
		PwCheckLb.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		PwCheckLb.setHorizontalAlignment(SwingConstants.TRAILING);

//		JLabel PwCheckLb = new JLabel("");
		PwCheckLb.setBounds(311, 480, 172, 15);

		panel.add(PwCheckLb);

		// 가입완료 버튼
		JButton btnSignUp = new JButton("");
		
		ImageIcon ic = new ImageIcon("./IMG\\btnsign1.png");
		Image signUp = ic.getImage();
		signUp = signUp.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
		ic = new ImageIcon(signUp);
		
		btnSignUp.setContentAreaFilled(false);
		btnSignUp.setBounds(50, 659, 445, 83);
		btnSignUp.setIcon(ic);
		
		btnSignUp.setBorderPainted(false);
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon ic = new ImageIcon("./IMG\\btnsign2.png");
				Image imgOk = ic.getImage();
				imgOk = imgOk.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
				ic = new ImageIcon(imgOk);
				btnSignUp.setIcon(ic);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon ic = new ImageIcon("./IMG\\btnsign1.png");
				Image imgOk = ic.getImage();
				imgOk = imgOk.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
				ic = new ImageIcon(imgOk);
				btnSignUp.setIcon(ic);
			}

		});
		panel.add(btnSignUp);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = idText.getText();
				String pw = new String(pwText.getPassword());
				String name = nameText.getText();

				if (id.isEmpty() || pw.isEmpty() || name.isEmpty()) {
					JOptionPane.showMessageDialog(sup, "빈칸 없이 채워주세요.");
					return;
				}  else if (!idCheck.isSelected()) {
					JOptionPane.showMessageDialog(sup, "아이디 중복확인 체크를 해주세요.");
				}	else {
					// Insert the user data into the database
					if (sm.signUp(id, pw, name)) {
						JOptionPane.showMessageDialog(sup, "회원가입이 완료되었습니다.");
						LoginPage lp = LoginPage.getInstance();
						lp.setVisible(true);

					} else
						JOptionPane.showMessageDialog(sup, "회원가입 실패!");
				}

			}
		});
		
		

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("./IMG\\newsignup.png"));
		lblNewLabel.setBounds(-5, 0, 535, 800);
		getContentPane().add(lblNewLabel);
		
		
	}

	public void pwCheck() {
		if (new String(pwText.getPassword()).equals(new String(pwCheck.getPassword()))&& !(new String(pwText.getPassword()).equals(""))) {
			PwCheckLb.setText("비밀번호가 일치합니다");
			PwCheckLb.setForeground(Color.green);
		}else {
			PwCheckLb.setText("비밀번호가 일치하지 않습니다");
			PwCheckLb.setForeground(Color.red);
		}
	}
	//이미지크기조절
		public Icon resizeIcon(ImageIcon ii, int w, int h) {
			ImageIcon ic = ii;
			Image img = ic.getImage();
			Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			ic = new ImageIcon(img2);
			return ic;
		}
}
