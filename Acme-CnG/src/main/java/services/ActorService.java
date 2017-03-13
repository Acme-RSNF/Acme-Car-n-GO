
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
/*
	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public void save(final Actor actor) {
		Assert.notNull(actor);

		this.actorRepository.save(actor);
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();
		result = this.actorRepository.findByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccount(final int id) {
		Actor result;

		result = this.actorRepository.findByUserAccountId(id);
		Assert.notNull(result);

		return result;
	}

	public Actor findActorByUsername(final String username) {
		Assert.notNull(username);
		final Actor actor = this.actorRepository.findActorByUsername(username);
		Assert.notNull(actor);
		return actor;
	}*/
}
