package be.unamur.info.mdl.dal.entity;


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


}
