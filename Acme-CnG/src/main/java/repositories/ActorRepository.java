
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id=?1")
	Actor findByUserAccountId(int id);

	@Query("select a from Actor a join a.userAccount us where us.username = ?1")
	Actor findActorByUsername(String username);

	//DashBoard ---------------------------------------------------

	@Query("select avg(a.writtenComments.size) from Actor a")
	Double avgCommentByActor();

	@Query("select a from Actor a where a.writtenComments.size>=(select avg(a.writtenComments.size) from Actor a)*0.1")
	Collection<Actor> actorAvgCommentPlusTenPercent();

	@Query("select a from Actor a where a.writtenComments.size<=(select avg(a.writtenComments.size) from Actor a)*0.1")
	Collection<Actor> actorAvgCommentMinusTenPercent();

	@Query("select min(a.received.size), avg(a.received.size), max(a.received.size) from Actor a")
	Object[] minAvgMaxReceived();

	@Query("select min(a.sended.size) from Actor a")
	Double findMinMessageSentByActor();

	@Query("select avg(a.sended.size) from Actor a")
	Double findAvgMessageSentByActor();

	@Query("select max(a.sended.size) from Actor a")
	Double findMaxMessageSentByActor();

	@Query("select min(a.received.size) from Actor a")
	Double findMinMessageReceivedByActor();

	@Query("select avg(a.received.size) from Actor a")
	Double findAvgMessageReceivedByActor();

	@Query("select max(a.received.size) from Actor a")
	Double findMaxMessageReceivedByActor();

	@Query("select a from Actor a where a.sended.size=(select max(a2.sended.size) from Actor a2)")
	Collection<Actor> actorSentMoreMessage();

	@Query("select a from Actor a where a.received.size=(select max(a2.received.size) from Actor a2)")
	Collection<Actor> actorReceivedMoreMessage();
}
