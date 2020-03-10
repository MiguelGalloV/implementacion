<%@ include file="/init.jsp" %>

<portlet:actionURL name="login" var="loginURL">
</portlet:actionURL>
  
<div  class="container">         
	<form action="<%=loginURL%>" method="POST">
	
		<div class="form-group">
		    <label for="inputEmail">
		    Usuario: </label>
		    <div class="input-group">
		        <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend2">
		          	<i class="icon-user"></i>
		          </span>
		        </div>
		        <input type="email" class="form-control" id="inputEmail" placeholder="Email*" aria-describedby="emailHelp"
		        required name="<portlet:namespace/>username"/>
		      </div>
	  	</div>
	  	
	  	<div class="form-group">
		    <label for="inputPassword">
		    Contraseña: </label>
		    <div class="input-group">
		        <div class="input-group-prepend">
		          <span class="input-group-text" id="inputGroupPrepend2">
		          	<i class="icon-lock"></i>
		          </span>
		        </div>
		        <input type="password" class="form-control" id="inputPassword" placeholder="Password*" aria-describedby="emailHelp"
		        required name="<portlet:namespace/>password"/>
		      </div>
	  	</div>		
	  	
		  <button type="submit" class="btn btn-primary text-center mx-auto">Iniciar sesión</button>
	  
	</form>

</div>
