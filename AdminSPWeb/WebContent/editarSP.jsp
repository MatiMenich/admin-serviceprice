<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Servicio Precio</title>
	
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/site.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/pygments.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.min.css">
	
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script>
	$(document).ready(function() {
        $("#tipoSP").change(function(){
                if($(this).val() == "RecSMS" || $(this).val() == "RecMMS"){
                	$("#canal-div").slideUp();
                    $("#cache-div").slideUp();
                    $("#args-div").slideUp();
                    $("#la-div").slideDown();
                    $("#sp-div").slideDown();
                }
                else if($(this).val() == "EnvSMS" || $(this).val() == "EnvMMS" || $(this).val() == "EnvVSMS"){
                	$("#la-div").slideUp();
                	$("#canal-div").slideDown();
                	$("#sp-div").slideDown();
                	$("#args-div").slideDown();
                	$("#cache-div").slideDown();
                }
                else{
                	 $("#la-div").slideUp();
                     $("#sp-div").slideUp();
                     $("#canal-div").slideUp();
                     $("#cache-div").slideUp();
                     $("#args-div").slideUp();
                }
        });
        
        if($("#tipoSP").val() == "RecSMS" || $("#tipoSP").val() =="RecMMS"){
        	$("#canal-div").hide();
            $("#cache-div").hide();
            $("#args-div").hide();
        }
        else if($("#tipoSP").val() == "EnvSMS" || $("#tipoSP").val() == "EnvMMS" || $("#tipoSP").val() == "EnvVSMS"){
        	$("#la-div").hide();
        }
        else{
     	  	$("#la-div").hide();
            $("#sp-div").hide();
            $("#canal-div").hide();
            $("#cache-div").hide();
            $("#args-div").hide();
        }
        
        
        
      
	});
	</script>

	<style type="text/css">	body{background-color : #f9f9f9;}</style>	
	
</head>
<body>



  	<form class="form-horizontal" method="post" action="SPController">
			<fieldset>
				<legend>Editar Servicio Precio</legend>


				<!-- Multiple Radios (inline) -->
				<div class="control-group">
				  <label class="control-label" for="radios">Estado:</label>
				  <div class="controls">
				    <label class="radio inline" for="radios-0">
				      <input type="radio" name="radios" id="radios-0" value="1" ${sp.getEstado().toString()=='INACTIVO' ? 'checked="checked"' : "" }>
				      Inactivo
				    </label>
				    <label class="radio inline" for="radios-1">
				      <input type="radio" name="radios" id="radios-1" value="2" ${sp.getEstado().toString()=='TESTING' ? 'checked="checked"' : "" }>
				      Testing
				    </label>
				    <label class="radio inline" for="radios-2">
				      <input type="radio" name="radios" id="radios-2" value="3" ${sp.getEstado().toString()=='ACTIVO' ? 'checked="checked"' : "" }>
				      Activo
				    </label>
				  </div>
				</div>
				
				<div class="control-group">
					<label class="control-label">Seleccione Operador: </label>
					<div class="controls">
						  <select id="selectOperador" name="selectOperador" class="input-xlarge">
						      	<c:forEach var="op" items="${opList}">
							  		<option value='${op.getIdBD()}' ${sp.getOperador().toString() == op.toString() ? 'selected' : "" }>${op.toString() }</option>
						  		</c:forEach>
						  </select>
					</div>
				</div>
				
				 
				<div class="control-group">
					  <label class="control-label">Tipo SP: </label>
					  <div class="controls">
					    <select id="tipoSP" name="tipoSP" class="input-xlarge">
					      <option>Seleccione Tipo...</option>
					      <option  value="RecSMS" ${sp.getTipo()=="Recepcion" && sp.getTipoRec().toString()=="SMS" ? 'selected' : "" }>Recepción SMS</option>
					      <option  value="RecMMS" ${sp.getTipo()=="Recepcion" && sp.getTipoRec().toString()=="MMS" ? 'selected' : "" }>Recepción MMS </option>
					      <option  value="EnvSMS" ${sp.getTipo()=="Envio" && sp.getTipoEnv().toString()=="SMSWP" ? 'selected' : "" }>Envío SMS/WP</option>
					      <option  value="EnvMMS" ${sp.getTipo()=="Envio" && sp.getTipoEnv().toString()=="MMS" ? 'selected' : "" }>Envío MMS</option>
					      <option  value="EnvVSMS" ${sp.getTipo()=="Envio" && sp.getTipoEnv().toString()=="VSMS" ? 'selected' : "" }>Envío VSMS</option>
					      <option  value="Bill">Billing</option>
					    </select>
					  </div>
					</div>
				 
				
				<div id="la-div" class="control-group">
					<label class="control-label">LA:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span3">
								<input id="la" type="text" class="input-block-level" autocomplete="off" value='${sp.getTipo()=="Recepcion" ? sp.getLA() : ""}' >
							</div>
						</div>
					</div>
				</div>
				 
				
				
				<div id="canal-div" class="control-group">
					<label class="control-label">Canal:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span10">
								<input id="canal" type="text" class="input-block-level" autocomplete="off" value='${sp.getTipo()=="Envio"||sp.getTipo()=="Billing" ? sp.getCanal() : "" }' >
							</div>
						</div>
					</div>
				</div>
				 
				
				

				<div id="sp-div" class="control-group">
					<label class="control-label">Servicio/Precio:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span9">
								<input id="servicio" type="text" class="input-block-level" autocomplete="off" value='${sp.getServicio()}'>
							</div>
							<div class="span3">
								<input id="precio" type="text" class="input-block-level" autocomplete="off"   value='${sp.getPrecio() }'>
							</div>
						</div>
					</div>
				</div>
				
				
				
				<div id="args-div" class="control-group">
					<label class="control-label">Argumentos:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span12">
								<input id="args" type="text" class="input-block-level" autocomplete="off" value='${sp.getTipo()=="Envio" || sp.getTipo()=="Billing" ? sp.getArgs() : "" }' >
							</div>
						</div>
					</div>
				</div>
				 	 
				
				
			
				<div id="cache-div" class="control-group">
					<label class="control-label">Cache</label>
					<div class="controls">
						<div class="row-fluid">
							 <input type="checkbox" name="checkboxes" id="checkboxes-0" value="Option one" ${sp.getTipo()=="Envio" || sp.getTipo()=="Billing" ? (sp.hasCache() ?  'checked' : "") : ""}>
						</div>
					</div>
				</div>
				 

				

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">Editar SP</button>
					<button type="button" class="btn">Cancelar</button>
				</div>
			</fieldset>
		</form>


</body>
</html>