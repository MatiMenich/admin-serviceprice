<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<meta charset="utf-8">
	<title>Admin SP</title>
	
	
	
		<!-- CSS -->
	<link href="css/demo_page.css" rel="stylesheet" type="text/css" />
	<link href="css/demo_table.css" rel="stylesheet" type="text/css" />      
	<link href="css/demo_table_jui.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/DT_bootstrap.css">
	<!-- Scripts -->
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="js/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="js/jquery.dataTables.columnFilter.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8">
		$(document).ready( function () {
			oTable = $('#example').dataTable({
				"sDom" : 'rtlp<"clear">',
				"sPaginationType": "bootstrap",
				"bAutoWidth": false, // Disable the auto width calculation 
				"aoColumns": [
					{ "sWidth": "5%" },  
					{ "sWidth": "30%" }, 
					{ "sWidth": "15%" }, 
					{ "sWidth": "15%"},
					{ "sWidth": "15%"},
					{ "sWidth": "10%"},
					{ "sWidth": "10%"}
				]
			}).columnFilter({
				aoColumns:[
				   	null,
					{ type:"select", sSelector: "#operador" },
					null,
					null,
					null,
					null,
					null
					]
			});
			$.extend( $.fn.dataTableExt.oStdClasses, {
			    "sWrapper": "dataTables_wrapper form-inline"
			} );
			
			$('#search-box').keyup(function() {
				oTable.fnFilter( $(this).val() );
			});
			
		} );
		
	</script>

	
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/site.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/pygments.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.min.css">
	
	<style>
	        .header{
	            height:70px;
	            background-color:#691760;
	            color: white;
	            font-size:55pt;
	            padding:40px 0px 0px 30px;
	        }
	
	        .myFooter{
	            height:20px;
	            background-color:#691760;
	            color: white;
	            font-size:10pt;
	            padding:5px;
	            text-align:center;
	        }
	    </style>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top hidden-print">
    <div class="navbar-inner">
        <ul class="nav">
            <li><a href="#">SP</a></li>
            <li class="active"><a href="#">Scheduling</a></li>
            <li><a href="#">Admin3</a></li>
            <li><a href="#">Admin4</a></li>
            <li><a href="#">Admin5</a></li>
            <li><a href="#">Admin6</a></li>
            <li><a href="#">Admin7</a></li>
            <li><a href="#">Admin8</a></li>
            <li><a href="#">Admin9</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="mailto:dave@fontawesome.io"><i class="icon-user"></i>&nbsp; jperez</a></li>
        </ul>
    </div>
</div>

<!--div class="jumbotron hidden-print maincolor">
    <div class="container"><h1>ServicePrice</h1></div>
</div-->

<div class="header">Service Price</div>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner">
        <ul class="nav">
            <li><a href="SPController?type=Env">Envio</a></li>
            <li class="divider-vertical"></li>
            <li class="active"><a href="SPController?type=Rec">Recepción</a></li>
            <li class="divider-vertical"></li>
            <li><a href="SPController?type=Bill">Billing</a></li>
            <li class="divider-vertical"></li>

        </ul>
        <form class="navbar-search pull-right">
            <input id="search-box" type="text"  class="search-query" placeholder="Search"/>
            
        </form>
        
        
        
    </div>
</div>

${param.busq!=null ? '
	<div id="busq-avanzada">
		<p id="operador"></p>
	</div>'
	: ' '}


<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="example">
	<thead>
		<tr>
		<th>Estado</th>
        <th>Operador</th>
        <th>Tipo</th>
        <th>LA</th>
        <th>Servicio</th>
        <th>Precio</th>
        <th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sp" items="${sps}">
		
		<tr class="odd_gradeA">
		<td class="center" width="1">${sp.getEstado().toString() }</td>
        <td>${sp.getOperador().toString() }</td>
        <td>${sp.getTipoRec()}</td>
        <td>${sp.getLA() }</td>
        <td>${sp.getServicio() }</td>
        <td>${sp.getPrecio() }</td>
        <td class="center"><i class="icon-time icon-large"></i> <i class="icon-edit icon-large"></i> <i class="icon-trash icon-large"></i></td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>

<div class="myFooter">
   Movix - 2013
</div>

</body>
</html>