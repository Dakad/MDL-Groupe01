package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("stateOfTheArtService")
@Transactional
public class StateOfTheArtServiceImpl implements StateOfTheArtService {

  private StateOfTheArtRepository sotaRepository;

  @Autowired
  public StateOfTheArtServiceImpl(StateOfTheArtRepository sotaRepo, UserRepository userRepo) {
    this.sotaRepository = sotaRepo;
  }


  @Override
  public StateOfTheArtDTO getSotaByReference(String reference) throws SotatNotFoundException {
    Optional<StateOfTheArtEntity> dbSota = this.sotaRepository.findByReference(reference);
    if (!dbSota.isPresent()) {
      throw new SotatNotFoundException("The referenced article was not found");
    } else {
      return dbSota.get().toDTO();
    }
  }


}

