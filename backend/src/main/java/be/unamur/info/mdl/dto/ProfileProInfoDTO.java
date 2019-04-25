package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileProInfoDTO {

  @JsonAlias("research_group")
  private List<String> researchGroup;

  private List<UniversityInfoDTO> universities;

  @JsonAlias("recent_articles")
  private Map<String, String> recentArticles;

  @JsonAlias("recent_sotas")
  private Map<Long, String> recentSotAs;
}
