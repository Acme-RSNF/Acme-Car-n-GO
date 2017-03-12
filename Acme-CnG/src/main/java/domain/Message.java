
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class Message extends DomainEntity {

	// Attributes --------------------------------------

	private Date				moment;
	private String				title;
	private String				text;
	private Collection<String>	attachment;


	// Getters and Setters -----------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Collection<String> getAttachment() {
		return attachment;
	}
	public void setAttachment(Collection<String> attachment) {
		this.attachment = attachment;
	}


	// Relationships -----------------------------------

	private Actor	sender;
	private Actor	recipient;


	public Actor getSender() {
		return sender;
	}
	public void setSender(Actor sender) {
		this.sender = sender;
	}

	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}

}
