package org.tsc.core.init;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;

public class InitializeProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static String projectCode;
	
	public static String getProjectCode() {
		return projectCode;
	}
	private void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("这是初始化操作！！！");
		String sql = "SELECT MAX(project_code) AS project_code FROM tsc_project";
		Map<String, Object> map = new HashMap<String, Object>();
		map = jdbcTemplate.queryForMap(sql);
		System.out.println("projectCode == "+map.get("project_code").toString());
		setProjectCode(map.get("project_code").toString());
	}

}
