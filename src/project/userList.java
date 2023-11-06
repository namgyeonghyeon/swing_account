package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class userList extends JFrame{
	private String[] columnNames= {"아이디","비밀번호","이름"};
	private Object[][] rowData;
	private JTable table;
	private JScrollPane scrollPane;
	UserDao userdao;
	ArrayList<UserBean> lists;
	Container contentPane;
	ProductsDao dao;
	JButton backbtn;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 20);
	
	public userList(String title) {
		super(title);
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		compose();
		setSize(500,480);
		setLocationRelativeTo(null);
		setVisible(true);
	}//userList
	
	public void compose() {
		userdao = new UserDao();
		lists = userdao.getAllUser();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData,columnNames);
		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();
		
		contentPane.setLayout(null);
		scrollPane.setBounds(0,50,490,500);
		contentPane.add(scrollPane);
		
		backbtn = new JButton("◀");
		backbtn.setBounds(10, 10, 50, 30);
		contentPane.add(backbtn);
		
		JLabel lbTitle = new JLabel("회원 리스트");
		JPanel TopPlace = new JPanel();
		TopPlace.setLayout(null);
		TopPlace.setBounds(0, 250, 200, 200);
		TopPlace.add(lbTitle);
		lbTitle.setFont(f1);
		lbTitle.setBounds(180, 5, 150, 50);
		getContentPane().add(lbTitle);
		
		backbtn.setBackground(Color.black); 
		backbtn.setForeground(Color.white);
		
		backbtn.setBorderPainted(false);
		
		backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Manager(getTitle());
                setVisible(false);
            }
        });
		getAllUser();
	}//compose

	private void fillData() { 
		Object[] arr = lists.toArray();
		int j = 0 ;
		for(int i = 0; i < arr.length; i++) {
			UserBean pb = (UserBean)arr[i];
			rowData[i][j++] = pb.getUserId();
			rowData[i][j++] = pb.getUserPw();
			rowData[i][j++] = pb.getUserName();
			j = 0;
		}
	}//fillData
	
	private void getAllUser() {
		userdao.getAllUser();
		userdao = new UserDao();
		lists = userdao.getAllUser();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData,columnNames);
		scrollPane.setViewportView(table);
	}//getAllPUser
	
	public static void main(String[] args) {
		new ProductsMain("회원리스트");
	}//main
}//userList