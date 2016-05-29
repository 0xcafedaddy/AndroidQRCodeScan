package com.qlu.android.product.pojo;

import java.util.Date;

import com.qlu.android.product.util.DateUtil;

public class Product {
    private Integer id;

    private String mid;

    private String nam;

    private Date pd;
    
    private String pdString;

    private Date gd;
    
    private String gdString;

    private Date record;
    
    private String recordString;

    private Integer frequency;

    private String company;

    private String phone;

    private Integer price;
    
    
    

    public String getRecordString() {
		return recordString;
	}

	public void setRecordString(String recordString) {
		this.recordString = recordString;
	}

	public String getPdString() {
		return pdString;
	}

	public void setPdString(String pdString) {
		
		this.pdString = pdString;
	}

	public String getGdString() {
		return gdString;
	}

	public void setGdString(String gdString) {
		this.gdString = gdString;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam == null ? null : nam.trim();
    }

    public Date getPd() {
        return pd;
    }

    public void setPd(Date pd) {
    	this.pdString = DateUtil.format(pd, DateUtil.YYYYMMDD);
        this.pd = pd;
    }

    public Date getGd() {
        return gd;
    }

    public void setGd(Date gd) {
		this.gdString = DateUtil.format(gd, DateUtil.YYYYMMDD);
        this.gd = gd;
    }

    public Date getRecord() {
        return record;
    }

    public void setRecord(Date record) {
    	this.recordString = DateUtil.format(record, DateUtil.YYYYMMDD);
        this.record = record;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

	@Override
	public String toString() {
		return "Product [id=" + id + ", mid=" + mid + ", nam=" + nam + ", pd="
				+ pd + ", pdString=" + pdString + ", gd=" + gd + ", gdString="
				+ gdString + ", record=" + record + ", frequency=" + frequency
				+ ", company=" + company + ", phone=" + phone + ", price="
				+ price + "]";
	}

    
    
}