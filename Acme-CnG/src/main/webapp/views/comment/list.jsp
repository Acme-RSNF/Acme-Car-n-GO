<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">

<display:table name="comments"
	id="row"
	class="displaytag"
	pagesize="5"
	requestURI="${requestURI}" >
	
	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true"/>
	
	<spring:message code="comment.postedMoment" var="postedMomentHeader" />
	<display:column title="${postedMomentHeader}"	sortable="false"><fmt:formatDate value="${row.postedMoment }" pattern="dd/MM/yyyy" /></display:column>
	
	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="false"/>
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true"/>
	
	<spring:message code="comment.banned" var="bannedHeader" />
	<display:column title="${bannedHeader}">
		<jstl:choose>
			<jstl:when test="${row.banned}">
				<a href="comment/banUnbanComment.do?commentId=${row.id}">
					<spring:message code="comment.unban" />
				</a>
			</jstl:when>
			<jstl:otherwise>
				<a href="comment/banUnbanComment.do?commentId=${row.id}">
					<spring:message code="comment.ban" />
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
</display:table>

</security:authorize>
