package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("stateOfTheArtService")
@Transactional
public class StateOfTheArtServiceImpl implements StateOfTheArtService {
  private StateOfTheArtRepository stateOfTheArtRepository;
@Autowired
  public StateOfTheArtServiceImpl( StateOfTheArtRepository sotaRepository){

    this.stateOfTheArtRepository =sotaRepository ;
  }

  @Override
  public StateOfTheArtDTO getStateOfTheArt(ArticleDTO sota) {
    Pageable pageable =null;
   // List<StateOfTheArtDTO> stateOfTheArtList= stateOfTheArtRepository.findDistinctByNameLike(sota.getTitle(),pageable).toDTO();
  return null;

}
}
