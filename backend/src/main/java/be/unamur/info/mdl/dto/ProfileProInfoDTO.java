package be.unamur.info.mdl.dto;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileProInfoDTO {
  private Pair<String,String> researchGroup;
  private UniversityInfoDTO[] universities = new UniversityInfoDTO[20];
  private List<Pair<String, Long>> recentArticles;
  private List<Pair<String, Long>> recentSotAs;
}
