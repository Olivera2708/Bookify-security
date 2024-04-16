package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int rate;
	private String comment;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private boolean accepted;

	@Column(nullable = false)
	private boolean reported;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReviewType reviewType;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	private Guest guest;
}