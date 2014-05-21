<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="my_table">
	<tr>
		<th width="20%">名称</th>
		<th width="75%" style="text-align: center">可用性</th>
		<th></th>
	</tr>
	<%-- <c:forEach items="${map }" var="entry">
								<tr>
									<td><a href="Linuxcentos.html">${entry.key}</a></td>
									<td><table width="100%" border="0" cellspacing="0"
											cellpadding="0" class="green_bar">
											<tr>
												<c:forEach items="${entry.value }" var="model">
													<c:if test="${model.status == '0'}">
														<td class="not_available" width="${model.percentage }%"></td>
													</c:if>
													<c:if test="${model.status == '2' }">
														<td class="not_available" width="${model.percentage }%"></td>
													</c:if>
													<c:if test="${model.status == '1' }">
														<td width="${model.percentage }%"></td>
													</c:if>
												</c:forEach>
											</tr>
										</table>
									</td>
									<td>100</td>
								</tr>

							</c:forEach> --%>
	<c:forEach items="${maplist }" var="list">

		<tr>
			<td><a href="${ctx}/os/linuxcentos/${list.id}">${list.name}</a></td>
			<td><table width="100%" border="0" cellspacing="0"
					cellpadding="0" class="xp_available">
					<tr>
						<c:forEach items="${list.list}" var="model">

							<c:if test="${model.status == '0'}">
								<td class="not_available" width="${model.percentage }%"></td>
							</c:if>
							<c:if test="${model.status == '2' }">
								<td class="not_available" width="${model.percentage }%"></td>
							</c:if>
							<c:if test="${model.status == '1' }">
								<td class="green_bar" width="${model.percentage }%"></td>
							</c:if>
						</c:forEach>
					</tr>
				</table></td>
			<td></td>
		</tr>


	</c:forEach>
	<tr class="last_row">
		<td>&nbsp;</td>
		<td><table width="100%" border="0" cellspacing="0"
				cellpadding="0" class="ruler_bar">
				<tr>
					<td>&nbsp</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table></td>
		<td>&nbsp;</td>
	</tr>
	<tr class="last_row">
		<td>&nbsp;</td>
		<td><table width="100%" border="0" cellspacing="0"
				cellpadding="0" class="time_bar">
				<tr>
					<c:forEach items="${timeList }" var="time">
						<td>${time}</td>
					</c:forEach>
				</tr>
			</table></td>
		<td>&nbsp;</td>
	</tr>
</table>
