package rs.ac.uns.ftn.Bookify.service.interfaces;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public interface IAccommodationService {
    public Collection<Accommodation> getAccommodationsForSearch(Integer persons, String location, LocalDate begin, LocalDate end);
    public long countByLocationAndGuestRange(Integer persons, String location, LocalDate begin, LocalDate end);
    public Collection<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort);
    public Collection<AccommodationBasicDTO> setPrices(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons);
    public List<Accommodation> getForFilter(List<Accommodation> accommodations, FilterDTO filter);
    public AccommodationDetailDTO getAccommodationDetails(Long id);
    public Collection<AccommodationBasicDTO> getForPriceRange(Collection<AccommodationBasicDTO> accommodations, FilterDTO filter);
    public double getTotalPrice(Long id, LocalDate begin, LocalDate end, PricePer pricePer, int persons);
    public double getOnePrice(Long id, LocalDate begin, LocalDate end, PricePer pricePer, int persons);
    public FileSystemResource getImage(Long id);
    public Accommodation save(Accommodation accommodation);
    public Accommodation saveAccommodation(Accommodation accommodation);
    public Long update(Accommodation accommodation);
    public Long addPriceList(Long accommodationId, PricelistItem item);
    public Long addAvailability(Long accommodationId, Availability availability);
    public Collection<PricelistItem> getAccommodationPriceListItems(Long accommodationId);
    public Collection<PricelistItem> savePriceListItem(Long accommodationId, PricelistItem item);
    public void mergePricelistIntervals(Long accommodationId);
    public void mergeAvailabilityIntervals(Long accommodationId);
    public Boolean deletePriceListItem(Long accommodationId, PricelistItem item);
    public List<FileSystemResource> getAllImages(Long accommodationId);
    public List<FileSystemResourcesDTO> getAllImagesDTO(Long accommodationId);
    public float getAvgRating(Long id);
    public Collection<AccommodationBasicDTO> getAvgRatings(Collection<AccommodationBasicDTO> accommodations);
    public List<AccommodationOwnerDTO> getOwnerAccommodation(Long ownerId);
    public Accommodation getAccommodation(Long accommodationId);
    public SearchResponseDTO getSearchResponseForSearch(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons, String location, int page, int size);
    public SearchResponseDTO getSearchReposnseForFilter(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons, String location, int page, int size, String sort, FilterDTO filter);
    public float getMinPrice(Collection<AccommodationBasicDTO> accommodationBasicDTO);
    public float getMaxPrice(Collection<AccommodationBasicDTO> accommodationBasicDTO);
    public Collection<AccommodationBasicDTO> paging(Collection<AccommodationBasicDTO> accommodationBasicDTO, int page, int size);
    public Collection<Accommodation> filterAccommodations(int persons, String location, LocalDate bein, LocalDate end, FilterDTO filter);
    public boolean isAvailable(Long id, LocalDate beginL, LocalDate endL);
    public boolean checkPersons(Long id, int persons);
    public void setAccommodationStatus(Long id, AccommodationStatusRequest newStatus);
    public void deleteAccommodation(Long accommodationId);
    public void insertForGuest(Long guestId, Long accommodationId);
    public void acceptReservationIfAutomaticConformation(Reservation reservation);
    public void acceptReservationForAccommodation(Reservation reservation);
    public List<ChartDTO> getChartsByPeriod(Long ownerId, LocalDate begin, LocalDate end);
    public byte[] generatePdfReportForOverall(Long ownerId, LocalDate begin, LocalDate end) throws DocumentException;
    public Map<Long, String> getAccommodationNames(Long ownerId);
    public List<ChartDTO> getChartsByAccommodationAndYear(Long ownerId, Long accommodationId, int year);
    public byte[] generatePdfReportForAccommodation(Long ownerId, Long accommodationId, int year) throws DocumentException;
    public List<Accommodation> getTopAccommodations(int results);
    public void removeReview(Review review);
}
