<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container">
	<form id="formRoles" data-ng-submit="createUpdateRole()">
		<div id="divRoleInputs"
			class="row"
			style="margin-bottom: 15px;">
			<div class="col-md-4">
				<div class="col-md-2 text-right nowrap">
					<label for="role">Rol</label>
				</div>
				<div class="col-md-10 form-inline"
					style="white-space: nowrap;">
					<select
						id="role"
						class="form-control"
						data-ng-model="selectedRole"
						data-ng-change="setSelectedRole()"
						data-ng-options="role.name for role in roles track by role.id">
					</select>
				
					<sec:accesscontrollist hasPermission="CREATE_ROLE" domainObject="null">
						<a class="btn btn-success btn-sm"
							data-ng-click="newRole()"
							data-popover="Nuevo rol"
							data-popover-trigger="mouseenter">
							<span class="glyphicon glyphicon-plus"></span>
						</a>
					</sec:accesscontrollist>
					
					<sec:accesscontrollist hasPermission="DELETE_ROLE" domainObject="null">
						<a class="btn btn-danger btn-sm"
							data-ng-click="deleteRole()"
							data-ng-confirm-click="&iquest;Est&aacute; seguro que desea eliminar el rol {{selectedRole.name}}?"
							data-ng-show="selectedRole.id != null"
							data-popover="Eliminar rol"
							data-popover-trigger="mouseenter">
							<span class="glyphicon glyphicon-remove"></span>
						</a>
					</sec:accesscontrollist>
		        </div>
			</div>
			<div class="col-md-3"
				data-ng-show="creatingRole || selectedRole.id != null">
				<div class="col-md-3 text-right nowrap">
					<label for="name">Nombre</label>
				</div>
				<div class="col-md-9">
					<input
						name="name"
		        		type="text"
		         		class="form-control"
		         		style="text-transform: uppercase;"
		         		maxlength="20"
		         		data-ng-model="selectedRole.name"
		         		required/>
		        </div>
			</div>
			<div class="col-md-5"
				data-ng-show="creatingRole || selectedRole.id != null">
				<div class="col-md-3 text-right nowrap">
					<label for="description">Descripci&oacute;n</label>
				</div>
				<div class="col-md-9">
					<input
						name="description"
		        		type="text"
		         		class="form-control"
		         		maxlength="100"
		         		data-ng-model="selectedRole.description"
		         		required/>
		        </div>
			</div>
		</div>
		
		<div class="row"
			style="margin-bottom: 15px;"
			data-ng-show="selectedRole.id != null || creatingRole">
			
			<!-- Permisos -->
			<div id="actions"
				class="col-md-6">
				<div class="col-md-5"
					style="padding: 0px;">
					<div id="divPanelHeadingAvailableActions"
						class="panel-heading">
						<h4 class="panel-title text-center">Permisos disponibles</h4>
					</div>
					<div class="panel-body">
						<div class="scrolleableDiv">
							<table class="table table-striped table-hover table-clickeable-rows">
								<tbody>
									<tr data-ng-repeat="action in availableActions"
										data-ng-click="setSelectedAvailableAction(action)"
										data-ng-class="{selected: checkSelectedAvailableAction(action)}">
										<td>
											{{action.description}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-md-2 v-center">
					<button
						type="button"
						class="btn btn-default center-block"
						data-popover="Agregar"
						data-popover-trigger="mouseenter"
						data-ng-click="availableToSelectedActions()">
						<i class="glyphicon glyphicon-chevron-right"></i>
					</button>
					<button
						type="button"
						class="btn btn-default center-block"
						data-popover="Quitar"
						data-popover-trigger="mouseenter"
						data-popover-placement="bottom"
						data-ng-click="selectedToAvailableActions()">
						<i class="glyphicon glyphicon-chevron-left"></i>
					</button>
				</div>
				<div class="col-md-5"
					style="padding: 0px;">
					<div class="panel-heading">
						<h4 class="panel-title text-center">Permisos seleccionados</h4>
					</div>
					<div class="panel-body">
						<div class="scrolleableDiv">
							<table class="table table-striped table-hover table-clickeable-rows">
								<tbody>
									<tr data-ng-repeat="action in selectedRole.actions"
										data-ng-click="setSelectedAction(action)"
										data-ng-class="{selected: checkSelectedAction(action)}">
										<td>
											{{action.description}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Usuarios -->
			<div id="users"
				class="col-md-6">
				<div class="col-md-5"
					style="padding: 0px;">
					<div class="panel-heading">
						<h4 class="panel-title text-center">Usuarios disponibles</h4>
					</div>
					<div class="panel-body">
						<div class="scrolleableDiv">
							<table class="table table-striped table-hover table-clickeable-rows">
								<tbody>
									<tr data-ng-repeat="user in availableUsers"
										data-ng-click="setSelectedAvailableUser(user)"
										data-ng-class="{selected: checkSelectedAvailableUser(user)}">
										<td
											data-popover="{{user.lastName + ', ' + user.name}}"
											data-popover-trigger="mouseenter"
											data-popover-placement="bottom">
											{{user.email}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-md-2 v-center">
					<button
						type="button"
						class="btn btn-default center-block"
						data-popover="Agregar"
						data-popover-trigger="mouseenter"
						data-ng-click="availableToSelectedUsers()">
						<i class="glyphicon glyphicon-chevron-right"></i>
					</button>
					<button
						type="button"
						class="btn btn-default center-block"
						data-popover="Quitar"
						data-popover-trigger="mouseenter"
						data-popover-placement="bottom"
						data-ng-click="selectedToAvailableUsers()">
						<i class="glyphicon glyphicon-chevron-left"></i>
					</button>
				</div>
				<div class="col-md-5"
					style="padding: 0px;">
					<div class="panel-heading">
						<h4 class="panel-title text-center">Usuarios seleccionados</h4>
					</div>
					<div class="panel-body">
						<div class="scrolleableDiv">
							<table class="table table-striped table-hover table-clickeable-rows">
								<tbody>
									<tr data-ng-repeat="user in selectedRole.users"
										data-ng-click="setSelectedUser(user)"
										data-ng-class="{selected: checkSelectedUser(user)}">
										<td
											data-popover="{{user.lastName + ', ' + user.name}}"
											data-popover-trigger="mouseenter"
											data-popover-placement="bottom">
											{{user.email}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Botones -->
		<div id="divButtons"
			align="center">
			<input id="submitInput" type="submit" data-ng-show="false"/>
			
			<sec:accesscontrollist hasPermission="CREATE_ROLE" domainObject="null">
				<a class="btn btn-success btn-sm"
					data-submit="submitInput"
					data-ng-show="creatingRole"
					data-popover="Crear rol"
					data-popover-trigger="mouseenter">
					<span class="glyphicon glyphicon-plus"></span>
				</a>
			</sec:accesscontrollist>
			
			<sec:accesscontrollist hasPermission="EDIT_ROLE" domainObject="null">
				<a class="btn btn-success btn-sm"
					data-submit="submitInput"
					data-ng-show="selectedRole.id != null"
					data-popover="Actualizar rol"
					data-popover-trigger="mouseenter">
					<span class="glyphicon glyphicon-pencil"></span>
				</a>
			</sec:accesscontrollist>
			
			<a class="btn btn-danger btn-sm"
				href=""
				data-ng-click="initRole()"
				data-ng-show="creatingRole || selectedRole.id != null"
				data-popover="Cancelar"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-remove"></span>
			</a>
			
			<a class="btn btn-info btn-sm"
				href="#/ofertas"
				data-popover="Volver"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-arrow-left"></span>
			</a>
		</div>
	</form>
</div>