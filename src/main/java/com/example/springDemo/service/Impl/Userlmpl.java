package com.example.springDemo.service.Impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.springDemo.model.User;
import com.example.springDemo.service.IUserService;

import java.util.List;
import java.util.Map;


@Service
@Repository
public class Userlmpl  implements IUserService   {


    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public Userlmpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


	@Override
	public Map login(String username, String password) {
		// TODO Auto-generated method stub
        String sql = "select * from  sys_user_info where login_name =? and login_pwd =?  limit 1";
        
        			
        return jdbcTemplate.queryForMap(sql,username,password);
	}



}
