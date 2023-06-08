package project.ui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.xml.validation.Validator;

import project.bean.ProductBean;
import project.db.ShopMgr;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Color;

public class AddProduct extends JFrame {

	private static AddProduct ap;
	private JTextField tfCate, tfName, tfPrice, tfInven;
	private FileDialog openDialog, saveDialog;
	private File src;
	private JComboBox combo_cate;
	private ShopMgr sm = ShopMgr.getInstance();
	private String imgAdd;
	private JLabel img_1;
	private ProductBean pb = new ProductBean();
	private DefaultComboBoxModel<String> cateList = new DefaultComboBoxModel<>();
	private Vector<Integer> cateIdx = new Vector<>();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddProduct window = new AddProduct();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static AddProduct getInstance() {
		if (ap == null) {
			ap = new AddProduct();
		}
		return ap;
	}

	public void refresh() {
		ap.cateIdx = sm.comboList(cateList);
		combo_cate.setSelectedIndex(0);
		tfCate.setText("");
		tfInven.setText("");
		tfName.setText("");
		tfPrice.setText("");
	}

	/**
	 * Create the application.
	 */
	private AddProduct() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		setBounds(100, 100, 534, 810);
		setTitle("관리자 - 새 상품 추가");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		img_1 = new JLabel("");

		JLayeredPane panel = new JLayeredPane();
		panel.setBounds(-10, -20, 534, 810);
		getContentPane().add(panel);

		tfCate = new JTextField();
		tfCate.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		tfCate.setText("");
		tfCate.setOpaque(false);
		tfCate.setBounds(313, 80, 149, 29);
//		tfCate.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(tfCate);
		tfCate.setColumns(10);

		tfName = new JTextField();
		tfName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		tfName.setText("");
		tfName.setOpaque(false);
		tfName.setBounds(313, 194, 149, 29);
//		tfName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(tfName);
		tfName.setColumns(10);

		tfPrice = new JTextField();
		tfPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		tfPrice.setText("");
		tfPrice.setOpaque(false);
		tfPrice.setBounds(313, 237, 149, 29);
//		tfPrice.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(tfPrice);
		tfPrice.setColumns(10);

		tfInven = new JTextField();
		tfInven.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		tfInven.setText("");
		tfInven.setOpaque(false);
		tfInven.setBounds(313, 280, 149, 29);
//		tfInven.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(tfInven);
		tfInven.setColumns(10);

		// 카테고리 선택 콤보박스
		cateIdx = sm.comboList(cateList);
		combo_cate = new JComboBox(cateList);
		combo_cate.setForeground(new Color(0, 0, 0));
		combo_cate.setFont(new Font("돋움", Font.BOLD, 17));
		combo_cate.setBackground(new Color(255, 255, 255));
		combo_cate.setBounds(43, 80, 168, 60);
		panel.add(combo_cate);

		JButton btn_home = new JButton("");
		btn_home.setBorderPainted(false);
		btn_home.setContentAreaFilled(false);
		btn_home.setBounds(471, 30, 40, 40);
		btn_home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
		btn_home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeFill.png"), 40, 40));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn_home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
			}
		});
		btn_home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_home.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
				resetInfo();
				Mainpage mp = Mainpage.getInstance();
				mp.setLocationRelativeTo(ap);
				mp.setVisible(true);
				dispose();
			}
		});
		panel.add(btn_home);

//		JLabel img_1 = new JLabel("");
		img_1.setBounds(43, 331, 453, 331);
		panel.add(img_1);

		JButton btn_back = new JButton("");
		btn_back.setIcon(resizeIcon(new ImageIcon("./IMG\\BACK.png"), 40, 40));
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetInfo();
				AdminMain am = AdminMain.getinstance();
				am.setLocationRelativeTo(ap);
				am.setVisible(true);
				dispose();
			}
		});
		btn_back.setContentAreaFilled(false);
		btn_back.setBounds(30, 30, 40, 40);
		panel.add(btn_back);

		JLabel lbLogo = new JLabel();
		lbLogo.setBounds(80, 30, 69, 39);
		lbLogo.setIcon(resizeIcon(new ImageIcon("./IMG\\LOGO.png"), 69, 39));
		panel.add(lbLogo);

		JLabel lbCate = new JLabel("카테고리");
		lbCate.setForeground(Color.GRAY);
		lbCate.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 17));
		lbCate.setBounds(223, 80, 78, 23);
		panel.add(lbCate);

		JLabel lbName = new JLabel("상품명");
		lbName.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 17));
		lbName.setBounds(223, 194, 78, 23);
		panel.add(lbName);

		JLabel lbPrice = new JLabel("가격");
		lbPrice.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 17));
		lbPrice.setBounds(223, 237, 78, 23);
		panel.add(lbPrice);

		JLabel lbInven = new JLabel("재고량");
		lbInven.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 17));
		lbInven.setBounds(223, 280, 78, 23);
		panel.add(lbInven);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("./IMG\\addpro2.png"));
		lblNewLabel.setBounds(-10, -20, 534, 810);
		getContentPane().add(lblNewLabel);

		// 이미지삽입버튼
		JButton add_img = new JButton("");
		ImageIcon ai = new ImageIcon("./img\\addimg11.png");
		Image addImg = ai.getImage();
		addImg = addImg.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
		ai = new ImageIcon(addImg);
		add_img.setIcon(ai);
		add_img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon ai = new ImageIcon("./img\\addimg12.png");
				Image addImg = ai.getImage();
				addImg = addImg.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
				ai = new ImageIcon(addImg);
				add_img.setIcon(ai);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon ai = new ImageIcon("./img\\addimg11.png");
				Image addImg = ai.getImage();
				addImg = addImg.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
				ai = new ImageIcon(addImg);
				add_img.setIcon(ai);
			}

		});
		add_img.addActionListener(new ActionListener() {
			private Object ImageIcon;

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == add_img) {
					FileDialog fdOpenDialog = new FileDialog(openDialog);
					fdOpenDialog.setFilenameFilter(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							String[] str = name.split(".");
							System.out.println(str[0] + "/" + str[1]);
							if (str[1].equals("png")) {
								return true;
							} else {
								return false;
							}
						}
					});
					fdOpenDialog.setVisible(true);

					String path = fdOpenDialog.getDirectory();// 파일경로
					String name = fdOpenDialog.getFile();// 파일이름

					if (path != null) {
						String[] str = name.split("\\.");
						String ex = str[str.length-1].toLowerCase();
						if (ex.equals("png") || ex.equals("jpeg") || ex.equals("jpg")) {
							System.out.println(path + name);
							src = new File(path + name);
							ImageIcon icon_add = new ImageIcon((path + name));
							Image img1 = icon_add.getImage();
							Image chanImage = img1.getScaledInstance(453, 331, img1.SCALE_SMOOTH);
							ImageIcon changeIcon = new ImageIcon(chanImage);
							img_1.setIcon(changeIcon);
						} else {
							JOptionPane.showMessageDialog(ap, "png, jpg, jpeg형식의 이미지만 불러올 수 있습니다.","경고",JOptionPane.ERROR_MESSAGE);
						}
					}
//						img_1.setText(path+name);

//						img_1.setIcon(new ImageIcon(path+name));

				}

			}
		});
		add_img.setBorderPainted(false);
		add_img.setContentAreaFilled(false);
		add_img.setBounds(43, 272, 149, 47);
		panel.add(add_img);

		// 등록 버튼

		JButton btn_ok = new JButton("");

		ImageIcon ic = new ImageIcon("./img\\newbtnok2.png");
		Image imgOk = ic.getImage();
		imgOk = imgOk.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
		ic = new ImageIcon(imgOk);

//		btn_ok.setIcon(new ImageIcon("C:\\Java2\\Nice-practice\\img\\btn_ok11.png"));
		btn_ok.setBorderPainted(false);
		btn_ok.setContentAreaFilled(false);
		btn_ok.setBounds(43, 683, 445, 83);
		btn_ok.setIcon(ic);
		btn_ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon ic = new ImageIcon("./img\\newbtnok1.png");
				Image imgOk = ic.getImage();
				imgOk = imgOk.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
				ic = new ImageIcon(imgOk);
				btn_ok.setIcon(ic);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon ic = new ImageIcon("./img\\newbtnok2.png");
				Image imgOk = ic.getImage();
				imgOk = imgOk.getScaledInstance(445, 83, Image.SCALE_SMOOTH);
				ic = new ImageIcon(imgOk);
				btn_ok.setIcon(ic);
			}

		});
		panel.add(btn_ok);

		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btn_ok) {
//					FileDialog addDialog = new FileDialog(saveDialog);
//					addDialog.setVisible(true);
//					JFileChooser jfc = new JFileChooser();
					try {
						pb.setCateIdx(ap.cateIdx.get(combo_cate.getSelectedIndex()));
						pb.setProName(tfName.getText());
						pb.setPrice(Integer.parseInt(tfPrice.getText().trim()));
						pb.setInventory(Integer.parseInt(tfInven.getText().trim()));
						pb.setImgAddress(null);
						pb = sm.addProduct(pb);
						if (src == null) {
							throw new Exception();
						}
						if (pb.getProIdx() != -1) {
							JOptionPane.showMessageDialog(ap, "상품 등록 완료");
							imgCopy();
							resetInfo();
						} else {
							JOptionPane.showMessageDialog(ap, "상품 등록 실패");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(ap, "올바른 상품정보를 입력했는지 확인해 주세요. (이미지 필수)","경고",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// 카테고리 추가 버튼
		JButton add_cate = new JButton("");
		add_cate.setBounds(318, 120, 149, 47);
		ImageIcon ac = new ImageIcon("./img\\newcate1.png");
		Image addCate = ac.getImage();
		addCate = addCate.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
		ac = new ImageIcon(addCate);
		add_cate.setIcon(ac);
		add_cate.setBorderPainted(false);
		add_cate.setContentAreaFilled(false);

		add_cate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon ac = new ImageIcon("./img\\newcate2.png");
				Image addCate = ac.getImage();
				addCate = addCate.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
				ac = new ImageIcon(addCate);
				add_cate.setIcon(ac);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon ac = new ImageIcon("./img\\newcate1.png");
				Image addCate = ac.getImage();
				addCate = addCate.getScaledInstance(149, 47, Image.SCALE_SMOOTH);
				ac = new ImageIcon(addCate);
				add_cate.setIcon(ac);
			}

		});

		add_cate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == add_cate) {
//					
//					String addCate1 = new String();
					String addCate11 = tfCate.getText();
//					sm.addCate(addCate11);
					System.out.println(addCate11);
					if (tfCate.getText() != null && sm.addCate(addCate11)) {
						JOptionPane.showMessageDialog(ap, "카테고리 추가 성공");
						cateIdx = sm.comboList(cateList);
					} else {
						JOptionPane.showMessageDialog(ap, "카테고리 추가 실패");
					}

				}

			}
		});

		panel.add(add_cate);
	}

	// 이미지 복사
	public void imgCopy() {
		File dest = new File("./img\\\\product" + pb.getProIdx() + ".png");
		try {
			FileInputStream fi = new FileInputStream(src);
			FileOutputStream fo = new FileOutputStream(dest);
			byte[] buf = new byte[1024 * 10];
			while (true) {
				int n = fi.read(buf);
				fo.write(buf, 0, n); // buf[0]부터 n byte 쓰기
				if (n < buf.length)
					break;
			}
			fi.close();
			fo.close();
			System.out.println("복사 성공");
		} catch (Exception e) {
			System.out.println("복사 오류");
		}
	}

	// 이미지크기조절
	public Icon resizeIcon(ImageIcon ii, int w, int h) {
		ImageIcon ic = ii;
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		return ic;
	}
	
	public void resetInfo() {
		tfCate.setText("");
		tfInven.setText("");
		tfName.setText("");
		tfPrice.setText("");
		img_1.setIcon(null);
		src = null;
		combo_cate.setSelectedIndex(0);
	}
}
