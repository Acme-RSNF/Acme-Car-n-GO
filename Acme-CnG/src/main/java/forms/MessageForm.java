
package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Actor;

@Embeddable
@Access(AccessType.PROPERTY)
public class MessageForm {

	// Attributes -----------------------------------------

	private String				title;
	private String				text;
	private Collection<String>	attachment;
	private Actor				sender;
	private Actor				recipient;


	// Getters and Setters --------------------------------
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Collection<String> getAttachment() {
		return attachment;
	}
	public void setAttachment(Collection<String> attachement) {
		this.attachment = attachement;
	}

	public Actor getSender() {
		return sender;
	}
	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public Actor getRecipient() {
		return recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
