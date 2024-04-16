package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Active {
	@Column(nullable = false)
	private boolean isActive;

	@Column(nullable = false)
	private Date time;

	@Column(nullable = false)
	private String hashToken;
}
