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

public class Register extends JFrame{
	JTextField txtId = new JTextField(); 
	JPasswordField txtPw = new JPasswordField ();
	JTextField txtName = new JTextField();
	JPanel jPanel;
	JButton backBtn, registerBtn;
	Container contentPane;
	UserDao UserDao;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 30);
	Font f2 = new Font("바탕", Font.ITALIC | Font.BOLD, 12);

	public Register(String title){
		super(title);
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		compose();
		setVisible(true);
		setSize(300, 480);
		setLocationRelativeTo(null);

	}
	
	public void compose() {
		UserDao = new UserDao();
		getContentPane().setLayout(null);

		registerBtn = new JButton("회원가입");
		backBtn = new JButton("뒤로가기");
		registerBtn.setBounds(50, 300, 200, 30);
		backBtn.setBounds(50, 350, 200, 30);


		JLabel lbId = new JLabel("아이디");
		JLabel lbPw = new JLabel("비밀번호");
		JLabel lbName = new JLabel("이름");
		JLabel lbTitle = new JLabel("Register");
		
		txtId.setBounds(90, 150, 150, 20);
		txtPw.setBounds(90, 190, 150, 20);
		txtName.setBounds(90, 230, 150, 20);

		lbId.setBounds(20, 150, 150, 20);
		lbPw.setBounds(20, 190, 150, 20);
		lbName.setBounds(20, 230, 150, 20);
		lbTitle.setBounds(90, 80, 150, 50);
		JPanel TopPlace = new JPanel();
		TopPlace.setLayout(null);
		TopPlace.setBounds(0, 250, 200, 200);
		TopPlace.add(lbTitle);
		
		lbTitle.setFont(f1);
		lbId.setFont(f2);
		lbPw.setFont(f2);
		lbName.setFont(f2);
		registerBtn.setFont(f2);
		backBtn.setFont(f2);
		
		registerBtn.setBorderPainted(false);
		backBtn.setBorderPainted(false);
		
		registerBtn.setBackground(Color.black); 
		registerBtn.setForeground(Color.white);
		backBtn.setBackground(Color.black); 
		backBtn.setForeground(Color.white);
		
		getContentPane().add(registerBtn);
		getContentPane().add(backBtn);
		getContentPane().add(txtId);
		getContentPane().add(txtPw);
		getContentPane().add(txtName);
		getContentPane().add(lbId);
		getContentPane().add(lbPw);
		getContentPane().add(lbName);
		getContentPane().add(lbTitle);
		
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login(getTitle());
				setVisible(false);
			}
		});
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					RegisterData();
			}
		});
	}

	public void RegisterData() {
		boolean check = checkData();

		if(check) { 
			String UserId = txtId.getText();
			String UserPw = txtPw.getText();
			String UserName = txtName.getText();

			UserBean ub = new UserBean();
			ub.setUserId(UserId);
			ub.setUserPw(UserPw);
			ub.setUserName(UserName);

			int cnt = UserDao.RegisterData(ub);
			if(cnt == -1) {
				System.out.println("회원가입X");
			}else {
				System.out.println("회원가입 성공했습니다.");
				JOptionPane.showMessageDialog(this, "회원가입 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				new Login(getTitle());
				setVisible(false); 
			}
		}
	}//RegisterData

	private boolean checkData() { 
		if(txtId.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "아이디가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtId.requestFocus();
			return false;
		}else if(txtPw.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "비밀번호가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtPw.requestFocus();
			return false;
		}else if(txtName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "이름이 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtName.requestFocus();
			return false;
		}
		return true;
	}//checkData

	public static void main(String[] args) {
		new Login("회원가입");
	}//main
}//Register