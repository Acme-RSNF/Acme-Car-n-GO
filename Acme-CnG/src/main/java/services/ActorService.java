
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public void save(Actor actor) {
		Assert.notNull(actor);

		actorRepository.save(actor);
	}

	public void delete(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(actorRepository.exists(actor.getId()));

		actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();
		result = actorRepository.findByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccount(int id) {
		Actor result;

		result = actorRepository.findByUserAccountId(id);
		Assert.notNull(result);

		return result;
	}

	public Actor findActorByUsername(String username) {
		Assert.notNull(username);
		Actor actor = actorRepository.findActorByUsername(username);
		Assert.notNull(actor);
		return actor;
	}

	//Dashboard Services ---------

	public Double avgCommentByActor() {
		Double result;
		result = actorRepository.avgCommentByActor();
		return result;
	}

	public Collection<Actor> actorAvgCommentPlusTenPercent() {
		Collection<Actor> result = new ArrayList<Actor>();
		result = actorRepository.actorAvgCommentPlusTenPercent();
		return result;
	}

	public Collection<Actor> actorAvgCommentMinusTenPercent() {
		Collection<Actor> result = new ArrayList<Actor>();
		result = actorRepository.actorAvgCommentMinusTenPercent();
		return result;
	}

	public Collection<Double> minAvgMaxSent() {
		Collection<Double> result = new ArrayList<Double>();
		Object[] aux = actorRepository.minAvgMaxSent();

		for (int i = 0; i < 3; i++)
			result.add((Double) aux[i]);

		return result;
	}

	public Collection<Double> minAvgMaxReceived() {
		Collection<Double> result = new ArrayList<Double>();
		Object[] aux = actorRepository.minAvgMaxReceived();

		for (int i = 0; i < 3; i++)
			result.add((Double) aux[i]);

		return result;
	}

	public Collection<Actor> actorSentMoreMessage() {
		Collection<Actor> result = new ArrayList<Actor>();
		result = actorRepository.actorSentMoreMessage();
		return result;
	}

	public Collection<Actor> actorReceivedMoreMessage() {
		Collection<Actor> result = new ArrayList<Actor>();
		result = actorRepository.actorReceivedMoreMessage();
		return result;
	}

}
