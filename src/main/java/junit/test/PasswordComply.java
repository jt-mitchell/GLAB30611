package junit.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; // handles errors

public class PasswordComply {
	
	private String  passwordString;
	private final int minPasswordLength = 8; // final indicates a constant that cannot be changed
	private final int maxPasswordLength = 12;
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/PERSCHOLAS";
	static final String USER = "root";
	static final String PASS = "password";
	static final String QUERY = "{call getEmpName (?, ?)}";
	
	public PasswordComply (String verifyPassword) {
		passwordString = verifyPassword;		
	}

	
	private boolean verifyPasswordLength() {
		
		if(!passwordString.isEmpty()) {		
			if(passwordString.length() >= minPasswordLength && passwordString.length() <= maxPasswordLength) {
				return true;
				}	
		}
		return false;
	}
	
	private boolean verifyAlphaNumeric() {
		
		return true;		
	}
	
	private boolean hasAllowedspecialCharacters() {
		
		return true;		
	}
	
     // This is a dummy method and needs to implement the real code to validate      // password against database entries.
	public boolean doesNotAlreadyExist() throws SQLException {		
		
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        CallableStatement stmt = conn.prepareCall(QUERY);		      		     
        stmt.setString(1, passwordString); 
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        System.out.println("Executing stored procedure..." );
        stmt.execute();
        //Retrieve password
        String existingPassword = stmt.getString(2);
        System.out.println("Password already exist" + existingPassword);		
		return true;		
	}
	
	private boolean hasNoSpecialCharacters() {
		
		return true;		
	}
	
	public void setPassword(String givenPassword) {
		passwordString = givenPassword;
	}
	
	public boolean doesPasswordComply() {
		
		return verifyPasswordLength() && verifyAlphaNumeric() && hasAllowedspecialCharacters() && hasNoSpecialCharacters();		
	}	
}

