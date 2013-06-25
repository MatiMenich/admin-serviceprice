<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Servicio Precio</title>
	
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	
	<script src="js/jquery.js"></script>
	<script src="js/jquery.validate.js"></script>
	<script>
	$(document).ready(function() {
		
		$.validator.addMethod('positiveNumber',
			    function (value) { 
			        return Number(value) > 0;
			    }, 'Ingrese un número positivo.');
		
		
		 var RecRules = {
				 
				 	servicio: {
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese un servicio",
	        	            minlength: "Largo mayor que 2"
	        	        }
	        	    },
	        	    
	        	    la: {
	        	        required: true,
	        	        number: true,
	        	        positiveNumber: true,
	        	        messages: {
	        	        	required: "Ingrese un LA",
	        	            number: "Ingrese un número válido"
	        	        }
	        	    },
	        	    
	        	    precio: {
	        	    	required:true,
	        	    	number: true,
	        	    	positiveNumber: true,
	        	    	messages: {
	        	        	required: "Ingrese un precio",
	        	            number: "Ingrese un número válido"
	        	    	}
	        	    }
	        };
		 
		 var EnvRules = {
				 
				 	servicio: {
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese un servicio",
	        	            minlength: "Largo mayor que 2"
	        	        }
	        	    },
	        	    
	        	     precio: {
	        	    	required:true,
	        	    	number: true,
	        	    	positiveNumber: true,
	        	    	messages: {
	        	        	required: "Ingrese un precio",
	        	            number: "Ingrese un número válido"
	        	    	}
	        	    },
	        	    
	        	    canal: {
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese un canal",
	        	            minlength: "Largo mayor que 2"
	        	        }
	        	    }
	        };
		 
		 
		 function addRules(rulesObj){
	            for (var item in rulesObj){
	               $('#'+item).rules('add',rulesObj[item]);  
	            } 
	        }

	        function removeRules(rulesObj){
	            for (var item in rulesObj){
	               $('#'+item).rules('remove');  
	            } 
	        }
		
	        $('#SPform').validate({
	            submitHandler: function(){
	              alert('submit was successful'); 
	            },
	        	errorPlacement: function(error, element) {
		           error.insertAfter(element);
		           parent.$.fancybox.update(); 
	        	}
	        });
		
	        $("#tipoSP").change(function(){
	        	
	        	
                if($(this).val() == "RecSMS" || $(this).val() == "RecMMS"){
                	$("#canal-div").hide();
                    $("#cache-div").hide();
                    $("#estrategia-div").hide();
                    $("#args-div").hide();
                    $("#la-div").show();
                    $("#sp-div").show();
                    removeRules(EnvRules);
                    addRules(RecRules);
                }
                else if($(this).val() == "EnvSMS" || $(this).val() == "EnvMMS" || $(this).val() == "EnvVSMS" || $(this).val() == "Bill"){
                	$("#la-div").hide();
                	$("#canal-div").show();
                	$("#sp-div").show();
                	$("#estrategia-div").show();
                	$("#cache-div").show();
                	removeRules(RecRules);
                    addRules(EnvRules);
                }
                else{
                	 $("#la-div").hide();
                     $("#sp-div").hide();
                     $("#canal-div").hide();
                     $("#cache-div").hide();
                     $("#estrategia-div").hide();
                     $("#args-div").hide();
                }
                
               
               

                $('#SPform').validate({
    	            submitHandler: function(){
    	              alert('submit was successful'); 
    	            },
    	        	errorPlacement: function(error, element) {
    		           error.insertAfter(element);
    		           parent.$.fancybox.update(); 
    	        	}
    	        });
               
                parent.$.fancybox.update();	
               
        });
		 
		
		
        if($("#tipoSP").val() == "RecSMS" || $("#tipoSP").val() =="RecMMS"){
        	$("#canal-div").hide();
            $("#cache-div").hide();
            $("#estrategia-div").hide();
            $("#args-div").hide();
            removeRules(EnvRules);
            addRules(RecRules);
        }
        else if($("#tipoSP").val() == "EnvSMS" || $("#tipoSP").val() == "EnvMMS" || $("#tipoSP").val() == "EnvVSMS" || $("#tipoSP").val() == "Bill"){
        	$("#la-div").hide();
        	removeRules(RecRules);
            addRules(EnvRules);
        }
        else{
     	  	$("#la-div").hide();
            $("#sp-div").hide();
            $("#canal-div").hide();
            $("#estrategia-div").hide();
            $("#args-div").hide();
            $("#cache-div").hide();
        }
        
        $("#tipoEstrategia").change(function(){
           	if($(this).val() == "fp" || $(this).val() == "asc" || $(this).val() == "dsc"){
           		$("#args-div").slideDown();
           	}
           	else{
           		$("#args-div").slideUp();
           	}
           	
           	$(":animated").promise( function (){
           		parent.$.fancybox.update();
           	});	
         });
            
   		if($("#tipoEstrategia").val() == "fp" || $("#tipoEstrategia").val() == "asc" || $("#tipoEstrategia").val() == "dsc" || $("#tipoEstrategia") == "fin"){
   			$("#args-div").show();
   		}
   		else{
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
					      <option  value="Bill" ${sp.getTipo()=="Billing" ? 'selected' : "" }>Billing</option>
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
				
				<div id="estrategia-div" class="control-group">
					<label class="control-label">Estrategia:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span12">
								<select id="tipoEstrategia" name="tipoEstrategia" class="input-xlarge">
							      <option>Seleccione Estrategia...</option>
							      <option  value="fp" ${sp.getTipo()=="Envio" && sp.getEstrategia().toString()=="FULLPRICE" ? 'selected' : 
														sp.getTipo()=="Billing" && sp.getEstrategia().toString()=="FULLPRICE" ? 'selected' : ""}>Full Price</option>
							      <option  value="asc" ${sp.getTipo()=="Envio" && sp.getEstrategia().toString()=="ASCENDENTE" ? 'selected' : 
							      						 sp.getTipo()=="Billing" && sp.getEstrategia().toString()=="ASCENDENTE" ? 'selected' : ""}>Ascendente </option>
							      <option  value="dsc" ${sp.getTipo()=="Envio" && sp.getEstrategia().toString()=="DESCENDENTE" ? 'selected' : 
														 sp.getTipo()=="Billing" && sp.getEstrategia().toString()=="DESCENDENTE" ? 'selected' : "" }>Descendente</option>
							      <option  value="fin" ${sp.getTipo()=="Envio" && sp.getEstrategia().toString()=="FINANCE" ? 'selected' : 
														 sp.getTipo()=="Billing" && sp.getEstrategia().toString()=="FINANCE" ? 'selected' : "" }>Finance</option>
							    </select>
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
				 
				
				<!-- Textarea -->
				<div class="control-group">
				  <label class="control-label" for="comment">Comentario:</label>
				  <div class="controls">                     
				    		<textarea id="comment" name="comment" placeholder="Ingrese un comentario..."></textarea>
				  </div>
				</div>

				

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">Editar SP</button>
					<button type="button" class="btn btn-danger">Cancelar</button>
				</div>
			</fieldset>
		</form>


</body>
</html>