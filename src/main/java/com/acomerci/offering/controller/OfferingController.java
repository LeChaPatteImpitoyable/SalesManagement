package com.acomerci.offering.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.acomerci.offering.model.business.ManageCurrencyBF;
import com.acomerci.offering.model.business.ManageOfferingBF;
import com.acomerci.offering.model.business.ManageProductBF;
import com.acomerci.offering.model.business.ManageProductTypeBF;
import com.acomerci.offering.model.business.ManageStatusBF;
import com.acomerci.offering.model.business.ManageUserBF;
import com.acomerci.offering.model.entities.Currency;
import com.acomerci.offering.model.entities.FinancialService;
import com.acomerci.offering.model.entities.Offering;
import com.acomerci.offering.model.entities.OfferingMainFields;
import com.acomerci.offering.model.entities.OfferingStatus;
import com.acomerci.offering.model.entities.Product;
import com.acomerci.offering.model.entities.ProductType;
import com.acomerci.offering.model.entities.RejectOffering;
import com.acomerci.offering.model.entities.Status;

@Controller
@Scope("session")
public class OfferingController {
	ManageOfferingBF offeringBF = new ManageOfferingBF();
	ManageUserBF userBF = new ManageUserBF();
	
	private List<Product> products;
	private List<Product> productsToDelete;
	private List<FinancialService> financialServices;
	private List<FinancialService> financialServicesToDelete;
	private byte[] productImage;
	
	
	public OfferingController() {
		initOffering();
	}
	
	
	/***************** Oferta *****************/
	@RequestMapping(value="/initOffering", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void initOffering() {
		this.initProducts();
	}
	
	@RequestMapping(value="/getStatuses", method = RequestMethod.GET)
	public @ResponseBody List<Status> getEstados() {
		return ManageStatusBF.getInstance().getAll();
	}
	
	@RequestMapping(value="/getOfferings", method = RequestMethod.GET)
	public @ResponseBody List<OfferingMainFields> getOfferings() {
		List<Offering> ofertas = offeringBF.getAll();
		List<OfferingMainFields> offeringMainFieldsList = new ArrayList<OfferingMainFields>();
		for (Offering offering : ofertas) {
			offeringMainFieldsList.add(new OfferingMainFields(offering));
		}
		
		return offeringMainFieldsList;
	}
	
	@RequestMapping(value="/getOffering", method = RequestMethod.GET)
	public @ResponseBody Offering getOffering(@RequestParam("id") int offeringId) {
		Offering offering =  offeringBF.find(offeringId);
		this.initProducts();
		this.products = offering.getProducts();
		
		return offering;
	}
	
	@RequestMapping(value="/getLastOfferingStatus", method = RequestMethod.GET)
	public @ResponseBody OfferingStatus getLastOfferingStatus(@RequestParam("id") int offeringId) {
		OfferingStatus offeringStatus =  offeringBF.getLastOfferingStatus(offeringId);
		
		return offeringStatus;
	}
	
	
	@RequestMapping(value="/createOffering", method = RequestMethod.POST)
	public ResponseEntity<Object> createOffering(@RequestBody Offering offering) {
		offering.setCreationDate(new Date());
		offering.setProducts(this.products);
		
		boolean insert = offeringBF.create(offering);
		
		if (insert)
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/updateOffering", method = RequestMethod.POST)
	public ResponseEntity<Object> updateOffering(@RequestBody Offering offering) {
		offering.setProducts(this.products);
		boolean update = offeringBF.update(offering, this.productsToDelete, this.financialServicesToDelete);
		
		if (update)
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/deleteOffering", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteOffering(@RequestBody Offering offering) {
		offeringBF.delete(offering);
	}
	
	@RequestMapping(value="/retireOffering", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void retireOffering(@RequestBody Offering offering) {
		offeringBF.retire(offering);
	}
	
	@RequestMapping(value="/duplicateOffering", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void duplicateOffering(@RequestBody Offering offering) {
		offering = offeringBF.find(offering.getId());
		offeringBF.duplicate(offering);
	}
	
	@RequestMapping(value="/requestPublishOffering", method = RequestMethod.POST)
	public ResponseEntity<Object> requestPublishOffering(@RequestBody Offering offering) {
		String ret = offeringBF.requestPublish(offering);
		
		if (ret.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/publishOffering", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void publishOffering(@RequestBody Offering offering) {
		offeringBF.publish(offering);
	}
	
	@RequestMapping(value="/rejectOffering", method = RequestMethod.POST)
	public ResponseEntity<Object> rejectOffering(@RequestBody RejectOffering rejectOffering) {
		
		String ret = offeringBF.reject(rejectOffering);
		
		if (ret.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/***************** Producto *****************/
	
	public void initProducts() {
		products = new ArrayList<Product>();
		productsToDelete = new ArrayList<Product>();
		productImage = null;
		financialServices = new ArrayList<FinancialService>();
		financialServicesToDelete = new ArrayList<FinancialService>();
	}
	
	@RequestMapping(value="/initProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void initProduct(@RequestBody Product productIn) {
		productImage = null;
		financialServices = productIn.getFinancialServices();
		financialServicesToDelete = new ArrayList<FinancialService>();
	}

	
	/*********** ComboBoxes ***********/
	@RequestMapping(value="/getProductTypes", method = RequestMethod.GET)
	public @ResponseBody List<ProductType> getProductTypes() {
		return ManageProductTypeBF.getInstance().getAll();
	}
	
	@RequestMapping(value="/getProductNames", method = RequestMethod.GET)
	public @ResponseBody List<Product> getProductNames() {
		return ManageProductBF.getInstance().getBankProductNames();
	}
	
	@RequestMapping(value="/getCurrencies", method = RequestMethod.GET)
	public @ResponseBody List<Currency> getCurrencies() {
		return ManageCurrencyBF.getInstance().getAll();
	}
	
	@RequestMapping(value="/addProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProduct(@RequestBody Product productIn) {
		Product product = new Product();
		product = productIn;
		product.setImage(productImage);
		products.add(product);
		productImage = null;
	}
	
	@RequestMapping(value="/updateProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateProduct(@RequestBody Product productIn) {
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			if (product.getId() == productIn.getId()) {
				if (productImage != null) {
					productIn.setImage(productImage);
				}
				products.set(i, productIn);
			}
		}
	}
	
	@RequestMapping(value="/deleteProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProduct(@RequestBody Product productIn) {
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			if (product.getId() == productIn.getId()) {
				products.remove(i);
			}
		}
		
		// Si el producto esta en la db se borra
		if(productIn.getId() > 0) {
			productsToDelete.add(productIn);
		}
	}
	
	@RequestMapping(value="/deleteFinancialService", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteFinancialService(@RequestBody FinancialService financialServiceIn) {
		for (int i = 0; i < financialServices.size(); i++) {
			FinancialService financialService = financialServices.get(i);
			if (financialService.getId() == financialServiceIn.getId()) {
				financialServices.remove(i);
			}
		}
		
		// Si el financialServiceo esta en la db se borra
		if(financialServiceIn.getId() > 0) {
			financialServicesToDelete.add(financialServiceIn);
		}
	}
	
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public @ResponseBody Product fileupload(@RequestParam(value = "file") MultipartFile file) {
		try {
			productImage = file.getBytes();
			Product product = new Product();
			product.setImage(Base64.encodeBase64(productImage));
			return product;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
