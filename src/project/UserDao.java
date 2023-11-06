package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class UserDao extends JFrame{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String user = "sqlid";
	String pw = "sqlpw";
	Connection conn;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public UserDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw); 
			System.out.println("userDao접속성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//UserDao
	
	public ArrayList<UserBean> getAllUser() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<UserBean> lists = new ArrayList<UserBean>();
		String sql = "select * from prouser order by userid asc";
		
		try {
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while(rs.next()) {
				String userid = rs.getString("userid");
				String userpw = rs.getString("userpw");
				String username = rs.getString("username");

				UserBean pb = new UserBean();
				pb.setUserId(userid);
				pb.setUserPw(userpw);
				pb.setUserName(username);
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
	}//getAllUser
	
	public int userLoginData(UserBean ub) {
		PreparedStatement ps = null;
		boolean flag = false;
		int cnt = -1;
		String type = null;
		String type2 = null;
		String sql = "select * from prouser WHERE '" + type  + "' = '" + type2 + "'";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			
			while(rs.next()) {
				type = "userid";
				type2 = ub.getUserId();
				if(ub.getUserId().equals(rs.getString("UserId"))) {
					type = "userpw";
					type2 = ub.getUserPw();
					cnt = -2;
					if(ub.getUserPw().equals(rs.getString("UserPw"))) {
						flag = true;
						System.out.println("로그인 성공");
						cnt = 1;
					}	
				}else {
					flag = false;
					System.out.println("로그인 실패");
				}
			}
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
	}//userLoginData
	
	public int RegisterData(UserBean ub){
		PreparedStatement ps = null;
		int cnt = -1;
		String sql = "insert into prouser values(?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, ub.getUserId());
			ps.setString(2, ub.getUserPw());
			ps.setString(3, ub.getUserName());
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
	}//RegisterData
}//UserDao