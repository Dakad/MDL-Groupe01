package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.UserDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column
  private String password;

  @Email
  private String email;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "last_name")
  private String lastname;


  public static User of(UserDTO userData) {
    return new User(null, userData.getUsername(), userData.getPassword(), userData.getEmail(),
        userData.getFirstname(), userData.getLastname()
    );
  }


  public UserDTO toDTO() {
    return new UserDTO(username, password, lastname, firstname, email);
  }

}
