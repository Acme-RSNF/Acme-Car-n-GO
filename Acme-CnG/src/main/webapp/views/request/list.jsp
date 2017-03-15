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

<security:authorize access="hasRole('CUSTOMER')">

<display:table name="requests"
	id="row"
	class="displaytag"
	pagesize="5"
	requestURI="${requestURI}" >
	
	<spring:message code="request.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false"/>
	
	<spring:message code="request.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false"/>
	
	<spring:message code="request.moment" var="momentHeader" />
	<display:column title="${momentHeader}"	sortable="false"><fmt:formatDate value="${row.moment }" pattern="dd/MM/yyyy" /></display:column>
	
	<spring:message code="request.origin" var="originHeader" />
	<display:column property="origin" title="${originHeader}" sortable="true"/>
	
	<spring:message code="request.destination" var="destinationHeader"/>
	<display:column property="destination" title="${destinationHeader}" sortable="true"/>
	
	<spring:message code="request.banned" var="bannedHeader" />
	<display:column title="${bannedHeader}">
		<jstl:if test="${row.banned==true}">
			<spring:message code="request.banned.yes" var="yesH" />
			<jstl:out value="${yesH}"/>
		</jstl:if>
		<jstl:if test="${row.banned==false}">
			<spring:message code="request.banned.no" var="noH" />
			<jstl:out value="${noH}"/>
		</jstl:if>
	</display:column>
	
</display:table>

</security:authorize>
