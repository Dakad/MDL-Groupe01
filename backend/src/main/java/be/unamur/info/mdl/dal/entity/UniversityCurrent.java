package be.unamur.info.mdl.dal.entity;

import javax.persistence.Column;
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

  @Column
  private boolean current;


  @ManyToOne
  @MapsId("user_id")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("university_id")
  @JoinColumn(name = "university_id")
  private UniversityEntity university;



}
