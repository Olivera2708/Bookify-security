package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Image;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT a.images FROM Accommodation a WHERE a.id = :accommodationId")
    List<Image> findImagesByAccommodationId(Long accommodationId);

    @Query("SELECT i FROM Image i WHERE" +
            " i.id NOT IN (SELECT img.id FROM Accommodation a JOIN a.images img) AND" +
            " i.id NOT IN (SELECT u.profileImage.id FROM User u where u.profileImage.id IS NOT NULL)")
    List<Image> findImageByIdNotInAccommodation();



}
