package com.example.cafe.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.cafe.enums.TableStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;




@Entity
public class CafeTables {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tableId;
	private String tableName;

	@Enumerated
	private TableStatus status;
	
	@OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();



	@PrePersist
	public void prePersist() {
		if (status == null) {
			status = TableStatus.AVAILABLE;
		}
	}



	public Integer getTableId() {
		return tableId;
	}



	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}



	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public TableStatus getStatus() {
		return status;
	}



	public void setStatus(TableStatus status) {
		this.status = status;
	}



	public List<Orders> getOrders() {
		return orders;
	}



	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}





}
