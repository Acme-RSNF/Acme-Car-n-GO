<%--
 * dashboard.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
<!-- C -->
<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.ratioOVSR" /></legend>
		<table id="ratioOVSR" class="table">
			<tr>
				<jstl:if test="${not empty ratioOVSR }">
					<td><jstl:out value="${ratioOVSR}" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.avgNORC" /></legend>
		<table id="avgNORC" class="table">
			<tr>
				<jstl:if test="${not empty avgNORC }">
					<td><jstl:out value="${avgNORC}" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.avgNAOR" /></legend>
		<table id="avgNAOR" class="table">
			<tr>
				<jstl:if test="${not empty avgNAOR }">
					<td><jstl:out value="${avgNAOR}" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="customerMAA" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.customerMAA" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<div>
	<display:table name="customerMAD" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.customerMAD" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<!-- B -->

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.avgCAOR" /></legend>
		<table id="avgCAOR" class="table">
			<tr>
				<jstl:if test="${not empty avgCAOR }">
					<td><jstl:out value="${avgCAOR}" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.avgPAC" /></legend>
		<table id="avgPAC" class="table">
			<tr>
				<jstl:if test="${not empty avgPAC }">
					<td><jstl:out value="${avgPAC}" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.pmTEN" /></legend>
		<div>
			<display:table name="actorPavgC" id="row" class="displaytag" pagesize="5">
				<spring:message code="administrator.actorPavgC" var="name"/>
				<display:column title="${name}" property="userAccount.username"/>
			</display:table>
		</div>
		<div>
			<display:table name="actorMavgC" id="row" class="displaytag" pagesize="5">
				<spring:message code="administrator.actorMavgC" var="name"/>
				<display:column title="${name}" property="userAccount.username"/>
			</display:table>
		</div>
	</fieldset>
</div>

<!-- A -->

</security:authorize>
