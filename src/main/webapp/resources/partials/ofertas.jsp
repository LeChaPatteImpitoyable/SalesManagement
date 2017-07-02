<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container" data-ng-init="getOfferings()">
	<!-- Filtros -->
	<form class="form-inline">
		<div class="col-sm-3">
	    	<label>Per&iacute;odo</label><br>
	    	<div class="form-group btn-group">
		        <label class="btn btn-primary btn-autosize" data-ng-model="period" data-btn-radio="'YTD'" data-uncheckable>YTD</label>
		        <label class="btn btn-primary btn-autosize" data-ng-model="period" data-btn-radio="'MTD'" data-uncheckable>MTD</label>
		        <label class="btn btn-primary btn-autosize" data-ng-model="period" data-btn-radio="'WTD'" data-uncheckable>WTD</label>
	        </div>
	    </div>
	    
		<div class="form-group col-sm-3">
	    	<label class="label" for="fechaDesde">F. Creaci&oacute;n Desde</label><br>
			<div class="input-group datepicker">
				<input
					id="fechaDesde"
	        		type="text"
	         		class="form-control"
	         		data-ng-model="fechaDesde"
	         		data-datepicker-popup="dd/MM/yyyy"
	         		data-is-open="desde"
	         		data-current-text="Hoy"
	         		data-clear-text="Limpiar"
	         		data-close-text="Cerrar"
	         		disabled/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-ng-click="open($event, 'desde')"><i class="glyphicon glyphicon-calendar"></i></button>
				</span>
	        </div>
		</div>
		
		<div class="form-group col-sm-3">
	    	<label for="fechaHasta">F. Creaci&oacute;n Hasta</label><br>
			<div class="input-group datepicker">
				<input
					id="fechaHasta"
	        		type="text"
	         		class="form-control"
	         		data-ng-model="fechaHasta"
	         		data-datepicker-popup="dd/MM/yyyy"
	         		data-is-open="hasta"
	         		data-current-text="Hoy"
	         		data-clear-text="Limpiar"
	         		data-close-text="Cerrar"
	         		disabled/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-ng-click="open($event, 'hasta')"><i class="glyphicon glyphicon-calendar"></i></button>
				</span>
	        </div>
		</div>
		
		<div id="divStatus"
			class="form-group col-sm-3">
			<label for="estado">Estado</label><br>
			<select
				class="form-control" 
				style="margin-bottom: 15px;"
				data-ng-model="status"
				data-ng-options="status.id as status.name for status in statuses">
				<option value=""></option>
			</select>
		</div>
	</form>
	
	<div>
		<!-- Tabla Ofertas -->
		<div class="col-md-11 col-sm-12">
		<scrollable-table>
			<table class="table table-bordered table-striped table-hover table-clickeable-rows">
				<thead>
					<tr>
						<th id="firstTh">F. Creaci&oacute;n</th>
						<th>F. Publicaci&oacute;n</th>
						<th>F. Vigencia</th>
						<th>Estado</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="offering in offerings | filterCreationDateFrom:fechaDesde | filterCreationDateTo:fechaHasta | filterStatus:status | filterPeriod:period"
						data-ng-click="setSelected(offering)"
						data-ng-class="{selected: offering.id === selectedOffering.id}">
						<td class="text-right">{{offering.creationDate | date:'dd/MM/yyyy' : 'UTC'}}</td>
						<td class="text-right">{{offering.publishDate | date:'dd/MM/yyyy' : 'UTC'}}</td>
						<td class="text-right">{{offering.dueDate | date:'dd/MM/yyyy' : 'UTC'}}</td>
						<td>{{offering.status.name}}</td>
					</tr>
				</tbody>
			</table>
		</scrollable-table>
		</div>
		
		<!-- Botonera -->
		<div class="col-md-1 col-sm-12 text-center nopadding">
			<a href=""
				class="btn botonera"
				style="text-decoration: none; font-size: 25px; margin-bottom: 0px;"
				data-ng-click="getOfferings()"
				data-popover="Refrescar"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-refresh"></span>
			</a>
			
			<sec:accesscontrollist hasPermission="CREATE_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a href="#/oferta"
						class="btn btn-primary botonera">
						Nueva
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="VIEW_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a href="#/oferta/{{selectedOffering.id}}"
						data-ng-show="selectedOffering.id != null && (!canEdit || (selectedOffering.status.id != 1 && selectedOffering.status.id != 7))"
						class="btn btn-primary botonera">
						Ver
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="EDIT_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a href="#/oferta/{{selectedOffering.id}}"
						data-ng-show="selectedOffering.id != null && (selectedOffering.status.id == 1 || selectedOffering.status.id == 7)"
						class="btn btn-primary botonera">
						Editar
					</a>
					<input type="hidden" value="1" id="canEdit"/>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="REMOVE_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a data-ng-click="openConfirmation('deleteOffering',
													   '&iquest;Est&aacute; seguro que desea eliminar la oferta seleccionada?')"
						data-ng-show="selectedOffering.id != null && (selectedOffering.status.id == 1 || 
																	 selectedOffering.status.id == 6 ||
																	 selectedOffering.status.id == 7)"
						class="btn btn-primary botonera">
						Eliminar
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="RETIRE_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a data-ng-click="openConfirmation('retireOffering',
													   '&iquest;Est&aacute; seguro que desea retirar la oferta seleccionada?')"
						data-ng-show="selectedOffering.status.id == 2"
						class="btn btn-primary botonera">
						Retirar
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="DUPLICATE_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a data-ng-click="openConfirmation('duplicateOffering',
													   '&iquest;Est&aacute; seguro que desea duplicar la oferta seleccionada?')"
						data-ng-show="selectedOffering.id != null"
						class="btn btn-primary botonera">
						Duplicar
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="REQ_PUBLISH_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a data-ng-click="openConfirmation('requestPublishOffering',
													   '&iquest;Est&aacute; seguro que desea solicitar la publicaci&oacute;n de la oferta seleccionada?')"
						data-ng-show="selectedOffering.id != null && (selectedOffering.status.id == 1 || selectedOffering.status.id == 7)"
						class="btn btn-primary botonera">
						Solicitar publicaci&oacute;n
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="PUBLISH_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding"
					data-popover="{{cantPublishReason}}"
					data-popover-placement="left"
					data-popover-trigger="mouseenter">
					<a data-ng-click="openConfirmation('publishOffering',
													   '&iquest;Est&aacute; seguro que desea publicar la oferta seleccionada?')"
						data-ng-show="selectedOffering.id != null"
						data-a-disabled="selectedOffering.status.id != 6 || dueDateUTC < nowUTC"
						class="btn btn-primary botonera">
						Publicar
					</a>
				</div>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="REJECT_OFFERING" domainObject="null">
				<div class="col-xs-12 nopadding">
					<a data-ng-click="openConfirmation('rejectOffering',
													   '&iquest;Est&aacute; seguro que desea rechazar la oferta seleccionada?')"
						data-ng-show="selectedOffering.id != null"
						data-a-disabled="selectedOffering.status.id != 6"
						class="btn btn-primary botonera">
						Rechazar
					</a>
				</div>
			</sec:accesscontrollist>
		</div>
	</div>
</div>

 <script type="text/ng-template" id="modalConfirmation">
	<div class="modal-header">
            <h3 class="modal-title">Confirmaci&oacute;n</h3>
    </div>
    <div class="modal-body">
		{{ message }}
		<div class="row" style="width: 100%; margin-top: 15px;" data-ng-show="action == 'rejectOffering'">
			<span class="col-sm-2">
				Motivo:
			</span> 
			<textarea
				id="rejectReason"
				data-ng-model="rejectReason"
				rows="3"
				class="col-sm-10"
				maxlength="255"
				style="width: 75%;"/>
		</div>
	</div>
	<div class="modal-footer">
		<button
			class="btn btn-primary"
			type="button"
			data-ng-click="ok()">
			Aceptar
		</button>
		<button
			class="btn btn-primary"
			type="button"
			data-ng-click="close()">
			Cancelar
		</button>
	</div>
</script>