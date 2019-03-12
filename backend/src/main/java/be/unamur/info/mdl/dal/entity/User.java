package be.unamur.info.mdl.dal.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity // This tells Hibernate to make a table out of this class
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String userName;

  @Column
  private String password;

  @Email
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;




  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", login='").append(login).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", fullname='").append(fullName).append('\'');
    sb.append('}');
    return sb.toString();
  }

  //
//  @Override
//  public String toString() {
//    return String.format("User[id=%d, fullname='%s', email='%s']", id(), fullName(), email());
//  }
}
