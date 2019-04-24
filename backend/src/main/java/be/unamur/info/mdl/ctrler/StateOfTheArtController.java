package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/sota")
@Api("Operations related to a SoTA such as add, get, delete ...")
public class StateOfTheArtController extends APIBaseController {

  @Autowired
  private StateOfTheArtService sotaService;


  @ApiOperation(value = "Retrieve a specific SOTA by it reference")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully registered", response = StateOfTheArtDTO.class),
    @ApiResponse(code = 400, message = "The SOTA reference is missing"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @GetMapping(path = "/{reference}")
  public ResponseEntity get(@PathVariable String reference) {
    try {
      StateOfTheArtDTO sota = sotaService.getSotaByReference(reference);
      return ResponseEntity.status(HttpStatus.OK).body(sota);
    } catch (SotatNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

}
