package hh.sof3as3.Reseptikirjasto.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignupForm {
	
	@NotEmpty
	@Size(min=6, max=20)
	private String username = "";
	
	@NotEmpty
	@Size(min=4, max=30) // todo: muuta min isommaksi
	private String password = "";
	
	@NotEmpty
	@Size(min=4, max=30) // todo: muuta min isommaksi
	private String passwordCheck = "";
	
	@NotEmpty
    private String role = "USER";

	
	// getterit ja setterit
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
