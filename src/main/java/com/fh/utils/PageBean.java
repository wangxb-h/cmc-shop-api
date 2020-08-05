package com.fh.utils;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fh.entity.OrderInfo;

import java.util.List;

public class PageBean<T>{
	
	//总条数
	@TableField(exist = false)
	private Long recordsTotal;
	@TableField(exist = false)
	private Long recordsFiltered;
	//每页条数
	@TableField(exist = false)
	private Integer length;
	//当前页数
	@TableField(exist = false)
	private Integer draw;
	//起始下标
	@TableField(exist = false)
	private Integer start;
	@TableField(exist = false)
	private List<T> data;



	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	

}
