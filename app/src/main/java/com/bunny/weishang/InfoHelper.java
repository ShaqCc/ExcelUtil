package com.bunny.weishang;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

 
public class InfoHelper {

	private DatabaseHelper helper;
	private Dao<Info, Integer> dao;

	public InfoHelper(Context context) {
		try {
			helper = DatabaseHelper.getHelper(context);
			dao = helper.getDao(Info.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @Title: queryAll
	 * @Description: 查询所有
	 * @param: @return  
	 * @return: List<Info>   
	 * @throws
	 */
	public List<Info> queryAll() {
		List<Info> retList = null;
		try {
			retList = dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retList;
	}

	public int delete(Info msg) {
		int i = -9;
		try {
			i = dao.delete(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

 
	public void create(List<Info> info) {
		for (Info f : info) {
			try {
				dao.create(f);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void create( Info c) {
			try {
				dao.create(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
 
	
	public int deleteById(int id) {
		int i = -1;
			try {
			Info cdf = new Info();
			cdf.setId(id);
			i = dao.delete(cdf);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int update(Info info){
		int i = -1;
		try {
			i = dao.update(info);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
}
