package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingDao {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String user="sqlid";
	String pw="sqlpw";
	Connection conn;
	PreparedStatement ps = null;

	public ShoppingDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw); 
			System.out.println("shoDao접속성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//ShoppingDao

	public ArrayList<ShoppingBean> getAllShopping(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ShoppingBean> lists = new ArrayList<ShoppingBean>();
		String sql = "select * from shopping order by id asc";
		
		try {
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				Date d = rs.getDate("inputdate");

				String inputdate = String.valueOf(d);

				ShoppingBean pb = new ShoppingBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category);
				pb.setInputdate(inputdate);
				lists.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return lists;
	}//getAllShopping

	public void exit() {
		if(conn != null)
			try {
				System.out.println("DB 접속종료");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}//exit

	public int addData(ShoppingBean bean){
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "insert into shopping values(shoseq.nextval,?, ?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getStock());
			ps.setInt(3, bean.getPrice());
			ps.setString(4, bean.getCategory());
			ps.setString(5, bean.getInputdate());
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}//addData
	
	public int subData(int id) {
		String sql = "delete from shopping where id = ?";
		ps = null;  
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cnt;
	}//subData
}//ShoppingDao