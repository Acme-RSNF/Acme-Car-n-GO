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

<display:table name="requests"
	id="row"
	class="displaytag"
	pagesize="5"
	requestURI="${requestURI}" >
<jstl:choose>
	<jstl:when test="${row.banned==true}">
	<spring:message code="request.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" style="font-weight:bold"/>
	
	<spring:message code="request.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" style="font-weight:bold"/>
	
	<spring:message code="request.moment" var="momentHeader" />
	<display:column title="${momentHeader}"	sortable="false" style="font-weight:bold"><fmt:formatDate value="${row.moment }" pattern="dd/MM/yyyy" /></display:column>
	
	<spring:message code="request.origin" var="originHeader" />
	<display:column property="origin" title="${originHeader}" sortable="true" style="font-weight:bold"/>
	
	<spring:message code="request.origin.coordinates" var="originCoordinateHeader" />
	<display:column title="${originCoordinateHeader}" style="font-weight:bold"> 
		<jstl:if test="${row.originCoordinate!=''}">
			<jstl:out value="${row.originCoordinate.latitude}    ${row.originCoordinate.longitude}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="request.destination" var="destinationHeader"/>
	<display:column property="destination" title="${destinationHeader}" sortable="true" style="font-weight:bold"/>
	
	<spring:message code="request.destination.coordinates" var="destinationCoordinateHeader"/>
	<display:column title="${destinationCoordinate}" style="font-weight:bold"> 
		<jstl:if test="${row.destinationCoordinate!=''}">
			<jstl:out value="${row.destinationCoordinate.latitude}    ${row.destinationCoordinate.longitude}" />
		</jstl:if>
	</display:column>
	
	
	
	<spring:message code="request.banned" var="bannedHeader" />
	<display:column title="${bannedHeader}" style="font-weight:bold">
		<jstl:if test="${row.banned==true}">
		
			<spring:message code="request.banned.yes" var="yesH" />
			<jstl:out value="${yesH}"/>
		
		</jstl:if>
		<jstl:if test="${row.banned==false}">
			<spring:message code="request.banned.no" var="noH" />
			<jstl:out value="${noH}"/>
		</jstl:if>
	</display:column>
	
	
	</jstl:when>
	<jstl:otherwise>
	<spring:message code="request.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" style="none"/>
	
	<spring:message code="request.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" style="none"/>
	
	<spring:message code="request.moment" var="momentHeader" />
	<display:column title="${momentHeader}"	sortable="false" style="none"><fmt:formatDate value="${row.moment }" pattern="dd/MM/yyyy" /></display:column>
	
	<spring:message code="request.origin" var="originHeader" />
	<display:column property="origin" title="${originHeader}" sortable="true" style="none"/>
	
	<spring:message code="request.origin.coordinates" var="originCoordinateHeader" />
	<display:column title="${originCoordinateHeader}" style="none"> 
		<jstl:if test="${row.originCoordinate!=''}">
			<jstl:out value="${row.originCoordinate.latitude}    ${row.originCoordinate.longitude}"/>
		</jstl:if>
	</display:column>
	
	<spring:message code="request.destination" var="destinationHeader"/>
	<display:column property="destination" title="${destinationHeader}" sortable="true" style="none"/>
	
	<spring:message code="request.destination.coordinates" var="destinationCoordinateHeader"/>
	<display:column title="${destinationCoordinate}" style="none"> 
		<jstl:if test="${row.destinationCoordinate!=''}">
			<jstl:out value="${row.destinationCoordinate.latitude}    ${row.destinationCoordinate.longitude}"/>
		</jstl:if>
	</display:column>
	
	
	
	<spring:message code="request.banned" var="bannedHeader" />
	<display:column title="${bannedHeader}" style="none">
		<jstl:if test="${row.banned==true}">
		
			<spring:message code="request.banned.yes" var="yesH" />
			<jstl:out value="${yesH}"/>
		
		</jstl:if>
		<jstl:if test="${row.banned==false}">
			<spring:message code="request.banned.no" var="noH" />
			<jstl:out value="${noH}"/>
		</jstl:if>
	</display:column>
	</jstl:otherwise>
</jstl:choose>
	<security:authorize access="hasRole('ADMIN')">
	
	<spring:message code="request.banned" var="bannedHeader" />
	<display:column title="${bannedHeader}">
		<jstl:choose>
			<jstl:when test="${row.banned}">
				<a href="request/banUnbanRequest.do?requestId=${row.id}">
					<spring:message code="request.unban" />
				</a>
			</jstl:when>
			<jstl:otherwise>
				<a href="request/banUnbanRequest.do?requestId=${row.id}">
					<spring:message code="request.ban" />
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	</security:authorize>
	<display:column>
		<a href="customer/displayById.do?customerId=${row.customer.id}"><spring:message code="request.view.customer" /></a>
	</display:column>
	<display:column>
		<a href="request/display.do?requestId=${row.id}"><spring:message code="request.view" /></a>
	</display:column>
</display:table>

</security:authorize>

