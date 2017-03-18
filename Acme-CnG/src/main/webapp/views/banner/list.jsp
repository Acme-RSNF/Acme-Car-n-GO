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


<security:authorize access="isAuthenticated()">

<display:table name="banners"
	id="row"
	class="displaytag"
	pagesize="5"
	requestURI="${requestURI}" >
	
	<spring:message code="banner.image" var="imageHeader" />
	<display:column title="${imageHeader}">
		<img src="${row.image}" width="400" height="100">
	</display:column>
	
	<spring:message code="banner.isPrincipal" var="isPrincipalHeader" />
	<display:column title="${isPrincipalHeader}">
		<jstl:choose>
			<jstl:when test="${row.isPrincipal}">
				<spring:message code="banner.principal" var="yes" />
			<jstl:out value="${yes}"/>
			</jstl:when>
			<jstl:otherwise>
				<a href="banner/makePrincipal.do?bannerId=${row.id}">
					<spring:message code="banner.makePrincipal" />
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
</display:table>

</security:authorize>
