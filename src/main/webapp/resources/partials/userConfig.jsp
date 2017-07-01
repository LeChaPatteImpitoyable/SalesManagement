<div class="container">
	<form data-ng-submit="updateUserConfig()">
		<div class="form-horizontal">
			<div class="form-group">
				<label for="password" class="col-md-5 control-label">Contrase&ntilde;a actual</label>
				<div class="col-md-3">
					<input
						id="password"
						type="password"
						class="form-control"
						data-ng-model="password"
						required>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-5 control-label">Nueva Contrase&ntilde;a</label>
				<div class="col-md-3">
					<input
						id="newPassword"
						type="password"
						class="form-control"
						data-ng-model="newPassword"
						required>
				</div>
			</div>
			<div class="form-group">
				<label for="passwordConfirm" class="col-md-5 control-label">Confirmaci&oacute;n nueva contrase&ntilde;a</label>
				<div class="col-md-3">
					<input
						id="newPasswordConfirm"
						type="password"
						class="form-control"
						data-ng-model="newPasswordConfirm"
						required>
				</div>
			</div>
		</div>
	
		<!-- Botones -->
		<div align="center">
			<input id="submitInput" type="submit" data-ng-show="false"/>
			
			<a class="btn btn-success btn-sm"
				data-submit="submitInput"
				data-popover="Aceptar"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-ok"></span>
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