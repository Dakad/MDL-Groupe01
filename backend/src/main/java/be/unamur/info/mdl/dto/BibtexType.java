package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BibtexType {
  @JsonProperty("article")
  ARTICLE,

  @JsonProperty("book")
  BOOK,

  @JsonProperty("booklet")
  BOOKLET,

  @JsonProperty("conference")
  CONFERENCE,

  @JsonProperty("inbook")
  INBOOK,

  @JsonProperty("incollection")
  INCOLLECTION,

  @JsonProperty("inproceedings")
  INPROCEEDINGS,

  @JsonProperty("manual")
  MANUAL,

  @JsonProperty("mastersthesis")
  MASTERSTHESIS,

  @JsonProperty("misc")
  MISC,

  @JsonProperty("phdthesis")
  PHDTHESIS,

  @JsonProperty("proceedings")
  PROCEEDINGS,

  @JsonProperty("techreport")
  TECHREPORT,

  @JsonProperty("unpublished")
  UNPUBLISHED;

}
