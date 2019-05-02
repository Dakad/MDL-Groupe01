package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;
import io.swagger.annotations.*;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    } catch (SotatNotFoundException e) {
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

  @ApiOperation(value = "Create a new bookmark on a state of the art")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully created"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @RequestMapping(path = "/{reference}/bookmark", method = RequestMethod.POST)
  public ResponseEntity addBookmark(@PathVariable String reference, Principal authUser,
                                    @ApiParam(name = "note", defaultValue = "A note about the bookmark") @RequestBody String note) {
    try {
      if (sotaService.addBookmark(reference, authUser.getName(), note)) {
        return ResponseEntity.status(HttpStatus.OK).body("Bookmark added");
      } else {
        //TODO Add more explicit error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Something, somewhere, has gone sideways.\nAnd basically, error...");
      }
    } catch (SotatNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @ApiOperation(value = "Remove a bookmark on a state of the art")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully created"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist"),
    @ApiResponse(code = 404, message = "The specified state of the art is not bookmarked")
  })
  @RequestMapping(path = "/{reference}/bookmark", method = RequestMethod.DELETE)
  public ResponseEntity removeBookmark(
    @ApiParam(name = "reference", defaultValue = "The sota reference")
    @PathVariable String reference,
    Principal authUser) {
    try {
      if (sotaService.removeBookmark(reference, authUser.getName())) {
        return ResponseEntity.status(HttpStatus.OK).body("Bookmark removed");
      } else {
        //TODO Add more explicit error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
      }
    } catch (SotatNotFoundException | BookmarkNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }


  @RequestMapping(path = "/{reference}/bookmarked", method = RequestMethod.GET)
  public ResponseEntity isBookmarked(@PathVariable String reference, Principal authUser) {
    try {
      return ResponseEntity.status(HttpStatus.OK)
        .body(sotaService.isBookmarked(reference, authUser.getName()));
    } catch (SotatNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

}
