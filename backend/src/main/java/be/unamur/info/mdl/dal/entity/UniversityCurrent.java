package be.unamur.info.mdl.dal.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modlisation of joinTable
@Data
@NoArgsConstructor
@Entity
public class UniversityCurrent {

  @EmbeddedId
  private UserUniversityKey id;

  @ManyToOne
  @MapsId("user_id")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("university_id")
  @JoinColumn(name = "university_id")
  private UserEntity university;

  private boolean current;

  public UserUniversityKey getId() {
    return id;
  }

  public void setId(UserUniversityKey id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public UserEntity getUniversity() {
    return university;
  }

  public void setUniversity(UserEntity university) {
    this.university = university;
  }

  public boolean isCurrent() {
    return current;
  }

  public void setCurrent(boolean current) {
    this.current = current;
  }
}
