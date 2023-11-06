package project;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class ProductsMain extends JFrame implements ActionListener, ItemListener {
	private String[] columnNames= {"아이디","이름","입고수량","단가","카테고리","입고일"};
	private String[] shocolumnNames= {"아이디","이름","입고수량","단가","카테고리","입고일"};
	private Object[][] rowData;
	private Object[][] shorowData;
	Choice ch = new Choice();
	Choice ch2 = new Choice();
	private JTable protable, shotable;
	private JScrollPane proscrollPane, shoscrollPane;
	JButton addbtn, subbtn, managerbtn, refresh, exitbtn, backbtn;
	JTextField txtId = new JTextField();
	JTextField txtName = new JTextField();
	JTextField txtStock = new JTextField();
	JTextField txtPrice = new JTextField();
	JTextField txtCategory = new JTextField();
	JTextField txtInputdate = new JTextField();
	ArrayList<ProductsBean> prolists;
	ArrayList<ShoppingBean> sholists;
	Container contentPane;
	ProductsDao prodao;
	ShoppingDao shodao;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 30);
	Font f2 = new Font("바탕", Font.ITALIC | Font.BOLD, 12);
	
	public ProductsMain(String title) {
		super("장바구니");
		compose();
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(1200,550);
		setLocationRelativeTo(null);
		setVisible(true);
	}// ProductMain

	private void compose() {
		prodao = new ProductsDao();
		shodao = new ShoppingDao();
		prolists = prodao.getAllProducts();
		sholists = shodao.getAllShopping();
		rowData = new Object[prolists.size()][columnNames.length];
		shorowData = new Object[sholists.size()][shocolumnNames.length];
		fillData();

		protable = new JTable(rowData,columnNames);
		shotable = new JTable(shorowData,shocolumnNames);
		proscrollPane = new JScrollPane(protable);
		shoscrollPane = new JScrollPane(shotable);
		contentPane = getContentPane();
		contentPane.setLayout(null);
		proscrollPane.setBounds(550,100,490,300);
		shoscrollPane.setBounds(20,100,490,300);
		contentPane.add(proscrollPane);
		contentPane.add(shoscrollPane);
		
		JPanel pEast = new JPanel(); 
		pEast.setLayout(null);
		pEast.setBounds(0, 700, 200, 200);
		contentPane.add(pEast);
		pEast.setBackground(Color.black);
		
		JLabel title = new JLabel("장바구니");
		JPanel pTop = new JPanel();
		pTop.setLayout(null);
		pTop.setBounds(0, 200, 500, 500);
		title.setFont(f1);
		pTop.add(title);
		title.setBounds(470, 30, 500, 40);
		getContentPane().add(title);
		
		refresh= new JButton("새로고침");
		refresh.setBounds(1050, 100, 130, 30);
		contentPane.add(refresh);
		refresh.addActionListener(this);
		
		managerbtn = new JButton("관리자");
		managerbtn.setBounds(1050, 140, 130, 30);
		contentPane.add(managerbtn);
		managerbtn.addActionListener(this);

		addbtn = new JButton("담기");
		addbtn.setBounds(1050, 180, 60, 30);
		contentPane.add(addbtn);
		addbtn.addActionListener(this);

		subbtn = new JButton("빼기");
		subbtn.setBounds(1120, 180, 60, 30);
		contentPane.add(subbtn);
		subbtn.addActionListener(this);

		exitbtn = new JButton("종료");
		exitbtn.setBounds(1050, 370, 130, 30);
		contentPane.add(exitbtn);
		exitbtn.addActionListener(this);
		
		backbtn = new JButton("◀");
		backbtn.setBounds(20, 20, 50, 30);
		contentPane.add(backbtn);
		backbtn.addActionListener(this);

		protable.addMouseListener(new MouseHandler());
		shotable.addMouseListener(new MouseHandler2());
		refresh.addMouseListener(new MouseHandler3());
		txtStock.addKeyListener(new keyHandler());
		txtPrice.addKeyListener(new keyHandler());
		
		refresh.setFont(f2);
		managerbtn.setFont(f2);
		addbtn.setFont(f2);
		subbtn.setFont(f2);
		exitbtn.setFont(f2);
		
		refresh.setBackground(Color.black); 
		refresh.setForeground(Color.white); 
		managerbtn.setBackground(Color.black); 
		managerbtn.setForeground(Color.white); 
		addbtn.setBackground(Color.black); 
		addbtn.setForeground(Color.white); 
		subbtn.setBackground(Color.black); 
		subbtn.setForeground(Color.white); 
		exitbtn.setBackground(Color.black); 
		exitbtn.setForeground(Color.white); 
		backbtn.setBackground(Color.black); 
		backbtn.setForeground(Color.white); 
		
		refresh.setBorderPainted(false);
		managerbtn.setBorderPainted(false);
		addbtn.setBorderPainted(false);
		subbtn.setBorderPainted(false);
		exitbtn.setBorderPainted(false);
		backbtn.setBorderPainted(false);
		
		String[] category = {"전자기기", "음식", "음악", "과자", "라면"};
		ch = new Choice();
		ch.add("Category");
		for(int i = 0; i < category.length; i++) {
			ch.add(category[i]);
		}
		ch.addItemListener(this);
		ch.setBounds(1120, 220, 60, 30);
		add(ch);
		
		String[] price = {"50(down)", "100(down)", "500(down)", "1000(down)"};
		ch2 = new Choice();
		ch2.add("Price(down)");
		for(int i = 0; i < price.length; i++) {
			ch2.add(price[i]);
		}
		ch2.addItemListener(this);
		ch2.setBounds(1050, 220, 60, 30);
		add(ch2);
	}//compose

	private void fillData() {
		Object[] proarr = prolists.toArray();
		int j=0;
		for(int i=0;i<proarr.length;i++) {
			ProductsBean pb = (ProductsBean)proarr[i];
			rowData[i][j++] = pb.getId();
			rowData[i][j++] = pb.getName();
			rowData[i][j++] = pb.getStock();
			rowData[i][j++] = pb.getPrice();
			rowData[i][j++] = pb.getCategory();
			rowData[i][j++] = pb.getInputdate();
			j = 0;
		}

		Object[] shoarr = sholists.toArray();
		for(int i=0;i<shoarr.length;i++) {
			ShoppingBean pb = (ShoppingBean)shoarr[i];
			shorowData[i][j++] = pb.getId();
			shorowData[i][j++] = pb.getName();
			shorowData[i][j++] = pb.getStock();
			shorowData[i][j++] = pb.getPrice();
			shorowData[i][j++] = pb.getCategory();
			shorowData[i][j++] = pb.getInputdate();
			j = 0;
		}
	}//fillData

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == addbtn) {
			addData();
			getAllProducts();
		}else if(obj == subbtn) {
			subData();
			getAllProducts();
		}else if(obj == managerbtn) {
			new Admin(getTitle());
		}else if(obj == exitbtn) {
			exit();
		}else if(obj == backbtn) {
			setVisible(false);
			new Login(getTitle());
		}
	}//actionPerformed
	
	private void exit() {
		int answer = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "confirm",JOptionPane.OK_CANCEL_OPTION );
		if(answer == JOptionPane.OK_OPTION) {
			prodao.exit();
			shodao.exit();
			System.exit(0);
		}
	}//exit

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

			int cnt = prodao.updateData(pb);
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
		int row = protable.getSelectedRow();

		if(row != -1) {
			int id = Integer.parseInt(protable.getValueAt(row, 0).toString());	
			int cnt = prodao.deleteData(id);
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

	public void addData() {
		boolean check = checkData();

		if(check) {
			String name = txtName.getText();
			int stock = Integer.parseInt(txtStock.getText());
			int price = Integer.parseInt(txtPrice.getText());
			String category = txtCategory.getText();
			String inputdate = txtInputdate.getText();

			ShoppingBean pb = new ShoppingBean();
			pb.setName(name);
			pb.setStock(stock);
			pb.setPrice(price);
			pb.setCategory(category);
			pb.setInputdate(inputdate);

			int cnt = shodao.addData(pb);
			if(cnt == -1) {
				
			}else {
				JOptionPane.showMessageDialog(this, "등록 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				getAllShopping();
				getAllProducts();
				clearTextField();
			}
		}
	}//addData

	public void subData() {
		int row = shotable.getSelectedRow();

		if(row != -1) {
			int id = Integer.parseInt(shotable.getValueAt(row, 0).toString());	
			int cnt = shodao.subData(id);
			if(cnt == -1) {
				
			}else {
				JOptionPane.showMessageDialog(this, "빼기 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				getAllShopping();
				getAllProducts();
				clearTextField();
			}
		}else {
			JOptionPane.showMessageDialog(this, "삭제할 레코드를 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
		}
	}//subData

	private void getAllProducts() {
		prodao.getAllProducts();
		prodao = new ProductsDao();
		prolists = prodao.getAllProducts();
		rowData = new Object[prolists.size()][columnNames.length];
		fillData();

		protable = new JTable(rowData,columnNames);
		proscrollPane.setViewportView(protable);

		protable.addMouseListener(new MouseHandler());//다시 호출해서 계속 뜨게함
	}//getAllProducts

	private void getAllShopping() {
		shodao.getAllShopping();
		shodao = new ShoppingDao();
		sholists = shodao.getAllShopping();
		shorowData = new Object[sholists.size()][shocolumnNames.length];
		fillData();

		shotable = new JTable(shorowData,shocolumnNames);
		shoscrollPane.setViewportView(shotable);

		shotable.addMouseListener(new MouseHandler2());//다시 호출해서 계속 뜨게함
	}//getAllShopping
	
	private void getCategoryData() {
		prodao.getCategoryData(ch.getSelectedItem());
		prodao = new ProductsDao();
		prolists = prodao.getCategoryData(ch.getSelectedItem());
		rowData = new Object[prolists.size()][columnNames.length];
		fillData();

		protable = new JTable(rowData,columnNames);
		proscrollPane.setViewportView(protable);

		protable.addMouseListener(new MouseHandler());//다시 호출해서 계속 뜨게함
	}//getCategoryData
	
	public void getPriceData() {
		prodao.getProductByPrice(ch2.getSelectedItem());
		prodao = new ProductsDao();
		prolists = prodao.getProductByPrice(ch2.getSelectedItem());
		rowData = new Object[prolists.size()][columnNames.length];
		fillData();

		protable = new JTable(rowData,columnNames);
		proscrollPane.setViewportView(protable);

		protable.addMouseListener(new MouseHandler());//다시 호출해서 계속 뜨게함
	}//getProductByPrice
	
	class MouseHandler extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			int prorow = protable.getSelectedRow();

			txtId.setText(String.valueOf(protable.getValueAt(prorow, 0)));
			txtName.setText(String.valueOf(protable.getValueAt(prorow, 1)));
			txtStock.setText(String.valueOf(protable.getValueAt(prorow, 2)));
			txtPrice.setText(String.valueOf(protable.getValueAt(prorow, 3)));
			txtCategory.setText(String.valueOf(protable.getValueAt(prorow, 4)));
			txtInputdate.setText(String.valueOf(protable.getValueAt(prorow, 5)));
		}
	}//MouseHandler

	class MouseHandler2 extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			int shorow = shotable.getSelectedRow();
			txtId.setText(String.valueOf(shotable.getValueAt(shorow, 0)));
			txtName.setText(String.valueOf(shotable.getValueAt(shorow, 1)));
			txtStock.setText(String.valueOf(shotable.getValueAt(shorow, 2)));
			txtPrice.setText(String.valueOf(shotable.getValueAt(shorow, 3)));
			txtCategory.setText(String.valueOf(shotable.getValueAt(shorow, 4)));
			txtInputdate.setText(String.valueOf(shotable.getValueAt(shorow, 5)));
		}
	}//MouseHandler2

	class MouseHandler3 extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			getAllShopping(); // 삽입하고 다시 조회
			getAllProducts(); // 삽입하고 다시 조회
		}
	}//MouseHandler3

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
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Choice obj = (Choice) e.getSource();

		if(obj == ch) {
			prodao.getCategoryData(ch.getSelectedItem());
			getCategoryData();
		}
		else if(obj == ch2) {
			prodao.getProductByPrice(ch2.getSelectedItem());
			getPriceData();
		}
	}
	
	public static void main(String[] args) {
		new ProductsMain("장바구니");
	}//main
}//ProductsMain