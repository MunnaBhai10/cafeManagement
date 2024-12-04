package com.example.cafe.entity;

import com.example.cafe.enums.TableStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;




@Entity
public class CafeTables {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer tableId;
private String tableName;

@Enumerated
private TableStatus status;

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





}
