package com.ust.sourcecourse.metadataextractor.entity;

import java.sql.Date;

public class Report {


	    private String uid;
	    private String consumptionUid;
	    private String dataQualityUid;
	    private String createdBy;
	    private Date createdTimestamp;
	    private String modifiedBy;
	    private Date modifiedTimestamp;

	    public String getUid() {
	        return uid;
	    }

	    public void setUid(String uid) {
	        this.uid = uid;
	    }

	    public String getConsumptionUid() {
	        return consumptionUid;
	    }

	    public void setConsumptionUid(String consumptionUid) {
	        this.consumptionUid = consumptionUid;
	    }

	    public String getDataQualityUid() {
	        return dataQualityUid;
	    }

	    public void setDataQualityUid(String dataQualityUid) {
	        this.dataQualityUid = dataQualityUid;
	    }

	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    public Date getCreatedTimestamp() {
	        return createdTimestamp;
	    }

	    public void setCreatedTimestamp(Date createdTimestamp) {
	        this.createdTimestamp = createdTimestamp;
	    }

	    public String getModifiedBy() {
	        return modifiedBy;
	    }

	    public void setModifiedBy(String modifiedBy) {
	        this.modifiedBy = modifiedBy;
	    }

	    public Date getModifiedTimestamp() {
	        return modifiedTimestamp;
	    }

	    public void setModifiedTimestamp(Date modifiedTimestamp) {
	        this.modifiedTimestamp = modifiedTimestamp;
	    }
	

}
