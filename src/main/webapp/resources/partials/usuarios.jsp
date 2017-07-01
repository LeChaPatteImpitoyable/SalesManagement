<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container">
	<div class="col-md-3">
		<div id="divPanelHeadingUsers"
			class="panel-heading">
			<h4 class="panel-title text-center">Usuarios</h4>
		</div>
		<div class="panel-body">
			<div class="scrolleableDiv">
				<table
					id="usersTable"
					class="table table-striped table-hover table-clickeable-rows">
					<tbody>
						<tr data-ng-repeat="user in users"
							data-ng-click="setSelectedUser(user)"
							data-ng-class="{selected: user.id == selectedUser.id}"
							style="{{user.active == 0 ? 'background-color: #d9534f;' : ''}}">
							<td>
								{{user.email}}
							</td>
							<td style="width: 1%; white-space: nowrap;">
								<sec:accesscontrollist hasPermission="DELETE_USER" domainObject="null">
									<a href=""
										data-ng-show="user.active == 1"
										data-ng-click="deleteUser(user)"
										data-ng-confirm-click="&iquest;Est&aacute; seguro que desea eliminar al usuario {{user.email}} ({{user.lastName}}, {{user.name}})?"
										data-popover="Eliminar"
										data-popover-trigger="mouseenter">
										<span class="glyphicon glyphicon-remove"></span>
									</a>
								</sec:accesscontrollist>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div>
		    <label>
				<input 
	      			type="checkbox"
	      			data-ng-model="showInactive"
	      			data-ng-click="getUsers()">
	      		Mostrar inactivos
			</label>
		</div>
	</div>
	
	<form id="formUser" data-ng-submit="createUpdateUser()">
		<div class="col-md-9">
			<div class="panel-heading">
				<h4 class="panel-title text-center">{{selectedUser.id == null ? 'Nuevo' : 'Editar'}} Usuario</h4>
			</div>
			<div class="panel-body form-horizontal margin-bottom-15"
				style="border-bottom-left-radius: 3px; border-bottom-right-radius: 3px;">
				<div class="row margin-top-15 margin-bottom-15">
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="email">Email</label>
						</div>
						<div class="col-md-8">
							<input
								name="email"
				        		type="email"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.email"
				         		required/>
				        </div>
					</div>
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="bornDate">F. Nacimiento</label>
						</div>
						<div class="col-md-8">
							<div class="input-group datepicker">
								<input
									id="bornDate"
					        		type="text"
					         		class="form-control"
					         		data-ng-model="selectedUser.bornDate"
					         		data-datepicker-popup="dd/MM/yyyy"
					         		data-is-open="bornDate"
					         		data-current-text="Hoy"
					         		data-clear-text="Limpiar"
					         		data-close-text="Cerrar"
					         		disabled/>
								<span class="input-group-btn">
									<button
										type="button"
										class="btn btn-default"
										data-ng-click="open($event, 'bornDate')">
										<i class="glyphicon glyphicon-calendar"></i>
									</button>
								</span>
					        </div>
				        </div>
					</div>
				</div>
				
				<div class="row margin-bottom-15">
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="lastName">Apellido</label>
						</div>
						<div class="col-md-8">
							<input
								name="lastName"
				        		type="text"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.lastName"
				         		required/>
				        </div>
					</div>
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="name">Nombre</label>
						</div>
						<div class="col-md-8">
							<input
								name="name"
				        		type="text"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.name"
				         		required/>
				        </div>
					</div>
				</div>
				
				<div class="row margin-bottom-15">
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="docType">Tipo Documento</label>
						</div>
						<div class="col-md-8">
							<input
								name="docType"
				        		type="text"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.docType"/>
				        </div>
					</div>
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="document">Nro. Documento</label>
						</div>
						<div class="col-md-8">
							<input
								name="document"
				        		type="text"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.document"/>
				        </div>
					</div>
				</div>
				
				<div class="row margin-bottom-15">
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="gender">Sexo</label>
						</div>
						<div class="col-md-8">
							<select
								id="gender"
								class="form-control"
								data-ng-model="gender"
								data-ng-options="gender.name for gender in genders track by gender.id">
							</select>
				        </div>
					</div>
					<div class="col-md-6">
						<div class="col-md-4 text-right nowrap">
							<label for="phoneNumber">Tel&eacute;fono</label>
						</div>
						<div class="col-md-8">
							<input
								name="phoneNumber"
				        		type="text"
				         		class="form-control"
				         		maxlength="45"
				         		data-ng-model="selectedUser.phoneNumber"/>
				        </div>
					</div>
				</div>
			</div>
		
			<!-- Botones -->
			<div align="center">
				<input id="submitInput" type="submit" data-ng-show="false"/>
				
				<sec:accesscontrollist hasPermission="CREATE_USER" domainObject="null">
					<a class="btn btn-success btn-sm"
						data-ng-show="selectedUser.id == null"
						data-submit="submitInput"
						data-popover="Agregar usuario"
						data-popover-trigger="mouseenter">
						<span class="glyphicon glyphicon-plus"></span>
					</a>
				</sec:accesscontrollist>
				
				<sec:accesscontrollist hasPermission="EDIT_USER" domainObject="null">
					<a class="btn btn-success btn-sm"
						data-ng-show="selectedUser.active == 1"
						data-submit="submitInput"
						data-popover="Editar usuario"
						data-popover-trigger="mouseenter">
						<span class="glyphicon glyphicon-pencil"></span>
					</a>
				</sec:accesscontrollist>
				
				<sec:accesscontrollist hasPermission="EDIT_USER" domainObject="null">
					<a class="btn btn-success btn-sm"
						data-ng-show="selectedUser.active == 0"
						data-submit="submitInput"
						data-ng-confirm-click="&iquest;Est&aacute; seguro que desea reactivar al usuario {{selectedUser.email}} ({{selectedUser.lastName}}, {{selectedUser.name}})?"
						data-popover="Editar usuario"
						data-popover-trigger="mouseenter">
						<span class="glyphicon glyphicon-ok"></span>
					</a>
				</sec:accesscontrollist>
				
				<a class="btn btn-info btn-sm"
					href="#/ofertas"
					data-ng-show="selectedUser.id == null"
					data-popover="Volver"
					data-popover-trigger="mouseenter">
					<span class="glyphicon glyphicon-arrow-left"></span>
				</a>
				
				<a class="btn btn-danger btn-sm"
					href=""
					data-ng-click="newUser()"
					data-ng-show="selectedUser.id != null"
					data-popover="Cancelar"
					data-popover-trigger="mouseenter">
					<span class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
		</div>
	</form>
</div>