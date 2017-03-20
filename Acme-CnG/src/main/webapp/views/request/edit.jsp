<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access = "hasRole('CUSTOMER')">

	<form:form action = "${requestURI }" modelAttribute="requestForm">
		<acme:textbox code="request.title" path="title" />
		<br/>
		<acme:textarea code="request.description" path="description" />
		<br/>
		<acme:textbox code="request.moment" path="moment" />
		<br/>
		<fieldset>
			<legend align="left"><spring:message code="request.origin.info" /></legend>
			<acme:textbox code="request.origin" path="origin"/>
			<br/>
			<fieldset>
				<legend align="left"><spring:message code="request.origin.coordinates" /></legend>
				<acme:textbox code="request.latitude" path="originCoordinate.latitude"/>
				<br/>
				<acme:textbox code="request.longitude" path="originCoordinate.longitude"/>
			</fieldset>
		</fieldset>
		<br/>
		<fieldset>
			<legend align="left"><spring:message code="request.destination.info" /></legend>
			<acme:textbox code="request.destination" path="destination"/>
			<br/>
			<fieldset>
				<legend align="left"><spring:message code="request.destination.coordinates" /></legend>
				<acme:textbox code="request.latitude" path="destinationCoordinate.latitude"/>
				<br/>
				<acme:textbox code="request.longitude" path="destinationCoordinate.longitude"/>
			</fieldset>
		</fieldset>
		<br/>
		<acme:submit name="save" code="request.save"/>
		<acme:cancel url="welcome/index.do" code="request.cancel"/>
		
	</form:form>

</security:authorize>