package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE accommodations SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private int minGuest;

    @Column(nullable = false)
    private int maxGuest;

    @Column(nullable = false)
    private int cancellationDeadline;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationStatusRequest status;

    @Column(nullable = false)
    private boolean manual = true;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<PricelistItem> priceList;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Availability> availability;
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Review> reviews;

    @ElementCollection(targetClass = Filter.class)
    @Enumerated(EnumType.STRING)
    private Collection<Filter> filters;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PricePer pricePer;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @OneToMany
    private Collection<Image> images;
}
