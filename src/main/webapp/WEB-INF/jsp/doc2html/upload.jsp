<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<form:form method="post" commandName="doc" enctype="multipart/form-data">
		<form:input path="file" size="20" type="file" />
		<input type="submit" value="上传" />
	</form:form>
</div>
