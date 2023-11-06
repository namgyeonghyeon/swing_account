package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Manager extends JFrame implements ActionListener{
	private String[] columnNames= {"아이디","이름","입고수량","단가","카테고리","입고일"};
	private Object[][] rowData;
	private JTable table;
	private JScrollPane scrollPane;

	private JTextField txtId = new JTextField();
	private JTextField txtName = new JTextField();
	private JTextField txtStock = new JTextField();
	private JTextField txtPrice = new JTextField();
	private JTextField txtCategory = new JTextField();
	private JTextField txtInputdate = new JTextField();
	JButton addbtn, updatebtn, deletebtn, exitbtn, userManagerbtn, backbtn;
	
	ArrayList<ProductsBean> lists;
	Container contentPane;
	ProductsDao dao;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 30);
	Font f2 = new Font("바탕", Font.ITALIC | Font.BOLD, 12);

	public Manager(String title) {
		super(title);
		getContentPane().setBackground(Color.white);
		compose();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(500,520);
		setLocationRelativeTo(null);
		setVisible(true);
	}// Manager

	private void compose() {
		dao = new ProductsDao();
		lists = dao.getAllProducts();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData,columnNames);
		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();

		contentPane.setLayout(null);
		scrollPane.setBounds(0,0,490,200);
		contentPane.add(scrollPane);

		JLabel lbId = new JLabel("아이디");
		JLabel lbName = new JLabel("이름");
		JLabel lbStock = new JLabel("입고 수량");
		JLabel lbPrice = new JLabel("단가");
		JLabel lbCategory = new JLabel("카테고리");
		JLabel lbInputdate = new JLabel("입고일자(yyyy/mm/dd)");

		JPanel pCenter = new JPanel();
		pCenter.setLayout(null);
		pCenter.setBounds(0, 220,350,150);
		pCenter.setBackground(Color.white);
		contentPane.add(pCenter);

		int vposition = 20;
		lbId.setBounds(30,1*vposition,150,20);
		lbName.setBounds(30,2*vposition,150,20);
		lbStock.setBounds(30,3*vposition,150,20);
		lbPrice.setBounds(30,4*vposition,150,20);
		lbCategory.setBounds(30,5*vposition,150,20);
		lbInputdate.setBounds(30,6*vposition,150,20);

		pCenter.add(lbId);
		pCenter.add(lbName);
		pCenter.add(lbStock);
		pCenter.add(lbPrice);
		pCenter.add(lbCategory);
		pCenter.add(lbInputdate);

		txtId.setBounds(180, 1*vposition, 150, 20);
		txtName.setBounds(180, 2*vposition, 150, 20);
		txtStock.setBounds(180, 3*vposition, 150, 20);
		txtPrice.setBounds(180, 4*vposition, 150, 20);
		txtCategory.setBounds(180, 5*vposition, 150, 20);
		txtInputdate.setBounds(180, 6*vposition, 150, 20);

		lbId.setFont(f2);
		lbName.setFont(f2);
		lbStock.setFont(f2);
		lbPrice.setFont(f2);
		lbCategory.setFont(f2);
		lbInputdate.setFont(f2);
		txtId.setFont(f2);
		txtCategory.setFont(f2);
		txtName.setFont(f2);
		txtPrice.setFont(f2);
		txtStock.setFont(f2);
		txtInputdate.setFont(f2);
		
		txtId.setText(" ");
		txtId.setEnabled(false);
		pCenter.add(txtId);
		pCenter.add(txtCategory);
		pCenter.add(txtName);
		pCenter.add(txtPrice);
		pCenter.add(txtStock);
		pCenter.add(txtInputdate);

		addbtn = new JButton("추가");
		addbtn.setBounds(390, 245, 60, 30);
		contentPane.add(addbtn);
		addbtn.addActionListener(this);

		updatebtn = new JButton("수정");
		updatebtn.setBounds(390, 285, 60, 30);
		contentPane.add(updatebtn);
		updatebtn.addActionListener(this);

		deletebtn= new JButton("삭제");
		deletebtn.setBounds(390, 325, 60, 30);
		contentPane.add(deletebtn);
		deletebtn.addActionListener(this);

		
		userManagerbtn= new JButton("회원리스트");
		userManagerbtn.setBounds(30, 380, 420, 30);
		contentPane.add(userManagerbtn);
		userManagerbtn.addActionListener(this);
		
		exitbtn= new JButton("종료");
		exitbtn.setBounds(30, 430, 200, 30);
		contentPane.add(exitbtn);
		exitbtn.addActionListener(this);
		
		backbtn= new JButton("뒤로가기");
		backbtn.setBounds(250, 430, 200, 30);
		contentPane.add(backbtn);
		backbtn.addActionListener(this);
		
		addbtn.setFont(f2);
		updatebtn.setFont(f2);
		deletebtn.setFont(f2);
		userManagerbtn.setFont(f2);
		exitbtn.setFont(f2);
		backbtn.setFont(f2);
		
		addbtn.setBackground(Color.black); 
		addbtn.setForeground(Color.white); 
		updatebtn.setBackground(Color.black); 
		updatebtn.setForeground(Color.white); 
		deletebtn.setBackground(Color.black); 
		deletebtn.setForeground(Color.white); 
		userManagerbtn.setBackground(Color.black); 
		userManagerbtn.setForeground(Color.white); 
		exitbtn.setBackground(Color.black); 
		exitbtn.setForeground(Color.white); 
		backbtn.setBackground(Color.black); 
		backbtn.setForeground(Color.white);
		
		addbtn.setBorderPainted(false);
		updatebtn.setBorderPainted(false);
		deletebtn.setBorderPainted(false);
		userManagerbtn.setBorderPainted(false);
		exitbtn.setBorderPainted(false);
		backbtn.setBorderPainted(false);
		
		table.addMouseListener(new MouseHandler());
		txtStock.addKeyListener(new keyHandler());
		txtPrice.addKeyListener(new keyHandler());
	}//compose

	private void fillData() {
		Object[] arr = lists.toArray();
		int j=0;
		for(int i=0;i<arr.length;i++) {
			ProductsBean pb = (ProductsBean)arr[i];
			rowData[i][j++] = pb.getId();
			rowData[i][j++] = pb.getName();
			rowData[i][j++] = pb.getStock();
			rowData[i][j++] = pb.getPrice();
			rowData[i][j++] = pb.getCategory();
			rowData[i][j++] = pb.getInputdate();
			j = 0;
		}
	}//fillData

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == addbtn) {
			insertData();
		}else if(obj == updatebtn) {
			updateData();
		}else if(obj == deletebtn) {
			deletedata();
		}else if(obj == userManagerbtn) {
			userlist();
		}else if(obj == exitbtn) {
			exit();
		}else if(obj == backbtn) {
			setVisible(false);
			new Admin(getTitle());
		}
	}//actionPerformed

	private void exit() {
		int answer = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "confirm",JOptionPane.OK_CANCEL_OPTION );
		if(answer == JOptionPane.OK_OPTION) {
			dao.exit();
			System.exit(0);
		}
	}//exit
	
	private void insertData() {
		boolean check = checkData();
		
		if(check) {
			String name = txtName.getText();
			int stock = Integer.parseInt(txtStock.getText());
			int price = Integer.parseInt(txtPrice.getText());
			String category = txtCategory.getText();
			String inputdate = txtInputdate.getText();

			ProductsBean pb = new ProductsBean();
			pb.setName(name);
			pb.setStock(stock);
			pb.setPrice(price);
			pb.setCategory(category);
			pb.setInputdate(inputdate);

			int cnt = dao.insertData(pb);
			if(cnt == -1) {
				System.out.println("삽입된 데이터가 없습니다.");
			}else {
				System.out.println("삽입 성공했습니다.");
				JOptionPane.showMessageDialog(this, "등록 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				getAllProducts(); // 삽입하고 다시 조회
				clearTextField();
			}
		}
	}//insertData

	public void updateData() {
		boolean check = checkData();
		
		if(check) {
			int id = Integer.parseInt(txtId.getText());
			String name = txtName.getText();
			int stock = Integer.parseInt(txtStock.getText());
			int price = Integer.parseInt(txtPrice.getText());
			String category = txtCategory.getText();
			String inputdate = txtInputdate.getText();

			ProductsBean pb = new ProductsBean();
			pb.setId(id);
			pb.setName(name);
			pb.setStock(stock);
			pb.setPrice(price);
			pb.setCategory(category);
			pb.setInputdate(inputdate);

			int cnt = dao.updateData(pb);
			if(cnt == -1) {
				System.out.println("업데이트된 데이터가 없습니다.");
			}else {
				System.out.println("업데이트 성공했습니다.");
				JOptionPane.showMessageDialog(this, "업데이트 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				getAllProducts();
				clearTextField();
			}
		}
	}//updateData

	public void deletedata() {
		int row = table.getSelectedRow();

		if(row != -1) {
			int id = Integer.parseInt(table.getValueAt(row, 0).toString());	
			int cnt = dao.deleteData(id);
			if(cnt == -1) {
				System.out.println("삭제된 데이터가 없습니다.");
			}else {
				System.out.println("삭제 성공했습니다.");
				JOptionPane.showMessageDialog(this, "삭제 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				getAllProducts();
				clearTextField();
			}
		}else {
			JOptionPane.showMessageDialog(this, "삭제할 레코드를 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
		}
	}//deletedata
	
	public void userlist() {
		new userList(getTitle());
		setVisible(false);
	}//userlist

	private boolean checkData() {
		if(txtName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "이름이 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtName.requestFocus();
			return false;
		}else if(txtStock.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "입고수량이 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtStock.requestFocus();
			return false;
		}else if(txtPrice.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "단가가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtPrice.requestFocus();
			return false;
		}else if(txtCategory.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "카테고리가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtCategory.requestFocus();
			return false;
		}else if(txtInputdate.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "입고일이 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtInputdate.requestFocus();
			return false;
		}
		return true;
	}//checkData

	public void clearTextField() {
		txtId.setText("");
		txtName.setText("");
		txtStock.setText("");
		txtPrice.setText("");
		txtCategory.setText("");
		txtInputdate.setText("");
	}//clearTextField

	private void getAllProducts() {
		dao.getAllProducts();
		dao = new ProductsDao();
		lists = dao.getAllProducts();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData,columnNames);
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseHandler());
	}//getAllProducts

	class MouseHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			int row = table.getSelectedRow();

			txtId.setText(String.valueOf(table.getValueAt(row, 0)));
			txtName.setText(String.valueOf(table.getValueAt(row, 1)));
			txtStock.setText(String.valueOf(table.getValueAt(row, 2)));
			txtPrice.setText(String.valueOf(table.getValueAt(row, 3)));
			txtCategory.setText(String.valueOf(table.getValueAt(row, 4)));
			txtInputdate.setText(String.valueOf(table.getValueAt(row, 5)));
		}
	}//MouseHandler

	class keyHandler extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {

			Object obj = e.getSource();
			if(obj == txtStock){
				try {
					Integer.parseInt(txtStock.getText());
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(txtStock, "숫자로 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
					txtStock.setText("");
				}
			}else {
				try {
					Integer.parseInt(txtPrice.getText());
				}catch(NumberFormatException nfe2) {
					JOptionPane.showMessageDialog(txtPrice, "숫자로 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
					txtPrice.setText("");
				}
			}
		}
	}//keyHandler
	
	public static void main(String[] args) {
		new ProductsMain("관리자 화면");
	}//main
}//Manager