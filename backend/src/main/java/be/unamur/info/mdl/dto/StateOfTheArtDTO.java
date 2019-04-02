package be.unamur.info.mdl.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateOfTheArtDTO {
  private Long id;

  private String name;

  private String subject;

  private LocalDate date;

  private UserDTO user;

  private Set<TagDTO> tags;

}
