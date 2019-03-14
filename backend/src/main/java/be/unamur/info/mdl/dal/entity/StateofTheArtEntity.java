package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;

public class StateofTheArtEntity {

  private Long id;
  private String name;
  private String subject;
  private LocalDate date;

  public StateofTheArtEntity(Long id, String name, String subject, LocalDate date) {
    this.id = id;
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
