package rs.ac.uns.ftn.Bookify.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
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

	@Column
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private boolean blocked;

	@Column
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

	public Attributes toAttributes() {
		Attributes attributes = new BasicAttributes();

		attributes.put("uid", email);  // Assuming username is the attribute used as the unique identifier
		attributes.put("cn", email);
		attributes.put("sn", email);
		attributes.put("givenName", firstName);
		attributes.put("displayName", lastName);
		attributes.put("mail", email);
		attributes.put("mobile", phone);

		return attributes;
	}
}