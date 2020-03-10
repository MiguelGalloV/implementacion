<%@ include file="/init.jsp" %>

<div class="container">
	<div class="row text-center">
	  <div class="col-lg-4">
	    <div class="card border-dark mb-3"" style="width: 20rem;">
	      <img class="card-img-top img-circle rounded-circle" src="http://50.116.18.134:3001/squadms/public/users/<%= request.getAttribute("imagenprofile") %>" alt="Card image cap">
	      <div class="card-block">
	        <h4 class="card-title"> <%= request.getAttribute("nombres") %> </h4>
	        <p class="card-position"> <%= request.getAttribute("cargo") %> </p>
	        <p class="card-text"> <%= request.getAttribute("descripcion") %> </p>
	      </div>
	    </div>
	  </div>
	</div>
</div>