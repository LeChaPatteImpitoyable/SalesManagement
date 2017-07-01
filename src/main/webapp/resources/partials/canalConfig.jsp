<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container">
	<h4>Horario de Atenci&oacute;n - P&uacute;blico</h4>
	
	<form id="formConfiguracion" data-ng-submit="updateChannelConfig()">
		<div class="row form-inline" style="margin-bottom: 30px;">
			<div class="col-sm-4 col-xs-12 box-light">
				<div class="col-md-6 col-sm-12 centered" style="margin-bottom:10px;">
					<label for="openingTime">Abre</label>
					<timepicker
						ng-model="openingTime"
						minute-step="15"
						show-meridian="false">
					</timepicker>
				</div>
				<div class="col-md-6 col-sm-12 centered">
					<label for="closingTime">Cierra</label>
					<timepicker
						ng-model="closingTime"
						minute-step="15"
						show-meridian="false">
					</timepicker>
				</div>
			</div>
			
			<div class="col-sm-7 col-xs-12 col-sm-offset-1 box-light seven-cols">
				<div class="col-xs-1" align="center">
					<label class="checkbox">Lun</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[0].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Mar</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[1].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Mie</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[2].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Jue</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[3].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Vie</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[4].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Sab</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[5].enable">
				</div>
				<div class="col-xs-1" align="center">
					<label class="checkbox">Dom</label><br>
					<input type="checkbox" data-ng-true-value="1" data-ng-false-value="0" data-ng-model="businessDays[6].enable">
				</div>
			</div>
		</div>
	
		<!-- Botones -->
		<div align="center">
			<input id="submitInput" type="submit" data-ng-show="false"/>
			
			<sec:accesscontrollist hasPermission="EDIT_CHANNEL_CONF" domainObject="null">
				<a class="btn btn-success btn-sm"
					data-submit="submitInput"
					data-popover="Aceptar"
					data-popover-trigger="mouseenter">
					<span class="glyphicon glyphicon-ok"></span>
				</a>
			</sec:accesscontrollist>
			
			<a class="btn btn-info btn-sm"
				href="#/ofertas"
				data-popover="Volver"
				data-popover-trigger="mouseenter">
				<span class="glyphicon glyphicon-arrow-left"></span>
			</a>
		</div>
	</form>
</div>