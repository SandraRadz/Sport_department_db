public class User {
	private String fname;
	private String sname;
	private String lname;
	private String status;
	private String email;
	private String login;
	private String password;

	public User(String fname, String lname, String status, String login, String password) {
		this.fname = fname;
		// this.sname=sname;
		this.lname = lname;
		this.status = status;
		// this.email=email;
		this.login = login;
		this.password = password;
		return;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
