package be.unamur.info.mdl.dal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(name = "password")
  private String hashedPassword;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;



  protected User() {

  }

  public User(String login, String email, String firstName, String lastName) {
    this.login(login);
    this.email(email);
    this.name(firstName, lastName);
  }


  public Long id() {
    return id;
  }

  public void id(Long id) {
    this.id = id;
  }


  public void login(String login) {
    this.login = login;
  }

  public String login() {
    return login;
  }

  public String fullName() {
    return String.format("%s %s", firstName, lastName) ;
  }

  public void name(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String email() {
    return email;
  }

  public void email(String email) {
    this.email = email;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id) &&
        login.equals(user.login);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", login='").append(login()).append('\'');
    sb.append(", email='").append(email()).append('\'');
    sb.append(", fullname='").append(fullName()).append('\'');
    sb.append('}');
    return sb.toString();
  }

  //
//  @Override
//  public String toString() {
//    return String.format("User[id=%d, fullname='%s', email='%s']", id(), fullName(), email());
//  }
}
