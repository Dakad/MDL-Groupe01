package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "state_of_the_art")
public class StateOfTheArtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;


  @Column(nullable = false)
  private String subject;


  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @PastOrPresent
  private LocalDate date;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private UserEntity user;


}
