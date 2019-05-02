package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDTO {
  private String note;
  private String title;
  private String reference;
}
