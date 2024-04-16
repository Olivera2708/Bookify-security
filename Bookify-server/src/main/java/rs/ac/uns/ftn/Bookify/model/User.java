package rs.ac.uns.ftn.Bookify.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public abstract class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private boolean blocked;

	@Column(nullable = false)
	private String phone;

	@Column
	private boolean deleted = Boolean.FALSE;

	@OneToMany
	private Collection<Notification> notifications;

	@OneToOne
	private Image profileImage;

	@Embedded
	private Active active;

	@Embedded
	private Address address;

	public String getUserType() {
		DiscriminatorValue discriminatorValue = getClass().getAnnotation(DiscriminatorValue.class);
		return (discriminatorValue != null) ? discriminatorValue.value() : null;
	}
}