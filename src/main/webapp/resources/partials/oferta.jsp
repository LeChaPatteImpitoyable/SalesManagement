<div class="container">
	<form name="formOferta" data-ng-submit="createUpdateOffering()">
		<!-- Oferta -->
		<div class="form-inline text-center"
			style="margin-bottom: 15px;">
			<div class="form-group col-lg-3 col-sm-6 nopadding">
				<label for="creationDate">F. Creaci&oacute;n</label>
				<input
					name="creationDate"
	        		type="text"
	         		class="form-control"
	         		style="width: 120px;"
	         		data-datetime="dd/MM/yyyy"
	         		data-ng-model="offering.creationDate"
	         		readonly/>
			</div>
					
			<div class="form-group col-lg-3 col-sm-6 nopadding">
				<label for="publishDate">F. Publicaci&oacute;n</label>
				<input
					name="publishDate"
	        		type="text"
	         		class="form-control"
	         		style="width: 120px;"
	         		data-datetime="dd/MM/yyyy"
	         		data-ng-model="offering.publishDate"
	         		readonly/>
			</div>
					
			<div class="form-group">
				<label for="dueDate">F. Vigencia</label>
				<div class="input-group"
	         		style="{{offering.status.id == 1 || offering.status.id == null? 'width: 150px;' : ''}} margin-right: 30px;">
					<input
						id="dueDate"
		        		type="text"
		         		class="form-control"
		         		style="{{offering.status.id != 1 && offering.status.id != null? 'border-radius: 4px; width: 120px;' : ''}}"
		         		data-ng-model="offering.dueDate"
		         		data-datepicker-popup="dd/MM/yyyy"
		         		data-is-open="vigencia"
		         		data-current-text="Hoy"
		         		data-clear-text="Limpiar"
		         		data-close-text="Cerrar"
		         		data-min-date="offering.creationDate"
		         		readonly/>
					<span class="input-group-btn"
						data-ng-show="offering.status.id == 1 || offering.status.id == 7 || offering.status.id == null">
						<button type="button" class="btn btn-default" data-ng-click="open($event, 'vigencia')"><i class="glyphicon glyphicon-calendar"></i></button>
					</span>
		        </div>
			</div>
					
			<div class="form-group">
				<label for="status">Estado</label>
				<input id="status" 
					type="text"
					class="form-control"
	         		style="width: 180px;"
					data-ng-model="offering.status.name"
					readonly>
			</div>
		</div>
		
		<div class="alert alert-info"
			style="padding: 5px;"
			data-ng-show="rejectReason != null">
			<b>Motivo de rechazo:</b> {{rejectReason}}
		</div>
		
		<!-- Productos -->
		<div style="margin-bottom: 20px;">
			<fieldset>
			    <legend class="text-center">Producto
					<span
						class="color-primary-4 glyphicon glyphicon-collapse-{{isCollapsed ? 'down' : 'up'}}"
						style="cursor: pointer;" 
						data-ng-click="isCollapsed = !isCollapsed"
						data-popover="{{isCollapsed ? 'Expandir' : 'Contraer'}}"
						data-popover-trigger="mouseenter">
					</span>
				</legend>
				
				<div
					id="products"
					data-collapse="isCollapsed">
					
					<!-- Fila 1 -->
					<div class="row">
						<div class="col-md-4 form-group">
							<div class="col-md-4 nowrap nopadding">
								<label for="productType">Tipo Prod.</label>
							</div>
							<div class="col-md-8 nopadding">
								<select
									id="productType"
									class="form-control"
									data-ng-model="product.productType" 
									data-ng-options="productType.name for productType in productTypes track by productType.id"
									data-ng-disabled="!canEdit">
								</select>
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-4 nowrap nopadding">
								<label for="name">Producto</label>
							</div>
							<div class="col-md-8 nopadding">
								<ui-select
									id="productName"
									data-ng-model="product.name"
									reset-search-input="product.name == null || product.name == ''"
									data-ng-disabled="!canEdit"
									style="{{offering.status.name == 'Nueva' || offering.status.name == 'Guardada' ? '' : 'background-color: #ccc;'}}">
						        	<ui-select-match>
						        		<span>{{$select.selected.name || $select.search || product.name}}</span>
						        	</ui-select-match>
						        	<ui-select-choices
						        		repeat="productName in (productNames | filter: { name: $select.search })"
						        		refresh="refreshResults($select)"
						        		refresh-delay="0">
						        		<span>{{productName.name}}</span>
						        	</ui-select-choices>
						        </ui-select>
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-2 nowrap nopadding">
								<label style="text-align: center; line-height: initial;">
									Imagen<br>
									<span class="small">(opcional)</span>
								</label>
							</div>
							<div class="col-md-10"
								style="padding-right: 0px;">
								<div class="input-group">
									<input
										type="hidden"
										name="MAX_FILE_SIZE"
										value="5000" />
					                <input
					                	type="text"
					                	class="form-control"
					                	data-ng-model="product.imageName"
					                	readonly>
					                <span class="input-group-btn">
					                    <span class="btn btn-default btn-file"
											data-ng-disabled="!canEdit">
					                        &hellip;
					                        <input data-file 
					                        	type="file"
					                        	accept="image/*"
					                        	onchange="angular.element(this).scope().fileNameChanged(this)"
												data-ng-disabled="!canEdit">
					                    </span>
					                </span>
					            </div>
					    	</div>
						</div>
					</div>
					
					<!-- Fila 2 -->
					<div class="row">
						<div class="col-md-4 form-group">
							<div class="col-md-3 nowrap nopadding">
								<label for="currency">Moneda</label>
							</div>
							<div class="col-md-9 nopadding">
								<select
									id="currency"
									class="form-control"
									data-ng-model="product.currency" 
									data-ng-options="currency.name for currency in currencies track by currency.id"
									data-ng-disabled="!canEdit">
								</select>
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-4 nowrap nopadding">
								<label for="maxAmount">Monto Tope</label>
							</div>
							<div class="col-md-8 nopadding">
								<input
									id="maxAmount"
									type="text"
									class="form-control text-right"
									data-ng-model="product.maxAmount"
									data-ng-disabled="!canEdit">
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-6 nowrap nopadding">
								<label for="maxTerm">Plazo Tope (meses)</label>
							</div>
							<div class="col-md-6 nopadding">
								<input
									id="maxTerm"
									type="text"
									class="form-control text-right"
									data-ng-model="product.maxTerm"
									data-ng-disabled="!canEdit">
							</div>
						</div>
					</div>
					
					<!-- Fila 3 -->
					<div class="row">
					    <div class="col-md-4 form-group">
					    	<div class="col-md-6 nowrap nopadding">
								<label for="commissionOpening"
									style="text-align: center; line-height: initial;">
									Com. Apertura<br>
									<span class="small">(opcional)</span>
								</label>
							</div>
							<div class="col-md-6 nopadding">
								<input
									id="commissionOpening"
									type="text"
									class="form-control text-right"
									data-ng-model="product.commissionOpening"
									data-ng-disabled="!canEdit">
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-6 nowrap nopadding">
								<label for="commissionEarlyCancellation"
									style="text-align: center; line-height: initial;">
									Com. Canc. anticip.<br>
									<span class="small">(opcional)</span>
								</label>
							</div>
							<div class="col-md-6 nopadding">
								<input
									id="commissionEarlyCancellation"
									type="text"
									class="form-control text-right"
									data-ng-model="product.commissionEarlyCancellation"
									data-ng-disabled="!canEdit">
							</div>
						</div>
						
						<div class="col-md-4 form-group">
							<div class="col-md-6 nowrap nopadding">
								<label for="commissionDownPayment"
									style="text-align: center; line-height: initial;">
									Com. Entr. a cuenta<br>
									<span class="small">(opcional)</span>
								</label>
							</div>
							<div class="col-md-6 nopadding">
								<input
									id="commissionDownPayment"
									type="text"
									class="form-control text-right"
									data-ng-model="product.commissionDownPayment"
									data-ng-disabled="!canEdit">
							</div>
						</div>
					</div>
					
					<!-- Fila 4 -->
					<div class="row">
						<div class="col-md-6 form-group">
							<fieldset>
							    <legend>Intereses No Cliente</legend>
							    <div class="col-md-6 form-group">
							    	<div class="col-md-5 nowrap nopadding">
										<label for="teaRate">% T.E.A.</label>
									</div>
									<div class="col-md-7 nopadding">
										<input 
											id="teaRate"
											type="text"
											class="form-control text-right input-sm"
											data-ng-model="product.teaRate"
											data-ng-disabled="!canEdit">
									</div>
								</div>
									
								<div class="col-md-6 form-group">
									<div class="col-md-5 nowrap nopadding">
										<label for="teaRateDelay">% Mora</label>
									</div>
									<div class="col-md-7 nopadding">
										<input
											id="teaRateDelay"
											type="text"
											class="form-control text-right input-sm"
											data-ng-model="product.teaRateDelay"
											data-ng-disabled="!canEdit">
									</div>
								</div>
							</fieldset>
						</div>
						
						<div class="col-md-6 form-group">
							<fieldset>
							    <legend>Intereses Cliente <span class="small">(opcional)</span></legend>
							    <div class="col-md-6 form-group">
							    	<div class="col-md-5 nowrap nopadding">
										<label for="teaRateClient">
											% T.E.A.
										</label>
									</div>
									<div class="col-md-7 nopadding">
										<input 
											id="teaRateClient"
											type="text"
											class="form-control text-right input-sm"
											data-ng-model="product.teaRateClient"
											data-ng-disabled="!canEdit">
									</div>
								</div>
									
								<div class="col-md-6 form-group">
									<div class="col-md-5 nowrap nopadding">
										<label for="teaRateDelayClient">
											% Mora
										</label>
									</div>
									<div class="col-md-7 nopadding">
										<input
											id="teaRateDelayClient"
											type="text"
											class="form-control text-right input-sm"
											data-ng-model="product.teaRateDelayClient"
											data-ng-disabled="!canEdit">
									</div>
								</div>
							</fieldset>
						</div>
					</div>
					
					<!-- Fila 5 -->
					<div class="row">
						<div class="col-md-6 form-group"
							id="divFinancialServices">
							<fieldset>
								<legend class="text-center">Servicios Financieros <span class="small">(opcional)</span></legend>
								<div class="col-md-12 form-group"
									style="margin-top: 5px;">
									<div class="form-inline">
										<div data-ng-repeat="financialService in product.financialServices">
											<input
												type="text"
												class="form-control input-sm"
												style="width: 90%;"
												data-ng-model="financialService.name"
												data-ng-disabled="!canEdit">
											<a href=""
												data-ng-click="deleteFinancialService($index)"
												data-popover="Eliminar"
												data-popover-trigger="mouseenter"
												data-ng-show="canEdit">
												<span class="glyphicon glyphicon-remove"></span>
											</a>
										</div>
									</div>
										
									<div class="form-inline"
										data-ng-show="canEdit">
										<input
											type="text"
											class="form-control input-sm"
											style="width: 90%;"
											placeholder="ej.: Tarjeta Prepaga"
											data-ng-model="newFinancialService"
											data-ng-enter="addFinancialService()">
										<a href=""
											style="color: green;"
											data-ng-click="addFinancialService()"
											data-popover="Agregar"
											data-popover-trigger="mouseenter">
											<span class="glyphicon glyphicon-plus"></span>
										</a>
									</div>
								</div>
							</fieldset>
						</div>
						
						<div class="col-md-4 form-group text-center">
							<img
								class="box-light"
								style="height:100px; cursor: -webkit-zoom-in; cursor: -moz-zoom-in;"
								data-ng-show="product.image != null"
								data-ng-src="data:image/png;base64,{{product.image}}"
								data-ng-click="openImage()"
								data-popover="Ver imagen"
								data-popover-trigger="mouseenter"/>
						</div>
						
						<div class="col-md-2 form-group"
							id="divButtonsProduct">
							<div class="input-group pull-right"
								data-ng-show="canEdit">
								<a class="btn {{prodIndex === -1 ? 'btn-success' : 'btn-info'}} btn-sm"
									data-ng-click="insertUpdateProduct()">
									<span class="glyphicon {{prodIndex === -1 ? 'glyphicon-plus' : 'glyphicon-ok'}}"></span> {{prodIndex === -1 ? 'Agregar Producto' : 'Actualizar Producto'}}
								</a>
							</div>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
			
		<!-- Tabla Productos -->
		<table class="table table-bordered table-striped table-hover">
			<thead class="header-center">
				<tr>
					<th>Tipo Producto</th>
					<th>Producto</th>
					<th>Monto Tope</th>
					<th>Moneda</th>
					<th>P. Tope (meses)</th>
					<th>% T.E.A.</th>
					<th>% Mora</th>
					<th>Imagen asociada</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody data-ng-repeat="product in products">
				<tr>
					<td>{{product.productType.name}}</td>
					<td>{{product.name}}</td>
					<td class="text-right">$ {{product.maxAmount | currency:"":2}}</td>
					<td>{{product.currency.name}}</td>
					<td class="text-right">{{product.maxTerm}}</td>
					<td class="text-right">{{product.teaRate | percentage:2}}</td>
					<td class="text-right">{{product.teaRateDelay | percentage:2}}</td>
					<td>{{product.imageName}}</td>
					<td class="text-center">
						<a href=""
							style="color: #5bc0de; text-decoration: none;"
							data-ng-click="editProduct($index)"
							data-popover="{{canEdit ? 'Editar' : 'Ver'}}"
							data-popover-trigger="mouseenter">
							<span class="glyphicon {{canEdit ? 'glyphicon-pencil' : 'glyphicon-search'}}"></span>
						</a>
						<a href=""
							style="text-decoration: none;"
							data-ng-click="deleteProduct($index)"
							data-ng-show="canEdit"
							data-popover="Quitar"
							data-popover-trigger="mouseenter">
							<span class="glyphicon glyphicon-remove"></span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
		
		<!-- Botones -->
		<div align="center">
			<input id="submitInput" type="submit" data-ng-show="false"/>
			
			<a class="btn btn-success btn-sm"
				data-submit="submitInput"
				data-ng-show="canEdit"
				data-popover="Aceptar"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-ok"></span>
			</a>
			
			<a class="btn btn-danger btn-sm"
				href="#/ofertas"
				data-ng-show="canEdit"
				data-popover="Cancelar"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-remove"></span>
			</a>
			
			<a class="btn btn-info btn-sm"
				href="#/ofertas"
				data-ng-show="!canEdit"
				data-popover="Volver"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-arrow-left"></span>
			</a>
		</div>
		<input type="hidden"
								name="${_csrf.parameterName}"
								value="${_csrf.token}"/>
	</form>
</div>

 <script type="text/ng-template" id="modalImage">
	<div class="modal-body">
		<img
			style="width: 100%;"
			data-ng-src="data:image/png;base64,{{image}}"/>
	</div>
	<div class="modal-footer">
		<button
			class="btn btn-primary"
			type="button"
			data-ng-click="close()">
			Cerrar
		</button>
	</div>
</script>