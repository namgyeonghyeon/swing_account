package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Admin extends JFrame{
	JPasswordField txtSceret = new JPasswordField(); 
	JPanel jPanel;
	JButton loginBtn, backBtn;
	Container contentPane;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 30);
	Font f2 = new Font("바탕", Font.ITALIC | Font.BOLD, 12);

	public Admin(String title){
		super(title);
		getContentPane().setBackground(Color.white);
		setSize(300, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		loginBtn = new JButton("관리자 로그인");
		loginBtn.setBounds(45, 300, 200, 30);
		
		backBtn = new JButton("뒤로가기");
		backBtn.setBounds(45, 350, 200, 30);

		JLabel lbId = new JLabel("AdminKey");
		txtSceret.setBounds(85, 210, 160, 20);
		JPanel Place = new JPanel();
		Place.setLayout(null);
		Place.setBounds(550, 230, 490, 200);
		
		JLabel lbTitle = new JLabel("관리자 로그인");
		JPanel TopPlace = new JPanel();
		TopPlace.setLayout(null);
		TopPlace.setBounds(0, 250, 400, 200);
		TopPlace.add(lbTitle);
		lbTitle.setBounds(40, 110, 330, 50);
		getContentPane().add(lbTitle);
		
		lbTitle.setFont(f1);
		lbId.setFont(f2);
		loginBtn.setFont(f2);
		backBtn.setFont(f2);
		
		loginBtn.setBackground(Color.black); 
		loginBtn.setForeground(Color.white); 
		backBtn.setBackground(Color.black); 
		backBtn.setForeground(Color.white);
		loginBtn.setBorderPainted(false);
		backBtn.setBorderPainted(false);
		
		Place.add(txtSceret);
		Place.add(lbId);
		lbId.setBounds(20, 210, 150, 20);
		getContentPane().add(loginBtn);
		getContentPane().add(backBtn);
		getContentPane().add(txtSceret);
		getContentPane().add(lbId);

		setVisible(true);

		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminLoginData();
			}
		});
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}//Admin

	public void adminLoginData() {
		boolean check = checkData();

		if(check) { 
			String AdminId = txtSceret.getText();

			if(AdminId.equals("admin")) {
				JOptionPane.showMessageDialog(this, "관리자 로그인 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				new Manager(getTitle());
				setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "관리자 로그인 실패", "에러발생", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//userLoginData

	private boolean checkData() {
		if(txtSceret.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "AdminKey가가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtSceret.requestFocus();
			return false;
		}
		return true;
	}//checkData

	public static void main(String[] args) {
		new Login("관리자 로그인");
	}//main
}//Login