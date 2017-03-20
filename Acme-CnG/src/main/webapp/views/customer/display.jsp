<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table id="row" class="table">
	<tbody>
		<tr>
			<th><spring:message code="customer.name"/></th>
			<th><spring:message code="customer.surname"/></th>
			
		</tr>
		<tr>
			<td><jstl:out value="${customer.name}"/></td>
			<td><jstl:out value="${customer.surname}"/></td>
		</tr>
		<tr>
			<th><spring:message code="customer.email"/></th>
			<th><spring:message code="customer.phone"/></th>
		</tr>
		<tr>
			<td><jstl:out value="${customer.email}"/></td>
			<td><jstl:out value="${customer.phone}"/></td>
		</tr>
	</tbody>
</table>
			
	
<display:table pagesize="10" class="displaytag" keepStatus="true" name="comments" id="row" requestURI="${requestURI}">	
	<jstl:choose>
	<jstl:when test="${row.banned==true}">
		 <jstl:choose>
			<jstl:when test="${row.actor.userAccount.username == pageContext.request.remoteUser || pageContext.request.isUserInRole('ADMIN')}">
				<spring:message code="customer.comment.title" var="titleHeader" />
				<display:column title="${titleHeader }" property="title" style="font-weight:bold"/>
				
				<spring:message code="customer.comment.postedMoment" var="postedMomentHeader"/>
				<display:column title="${postedMomentHeader}" sortable="true" style="font-weight:bold"><fmt:formatDate value="${row.postedMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
				
				<spring:message code="customer.comment.text" var="textHeader"/>
				<display:column title="${textHeader }" property="text" style="font-weight:bold"/>
				
				<spring:message code="customer.comment.actor" var="commentatorHeader"/>
				<display:column title="${commentatorHeader }" style="font-weight:bold">
						<jstl:out value="${row.actor.name}" />
				</display:column>
				<spring:message code="customer.comment.stars" var="starsHeader"/>
				<display:column title="${starsHeader }" property="stars" style="font-weight:bold"/>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="customer.comment.title" var="titleHeader" />
				<display:column title="${titleHeader }" property="title" style="display:none;"/>
				
				<spring:message code="customer.comment.postedMoment" var="postedMomentHeader"/>
				<display:column title="${postedMomentHeader}" sortable="true" style="display:none;"><fmt:formatDate value="${row.postedMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
				
				<spring:message code="customer.comment.text" var="textHeader"/>
				<display:column title="${textHeader }" property="text" style="display:none;"/>
				
				<spring:message code="customer.comment.actor" var="commentatorHeader"/>
				<display:column title="${commentatorHeader }" style="display:none;">
						<jstl:out value="${row.actor.name}" />
				</display:column>
				<spring:message code="customer.comment.stars" var="starsHeader"/>
				<display:column title="${starsHeader }" property="stars" style="display:none;"/>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="customer.comment.title" var="titleHeader"/>
		<display:column title="${titleHeader }" property="title" style="none"/>
		
		<spring:message code="customer.comment.postedMoment" var="postedMomentHeader"/>
		<display:column title="${postedMomentHeader}" sortable="true" style="none"><fmt:formatDate value="${row.postedMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
		
		<spring:message code="customer.comment.text" var="textHeader"/>
		<display:column title="${textHeader }" property="text" style="none"/>
		
		<spring:message code="customer.comment.actor" var="commentatorHeader"/>
		<display:column title="${commentatorHeader }" style="none">
				<jstl:out value="${row.actor.name}"/>
		</display:column>
		<spring:message code="customer.comment.stars" var="starsHeader"/>
		<display:column title="${starsHeader }" property="stars" style="none"/>
	</jstl:otherwise>
	</jstl:choose>
</display:table>

<security:authorize access="hasAnyRole('CUSTOMER','ADMINISTRATOR')">
	<input type="button" name="comment" value="<spring:message code="customer.comment" />"
			onclick="javascript: window.location.replace('commentator/comment/create.do?commentableId=${customer.id}')" />
<br/>
</security:authorize>