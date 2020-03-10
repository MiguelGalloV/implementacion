<%@ include file="/init.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.liferay.portal.kernel.json.JSONArray;" %> 

<%@page import="com.liferay.portal.kernel.model.Role"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

<!-- Add the slick-theme.css if you want default styling -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.css"/>
<!-- Add the slick-theme.css if you want default styling -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick-theme.css"/>


<portlet:actionURL name="consultarProyecto" var="consultarProyectoURL">
</portlet:actionURL>

<%JSONArray userProfile=(JSONArray) request.getAttribute("userProfile");
%>

<%JSONArray tecnologias=(JSONArray) request.getAttribute("tecnologias");
%>

<%JSONArray asignacion=(JSONArray) request.getAttribute("asignacion");
%>

<%
List<Role> userRoles = themeDisplay.getUser().getRoles();
		
%>


<%-- <%= userProfile%> --%>

<div class="row">
    <div class="col">
        <button type="button" name="back"  class="btn btn-primary text-center mx-auto" onclick="goBack()"">Atrás</button>
    </div>
</div>

<div class="row text-center">
    <div class="col-6">
        <div class="card border-dark mb-3"" style="width: 20rem;">
            <img class="card-img-top img-circle rounded-circle" src="http://50.116.18.134:3001/squadms/public/users/<%= request.getAttribute("imagenprofile") %>" alt="Card image cap">
            <div class="card-block">
              <h4 class="card-title"> <%= request.getAttribute("nombres") %> </h4>
              <p class="card-position"> <%= request.getAttribute("cargo") %> </p>
              <p class="card-text"> <%= request.getAttribute("descripcion") %> </p>
            </div>
          </div>
    </div>
    <div class="col-6">
         <section id="features" class="blue">
			  <h2>Tecnologías</h2>
			  
			  <div class="slider center">
			        
						<%
							for (int i = 0; i < tecnologias.length(); i++) {
						%>
						<div class="container.slick">				
							<img class="card-slick" src="http://50.116.18.134:3001/squadms/public/tecnologies/<%=tecnologias.getJSONObject(i).getString("imagen")%>" style="height: 100px; width: 100px;">
							</div>
						<%
							}
						%>      
			   </div>
			           
			</section>
    </div>
</div>

<div class="row">
    <div class="col-6">
	<figure class="highcharts-figure">
	    <div id="container"></div>
	    <p class="highcharts-description">
	        Disponibilidad de usuarios según los proyectos asignados.
	    </p>
	</figure>
    </div>
    <div class="col-6">
         <c:forEach var="role" items="<%= userRoles %>">
			<c:if test="${role.getName() == 'adm'}">
				<div class="row">
				<h2>Admins ASD can See This.</h2><br>
				<div class="col">
			        <button type="button" name="modify"  class="btn btn-primary text-center mx-auto" onclick="modificarDisponibilidad()">Modificar disponibilidad</button>
			        <div class="col-12">
			<body>
				<table id="example" class="table table-bordered table-striped"
					style="width: 100%">
					<thead>
						<tr>
							<th>Nombre del proyecto</th>
							<th>Disponibilidad</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>

						<%
							for (int i = 0; i < asignacion.length(); i++) {
						%>
						<tr>
							<td><%=asignacion.getJSONObject(i).getString("nombre")%></td>
							<td><%=asignacion.getJSONObject(i).getString("valor")%></td>
							<td>
								<button type="submit"
									onclick="obtenerCodigo('<%=asignacion.getJSONObject(i).getString("codigo")%>')"
									id="idPerfil" class="btn btn-primary text-center mx-auto">
									Modificar</button>
						</tr>
						<%
							}
						%>

					</tbody>
				</table>

				<script>
					
				
				$(function(){
				$("#example").DataTable({
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
				})
					
				</script>
			</body>
		</div>
			    </div>
				</div>
			</c:if>
		</c:forEach>
    </div>
</div>

<!-- Slick Imports -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.js"></script>

  
  <!-- Highcharts Imports -->
  <script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

 <!-- Highcharts functionality --> 

<script type="text/javascript">
var processed_json = new Array();
var asig = <%=(JSONArray) request.getAttribute("asignacion")%>;

for (i = 0; i < asig .length; i++){
    processed_json.push([asig[i].nombre, asig[i].valor]);
 
}
     
  Highcharts.chart('container', {

    chart: {
        type: 'pie',
        options3d: {
            enabled: true,
            alpha: 45
        }
    },
    title: {
        text: 'Disponibilidad'
    },
    subtitle: {
        text: ''
    },
    plotOptions: {
        pie: {
            innerSize: 100,
            depth: 45
        }
    },
    series: [{
        name: 'Tiempo de dedicación',
        data: processed_json,
    }]
});
  
  </script>
  
  <!-- Slick functionality -->
  <script type="text/javascript">
    $(document).ready(function(){
    	$('.center').slick({
            centerMode: true,
            infinite: true,
            centerPadding: '80px',
            slidesToShow: 2,
            speed: 500,
            responsive: [{
                breakpoint: 768,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '40px',
                    slidesToShow: 2
                }
            }, {
                breakpoint: 480,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '40px',
                    slidesToShow: 1
                }
            }]
        });
    });
  </script>
  
<script>
function goBack() {
  window.history.back();
}
</script>

<script>
function modificarDisponibilidad() {
}
</script>

<script>
function obtenerCodigo(idProyecto) {
	document.getElementById("projectID").value = idProyecto;
	document.getElementById("hiddenForm").submit();
}
</script>

<form action="<%=consultarProyectoURL%>" method="POST" id="hiddenForm">

	<input type="hidden" class="form-control" id="projectID"
		name="<portlet:namespace/>projectID" />

</form>



