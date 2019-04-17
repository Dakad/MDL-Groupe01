package be.unamur.info.mdl.dal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modelisation of joinTable
@Data
@Entity
@Table(name = "university_current")
@NoArgsConstructor
@AllArgsConstructor
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
  private UniversityEntity university;



  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  static class UserUniversityKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "university_id")
    private Long universityId;


  }
}
