package be.unamur.info.mdl.ctrler;

import static be.unamur.info.mdl.ctrler.ApiControllerUtils.KEY_MESSAGE;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.exceptions.UserNotFoundException;
import be.unamur.info.mdl.service.StateOfTheArtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/sota")
@Api("Operations related to a SoTA such as add, get, delete ...")
public class StateOfTheArtController {

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
    StateOfTheArtDTO sota = sotaService.getSotaByReference(reference);
    return ResponseEntity.status(HttpStatus.OK).body(sota);
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
    String username = authUser.getName();
    UserDTO currentUser = new UserDTO();
    currentUser.setUsername(username);

    StateOfTheArtDTO sota = sotaService.create(data, currentUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(sota);
  }


  @ApiOperation(value = "Create a new bookmark on a state of the art")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully created"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @PostMapping(path = "/{reference}/bookmark")
  public ResponseEntity addBookmark(@PathVariable String reference, Principal authUser,
    @ApiParam(name = "note", defaultValue = "A note about the bookmark") @RequestBody String note) {

    if (sotaService.addBookmark(reference, authUser.getName(), note)) {
      String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSAGE, "Bookmark added");
      return ResponseEntity.status(HttpStatus.CREATED).body(responsesMsg);
    } else {
      //TODO Add more explicit error message
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Something, somewhere, has gone sideways.\nAnd basically, error...");
    }
  }


  @ApiOperation(value = "Remove a bookmark on a state of the art")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully removed"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist"),
    @ApiResponse(code = 404, message = "The specified state of the art is not bookmarked")
  })
  @DeleteMapping(path = "/{reference}/bookmark")
  public ResponseEntity removeBookmark(
    @ApiParam(name = "reference", defaultValue = "The sota reference")
    @PathVariable String reference,
    Principal authUser) throws BookmarkNotFoundException {
    sotaService.removeBookmark(reference, authUser.getName());
    String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSAGE, "Bookmark removed");
    return ResponseEntity.status(HttpStatus.OK).body(responsesMsg);
  }


  @GetMapping(path = "/{reference}/bookmarked")
  public ResponseEntity isBookmarked(@PathVariable String reference, Principal authUser) {
    String msg = "This article is present your bookmarks";
    if (!sotaService.isBookmarked(reference, authUser.getName())) {
      msg = "This article is not present your bookmarks";
    }

    String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSAGE, msg);
    return ResponseEntity.status(HttpStatus.OK).body(responsesMsg);
  }


  @DeleteMapping({"/{reference}"})
  public ResponseEntity delete(@PathVariable String reference, Principal authUser)
    throws UserNotFoundException {
    sotaService.delete(reference, authUser.getName());
    String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSAGE, "SoTA removed");
    return ResponseEntity.status(HttpStatus.OK).body(responsesMsg);
  }

}



