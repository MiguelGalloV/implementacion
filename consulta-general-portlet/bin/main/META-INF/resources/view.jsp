<%@ include file="/init.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" charset="utf8"
	src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.16/js/dataTables.jqueryui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/dataTables.jqueryui.min.css" />

<%@ page import="com.liferay.portal.kernel.json.JSONArray;"%>

<portlet:actionURL name="consultarLista" var="consultarListaURL">
	<portlet:param name="jspPage" value="/perfil.jsp" />
</portlet:actionURL>

<portlet:resourceURL var="dataFilterURL" >
</portlet:resourceURL>

<!-- <a href="<%=dataFilterURL%>">Download Users</a> -->


<%
	JSONArray userList = (JSONArray) request.getAttribute("userList");
%>

<%
	JSONArray userListParams = (JSONArray) request.getAttribute("userListParams");
%>

	<div class="row">
		<form class="col-12">
			<div class="row">
				<div class="form-group col-4">
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupPrepend2"> <i
								class="icon-user"></i>
							</span>
						</div>
						<input type="text" class="form-control" id="inputUserName"
							placeholder="Nombre" aria-describedby="userlHelp"
							name="<portlet:namespace/>texto" />
					</div>
				</div>
	
				<select class="custom-select col-3" id="inputDisponibilidad" name="<portlet:namespace/>disponibilidad" >
					<option value="" selected disabled hidden="disabled">Disponibilidad</option>
					<option value="0">No disponible</option>
					<option value="1">Disponible</option>
				</select>
	
				<div class="form-group col-4">
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupPrepend2"> <i
								class="icon-desktop"></i>
							</span>
						</div>
						<input type="text" class="form-control" id="inputTechName"
							placeholder="Tecnología" aria-describedby="techHelp"
							name="<portlet:namespace/>tecnologia" />
					</div>
				</div>
	
				<div class="col-1">
					<button type="button" class="btn" onclick="sendData()">
						<i class="icon-search"></i>
					</button>
	
				</div>
			</div>
		</form>

	</div>

	<div class="row">
		<div class="col-12">
			<body>
				<table id="example" class="table table-bordered table-striped"
					style="width: 100%">
					<thead>
						<tr>
							<th>Nombres</th>
							<th>Cargo</th>
							<th>Salario</th>
							<th>Ubicación</th>
							<th>Disponibilidad</th>
							<th>Consultar</th>
						</tr>
					</thead>
					<tbody>

						<%
							for (int i = 0; i < userList.length(); i++) {
						%>
						<tr>
							<td><%=userList.getJSONObject(i).getString("nombres")%></td>
							<td><%=userList.getJSONObject(i).getString("cargo")%></td>
							<td><%=userList.getJSONObject(i).getString("salario")%></td>
							<td><%=userList.getJSONObject(i).getString("locacion")%></td>
							<td><%=userList.getJSONObject(i).getString("disponibilidad")%></td>
							<td>
								<button type="submit"
									onclick="obtenerIdPerfil('<%=userList.getJSONObject(i).getString("_id")%>')"
									id="idPerfil" class="btn btn-primary text-center mx-auto">
									Consultar</button>
						</tr>
						<%
							}
						%>


						<%--
			<%-- 
				<c:forEach begin="0" end="${userList.length() -1}" var="index">
			 
					<tr>
					<td>${userList.getJSONObject(index).getString("nombres")}</td>
					
					<td>${userList.getJSONObject(index).getString("cargo")}</td>
					
					<td>${userList.getJSONObject(index).getString("salario")}</td>
					
					<td>${userList.getJSONObject(index).getString("locacion")}</td>
					
					<td>${userList.getJSONObject(index).getString("disponibilidad")}</td>
					
					<td>
					
						<button type="submit" onclick='obtenerIdPerfil(${userList.getJSONObject(index).getString(\\"_id\\")})'
						id="idPerfil" class="btn btn-primary text-center mx-auto">
							Consultar
						</button>
						${userList.getJSONObject(index).getString("_id")}
					</td>
					</tr>
				</c:forEach>
				--%>

						<%-- 
				<c:forEach items="${userList}" var="user">
					<tr>
					<td>${user.userId}</td>
					<td>${user.companyId}</td>
					<td>${user.emailAddress}</td>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					</tr>
				</c:forEach>
				--%>

					</tbody>
				</table>

				<script>
					
				<%--
				$(function(){
				$("#example").dataTable({
					"iDisplayLength":25, // default page size
					"aLengthMenu": [
						[25, 100, 200, -1],   // per page record options
						[25, 100, 200, "All"]
					],
					"bLengthChange": true, //Customizable page size 
					"bSort": false, // for Soring
					"bFilter": true, //search box
					"aaSorting": [],
					"aoColumns": [{// Columns width
						"sWidth": "15%"
					}, {
						"sWidth": "15%"
					}, {
						"sWidth": "30%"
					}, {
						"sWidth": "20%"
					},{
						"sWidth": "20%"
					}],
					"bAutoWidth": false,
					"oLanguage": {
						"sSearch": "Search: ",
						"sEmptyTable": "<div class='portlet-msg-alert'>No User Found</div>" // default message for no data
					},
					"sPaginationType": "full_numbers"
				});
				})--%>
					
				</script>
			</body>
		</div>
	</div>

<script>
	function obtenerIdPerfil(idPerfil) {
		document.getElementById("userID").value = idPerfil;
		document.getElementById("hiddenForm").submit();
	}
</script>

<form action="<%=consultarListaURL%>" method="POST" id="hiddenForm">

	<input type="hidden" class="form-control" id="userID"
		name="<portlet:namespace/>userID" />

</form>


<script>


var dataTable = $('#example').DataTable();


function sendData(){   
	
	var texto = document.getElementById("inputUserName").value;
	var disponibilidad = document.getElementById("inputDisponibilidad").value;
	var tecnologia = document.getElementById("inputTechName").value;

    jQuery.ajax({
             type: 'GET',
             url : '${dataFilterURL}',
             cache:false,
             async:true,
             error: function(){
                         alert('error');
                    },        
            dataType: "json",
            data: { 
            	<portlet:namespace/>texto: texto,
            	<portlet:namespace/>disponibilidad: disponibilidad,
            	<portlet:namespace/>tecnologia: tecnologia
            },

             success: function(status) {    
            	 var jsonResponse = status;
            	 const keys = Object.keys(jsonResponse);
            	 
            	 const values = Object.values(jsonResponse);
					
				const entries = Object.entries(jsonResponse);
				
				dataTable.clear().draw();
				for (const key of keys) {
					var id = status[key]._id;
					console.log(key, id);
					 dataTable.row.add([
	                     status[key].nombres,
	                     status[key].cargo,
	                     status[key].salario,
	                     status[key].locacion,
	                     status[key].disponibilidad,
	                     '<button type="submit" onclick="obtenerIdPerfil(\''+status[key]._id+'\')" id="idPerfil" class="btn btn-primary text-center mx-auto">Consultar</button>'
	                 ]).draw();
				}
          
             }
        });    
	
}
</script>


