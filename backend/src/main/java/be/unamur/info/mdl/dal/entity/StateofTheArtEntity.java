package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class StateofTheArtEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  @Column
  private String subject;
  @Column
  private LocalDate date;
  @OneToOne(cascade= CascadeType.ALL)
  @JoinColumn(name="user_ID", unique= true, nullable=true, insertable=true, updatable=true)
  private UserEntity user;
  public StateofTheArtEntity(String name, String subject, LocalDate date) {
    this.name = name;
    this.subject = subject;
    this.date = date;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
