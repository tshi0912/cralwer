<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="function-tags" %>
<c:if test="${not empty images}">
	<c:forEach var="image" items="${images}" varStatus="status">
		<li class="pin span3" <c:if test="${status.index == 0}">data-ts="${ts}"</c:if>>
			<div class="thumbnail">
				<a class="img" href="${image.href}">
					<img alt="" src="${image.href}" width=192 
					height=${fn:pinHeight(image.size)} >
				</a>
				<div class="caption">
					<h5>${image.title}</h5>
					<p><a href="javascript:void(0);">${image.from}</a>:
					${image.alt}</p>
				</div>
			</div>
		</li>
	</c:forEach>
</c:if>