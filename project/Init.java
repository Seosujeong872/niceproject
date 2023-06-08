package project;

import project.ui.LoginPage;

public class Init {

	public static void main(String[] args) {
		LoginPage lp = LoginPage.getInstance();
		lp.setVisible(true);
	}
}