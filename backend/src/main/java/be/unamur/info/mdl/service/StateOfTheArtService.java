package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.exceptions.NoSotaException;

public interface StateOfTheArtService {

  //methode getStateofTheArt renvoie un state of the artDTO ->go dans repository faire findbyod, lransformer en dto

  public StateOfTheArtDTO getStateOfTheArt (String reference) throws NoSotaException;




}
