<%@ page import="vn.toeiconline.core.web.common.WebConstant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<fmt:setBundle basename="ApplicationResources" var="lang"/>
<c:set var="sessionUser" value="${login_name}"/>