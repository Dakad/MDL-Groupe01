package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.NoSotaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/sota")
@Api(value = "sota_controller", description = "Operations of UserControler")
public class StateOfTheArtController extends APIBaseController{
  @Autowired
  private StateOfTheArtService sotaService;



  @ApiOperation(value = "Retrieve a specific sota by it reference")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully registered", response = StateOfTheArtDTO.class),
    @ApiResponse(code = 400, message = "The article reference is missing "),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @RequestMapping(path = "/{reference}", method = RequestMethod.GET)
  public ResponseEntity<?> get(@Valid @PathVariable String reference) {
    try {
      StateOfTheArtDTO data = sotaService.getStateOfTheArt(reference);
      return ResponseEntity.status(HttpStatus.OK).body(data);
    } catch (NoSotaException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

}
