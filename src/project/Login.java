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

public class Login extends JFrame{
	JTextField txtId = new JTextField(); 
	JPasswordField  txtPw = new JPasswordField ();
	JPanel jPanel;
	JButton loginBtn, registerBtn, exitBtn;
	Container contentPane;
	UserDao UserDao;
	Font f1 = new Font("바탕", Font.ITALIC | Font.BOLD, 30);
	Font f2 = new Font("바탕", Font.ITALIC | Font.BOLD, 12);
	
	public Login(String title){
		super(title);
		getContentPane().setBackground(Color.white);
		setSize(300, 480);
		UserDao = new UserDao();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
 
		loginBtn = new JButton("로그인");
		registerBtn = new JButton("회원가입");
		exitBtn = new JButton("X");
		loginBtn.setBounds(50, 300, 200, 30);
		registerBtn.setBounds(50, 350, 200, 30);
		exitBtn.setBounds(230, 10, 50, 30);
        
		JLabel lbId = new JLabel("아이디");
		JLabel lbPw = new JLabel("비밀번호");
		JLabel lbTitle = new JLabel("Login");
		txtId.setBounds(90, 170, 160, 20);
		txtPw.setBounds(90, 210, 160, 20);
		JPasswordField passwordField = new JPasswordField();
	    passwordField.setEchoChar('#');
	    
		JPanel Place = new JPanel();
		Place.setLayout(null);
		Place.setBounds(550, 220, 490, 200);
		
		JPanel TopPlace = new JPanel();
		TopPlace.setLayout(null);
		TopPlace.setBounds(0, 250, 200, 200);
		TopPlace.add(lbTitle);
		
		lbId.setFont(f2);
		lbPw.setFont(f2);
		lbTitle.setFont(f1);
		loginBtn.setFont(f2);
		registerBtn.setFont(f2);
		
		loginBtn.setBackground(Color.black); 
		loginBtn.setForeground(Color.white); 
		registerBtn.setBackground(Color.black); 
		registerBtn.setForeground(Color.white);
		exitBtn.setBackground(Color.black); 
		exitBtn.setForeground(Color.white);
		
		loginBtn.setBorderPainted(false);
		registerBtn.setBorderPainted(false);
		
		Place.add(txtId);
		Place.add(txtPw);
		Place.add(lbId);
		Place.add(lbPw);
		lbId.setBounds(30, 170, 150, 20);
		lbPw.setBounds(30, 210, 150, 20);
		lbTitle.setBounds(110, 90, 150, 50);
		
		getContentPane().add(loginBtn);
		getContentPane().add(registerBtn);
		getContentPane().add(exitBtn);
		getContentPane().add(txtId);
		getContentPane().add(txtPw);
		getContentPane().add(lbId);
		getContentPane().add(lbPw);
		getContentPane().add(lbTitle);
 
		setVisible(true);
 
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	userLoginData();
            }
        });
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register(getTitle());
                setVisible(false);
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	exit();
                setVisible(false);
            }
        });
    }
	
	private void exit() {
		int answer = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "confirm",JOptionPane.OK_CANCEL_OPTION );
		if(answer == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}//exit
 
	public void userLoginData() {
		boolean check = checkData();

		if(check) { 
			String UserId = txtId.getText();
			String UserPw = txtPw.getText();
		
			UserBean ub = new UserBean();
			ub.setUserId(UserId);
			ub.setUserPw(UserPw);
			int cnt = UserDao.userLoginData(ub);
			if(cnt == -1) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호가 틀립니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			}else if(cnt ==-2) {
				JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			}
			else {
				System.out.println("로그인 성공했습니다.");
				JOptionPane.showMessageDialog(this, "로그인 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
				 new ProductsMain(getTitle());
				 setVisible(false);
			}
		}
	}//userLoginData
	
	private boolean checkData() {
		if(txtId.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "아이디가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtId.requestFocus();
			return false;
		}else if(txtPw.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "비밀번호가 누락됬습니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtPw.requestFocus();
			return false;
		}
		return true;
	}//checkData
	
    public static void main(String[] args) {
        new Login("로그인");
    }//main
}//Login