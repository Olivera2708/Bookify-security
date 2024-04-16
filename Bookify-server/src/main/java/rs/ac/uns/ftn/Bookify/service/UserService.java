package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.exception.UserDeletionException;
import rs.ac.uns.ftn.Bookify.exception.UserIsBlockedException;
import rs.ac.uns.ftn.Bookify.exception.UserNotActivatedException;
import rs.ac.uns.ftn.Bookify.mapper.UserRegisteredDTOMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReportedUserRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.*;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserService implements IUserService {
    private final IImageService imageService;
    private final IUserRepository userRepository;
    private final IReportedUserRepository reportedUserRepository;
    private final IReservationRepository reservationRepository;
    private final IReservationService reservationService;
    private final PasswordEncoder passwordEncoder;
    private final IAccommodationService accommodationService;


    @Autowired
    public UserService(IImageService imageService, IUserRepository userRepository, IReservationService reservationService,
                       PasswordEncoder passwordEncoder, @Lazy IAccommodationService accommodationService, IReservationRepository reservationRepository,
                       IReportedUserRepository reportedUserRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.passwordEncoder = passwordEncoder;
        this.accommodationService = accommodationService;
        this.reservationRepository = reservationRepository;
        this.reportedUserRepository = reportedUserRepository;
    }


    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.removeIf(user -> user instanceof Admin);
        return users;
    }

    @Override
    public User get(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User get(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        return u.orElse(null);
    }

    @Override
    public User create(UserRegisteredDTO newUser) {
        User user = userRepository.findByEmail(newUser.getEmail());
        if (user != null) {
            throw new BadRequestException("Already exists.");
        }
        if (newUser.getRole().equals("owner")) {
            user = UserRegisteredDTOMapper.fromDTOtoOwner(newUser);
        } else {
            user = UserRegisteredDTOMapper.fromDTOtoGuest(newUser);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Active active = new Active(false, new Date(), UUID.randomUUID().toString());
        user.setActive(active);
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(UserDetailDTO updatedUser) {
        Optional<User> user = this.userRepository.findById(updatedUser.getId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User u = user.get();
        updateUserData(updatedUser, u);
        try {
            userRepository.save(u);
        } catch (Exception e) {
            return null;
        }
        return u;
    }

    public boolean changePassword(Long userId, String newPassword) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            return false;
        }
        User user = u.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Not found");
        }
        StringBuilder randomPassword = generaterandomPassword();
        user.setPassword(passwordEncoder.encode(randomPassword.toString()));
        userRepository.save(user);
        return randomPassword.toString();
    }

    private static StringBuilder generaterandomPassword() {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=";
        String allChars = upperCaseChars + lowerCaseChars + numberChars + specialChars;
        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder();

        randomString.append(getRandomChar(upperCaseChars, random));
        randomString.append(getRandomChar(lowerCaseChars, random));
        randomString.append(getRandomChar(numberChars, random));
        randomString.append(getRandomChar(specialChars, random));

        int remainingLength = 8;
        while (remainingLength-- > 0) {
            randomString.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return randomString;
    }

    private static char getRandomChar(String charSet, SecureRandom random) {
        return charSet.charAt(random.nextInt(charSet.length()));
    }

    @Override
    public boolean activateUser(String uuid) {
        User u = userRepository.findByHashToken(uuid);
        if (u == null) {
            return false;
        }
        u.setActive(new Active(true, new Date(), ""));
        userRepository.save(u);
        return true;
    }

    @Override
    public boolean isLoginAvailable(Long userId) {
        User user = get(userId);
        if (user.isBlocked()) {
            throw new UserIsBlockedException();
        }
        if (!user.getActive().isActive()) {
            throw new UserNotActivatedException();
        }
        return true;
    }

    @Override
    public boolean delete(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            return false;
        }
        return deleteUser(u.get());
    }

    private boolean deleteUser(User user) {
        if (deletionIsPossibility(user)) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

    @Override
    public String getRole(User user) {
        String role;
        if (user instanceof Owner) {
            role = "OWNER";
        } else if (user instanceof Admin) {
            role = "ADMIN";
        } else if (user instanceof SysAdmin){
            role = "SYSADMIN";
        } else {
            role = "GUEST";
        }
        return role;
    }

    @Override
    public UserDTO block(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) throw new BadRequestException("User not found");
        User user = u.get();

        if (user.isBlocked()) throw new BadRequestException("User is already blocked");

        return new UserDTO(block(user));

    }

    @Override
    public UserDTO unblock(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) throw new BadRequestException("User not found");
        User user = u.get();

        if(!user.isBlocked()) throw new BadRequestException("User is not blocked");
        return new UserDTO(unblock(user));
    }

    @Override
    public Collection<User> searchUsers(String searchParam) {
        return null;
    }

    @Override
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            throw new Exception();
        }
        Image image = imageService.save(bytes, "accounts", imageName);
        User user = u.get();
        user.setProfileImage(image);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return -1L;
        }
        return image.getId();
    }

    @Override
    public FileSystemResource getImage(Long imageId) {
        return imageService.find(imageId);
    }

    @Override
    public OwnerDTO findbyAccommodationId(Long id) {
        Owner o = userRepository.findByAccommodations_Id(id);
        float avgRating = 0;
        Long imageId = 0L;
        if (o.getProfileImage() != null)
            imageId = o.getProfileImage().getId();
        return new OwnerDTO(o.getId(), o.getFirstName(), o.getLastName(), o.getPhone(), avgRating, imageId);
    }

    @Override
    public Float getAvgRating(Long id) {
        Float f = this.userRepository.getAverageReviewByOwnerId(id);
        if (f == null)
            return 0f;
        else
            return f.floatValue();
    }

    @Override
    public void saveOwnerAccommodation(Accommodation accommodation, Long ownerId) {
        Owner owner = userRepository.findOwnerById(ownerId);
        owner.getAccommodations().add(accommodation);
        userRepository.save(owner);
    }

    @Override
    public OwnerDTO setOwnerForAccommodation(Long id) {
        OwnerDTO o = findbyAccommodationId(id);
        o.setAvgRating(getAvgRating(o.getId()));
        return o;
    }

    @Override
    @Transactional
    @Scheduled(cron = "${activation.cron}")
    public void checkInactiveUsers() {
        Calendar calendar = Calendar.getInstance();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            calendar.setTime(user.getActive().getTime());
            calendar.add(Calendar.MINUTE, 1);
            if (!user.getActive().isActive() && calendar.getTime().compareTo(new Date()) < 0) {
                userRepository.deleteUser(user.getId());
            }
        }
    }

    @Override
    public List<AccommodationRequestDTO> findAccommodationRequests() {
        List<AccommodationRequestDTO> response = new ArrayList<>();
        for (Owner owner : userRepository.findAllOwners()) {
            for (Accommodation accommodation : owner.getAccommodations()) {
                if (!isRequestReactedOn(accommodation)) response.add(new AccommodationRequestDTO(owner, accommodation));
            }
        }
        return response;
    }

    @Override
    public Owner getOwner(Long ownerId) {
        return userRepository.findOwnerById(ownerId);
    }

    @Override
    public Guest getGuest(Long guestId) {
        return userRepository.findGuestById(guestId);
    }

    @Override
    public void saveOwner(Owner owner) {
        userRepository.save(owner);
    }

    @Override
    public UserReservationDTO getOwnerForReservation(Long accommodationId) {
        Owner owner = userRepository.getOwner(accommodationId);
        UserReservationDTO user = new UserReservationDTO();
        user.setId(owner.getId());
        user.setFirstName(owner.getFirstName());
        user.setLastName(owner.getLastName());
        user.setAvgRating(getAvgRating(owner.getId()));
        return user;
    }

    @Override
    public UserReservationDTO getGuestForReservation(Long reservationId) {
        Guest guest = reservationRepository.findById(reservationId).get().getGuest();
        UserReservationDTO user = new UserReservationDTO();
        user.setId(guest.getId());
        user.setFirstName(guest.getFirstName());
        user.setLastName(guest.getLastName());
        user.setCancellationTimes(reservationRepository.getCancellationTimes(user.getId()));
        return user;
    }

    @Transactional
    @Override
    public void addToFavorites(Long guestId, Long accommodationId) {
        userRepository.addFavoriteToUser(guestId, accommodationId);
    }


    @Override
    public Long reportUser(ReportedUser reportedUser) {
        Guest guest;
        Owner owner;
        if(reportedUser.getReportedUser() instanceof Guest){
            guest = (Guest) reportedUser.getReportedUser();
            owner = (Owner) reportedUser.getCreatedBy();
            if(reservationService.getReservations(guest.getId(), owner.getId()).isEmpty()){
                throw new BadRequestException("Do not have reservations");
            }
        }else{
            owner = (Owner) reportedUser.getReportedUser();
            guest = (Guest) reportedUser.getCreatedBy();
            if(reservationService.getReservations(guest.getId(), owner.getId()).isEmpty()){
                throw new BadRequestException("Do not have reservations");
            }
        }
        return reportedUserRepository.save(reportedUser).getId();
    }

    @Override
    public void removeOwnerReview(Review review) {
        Owner owner = userRepository.getOwnerByReview(review);
        owner.getReviews().remove(review);
        userRepository.save(owner);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }


    @Override
    public boolean checkIfInFavorites(Long guestId, Long accommodationId) {
        return userRepository.checkIfInFavorites(guestId, accommodationId) == 1;
    }

    private void updateUserData(UserDetailDTO updatedUser, User u) {
        u.getAddress().setAddress(updatedUser.getAddress().getAddress());
        u.getAddress().setCity(updatedUser.getAddress().getCity());
        u.getAddress().setZipCode(updatedUser.getAddress().getZipCode());
        u.getAddress().setCountry(updatedUser.getAddress().getCountry());
        u.setPhone(updatedUser.getPhone());
        u.setFirstName(updatedUser.getFirstName());
        u.setLastName(updatedUser.getLastName());
    }

    private boolean deletionIsPossibility(User user) {
        switch (getRole(user)) {
            case "OWNER":
                List<Long> accId = new ArrayList<>();
                for (Accommodation acc : ((Owner) user).getAccommodations()) {
                    if (reservationService.hasFutureReservationsAccommodation(acc)) {
                        throw new UserDeletionException("Some of your accommodations have active future reservations.");
                    }
                    accId.add(acc.getId());
                }
                accId.forEach(this.accommodationService::deleteAccommodation);
                return true;
            case "GUEST":
                if (reservationService.hasFutureReservationsGuest(user.getId()))
                    throw new UserDeletionException("You have active future reservations.");
                return true;
            default:
                throw new UserDeletionException("Administrator account can't be deleted");
        }
    }

    private boolean isRequestReactedOn(Accommodation a) {
        return a.getStatus().toString().equals("APPROVED") || a.getStatus().toString().equals("REJECTED");
    }

    private User unblock(User user) {
        String role = getRole(user);
        if (role.equals("ADMIN")) throw new BadRequestException("Administrator's account cannot be blocked/unblocked");
        user.setBlocked(false);
        return userRepository.save(user);
    }

    private User block(User user) {
        String role = getRole(user);
        if (role.equals("ADMIN")) throw new BadRequestException("Administrator's account cannot be blocked/unblocked");
        if (role.equals("GUEST")) {
            reservationService.cancelGuestsReservations(user.getId());
        }
        user.setBlocked(true);
        return userRepository.save(user);
    }

}
