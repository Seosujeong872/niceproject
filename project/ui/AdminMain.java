package project.ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminMain extends JFrame {

	private JPanel contentPane;
	private static AdminMain adminMain;
	
	public static AdminMain getinstance() {
		if (adminMain == null) {
			adminMain = new AdminMain();
		} return adminMain;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminMain frame = new AdminMain();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	/**
	 * Create the frame.
	 */
	private AdminMain() {
		setTitle("관리자 메뉴");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./IMG\\LogoIcon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 376, 444);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 360, 405);
		contentPane.add(layeredPane);
		
		JButton btnHome = new JButton();
		btnHome.setBorderPainted(false);
		btnHome.setContentAreaFilled(false);
		btnHome.setBounds(308, 10, 40, 40);
		btnHome.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHome.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeFill.png"), 40, 40));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHome.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
			}
		});
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHome.setIcon(resizeIcon(new ImageIcon("./IMG\\HomeNull.png"), 40, 40));
				Mainpage mp = Mainpage.getInstance();
				mp.setLocationRelativeTo(adminMain);
				mp.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnHome);
		
		JButton btnNewPro = new JButton();
		btnNewPro.setFocusPainted(false);
		btnNewPro.setContentAreaFilled(false);
		btnNewPro.setBorderPainted(false);
		btnNewPro.setIcon(resizeIcon(new ImageIcon("./IMG\\NewProNull.png"), 100, 100));
		btnNewPro.setBounds(53, 68, 100, 100);
		btnNewPro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewPro.setIcon(resizeIcon(new ImageIcon("./IMG\\NewProFill.png"), 100, 100));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewPro.setIcon(resizeIcon(new ImageIcon("./IMG\\NewProNull.png"), 100, 100));
			}
		});
		btnNewPro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewPro.setIcon(resizeIcon(new ImageIcon("./IMG\\NewProNull.png"), 100, 100));
				AddProduct ap = AddProduct.getInstance();
				ap.setLocationRelativeTo(adminMain);
				ap.refresh();
				ap.setVisible(true);
				dispose();
			}
		});
		layeredPane.add(btnNewPro);
		
		JButton btnAddInven = new JButton("New button");
		btnAddInven.setFocusPainted(false);
		btnAddInven.setContentAreaFilled(false);
		btnAddInven.setBorderPainted(false);
		btnAddInven.setBounds(206, 68, 100, 100);
		btnAddInven.setIcon(resizeIcon(new ImageIcon("./IMG\\AddInvenNull.png"), 100, 100));
		btnAddInven.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAddInven.setIcon(resizeIcon(new ImageIcon("./IMG\\AddInvenFill.png"), 100, 100));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAddInven.setIcon(resizeIcon(new ImageIcon("./IMG\\AddInvenNull.png"), 100, 100));
			}
		});
		btnAddInven.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Admin_AddInventory aa = Admin_AddInventory.getinstance();
				aa.setLocationRelativeTo(adminMain);
				aa.refresh();
				aa.setVisible(true);
				btnAddInven.setIcon(resizeIcon(new ImageIcon("./IMG\\AddInvenNull.png"), 100, 100));
				dispose();
			}
		});
		layeredPane.add(btnAddInven);
		
		JButton btnChart = new JButton("New button");
		btnChart.setFocusPainted(false);
		btnChart.setContentAreaFilled(false);
		btnChart.setBorderPainted(false);
		btnChart.setBounds(53, 242, 100, 100);
		btnChart.setIcon(resizeIcon(new ImageIcon("./IMG\\ChartNull.png"), 100, 100));
		btnChart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnChart.setIcon(resizeIcon(new ImageIcon("./IMG\\ChartFill.png"), 100, 100));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnChart.setIcon(resizeIcon(new ImageIcon("./IMG\\ChartNull.png"), 100, 100));
			}
		});
		btnChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnChart.setIcon(resizeIcon(new ImageIcon("./IMG\\ChartNull.png"), 100, 100));
				Admin_Statistics as = Admin_Statistics.getinstance();
				as.setLocationRelativeTo(adminMain);
				as.doingSearch();
				as.setVisible(true);
				dispose();
			}
		});
		layeredPane.add(btnChart);
		
		JButton btnDelivery = new JButton("New button");
		btnDelivery.setFocusPainted(false);
		btnDelivery.setContentAreaFilled(false);
		btnDelivery.setBorderPainted(false);
		btnDelivery.setBounds(206, 242, 100, 100);
		btnDelivery.setIcon(resizeIcon(new ImageIcon("./IMG\\DeliNull.png"), 100, 100));
		btnDelivery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDelivery.setIcon(resizeIcon(new ImageIcon("./IMG\\DeliFill.png"), 100, 100));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDelivery.setIcon(resizeIcon(new ImageIcon("./IMG\\DeliNull.png"), 100, 100));
			}
		});
		btnDelivery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDelivery.setIcon(resizeIcon(new ImageIcon("./IMG\\DeliNull.png"), 100, 100));
				DeliveryManagementPage dmp = DeliveryManagementPage.getInstance();
				dmp.setLocationRelativeTo(adminMain);
				dmp.refresh();
				dmp.setVisible(true);
				dispose();
			}
		});
		layeredPane.add(btnDelivery);
		
		JLabel lblNewLabel = new JLabel("관리자 메뉴");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblNewLabel.setBounds(53, 10, 253, 48);
		layeredPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("새 상품 추가");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(53, 178, 100, 30);
		layeredPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("재고 추가");
		lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(206, 178, 100, 30);
		layeredPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("통계");
		lblNewLabel_1_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setBounds(50, 352, 100, 30);
		layeredPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("배송관리");
		lblNewLabel_1_1_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setBounds(203, 352, 100, 30);
		layeredPane.add(lblNewLabel_1_1_1_1);
		
		JLabel lbLogo = new JLabel();
		lbLogo.setBounds(10, 10, 69, 39);
		lbLogo.setIcon(resizeIcon(new ImageIcon("./IMG\\LOGO.png"), 69, 39));
		layeredPane.add(lbLogo);
		
		validate();
	}
	
	public Icon resizeIcon(ImageIcon ii, int w, int h) {
		ImageIcon ic = ii;
		Image img = ic.getImage();
		Image img2 = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ic = new ImageIcon(img2);
		return ic;
	}
}
