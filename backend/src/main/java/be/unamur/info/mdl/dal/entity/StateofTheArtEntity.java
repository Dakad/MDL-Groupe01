package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StateofTheArtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;


  @Column(nullable = false)
  private String subject;


  @Column(nullable = false)
  private LocalDate date;


  @OneToOne(cascade= CascadeType.ALL)
  @JoinColumn(name="user_ID", unique= true, nullable=true, insertable=true, updatable=true)
  private UserEntity user;



}
