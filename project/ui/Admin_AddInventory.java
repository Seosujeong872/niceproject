package project.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import project.bean.ProductBean;
import project.db.ShopMgr;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PublicKey;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import java.awt.Cursor;
import javax.swing.Icon;

public class Admin_AddInventory extends JFrame {

	private static Admin_AddInventory aa;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_1;
	private String Header[] = { "상품번호", "상품명", "카테고리", "가격", "현재재고량" };
	private DefaultTableModel dtm = new DefaultTableModel(Header, 0);
	private DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel(new String[] { "전체" });
	ShopMgr sm;
	private ProductBean pb = new ProductBean();
	private Vector<ProductBean> pbv = new Vector<>();
	private ImageIcon icon = new ImageIcon("IMG\\addInven.png");
	private JComboBox comboBox;

	public static Admin_AddInventory getinstance() {
		if (aa == null) {
			aa = new Admin_AddInventory();
		}
		return aa;
	}

	public void refresh() {
		updateTable("전체", dtm);
		sm.selectPro("전체", dtm);
		sm.selectCate(dcbm);
		comboBox.setSelectedIndex(0);
		validate();
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Admin_AddInventory frame = new Admin_AddInventory();
//					frame.
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	private Admin_AddInventory() {
		sm = ShopMgr.getInstance();
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		setResizable(false);
		setTitle("관리자 기존 상품 재고 추가");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 769);
		setLocationRelativeTo(null);
		JLayeredPane lp = new JLayeredPane();
		lp.setLocation(0, 0);
		lp.setSize(550, 731);
		lp.setLayout(null);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.add(lp);
		sm.selectCate(dcbm);

		JLabel bgilb = new JLabel(icon);
		bgilb.setBounds(0, 0, 550, 731);
		bgilb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.add(bgilb);

		JButton btn_back = new JButton("");
		btn_back.setContentAreaFilled(false);
		btn_back.setBounds(10, 10, 40, 40);
		btn_back.setIcon(resizeIcon(new ImageIcon("./IMG\\BACK.png"), 40, 40));
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetInfo();
				AdminMain am = AdminMain.getinstance();
				am.setLocationRelativeTo(aa);
				am.setVisible(true);
				dispose();
			}
		});
		lp.add(btn_back);

		JButton btn_Home = new JButton("");
		btn_Home.setContentAreaFilled(false);
		btn_Home.setBorderPainted(false);
		btn_Home.setBounds(482, 10, 40, 40);
		btn_Home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
		btn_Home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeFill.png"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_Home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
			}
		});
		btn_Home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetInfo();
				btn_Home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
				Mainpage mp = Mainpage.getInstance();
				mp.setLocationRelativeTo(aa);
				mp.setVisible(true);
				dispose();
			}
		});
		lp.add(btn_Home);

		comboBox = new JComboBox();
		comboBox.setBounds(34, 59, 97, 23);
		lp.add(comboBox);
		comboBox.setModel(dcbm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 92, 475, 341);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		lp.add(scrollPane);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("돋움체", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(55, 541, 172, 146);
		lp.add(lblNewLabel_1);

		table = new JTable(dtm) {
			public boolean isCellEditable(int row, int column) {

				// all cells false

				return false;

			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableSelect();
			}
		});

		scrollPane.setViewportView(table);

		textField = new JTextField();
		textField.setBounds(55, 471, 172, 20);
		lp.add(textField);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setOpaque(false);
		textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		textField_1 = new JTextField();
		textField_1.setBounds(55, 491, 172, 20);
		lp.add(textField_1);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setOpaque(false);
		textField_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		textField_2 = new JTextField();
		textField_2.setBounds(55, 511, 172, 20);
		lp.add(textField_2);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setOpaque(false);
		textField_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		JLabel lblNewLabel = new JLabel("추가할 재고량");
		lblNewLabel.setBounds(284, 455, 204, 40);
		lp.add(lblNewLabel);
		lblNewLabel.setFont(new Font("돋움체", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("돋움체", Font.PLAIN, 30));
		spinner.setBounds(284, 508, 204, 61);
		spinner.setOpaque(false);
		spinner.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lp.add(spinner);

		JButton btn_Confirm = new JButton("");
		btn_Confirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_Confirm.setFocusPainted(false);
		btn_Confirm.setBorderPainted(false);
		btn_Confirm.setContentAreaFilled(false);
		btn_Confirm.setIcon(resizeIcon(new ImageIcon("./IMG\\AssignNull.png"), 50, 50));
		btn_Confirm.setBounds(310, 637, 50, 50);
		btn_Confirm.setToolTipText("재고 추가");
		btn_Confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Confirm.setIcon(resizeIcon(new ImageIcon("./IMG\\AssignFill.png"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_Confirm.setIcon(resizeIcon(new ImageIcon("./IMG\\AssignNull.png"), 40, 40));
			}
		});
		btn_Confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sm.updateInven(pb.getProIdx(), (int) spinner.getValue())) {
					JOptionPane.showMessageDialog(aa, "성공적으로 재고가 갱신되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
					String cate = (String) comboBox.getSelectedItem();
					updateTable(cate, dtm);
				} else {
					JOptionPane.showMessageDialog(aa, "재고 갱신중에 문제가 발생했습니다.\n다시 확인해주세요.", "오류",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lp.add(btn_Confirm);

		JButton btn_Delete = new JButton("");
		btn_Delete.setFocusPainted(false);
		btn_Delete.setContentAreaFilled(false);
		btn_Delete.setBorderPainted(false);
		btn_Delete.setIcon(resizeIcon(new ImageIcon("./IMG\\DeleteNull.png"), 50, 50));
		btn_Delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_Delete.setBounds(420, 637, 50, 50);
		btn_Delete.setToolTipText("상품 삭제");
		btn_Delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Delete.setIcon(resizeIcon(new ImageIcon("./IMG\\DeleteFill.png"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_Delete.setIcon(resizeIcon(new ImageIcon("./IMG\\DeleteNull.png"), 40, 40));
			}
		});
		btn_Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = JOptionPane.showConfirmDialog(aa, "선택한 상품을 삭제합니다. 정말로 삭제하시겠습니까?", "주의",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selected == 0) {
					if (sm.deletePro(pb)) {
						JOptionPane.showMessageDialog(aa, "성공적으로 상품이 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
						File file = new File(pb.getImgAddress());

						if (file.exists()) {
							if (file.delete()) {
								System.out.println("파일삭제 성공");
							} else {
								System.out.println("파일삭제 실패");
							}
						} else {
							System.out.println("파일이 존재하지 않습니다.");
						}
						sm.selectCate(dcbm);
						String cate = (String) comboBox.getSelectedItem();
						updateTable(cate, dtm);
						resetInfo();
					} else {
						JOptionPane.showMessageDialog(aa, "상품 삭제중에 문제가 발생했습니다.\n다시 확인해주세요.", "오류",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(aa, "상품 삭제를 취소했습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		lp.add(btn_Delete);
//		btnNewButton_2.setBorderPainted(false);
//		btnNewButton_2.setFocusPainted(false);
		table.updateUI();
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cate = (String) comboBox.getSelectedItem();
				resetInfo();
				updateTable(cate, dtm);
			}
		});

		JLabel lbLogo = new JLabel();
		lbLogo.setBounds(80, 10, 69, 39);
		lbLogo.setIcon(resizeIcon(new ImageIcon("./IMG\\LOGO.png"), 69, 39));
		lp.add(lbLogo);

		updateTable("전체", dtm);
		sm.selectPro("전체", dtm);
		setVisible(true);
		validate();
	}

	public Icon resizeIcon(ImageIcon ii, int w, int h) {
		ImageIcon ic = ii;
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		return ic;
	}

	public void tableSelect() {
		int row = table.getSelectedRow();
		String path = null;
		pb.setProIdx((int) table.getModel().getValueAt(row, 0));
		textField.setText((String) table.getModel().getValueAt(row, 1));
		textField_1.setText("카테고리: " + (String) table.getModel().getValueAt(row, 2));
		textField_2.setText(Integer.toString((int) table.getModel().getValueAt(row, 3)) + "원");
		path = pbv.get(row).getImgAddress();
		aa.pb = aa.pbv.get(row);
		if (path == null) {
			lblNewLabel_1.setIcon(null);
			lblNewLabel_1.setText("No Image");
		} else {
			lblNewLabel_1.setText("");
			ImageIcon ic = new ImageIcon(path);
			Image img = ic.getImage();
			Image img2 = img.getScaledInstance(172, 146, Image.SCALE_SMOOTH);
			ic = new ImageIcon(img2);
			lblNewLabel_1.setIcon(ic);
			System.out.println(path);
		}
		repaint();
		validate();
	}

	public void updateTable(String Cate, DefaultTableModel dtm) {
		pbv = sm.selectPro(Cate, dtm);
		table.updateUI();
	}

	public void resetInfo() {
		this.pb = new ProductBean();
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		lblNewLabel_1.setIcon(null);
		lblNewLabel_1.setText("No Image");
	}
}
