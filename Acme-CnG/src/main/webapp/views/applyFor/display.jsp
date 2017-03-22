<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
<table>
	<tr>
		<td width="50%">
		<H2><spring:message code="apply.sended"/></H2>
			<table id="row" class="table">
			<tr>
				<th><spring:message code="apply.deal"/></th>
				<th><spring:message code="apply.type"/></th>
				<th><spring:message code="apply.status"/></th>
				
			</tr>
			<jstl:forEach items="${applyFor}" var="apply">
				<tr>
					<td><jstl:out value="${apply.deal.title}"/></td>
					<jstl:choose>
						<jstl:when test="${apply.deal.getClass().name eq 'domain.Request' }">
							<spring:message code="apply.request" var="requestHeader" />
							<td><jstl:out value="${requestHeader}"/></td>
						</jstl:when>
						<jstl:otherwise>
							<spring:message code="apply.offer" var="offerHeader" />
							<td><jstl:out value="${offerHeader}"/></td>
						</jstl:otherwise>
					</jstl:choose>
					<td><jstl:out value="${apply.status}"/></td>
				</tr>
			</jstl:forEach>
			</table>
		<td width="50%">
		<h2><spring:message code="apply.received"/></h2>
		<table id="row" class="table">
			<tr>
				<th><spring:message code="apply.customer"/></th>
				<th><spring:message code="apply.deal"/></th>
				<th><spring:message code="apply.status"/></th>
				
			</tr>
			<jstl:forEach items="${applyForDeal}" var="applyD">
				<tr>
					<td><jstl:out value="${applyD.customer.name}"/></td>
					<td><jstl:out value="${applyD.deal.title}"/></td>
					<td><jstl:out value="${applyD.status}"/></td>
					<td>
					<jstl:if test="${applyD.status=='PENDING' }">
						<input type="button" name="accept" value="<spring:message code="applyFor.accept" />"
				onclick="javascript: window.location.replace('applyFor/accept.do?applyForId=${applyD.id}')" />
						<input type="button" name="deny" value="<spring:message code="applyFor.deny" />"
				onclick="javascript: window.location.replace('applyFor/deny.do?applyForId=${applyD.id}')" />
					</jstl:if>
						</td>
				</tr>
		</jstl:forEach>
		</table>
	</td>
</tr>
</table>
<br/>
</security:authorize>