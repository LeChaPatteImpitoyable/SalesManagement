package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the offering_product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte[] image;

	@Column(name="image_name")
	private String imageName;

	@Column(name="max_amount")
	private BigDecimal maxAmount;

	@Column(name="max_term")
	private int maxTerm;

	private String name;

	@Column(name="tea_rate")
	private BigDecimal teaRate;

	@Column(name="tea_rate_delay")
	private BigDecimal teaRateDelay;

	@Column(name="tea_rate_client")
	private BigDecimal teaRateClient;

	@Column(name="tea_rate_delay_client")
	private BigDecimal teaRateDelayClient;
	
	@Column(name="commission_opening")
	private BigDecimal commissionOpening;
	
	@Column(name="commission_early_cancellation")
	private BigDecimal commissionEarlyCancellation;
	
	@Column(name="commission_down_payment")
	private BigDecimal commissionDownPayment;
	
	// bi-directional many-to-one association to FinancialService
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<FinancialService> financialServices;

	//bi-directional many-to-one association to Currency
	@ManyToOne
	private Currency currency;

	//bi-directional many-to-one association to Offering
	@ManyToOne
	@JsonBackReference
	private Offering offering;

	//bi-directional many-to-one association to ProductType
	@ManyToOne
	@JoinColumn(name="product_type_id")
	private ProductType productType;

	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public BigDecimal getMaxAmount() {
		return this.maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getMaxTerm() {
		return this.maxTerm;
	}

	public void setMaxTerm(int maxTerm) {
		this.maxTerm = maxTerm;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTeaRate() {
		return this.teaRate;
	}

	public void setTeaRate(BigDecimal teaRate) {
		this.teaRate = teaRate;
	}

	public BigDecimal getTeaRateDelay() {
		return this.teaRateDelay;
	}

	public void setTeaRateDelay(BigDecimal teaRateDelay) {
		this.teaRateDelay = teaRateDelay;
	}

	public BigDecimal getTeaRateClient() {
		return this.teaRateClient;
	}

	public void setTeaRateClient(BigDecimal teaRateClient) {
		this.teaRateClient = teaRateClient;
	}

	public BigDecimal getTeaRateDelayClient() {
		return this.teaRateDelayClient;
	}

	public void setTeaRateDelayClient(BigDecimal teaRateDelayClient) {
		this.teaRateDelayClient = teaRateDelayClient;
	}

	public BigDecimal getCommissionOpening() {
		return commissionOpening;
	}

	public void setCommissionOpening(BigDecimal commissionOpening) {
		this.commissionOpening = commissionOpening;
	}

	public BigDecimal getCommissionEarlyCancellation() {
		return commissionEarlyCancellation;
	}

	public void setCommissionEarlyCancellation(BigDecimal commissionEarlyCancellation) {
		this.commissionEarlyCancellation = commissionEarlyCancellation;
	}

	public BigDecimal getCommissionDownPayment() {
		return commissionDownPayment;
	}

	public void setCommissionDownPayment(BigDecimal commissionDownPayment) {
		this.commissionDownPayment = commissionDownPayment;
	}

	public List<FinancialService> getFinancialServices() {
		return financialServices;
	}

	public void setFinancialServices(List<FinancialService> financialServices) {
		this.financialServices = financialServices;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@JsonIgnore
	public Offering getOffering() {
		return this.offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productType=" + productType + ", name=" + name + ", maxAmount=" + maxAmount + ", maxTerm=" + maxTerm
				+ ", teaRate=" + teaRate + ", teaRateDelay=" + teaRateDelay + ", teaRateClient=" + teaRateClient + ", teaRateDelayClient="
				+ teaRateDelayClient + ", commissionOpening=" + commissionOpening + ", commissionEarlyCancellation="
				+ commissionEarlyCancellation + ", commissionDownPayment=" + commissionDownPayment + ", currency=" + currency
				+ ", imageName=" + imageName + ", financialServices=" + financialServices + "]";
	}
}