package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.DefaultResponseDTO.DefaultResponseDTOBuilder;
import be.unamur.info.mdl.dto.BookmarkDTO;
import be.unamur.info.mdl.dto.DefaultResponseDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.ProfileUpdateDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.exceptions.AutoFollowedException;
import be.unamur.info.mdl.exceptions.InvalidProfilePictureLinkException;
import be.unamur.info.mdl.exceptions.UserAlreadyFollowedException;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@Api(value = "User Operations such as change password, get basic profile, ...")
public class UserController {

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
  @PutMapping(path = "/changepwd")
  public ResponseEntity changePassword(
    @ApiParam(value = "The new && old password", required = true)
    @Valid @RequestBody PasswordChangeDTO passwordChangeDTO,
    Principal authUser
  ) {
    String authUsername = authUser.getName();
    userService.changePassword(authUsername, passwordChangeDTO);
    DefaultResponseDTO response = DefaultResponseDTO.builder().done(true)
      .message("Password changed").build();
    return ResponseEntity.ok(response);

  }


  @ApiOperation(value = "Retrieve the basic profile information", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The profile data", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @GetMapping(path = "/{username}/profile/base")
  public ResponseEntity getBasicInfo(
    @ApiParam(value = "Username of the profile owner", required = true)
    @PathVariable String username) {
    ProfileBasicInfoDTO dto = profileService.getBasicInfo(username);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @GetMapping(path = "/{username}/profile/pro")
  public ResponseEntity getProInfo(@PathVariable String username) {
    ProfileProInfoDTO dto = profileService.getProInfo(username);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }


  @ApiOperation(value = "Retrieve the social profile information", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The profile social data", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @GetMapping(path = "/{username}/profile/social")
  public ResponseEntity getSocialInfo(
    @ApiParam(value = "Username of the profile owner", required = true)
    @PathVariable String username) {
    ProfileSocialInfoDTO dto = profileService.getSocialInfo(username);
    return ResponseEntity.status(HttpStatus.OK).body(dto);

  }


  @ApiOperation(value = "Retrieve the user followers", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The following users", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @GetMapping(path = "/{username}/profile/followers")
  public ResponseEntity getFollowers(@PathVariable String username,
    @ApiParam(value = "Pagination")
    @RequestParam(defaultValue = "0", name = "page") int p) {
    List<UserDTO> userDTOS = profileService.getFollowers(username, p);
    return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
  }


  @ApiOperation(value = "Retrieve the users followed by an user", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "The followed users", response = ProfileBasicInfoDTO.class),
    @ApiResponse(code = 404, message = "The provided username does not exist")
  })
  @GetMapping(path = "/{username}/profile/follows")
  public ResponseEntity getFollows(@PathVariable String username,
    @ApiParam(value = "Pagination")
    @RequestParam(defaultValue = "0", name = "page") int p) {
    List<UserDTO> userDTOS = profileService.getFollows(username, p);
    return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
  }

  @PostMapping(path = "/{username}/follow")
  public ResponseEntity follow(@PathVariable String username, Principal authUser) {
    String user = authUser.getName();
    if (username.equals(user)) {
      throw new AutoFollowedException();
    }
    boolean done = userService.follow(username, user);
    String msg = "User " + (done ? "now" : "already") + "followed";

    return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponseDTO(done, msg));
  }

  @PostMapping(path = "/{username}/unfollow")
  public ResponseEntity unfollow(@PathVariable String username, Principal authUser) {
    String user = authUser.getName();
    if (username.equals(user)) {
      throw new AutoFollowedException();
    }
    boolean done = userService.unfollow(username, user);
    if (!done) {
      throw new UserAlreadyFollowedException("User already not followed");
    }

    return ResponseEntity.status(HttpStatus.OK)
      .body(new DefaultResponseDTO(done, "User now unfollowed"));
  }


  @GetMapping(path = "/{username}/followed")
  public ResponseEntity isFollowed(@PathVariable String username, Principal authUser) {
    if (username.equals(authUser.getName())) {
      throw new AutoFollowedException();
    }

    boolean done = userService.isFollowed(username, authUser.getName());
    String msg;
    if (done) {
      msg = "You follow " + username;
    } else {
      msg = "You do not follow " + username;
    }
    return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponseDTO(done, msg));
  }


  @GetMapping(path = "/{username}/profile/bookmarks")
  public ResponseEntity getBookmarks(@PathVariable String username,
    @RequestParam(defaultValue = "0") int p) {
    List<BookmarkDTO> bookmarks = profileService.getBookmarks(username, p);
    return ResponseEntity.status(HttpStatus.OK).body(bookmarks);
  }

  @PostMapping(path = "/profile/update")
  public ResponseEntity updateProfile(@RequestBody ProfileUpdateDTO updateDTO, Principal authUser) {
    try {
      profileService.update(updateDTO, authUser.getName());
      return ResponseEntity.status(HttpStatus.OK).body("Profile updated");
    } catch (InvalidProfilePictureLinkException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }
}

