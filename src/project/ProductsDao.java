package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsDao {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String user="sqlid";
	String pw="sqlpw";
	Connection conn;

	public ProductsDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw); 
			System.out.println("proDao접속성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// ProductDao

	public ArrayList<ProductsBean> getAllProducts(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductsBean> lists = new ArrayList<ProductsBean>();
		String sql = "select * from products order by id asc";
		
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

				ProductsBean pb = new ProductsBean();
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
		}//finally
		return lists;
	}//getAllProducts

	public ProductsBean getProductById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductsBean pb = null;
		String sql = "select * from products where id=? order by id asc";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if(rs.next()) {
				int id2 = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String inputdate = String.valueOf(rs.getDate("inputdate")); 

				pb = new ProductsBean();
				pb.setId(id2);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category);
				pb.setInputdate(inputdate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pb; 
	}//getProductById

	public ArrayList<ProductsBean> getProductByCategory(String category){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductsBean> lists = new ArrayList<ProductsBean>();
		String sql = "select * from products where upper(category)=? order by id asc";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, category.toUpperCase());

			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category2 = rs.getString("category");
				String inputdate = String.valueOf(rs.getDate("inputdate")); 

				ProductsBean pb = new ProductsBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category2);
				pb.setInputdate(inputdate);
				lists.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lists;
	}
	
	public ArrayList<ProductsBean> getProductByPrice(String price){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductsBean> lists = new ArrayList<ProductsBean>();
		String sql = "select * from products where price <= ? order by price asc";
		
		if(price.equals("50(down)")) {
			price = "50";
		}else if(price.equals("100(down)")) {
			price = "100";
		}else if(price.equals("500(down)")) {
			price = "500";
		}else if(price.equals("1000(down)")) {
			price = "1000";
		}else {
			return lists;
		}
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, price); // It
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price2 = rs.getInt("price");
				String category = rs.getString("category");
				String inputdate = String.valueOf(rs.getDate("inputdate")); 
				
				ProductsBean pb = new ProductsBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price2);
				pb.setCategory(category);
				pb.setInputdate(inputdate);
				lists.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lists;
	}//getProductByCategory

	public int insertData(ProductsBean bean){
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "insert into products values(prdseq.nextval,?, ?, ?, ?, ?)";

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
		} if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}//insertData

	public int updateData(ProductsBean pb){

		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "update products set name = ?, stock = ?, price = ?, category = ?, inputdate = ? where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pb.getName());
			ps.setInt(2, pb.getStock());
			ps.setInt(3, pb.getPrice());
			ps.setString(4, pb.getCategory());
			ps.setString(5, pb.getInputdate());
			ps.setInt(6, pb.getId());

			cnt = ps.executeUpdate();
			System.out.println("cnt:"+cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}//updateData

	public int deleteData(int id) {
		String sql = "delete from products where id=?";
		PreparedStatement ps = null;  
		int cnt=-1;
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			cnt=ps.executeUpdate();
			System.out.println("cnt:"+cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cnt;
	}//deleteData
	
	public void exit() {
		if(conn != null)
			try {
				System.out.println("DB 접속종료");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}//exit
	
	public ArrayList<ProductsBean> getCategoryData(String category) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductsBean pb = null;
		ArrayList<ProductsBean> lists = new ArrayList<ProductsBean>();

		if(category.equals("Category")) {
			return lists;
		}
		String sql = "select * from products where category = ? order by id asc";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, category);

			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category2 = rs.getString("category");
				String inputdate = String.valueOf(rs.getDate("inputdate")); 

				pb = new ProductsBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category2);
				pb.setInputdate(inputdate);

				lists.add(pb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lists;
	}//getCategoryData
}//ProductsDao