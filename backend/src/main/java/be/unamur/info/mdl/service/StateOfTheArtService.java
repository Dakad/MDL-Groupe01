package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;

public interface StateOfTheArtService {


  /**
   * Retrieve a SOTA specified by the provided reference
   * @param reference The provided reference
   * @return The data of the matching SOTA.
   * @throws IllegalArgumentException if the provided reference is null.
   * @throws SotatNotFoundException The referenced SOTA is not found in persistence.
   */
  StateOfTheArtDTO getSotaByReference(String reference) throws SotatNotFoundException;


}
