package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotaNotFoundException;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/sota")
@Api("Operations related to a SoTA such as add, get, delete ...")
public class StateOfTheArtController extends APIBaseController {

  @Autowired
  private StateOfTheArtService sotaService;


  @ApiOperation(value = "Retrieve a specific SoTA by it reference")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully registered", response = StateOfTheArtDTO.class),
    @ApiResponse(code = 400, message = "The SoTA reference is missing"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @GetMapping(path = "/{reference}")
  public ResponseEntity get(@PathVariable String reference) {
    try {
      StateOfTheArtDTO sota = sotaService.getSotaByReference(reference);
      return ResponseEntity.status(HttpStatus.OK).body(sota);
    } catch (SotaNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @ApiOperation(value = "Create a new SoTA")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully created"),
    @ApiResponse(code = 400, message = "The SoTA reference is missing"),
    @ApiResponse(code = 400, message = "The SoTA is already created"),
    @ApiResponse(code = 404, message = "The provided article reference doesn't exist")
  })
  @PostMapping({"/", "/add"})
  public ResponseEntity create(@Valid @RequestBody StateOfTheArtDTO data, Principal authUser) {
    try {
      String username = authUser.getName();
      UserDTO currentUser = new UserDTO();
      currentUser.setUsername(username);

      StateOfTheArtDTO sota = sotaService.create(data, currentUser);
      return ResponseEntity.status(HttpStatus.OK).body(sota);
    } catch (SotaAlreadyExistException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @DeleteMapping({"/{reference}"})
public ResponseEntity delete (@PathVariable String reference, Principal authUser)
    throws UsernameNotFoundException {
    try {
      sotaService.delete(reference, authUser.getName());
      return ResponseEntity.noContent().build();

    } catch (SotaNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping({"/{reference}"})
  public ResponseEntity put (@Valid @RequestBody StateOfTheArtDTO data, Principal authUser,@PathVariable String reference)
    throws UsernameNotFoundException {
    sotaService.put(reference, authUser.getName(),data);
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }
}



