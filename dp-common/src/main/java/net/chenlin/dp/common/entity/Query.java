package net.chenlin.dp.common.entity;

import java.awt.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

/**
 * 查询条件
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 *
 * @date 2017年8月11日 下午12:13:58
 */
public class Query extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	public Query() {
		super();
	}

	public Query(Map<String, Object> params){
        this.putAll(params);
    }
	
	public Double getAsDouble(String name) {
        Object value = this.get(name);
        if (value != null)
            return Double.valueOf(value.toString());
        return null;
    }

    public String getAsString(String name) {
        return this.get(name).toString();
    }

    public Long getAsLong(String name) {
        Object value = this.get(name);
        if (value != null)
            return Long.valueOf(value.toString());
        return null;
    }

    public Integer getAsInt(String name) {
        Object value = this.get(name);
        if (value != null)
            return Integer.valueOf(value.toString());
        return null;
    }

    public Boolean getAsBoolean(String name) {
        Object value = this.get(name);
        if (value != null)
            return Boolean.valueOf(value.toString());
        return null;
    }

    public java.util.Date getAsDate(String name) {
    	Object value = this.get(name);
        if (value != null)
        	return Date.valueOf(value.toString());
        return null;
    }

    public Object getObj(String name) {
        return this.get(name);
    }

    public List<Long> getAsLongList(String name) {
        List<Integer> value = (List<Integer>) this.get(name);
        List<Long> valueLong = null;
        if (value != null) {
            valueLong = new ArrayList<>();
            for (Integer item : value) {
                Long t = item.longValue();
                valueLong.add(t);
            }
            return valueLong;
        }
        return valueLong;
    }

}
