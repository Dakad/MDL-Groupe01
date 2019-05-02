package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@Api(value = "User endpoints", description = "User Operations such as change password, get basic profile, ...")
public class UserController extends APIBaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private ProfileService profileService;


  @ApiOperation(value = "Change the user password", response = String.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully authenticated user"),
    @ApiResponse(code = 400, message = "Some required fields are invalid"),
    @ApiResponse(code = 409, message = "If the username or password is not recognized")
  })
  @RequestMapping(path = "/changepwd", method = RequestMethod.POST)
  public String changePassword(
    @ApiParam(value = "The new && old password", required = true)
    @Valid @RequestBody PasswordChangeDTO passwordChangeDTO,
    Principal authUser
  ) {
    String authUsername = authUser.getName();
    if (userService.changePassword(authUsername, passwordChangeDTO)) {
      return "OK";
    }
    return "ERROR : 409 CONFLICT";
  }


  @ApiOperation(value = "Retrieve the basic profile information", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The profile data", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @RequestMapping(path = "/{username}/profile/base", method = RequestMethod.GET)
  public ResponseEntity getBasicInfo(
    @ApiParam(value = "Username of the profile owner", required = true)
    @PathVariable String username) {
    try {
      ProfileBasicInfoDTO dto = profileService.getBasicInfo(username);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }

  @RequestMapping(path = "/{username}/profile/pro", method = RequestMethod.GET)
  public ResponseEntity getProInfo(@PathVariable String username) {
    try {
      ProfileProInfoDTO dto = profileService.getProInfo(username);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
    }
  }


  @ApiOperation(value = "Retrieve the social profile information", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The profile social data", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @RequestMapping(path = "/{username}/profile/social", method = RequestMethod.GET)
  public ResponseEntity getSocialInfo(
    @ApiParam(value = "Username of the profile owner", required = true)
    @PathVariable String username) {
    try {
      ProfileSocialInfoDTO dto = profileService.getSocialInfo(username);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }


  @ApiOperation(value = "Retrieve the user followers", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The following users", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @RequestMapping(path = "/{username}/profile/followers", method = RequestMethod.GET)
  public ResponseEntity getFollowers(@PathVariable String username,
    @ApiParam(value = "Pagination")
    @RequestParam(defaultValue = "0", name = "page") int p) {
    try {
      List<UserDTO> userDTOS = profileService.getFollowers(username, p);
      return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }


  @ApiOperation(value = "Retrieve the users followed by an user", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The followed users", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @RequestMapping(path = "/{username}/profile/follows", method = RequestMethod.GET)
  public ResponseEntity getFollows(@PathVariable String username,
    @ApiParam(value = "Pagination")
    @RequestParam(defaultValue = "0", name = "page") int p) {
    try {
      List<UserDTO> userDTOS = profileService.getFollows(username, p);
      return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }

  @RequestMapping(path = "/{username}/follow", method = RequestMethod.POST)
  public ResponseEntity follow(@PathVariable String username, Principal authUser) {
    try {
      String user = authUser.getName();
      if (username.equals(user)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A user cannot follow themselves");
      }
      boolean done = userService.follow(username, user);
      if (done) {
        return ResponseEntity.status(HttpStatus.OK).body("User now followed");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already followed");
      }
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
    }
  }

  @RequestMapping(path = "/{username}/unfollow", method = RequestMethod.POST)
  public ResponseEntity unfollow(@PathVariable String username, Principal authUser) {
    try {
      String user = authUser.getName();
      if (username.equals(user)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A user cannot unfollow themselves");
      }
      boolean response = userService.unfollow(username, user);
      if (response) {
        return ResponseEntity.status(HttpStatus.OK).body("User now unfollowed");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already not followed");
      }
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }


  @RequestMapping(path = "/{username}/followed", method = RequestMethod.GET)
  public ResponseEntity isFollowed(@PathVariable String username, Principal authUser) {
    try {
      if (username.equals(authUser.getName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("If you followed yourself, you'd be walking in circles.");
      }
      boolean response = userService.isFollowed(username, authUser.getName());
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }


  @RequestMapping(path = "/{username}/profile/bookmarks", method = RequestMethod.GET)
  public ResponseEntity getBookmarks(@PathVariable String username,
    @RequestParam(defaultValue = "0") int p) {
    try {
      List<BookmarkDTO> bookmarks = profileService.getBookmarks(username, p);
      return ResponseEntity.status(HttpStatus.OK).body(bookmarks);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist");
    }
  }

}

