package be.unamur.info.mdl.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StateOfTheArtDTO {
  private Long id;
  private String name;
  private String subject;
  private LocalDate date;
  private UserDTO user;

  public StateOfTheArtDTO(Long id, String name, String subject, LocalDate date, UserDTO user){
    this.id = id;
    this.name = name;
    this.subject = subject;
    this.date = date;
    this.user = user;
  }
}
