package project.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.bean.CustomMethod;
import project.bean.ReviewBean;
import project.db.ShopMgr;
import javax.swing.SwingConstants;
import javax.swing.text.StyleContext.SmallAttributeSet;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class CreateReview extends JDialog implements MouseListener {

	private static CreateReview cr;
	String ImgName = "./IMG\\";
	JFrame frame;
	private JTextArea commentsT;
	public int dbstate = 0;
	private JLabel[] lbStar = new JLabel[5];
	private CustomMethod cm = new CustomMethod();
	private int starRate = 1;
	private ReviewBean bean = new ReviewBean();
	private JButton btnNewButton;
	private JLabel lbComLength;
	private boolean create = true;
	private ShopMgr sm = ShopMgr.getInstance();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CreateReview cr = CreateReview.getInstance();
//					cr.refresh(new ReviewBean(), true);
//					cr.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static CreateReview getInstance() {
		if (cr == null) {
			cr = new CreateReview(new ReviewBean());
		}
		return cr;
	}

	public void refresh(ReviewBean bean, boolean create) {
		this.bean = bean;
		this.create = create;
		this.starRate = this.bean.getStarRating();
		updateUI(create);
	}

	/**
	 * Create the application.
	 */
	private CreateReview(ReviewBean bean) {
		setTitle("상품 리뷰 작성");
		setBounds(0, 0, 420, 331);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		this.bean = bean;
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton back = new JButton("");
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setIcon(new ImageIcon(ImgName + "BACK.PNG"));
		back.setBorder(null);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		back.setBounds(352, 10, 40, 40);
		panel.add(back);

		lbComLength = new JLabel("(0/100)");
		lbComLength.setHorizontalAlignment(SwingConstants.CENTER);
		lbComLength.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		lbComLength.setBounds(164, 35, 76, 15);
		panel.add(lbComLength);

		commentsT = new JTextArea();
		commentsT.setLineWrap(true);
		commentsT.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		commentsT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateWordLen();
			}
		});

		btnNewButton = new JButton("등록하기");
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		btnNewButton.setContentAreaFilled(false);

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (commentsT.getText().length() > 100) {
					JOptionPane.showMessageDialog(cr, "리뷰 내용은 100자를 넘을 수 없습니다.","오류",JOptionPane.ERROR_MESSAGE);
				} else {
					cr.bean.setComments(commentsT.getText());
					cr.bean.setStarRating(starRate);
					commentsT.setText("");
					if (create) {
						sm.insertReview(cr.bean);
						JOptionPane.showMessageDialog(cr, "리뷰가 등록되었습니다.");
					} else {
						sm.updateReview(cr.bean);
						JOptionPane.showMessageDialog(cr, "리뷰가 수정되었습니다.");
					}
					ProductInfo pi = ProductInfo.getInstance();
					pi.addData();
					dispose();
				}
			}
		});
		btnNewButton.setBounds(119, 235, 166, 40);
		panel.add(btnNewButton);

		for (int i = 0; i < lbStar.length; i++) {
			lbStar[i] = new JLabel("");
			lbStar[i].setHorizontalAlignment(SwingConstants.CENTER);
			lbStar[i].setBounds(12 + i * 20, 20, 20, 20);
			if (i == 0) {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarFill.png"), 20, 20));
			} else {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarNull.png"), 20, 20));
			}
			lbStar[i].addMouseListener(this);
			panel.add(lbStar[i]);
		}

		JScrollPane scrollPane = new JScrollPane(commentsT);
		scrollPane.setBounds(12, 60, 380, 165);
		panel.add(scrollPane);
	}

	public void CreateUser(boolean create, ReviewBean bean) {
		try {
			if (dbstate == 0) {
				// 중복클릭방지 얼리 리턴
				return;
			}
			dbstate = 0;
			ShopMgr strUpdate = ShopMgr.getInstance();
			if (create) {
				strUpdate.insertReview(bean);
			} else {
				strUpdate.updateReview(bean);
			}

			// 값을 0으로바꿈
		} catch (Exception e) {
			System.err.println("에러 발생: " + e.getMessage());
			dbstate = 1;
		} finally {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					CreateReview.this.dbstate = 1;
				}
			}, 3000);
			dbstate = 2;
		}

	}

//버퍼링상태관리
	public void ObserverRelodimg(int dbstate) {

		switch (dbstate) {
		case 1:
			System.out.println("Number is 1");
			break;
		case 2:
			System.out.println("Number is 2");
			break;
		case 3:
			System.out.println("Number is 3");
			break;
		default:
			System.out.println("Number is not 1, 2, or 3");
			break;
		}

	}

	public void updateUI(boolean create) {
		for (int i = 0; i < lbStar.length; i++) {
			if (bean.getStarRating() < i + 1) {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarNull.png"), 20, 20));
			} else {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarFill.png"), 20, 20));
			}
		}
		if (create) {
			btnNewButton.setText("등록하기");
			commentsT.setText("");
		} else {
			btnNewButton.setText("수정하기");
			if (cr.bean.getComments().length() > 5 && cr.bean.getComments().substring(0,5).equals("(수정됨)")) {
				commentsT.setText(cr.bean.getComments().substring(5));
			} else {
				commentsT.setText(cr.bean.getComments());
			}
		}
		updateWordLen();
	}

	public void updateWordLen() {
		int comLength = commentsT.getText().length();
		lbComLength.setText("(" + comLength + "/100)");
		if (comLength > 100) {
			lbComLength.setForeground(Color.RED);
		} else {
			lbComLength.setForeground(Color.BLACK);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean isFind = false;
		for (int i = 0; i < lbStar.length; i++) {
			if (isFind) {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarNull.png"), 20, 20));
			} else {
				lbStar[i].setIcon(cm.resizeIcon(new ImageIcon("./IMG\\StarFill.png"), 20, 20));
			}
			if (e.getSource() == (JLabel) lbStar[i]) {
				isFind = true;
				starRate = i + 1;
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}