package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "Group of research", description = "Model representing a reasearch group")
public class ResearchGroupDTO {
private String name;
private String slug;
private String link;
private int nbMembers;

}
