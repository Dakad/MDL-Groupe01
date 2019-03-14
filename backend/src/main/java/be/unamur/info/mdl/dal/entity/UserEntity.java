package be.unamur.info.mdl.dal.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;


    protected User() {}

    public User(String name, String email) {
    	this.name(name);
    	this.email(email);
	}



	public Long id() {
		return id;
	}

	public void id(Long id) {
		this.id = id;
	}

	public String name() {
		return name;
	}

	public void name(String name) {
		this.name = name;
	}

	public String email() {
		return email;
	}

	public void email(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return String.format("User[id=%d, name='%s', email='%s']", id, name, email);
	}
}
