package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Inheritance(strategy = InheritanceType.JOINED)
public class Address {
	@Column(length = 50, nullable = false)
	private String country;

	@Column(length = 30, nullable = false)
	private String city;

	@Column(length = 52, nullable = false)
	private String address;

	@Column(length = 10, nullable = false)
	private String zipCode;
}
