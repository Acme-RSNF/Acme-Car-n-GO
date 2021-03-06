
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;
@Entity
@Access(AccessType.PROPERTY)
public class Actor extends Commentable {

	// Attributes ---------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;


	// Getters and Setters ------------------------------------

	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^([+]?\\d{1,3})?[ ]?(\\d{9})")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


	// Relationship -------------------------------------------

	private Collection<Message>	sended;
	private Collection<Message>	received;
	private Collection<Comment>	writtenComments;
	private UserAccount userAccount;

	@Valid
	@OneToOne(cascade=CascadeType.ALL,optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	
	
	@Valid
	@OneToMany(mappedBy="sender")
	public Collection<Message> getSended() {
		return sended;
	}
	public void setSended(Collection<Message> sended) {
		this.sended = sended;
	}
	@Valid
	@OneToMany(mappedBy="recipient")
	public Collection<Message> getReceived() {
		return received;
	}
	public void setReceived(Collection<Message> received) {
		this.received = received;
	}
	@Valid
	@OneToMany(mappedBy="actor")
	public Collection<Comment> getWrittenComments() {
		return writtenComments;
	}
	public void setWrittenComments(Collection<Comment> writtenComments) {
		this.writtenComments = writtenComments;
	}

}
