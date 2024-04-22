package rs.ac.uns.ftn.Bookify.controller;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.config.utils.UserJWT;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.mapper.ReportedUserDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.UserBasicDTOMapper;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.service.EmailService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReportedUserService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.*;

import rs.ac.uns.ftn.Bookify.dto.ReportedUserDTO;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IReportedUserService reportedUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;


    private final String IP_ADDRESS = "192.168.1.5";

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<ReportedUserDetailsDTO>> getReportedUsers() {
        List<ReportedUserDetailsDTO> response = new ArrayList<>();
        reportedUserService.getAllReports().forEach(reportedUser -> response.add(new ReportedUserDetailsDTO(reportedUser)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        List<UserDTO> response = new ArrayList<>();
        userService.getAll().forEach((u) ->{
            response.add(new UserDTO(u));
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER', 'ROLE_SYSADMIN')")
    public ResponseEntity<UserDetailDTO> getUserById(@PathVariable Long userId) {
        Optional<User> user = Optional.ofNullable(userService.get(userId));
        return user.map(userDetailDTO -> new ResponseEntity<>(new UserDetailDTO(user.get()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> registerUser(@Valid @RequestBody UserRegisteredDTO newUser) throws MessagingException {
        User user = userService.create(newUser);
        MessageDTO token = new MessageDTO();
        if (user != null) {
            emailService.sendEmail("Account Activation", user.getEmail(), "Click the link to activate your account: ",
                    "http://localhost:4200/confirmation?uuid=" + user.getActive().getHashToken());
            token.setToken(user.getActive().getHashToken());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        token.setToken("Failed to create new user");
        return new ResponseEntity<MessageDTO>(token, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<UserDetailDTO> updateUser(@Valid @RequestBody UserDetailDTO updatedUser) {
        Optional<User> user = Optional.ofNullable(userService.update(updatedUser));
        return user.map(userDetailDTO -> new ResponseEntity<>(new UserDetailDTO(user.get()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        boolean success = userService.changePassword(userId, newPassword);
        if (success) {
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to change password", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) throws MessagingException {
        String newPassword = userService.resetPassword(email);
        if (newPassword != null) {
            emailService.sendEmail("Reset Password", email, "Your new password is: ", newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://" + IP_ADDRESS + ":4200")
    @PutMapping(value = "/activate-account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> activateAccount(@Valid @RequestBody MessageDTO uuid) {
        boolean activated = userService.activateUser(uuid.getToken());
        MessageDTO message = new MessageDTO();
        if (activated) {
            message.setToken("Account activated, Congratulations.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message.setToken("Your acctivation expired, register again");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserJWT> login(@Valid @RequestBody UserCredentialsDTO userCredentials, @RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        User u = userService.get(user.getUsername());
        if (userService.isLoginAvailable(u.getId())) {
            String jwt = jwtUtils.generateToken(user.getUsername(), u.getId(), userService.getRole(u), userAgent);
            int expiresIn = jwtUtils.getExpiredIn(userAgent);
            return new ResponseEntity<>(new UserJWT(jwt, (long) expiresIn), HttpStatus.OK);
        }

        return null;
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean success = userService.delete(userId);
        if (success) return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Account has not been deleted", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}/block-user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> blockUser(@PathVariable Long userId) {
        UserDTO response = this.userService.block(userId);
        if(response != null) {
            reportedUserService.deletedUsersReports(response.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}/unblock-user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> unblockUser(@PathVariable Long userId) {
        UserDTO response = this.userService.unblock(userId);
        if(response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/report")
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> insertReport(@Valid @RequestBody ReportedUserDTO dto) {
        //insert new report
        ReportedUser user = ReportedUserDTOMapper.fromDTOtoUser(dto);
        User reportedUser = userService.get(dto.getReportedUser());
        User reportingUser = userService.get(dto.getCreatedBy());
        user.setCreatedBy(reportingUser);
        user.setReportedUser(reportedUser);
        user.setCreated(new Date());

        Long id = userService.reportUser(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<UserDTO>> searchUsers(@RequestParam String searchParameter) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/change-image/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> changeAccountImage(@RequestParam("image") MultipartFile image, @PathVariable Long userId) throws Exception {
        Long id = userService.updateImage(image.getBytes(), image.getName(), userId);
        if (id < 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getAccountImage(@PathVariable Long imageId) throws Exception {
        FileSystemResource image = userService.getImage(imageId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/account-pic/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> getAccountImageId(@PathVariable Long userId) throws Exception {
        User u = userService.get(userId);
        Long imageId = -1L;
        if (u.getProfileImage() != null) {
            imageId = u.getProfileImage().getId();
        }
        return new ResponseEntity<>(imageId, HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<?> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<String>("Goodbye", HttpStatus.OK);
        } else {
            throw new BadRequestException("User is not authenticated");
        }
    }

    @PostMapping(value = "/mobile")
    public ResponseEntity<MessageDTO> registerUserMobile(@Valid @RequestBody UserRegisteredDTO newUser) throws MessagingException {
        User user = userService.create(newUser);
        MessageDTO token = new MessageDTO();
        if (user != null) {
            emailService.sendEmail("Account Activation", user.getEmail(), "Click the link to activate your account: ",
                    "http://" + IP_ADDRESS + ":4200/confirmation?uuid=" + user.getActive().getHashToken());
            token.setToken(user.getActive().getHashToken());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        token.setToken("Failed to create new user");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<UserBasicDTO> getUser(@PathVariable Long userId) {
        User user = userService.get(userId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserBasicDTO dto = UserBasicDTOMapper.fromOwnertoDTO(user);
        dto.setType(user.getUserType());
        return new ResponseEntity<UserBasicDTO>(dto, HttpStatus.OK);
    }
}

