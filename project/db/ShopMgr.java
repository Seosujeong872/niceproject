package project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import oracle.jdbc.Const;
import project.bean.CartBean;
import project.bean.Constant;
import project.bean.MainProductBean;
import project.bean.MemberBean;
import project.bean.OrderLogBean;
import project.bean.ProductBean;
import project.bean.ReviewBean;
import project.bean.StatisBean;
import project.ui.Admin_AddInventory;
import project.ui.ProductInfo;

public class ShopMgr {
	private DBConnectionMgr pool;
	private static ShopMgr shopMgr = null;

	private ShopMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	public static ShopMgr getInstance() {
		if (shopMgr == null) {
			shopMgr = new ShopMgr();
		}
		return shopMgr;
	}

	// 송명준 재고추가, 통계, 메인페이지, 장바구니 로그인회원정보 시작
	public boolean updatePW(MemberBean mb) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "update member set PW = ? where MEM_IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getPassword());
			pstmt.setInt(2, mb.getIdx());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public Vector<ProductBean> selectPro(String Cate, DefaultTableModel dtm) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<ProductBean> pbv = new Vector<>();
		dtm.setNumRows(0);
		try {
			con = pool.getConnection();
			if (Cate != "전체") {
				sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_name as 카테고리, p.price as 가격, p.inventory as 현재재고량, p.IMG_ADDRESS, c.cat_idx\n"
						+ "FROM product p, category c "
						+ "WHERE p.category_id = c.cat_idx AND c.cat_name = ? ORDER BY p.pro_idx";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Cate);
			} else {
				sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_name as 카테고리, p.price as 가격, p.inventory as 현재재고량, p.IMG_ADDRESS, c.cat_idx\n"
						+ "FROM product p, category c " + "WHERE p.category_id = c.cat_idx ORDER BY p.pro_idx";
				pstmt = con.prepareStatement(sql);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Vector<Object> data = new Vector<>();
				ProductBean pb = new ProductBean();
				data.add(rs.getInt(1));
				data.add(rs.getString(2));
				data.add(rs.getString(3));
				data.add(rs.getInt(4));
				data.add(rs.getInt(5));
				pb.setProIdx(rs.getInt(1));
				pb.setCateName(rs.getString(3));
				pb.setImgAddress(rs.getString(6));
				pb.setCateIdx(rs.getInt(7));
				dtm.addRow(data);
				pbv.add(pb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return pbv;
	}

	public Vector<MainProductBean> selectPro(String Cate, int sortMode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<MainProductBean> mpbv = new Vector<>();
		try {
			con = pool.getConnection();
			if (Cate != "전체") {
				switch (sortMode) {
				case Constant.SORTPOPULARITY:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n"
							+ "JOIN category c ON p.category_id = c.cat_idx AND c.cat_name = ?\r\n"
							+ "LEFT JOIN order_log o ON p.pro_idx = o.pro_idx\r\n"
							+ "GROUP BY p.pro_idx, p.name, c.cat_idx, c.cat_name, p.price, p.IMG_ADDRESS\r\n"
							+ "ORDER BY COUNT(o.pro_idx) DESC";
					break;
				case Constant.SORTPRICEDESC:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n"
							+ "JOIN category c ON p.category_id = c.cat_idx AND c.cat_name = ?\r\n"
							+ "order by p.price desc";
					break;
				default:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n"
							+ "JOIN category c ON p.category_id = c.cat_idx AND c.cat_name = ?\r\n"
							+ "order by p.price";
					break;
				}
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Cate);
			} else {
				switch (sortMode) {
				case Constant.SORTPOPULARITY:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n" + "JOIN category c ON p.category_id = c.cat_idx\r\n"
							+ "LEFT JOIN order_log o ON p.pro_idx = o.pro_idx\r\n"
							+ "GROUP BY p.pro_idx, p.name, c.cat_idx, c.cat_name, p.price, p.IMG_ADDRESS\r\n"
							+ "ORDER BY COUNT(o.pro_idx) DESC";
					break;
				case Constant.SORTPRICEDESC:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n" + "JOIN category c ON p.category_id = c.cat_idx\r\n"
							+ "order by p.price desc";
					break;
				default:
					sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
							+ "FROM product p\r\n" + "JOIN category c ON p.category_id = c.cat_idx\r\n"
							+ "order by p.price";
					break;
				}
				pstmt = con.prepareStatement(sql);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MainProductBean mpb = new MainProductBean();
				ProductBean pb = new ProductBean();
				pb.setProIdx(rs.getInt(1));
				pb.setProName(rs.getString(2));
				pb.setCateIdx(rs.getInt(3));
				pb.setCateName(rs.getString(4));
				pb.setPrice(rs.getInt(5));
				pb.setImgAddress(rs.getString(6));
				mpb.setPb(pb);
				mpbv.add(mpb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return mpbv;
	}

	public Vector<MainProductBean> selectProBySearch(String word, int sortMode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<MainProductBean> mpbv = new Vector<>();
		try {
			con = pool.getConnection();
			switch (sortMode) {
			case Constant.SORTPOPULARITY:
				sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
						+ "FROM product p\r\n"
						+ "JOIN category c ON p.category_id = c.cat_idx AND replace(trim(p.name),' ','') like ?\r\n"
						+ "LEFT JOIN order_log o ON p.pro_idx = o.pro_idx\r\n"
						+ "GROUP BY p.pro_idx, p.name, c.cat_idx, c.cat_name, p.price, p.IMG_ADDRESS\r\n"
						+ "ORDER BY COUNT(o.pro_idx) DESC";
				break;
			case Constant.SORTPRICEDESC:
				sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
						+ "FROM product p\r\n"
						+ "JOIN category c ON p.category_id = c.cat_idx AND replace(trim(p.name),' ','') like ?\r\n"
						+ "order by p.price desc";
				break;
			default:
				sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호, c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
						+ "FROM product p\r\n"
						+ "JOIN category c ON p.category_id = c.cat_idx AND replace(trim(p.name),' ','') like ?\r\n"
						+ "order by p.price";
				break;
			}
//			sql = "SELECT p.pro_idx as 상품번호, p.name as 상품명, c.cat_idx as 카테고리번호,c.cat_name as 카테고리, p.price as 가격, p.IMG_ADDRESS\r\n"
//					+ "from product p, category c\r\n"
//					+ "where replace(trim(p.name),' ','') like ? AND p.category_id = c.cat_idx";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + word + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MainProductBean mpb = new MainProductBean();
				ProductBean pb = new ProductBean();
				pb.setProIdx(rs.getInt(1));
				pb.setProName(rs.getString(2));
				pb.setCateIdx(rs.getInt(3));
				pb.setCateName(rs.getString(4));
				pb.setPrice(rs.getInt(5));
				pb.setImgAddress(rs.getString(6));
				mpb.setPb(pb);
				mpbv.add(mpb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return mpbv;
	}

	public void selectCate(DefaultComboBoxModel<String> dcbm) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		dcbm.removeAllElements();
		dcbm.addElement("전체");
		try {
			con = pool.getConnection();
			sql = "select cat_name from category";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dcbm.addElement(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}

	public boolean updateInven(int idx, int inven) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "update product set inventory = inventory + ? where PRO_IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inven);
			pstmt.setInt(2, idx);
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public boolean deletePro(ProductBean pb) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "delete from PRODUCT where PRO_IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pb.getProIdx());
			updateAmount = pstmt.executeUpdate();

			sql = "select count(*)\r\n" + "from product\r\n" + "where category_id = ?\r\n" + "group by category_id";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pb.getCateIdx());
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				sql = "delete from category where cat_idx = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, pb.getCateIdx());
				int cnt = pstmt.executeUpdate();
				if (cnt > 0) {
					Admin_AddInventory aa = Admin_AddInventory.getinstance();
					JOptionPane.showMessageDialog(aa, pb.getCateName() + " 카테고리의 상품이 없어 해당 카테고리를 삭제합니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public Vector<StatisBean> selectCateSt() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<Integer> temp = new Vector<Integer>();
		Vector<StatisBean> returnbean = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select c.cat_name, sum(o.quantity)\r\n" + " from order_log o, product p, category c\r\n"
					+ " WHERE o.pro_idx = p.pro_idx AND p.category_id = c.cat_idx AND o.status = 1\r\n"
					+ " Group by c.cat_name\r\n" + " order by sum(o.quantity) DESC";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setcName(rs.getString(1));
				sb.setPurCount(rs.getInt(2));
				temp.add(rs.getInt(2));
				sum += rs.getInt(2);
				returnbean.add(sb);
			}
//			for (int i = 0; i < temp.size(); i++) {
//				sum += temp.get(i);
//				System.out.println("temp : "+ temp.get(i));
//			}
			for (int i = 0; i < temp.size(); i++) {
				returnbean.get(i).setPurCountRatio((int) Math.round((temp.get(i) / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnbean;
	}

	public Vector<StatisBean> selectCateAmountSt() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<Integer> temp = new Vector<Integer>();
		Vector<StatisBean> returnbean = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select c.cat_name, sum(o.quantity*p.price)\r\n" + " from order_log o, product p, category c\r\n"
					+ " WHERE o.pro_idx = p.pro_idx AND p.category_id = c.cat_idx AND o.status = 1 AND o.pro_idx = p.pro_idx\r\n"
					+ " Group by c.cat_name\r\n" + " order by sum(o.quantity*p.price) DESC";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setcName(rs.getString(1));
				sb.setPurCount(rs.getInt(2));
				temp.add(rs.getInt(2));
				sum += rs.getInt(2);
				returnbean.add(sb);
			}
//			for (int i = 0; i < temp.size(); i++) {
//				sum += temp.get(i);
//				System.out.println("temp : "+ temp.get(i));
//			}
			for (int i = 0; i < temp.size(); i++) {
				returnbean.get(i).setPurCountRatio((int) Math.round((temp.get(i) / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnbean;
	}

	public Vector<StatisBean> selectYearSt() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<Integer> temp = new Vector<Integer>();
		Vector<StatisBean> returnbean = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "SELECT Extract(Year from final_date), SUM(quantity)\r\n" + "FROM order_log\r\n"
					+ "where status = 1\r\n" + "group by Extract(Year from final_date)\r\n"
					+ "order by Extract(Year from final_date)";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setYear(rs.getInt(1));
				sb.setPurCount(rs.getInt(2));
				temp.add(rs.getInt(2));
				sum += rs.getInt(2);
				returnbean.add(sb);
			}
//			for (int i = 0; i < temp.size(); i++) {
//				sum += temp.get(i);
//				System.out.println("temp : "+ temp.get(i));
//			}
			for (int i = 0; i < temp.size(); i++) {
				returnbean.get(i).setPurCountRatio((int) Math.round((temp.get(i) / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnbean;
	}

	public Vector<StatisBean> selectYearAmountSt() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<Integer> temp = new Vector<Integer>();
		Vector<StatisBean> returnbean = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "SELECT Extract(Year from o.final_date), SUM(o.quantity*p.price)\r\n"
					+ "FROM order_log o, product p\r\n" + "where o.status = 1 AND o.pro_idx = p.pro_idx\r\n"
					+ "group by Extract(Year from o.final_date)\r\n" + "order by Extract(Year from o.final_date)";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setYear(rs.getInt(1));
				sb.setPurCount(rs.getInt(2));
				temp.add(rs.getInt(2));
				sum += rs.getInt(2);
				returnbean.add(sb);
			}
//			for (int i = 0; i < temp.size(); i++) {
//				sum += temp.get(i);
//				System.out.println("temp : "+ temp.get(i));
//			}
			for (int i = 0; i < temp.size(); i++) {
				returnbean.get(i).setPurCountRatio((int) Math.round((temp.get(i) / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnbean;
	}

	public Vector<StatisBean> selectQuarterSt(int year) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<StatisBean> returnBean = new Vector<StatisBean>();
		for (int i = 0; i < 4; i++) {
			StatisBean st = new StatisBean();
			st.setcName((i + 1) + "분기");
			returnBean.add(st);
		}
		Vector<StatisBean> temp = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select DISTINCT(EXTRACT(MONTH FROM final_date)), sum(Quantity)\r\n" + "from order_log\r\n"
					+ "where status = 1 AND EXTRACT(YEAR FROM final_date) = ?\r\n"
					+ "group by EXTRACT(MONTH FROM final_date)\r\n" + "order by EXTRACT(MONTH FROM final_date)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, year);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setMonth(rs.getInt(1));
				sb.setPurCount(rs.getInt(2));
				sum += rs.getInt(2);
				temp.add(sb);
			}
			for (int i = 0; i < temp.size(); i++) {
				if ((temp.get(i).getMonth() - 1) / 3 == 0) {
					returnBean.get(0).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 1) {
					returnBean.get(1).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 2) {
					returnBean.get(2).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 3) {
					returnBean.get(3).purCountPlus(temp.get(i).getPurCount());
				}
			}
			for (int i = 0; i < 4; i++) {
				returnBean.get(i).setPurCountRatio((int) Math.round((returnBean.get(i).getPurCount() / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnBean;
	}

	public Vector<StatisBean> selectQuarterAmountSt(int year) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<StatisBean> returnBean = new Vector<StatisBean>();
		for (int i = 0; i < 4; i++) {
			StatisBean st = new StatisBean();
			st.setcName((i + 1) + "분기");
			returnBean.add(st);
		}
		Vector<StatisBean> temp = new Vector<StatisBean>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select DISTINCT(EXTRACT(MONTH FROM o.final_date)), sum(o.Quantity*p.price)\r\n"
					+ "from order_log o, product p\r\n"
					+ "where o.status = 1 AND o.pro_idx = p.pro_idx AND EXTRACT(YEAR FROM o.final_date) = ?\r\n"
					+ "group by EXTRACT(MONTH FROM o.final_date)\r\n" + "order by EXTRACT(MONTH FROM o.final_date)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, year);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StatisBean sb = new StatisBean();
				sb.setMonth(rs.getInt(1));
				sb.setPurCount(rs.getInt(2));
				sum += rs.getInt(2);
				temp.add(sb);
			}
			for (int i = 0; i < temp.size(); i++) {
				if ((temp.get(i).getMonth() - 1) / 3 == 0) {
					returnBean.get(0).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 1) {
					returnBean.get(1).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 2) {
					returnBean.get(2).purCountPlus(temp.get(i).getPurCount());
				} else if ((temp.get(i).getMonth() - 1) / 3 == 3) {
					returnBean.get(3).purCountPlus(temp.get(i).getPurCount());
				}
			}
			for (int i = 0; i < 4; i++) {
				returnBean.get(i).setPurCountRatio((int) Math.round((returnBean.get(i).getPurCount() / sum) * 100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnBean;
	}

	public Vector<String> selectYear() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<String> returnStr = new Vector<String>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select DISTINCT(EXTRACT(YEAR FROM final_date))\r\n" + "from order_log\r\n" + "where status = 1\r\n"
					+ "order by EXTRACT(YEAR FROM final_date)";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				returnStr.add(Integer.toString(rs.getInt(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return returnStr;
	}

	public HashMap<Integer, CartBean> selectCart(int memIdx, int mode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		HashMap<Integer, CartBean> cartHm = new HashMap<>();
		double sum = 0;
		try {
			con = pool.getConnection();
			sql = "select p.pro_idx as proIdx, p.name as name, o.quantity as quantity, p.price as price, o.payment\r\n"
					+ "from order_log o, product p\r\n"
					+ "where p.pro_idx = o.pro_idx and o.mem_idx = ? and status = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memIdx);
			pstmt.setInt(2, mode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CartBean cb = new CartBean();
				cb.setProIdx(rs.getInt(1));
				cb.setProName(rs.getString(2));
				cb.setQuantity(rs.getInt(3));
				cb.setProPrice(rs.getInt(4));
				cb.getMb().setIdx(memIdx);
				cb.setPayment(rs.getInt(5));
				if (!cartHm.containsKey(cb.getProIdx())) {
					cartHm.put(cb.getProIdx(), cb);
				} else {
					int temp = cartHm.get(cb.getProIdx()).getQuantity();
					temp += cb.getQuantity();
					cartHm.get(cb.getProIdx()).setQuantity(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return cartHm;
	}

	public Vector<OrderLogBean> selectOrderLog(int memIdx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<OrderLogBean> olv = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select p.pro_idx as proIdx, p.name as name, o.quantity as quantity, p.price as price, p.img_address, o.order_date, o.final_date, o.status, o.payment\r\n"
					+ "from order_log o, product p\r\n"
					+ "where p.pro_idx = o.pro_idx and o.mem_idx = ? and status != 0 And status != 2 order by o.order_date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memIdx);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderLogBean olb = new OrderLogBean();
				olb.getPb().setProIdx(rs.getInt(1));
				olb.getPb().setProName(rs.getString(2));
				olb.setQuantity(rs.getInt(3));
				olb.getPb().setPrice(rs.getInt(4));
				olb.getPb().setImgAddress(rs.getString(5));
				olb.setOrderTimeStamp(rs.getString(6));
				olb.setFinalTimeStamp(rs.getString(7));
				olb.setStatus(rs.getInt(8));
				olb.setPayment(rs.getInt(9));
				olv.add(olb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return olv;
	}

	public Vector<OrderLogBean> selectOrderLog2() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		Vector<OrderLogBean> olv = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "select p.pro_idx as proIdx, p.name as name, o.quantity as quantity, p.price as price, p.img_address, o.order_date, o.final_date, o.status, m.Name, m.mem_idx\r\n"
					+ "from order_log o, product p, member m\r\n"
					+ "where p.pro_idx = o.pro_idx and o.status = 1 and m.mem_idx = o.mem_idx order by o.order_date DESC";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderLogBean olb = new OrderLogBean();
				olb.getPb().setProIdx(rs.getInt(1));
				olb.getPb().setProName(rs.getString(2));
				olb.setQuantity(rs.getInt(3));
				olb.getPb().setPrice(rs.getInt(4));
				olb.getPb().setImgAddress(rs.getString(5));
				olb.setOrderTimeStamp(rs.getString(6));
				olb.setFinalTimeStamp(rs.getString(7));
				olb.setStatus(rs.getInt(8));
				olb.setMemName(rs.getString(9));
				olb.setMemIdx(rs.getInt(10));
				olv.add(olb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return olv;
	}

	public boolean updateOrderLog(OrderLogBean olb, int oldMode, int newMode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		System.out.println(olb.getMemIdx() + "/" + olb.getPb().getProIdx() + "/" + olb.getStatus() + "/" + olb.getOrderDate() + "/" + olb.getFinalDate());
		Vector<OrderLogBean> olv = new Vector<>();
		try {
			con = pool.getConnection();
			sql = "update order_log set status = ?, final_date = SYSDATE where mem_idx = ? And pro_idx = ? And status = ? And order_date = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newMode);
			pstmt.setInt(2, olb.getMemIdx());
			pstmt.setInt(3, olb.getPb().getProIdx());
			pstmt.setInt(4, oldMode);
			pstmt.setString(5, olb.getOrderTimeStamp());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public boolean updateCartFromPro(CartBean cb, int status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			if (status == Constant.CARTORDERCOMPLETE) {
				con.setAutoCommit(false);
				sql = "update order_log set status = ?, Final_date = SYSDATE, Quantity = ? , payment = ? where PRO_IDX = ? AND MEM_IDX = ? AND STATUS = 0";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, status);
				pstmt.setInt(2, cb.getQuantity());
				pstmt.setInt(3, cb.getPayment());
				pstmt.setInt(4, cb.getProIdx());
				pstmt.setInt(5, cb.getMb().getIdx());
				updateAmount = pstmt.executeUpdate();
				sql = "update product set inventory = inventory - ? where PRO_IDX = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cb.getQuantity());
				pstmt.setInt(2, cb.getProIdx());
				updateAmount = pstmt.executeUpdate();
				System.out.println(cb.getProIdx() + "/" + cb.getQuantity());
				con.commit();
				con.setAutoCommit(true);
			} else {
				sql = "update order_log set status = ?, Final_date = SYSDATE where PRO_IDX = ? AND STATUS = 0";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, status);
				pstmt.setInt(2, cb.getProIdx());
				updateAmount = pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public int insertShopCart(CartBean cb, int status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "select quantity\r\n" + "from order_log\r\n" + "where pro_idx = ? AND mem_idx = ? AND status = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cb.getProIdx());
			pstmt.setInt(2, cb.getMb().getIdx());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next() && status == 0) {
				ProductInfo pi = ProductInfo.getInstance();
				int answer = JOptionPane.showConfirmDialog(pi,
						"장바구니에 이미 존재하는 상품입니다. 그래도 추가하시겠습니까?\n(예를 누르시면 장바구니에 있는 해당 상품의 수량에 1이 추가됩니다.)", "안내",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					sql = "UPDATE order_log\r\n" + "    SET QUANTITY = QUANTITY + ?,\r\n"
							+ "        final_date = SYSDATE, payment = payment + ?\r\n" + "    WHERE pro_idx = ?\r\n"
							+ "      AND mem_idx = ?\r\n" + "      AND status = 0";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(3, cb.getProIdx());
					pstmt.setInt(4, cb.getMb().getIdx());
					pstmt.setInt(1, cb.getQuantity());
					pstmt.setInt(2, cb.getPayment());
					updateAmount = pstmt.executeUpdate();
				} else {
					return -1;
				}
			} else {
				sql = "INSERT INTO ORDER_LOG VALUES (?,?,SYSDATE,?,?,SYSDATE,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cb.getProIdx());
				pstmt.setInt(2, cb.getMb().getIdx());
				pstmt.setInt(3, cb.getQuantity());
				pstmt.setInt(4, status);
				pstmt.setInt(5, cb.getPayment());
				updateAmount = pstmt.executeUpdate();
				System.out.println("인서트");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount;
	}

	public MemberBean selectMember(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		MemberBean mb = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "select *\r\n" + "from member\r\n" + "where id = ? AND pw = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				mb.setIdx(rs.getInt(1));
				mb.setId(rs.getString(2));
				mb.setPassword(rs.getString(3));
				mb.setName(rs.getString(4));
				mb.setGrade(rs.getString(5));
				mb.setPoint(rs.getInt(6));
				mb.setJoinDate(rs.getString(7));
				mb.setType(rs.getString(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return mb;
	}
	// 송명준 재고추가, 통계, 메인페이지, 장바구니 로그인회원정보 끝

	// 강성웅-리뷰

	public Vector<ReviewBean> selectReview(int proIdx, int mode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = new String();
		switch (mode) {
		case Constant.REVIEWLATEST:
			sql = "select r.pro_idx, r.mem_idx, m.name, r.com_date, r.comments, r.star_rating\r\n"
					+ "from review r, member m\r\n"
					+ "where r.pro_idx = ? AND r.mem_idx = m.mem_idx order by com_date desc";
			break;
		case Constant.REVIEWHIGHSTARRATE:
			sql = "select r.pro_idx, r.mem_idx, m.name, r.com_date, r.comments, r.star_rating\r\n"
					+ "from review r, member m\r\n"
					+ "where r.pro_idx = ? AND r.mem_idx = m.mem_idx order by star_Rating desc";
			break;
		default:
			sql = "select r.pro_idx, r.mem_idx, m.name, r.com_date, r.comments, r.star_rating\r\n"
					+ "from review r, member m\r\n"
					+ "where r.pro_idx = ? AND r.mem_idx = m.mem_idx order by star_Rating";
			break;
		}
		Vector<ReviewBean> rbv = new Vector<>();

		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, proIdx);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewBean bean = new ReviewBean();
				bean.setProIdx(rs.getInt(1));
				bean.setMemIdx(rs.getInt(2));
				bean.setMemName(rs.getString(3));
				bean.setComTimeStamp(rs.getString(4));
				bean.setComments(rs.getString(5));
				bean.setStarRating(rs.getInt(6));
				rbv.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return rbv;
	}

	public boolean isPurchased(int memIdx, int proIdx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select * from order_log where pro_idx = ? And mem_idx = ? AND status != 0 AND status != 2";
		boolean flag = false;
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, proIdx);
			pstmt.setInt(2, memIdx);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return flag;
	}

	public boolean deleteReview(ReviewBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM shop.REVIEW WHERE PRO_IDX = ? AND MEM_IDX = ? AND COM_DATE = to_timestamp(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bean.getProIdx());
			pstmt.setInt(2, bean.getMemIdx());
			pstmt.setString(3, bean.getComTimeStamp());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public boolean insertReview(ReviewBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO REVIEW VALUES (?,?,SYSDATE,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bean.getProIdx());
			pstmt.setInt(2, bean.getMemIdx());
			pstmt.setString(3, bean.getComments());
			pstmt.setInt(4, bean.getStarRating());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public boolean updateReview(ReviewBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "UPDATE REVIEW SET comments = ? , star_rating = ?, com_date = SYSDATE WHERE pro_idx = ? AND mem_idx = ? AND com_date = to_timestamp(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "(수정됨)" + bean.getComments());
			pstmt.setInt(2, bean.getStarRating());
			pstmt.setInt(3, bean.getProIdx());
			pstmt.setInt(4, bean.getMemIdx());
			pstmt.setString(5, bean.getComTimeStamp());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public boolean updateMember(MemberBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "UPDATE member SET PW = ?, GRADE = ?, POINT = ? WHERE mem_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getPassword());
			pstmt.setString(2, bean.getGrade());
			pstmt.setInt(3, bean.getPoint());
			pstmt.setInt(4, bean.getIdx());
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return updateAmount > 0 ? true : false;
	}

	public long selectConsumption(int memIdx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "select sum(o.payment)\r\n" + "from product p, order_log o\r\n"
				+ "where p.pro_idx = o.pro_idx AND p.price*o.quantity = o.payment AND o.status != 0 AND o.status != 2 AND mem_idx = ?";
		long Amount = 0;
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memIdx);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Amount = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		System.out.println(Amount);
		return Amount;
	}

	// 강성웅-리뷰

	// 박수근 - 회원가입 시작

	public boolean signUp(String id, String pw, String name) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int updateAmount = 0;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO member VALUES (seq_mem.nextval, ?, ?, ?, 'Bronze', 0, SYSDATE, '일반')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			updateAmount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return updateAmount > 0 ? true : false;
	}

	// 박수근 - 회원가입 끝

	// 서수정 시작
	// 로그인
	public boolean login(String id, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select pw\r\n" + "from member\r\n" + "where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(password))
					return true; // 로그인 성공
				else {
					return false; // 로그인 실패
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 새상품 추가

	public ProductBean addProduct(ProductBean pb) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;

		try {

			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "insert into product values(seq_pro.nextval,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pb.getProName());
			pstmt.setInt(2, pb.getCateIdx());
			pstmt.setInt(3, pb.getPrice());
			pstmt.setString(4, pb.getImgAddress());
			pstmt.setInt(5, pb.getInventory());

			int cnt = pstmt.executeUpdate();

			sql = "select LAST_NUMBER-1 from USER_SEQUENCES where SEQUENCE_NAME = 'SEQ_PRO' ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				pb.setProIdx(rs.getInt(1));

			}
			sql = "update product set img_address = ? where pro_idx = ? ";
			pstmt = con.prepareStatement(sql);

			String imgAddress = "./img\\\\product" + pb.getProIdx() + ".png";
			System.out.println(imgAddress);
			pstmt.setString(1, imgAddress);
			pstmt.setInt(2, pb.getProIdx());

			cnt = pstmt.executeUpdate();

			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
//				JOptionPane.showMessageDialog(null, (String)e.getMessage(), "11",JOptionPane.PLAIN_MESSAGE);
			pb.setProIdx(-1);
		} finally {
			pool.freeConnection(con, pstmt); // 리소스 반환
		}

		return pb;
	}

	// 카테고리 추가
	public boolean addCate(String addCate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();

			sql = "insert into category values(seq_cat.nextval,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, addCate);
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true; // 카테고리 추가 성공
			} else {
				flag = false; // 카테고리 추가 실패
			}
			System.out.println(flag);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;

	}

	// 카테고리 콤보박스 리스트
	public Vector<Integer> comboList(DefaultComboBoxModel<String> cateList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		Vector<Integer> cateIdx = new Vector<>();

		try {

			con = pool.getConnection();
			sql = "select cat_name, cat_idx from category";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			cateList.removeAllElements();
			while (rs.next()) {
				cateList.addElement(rs.getString(1));
				cateIdx.add(rs.getInt(2));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

		return cateIdx;

	}
	
	// 아이디 중복확인
		public boolean idCheck(String id) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			boolean flag = false;

			try {
				con = pool.getConnection();
				sql = "select id from member where id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					flag = true; // 중복
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			return flag;
		}
	// 서수정 끝
}
