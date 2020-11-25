package com.example.springDemo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.springDemo.annotation.Login;
import com.example.springDemo.common.utils.TokenUtils;
import com.example.springDemo.model.User;
import com.example.springDemo.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;

@RestController
@RequestMapping("/user-device")
@Api(tags = "用户设备模块")
@Login
public class UserDeviceController {
	
    
	@Autowired
	IUserService iuseService;
    
   
    /**
     * 用户登陆
     *
     * @param user 用户信息
     * @return 200成功 202异常 400失败
     */
    @PostMapping("/token")
    @ApiOperation(value = "用户token", produces = "application/x-www-form-urlencoded")
    public ResponseEntity<String> getToken(String username,String password)  throws IOException {
        Map<String,Object> result = new HashMap<String, Object>();
        //模仿登录
       
        Map user = iuseService.login(username, password);
            if(user != null){
            	System.out.println(user.toString());
            	System.out.println(user.get("login_name"));
           String token = TokenUtils.token(username, password,"user");
                result.put("code", 200);
                result.put("msg", "获取成功");
                result.put("token",token);
                return new ResponseEntity(result, HttpStatus.OK);
            }

        


        result.put("code", 500);
        result.put("msg", "获取失败");

        return new ResponseEntity(result, HttpStatus.OK);

    }
    
    
    /**
     * 用户登陆
     *
     * @param user 用户信息
     * @return 200成功 202异常 400失败
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody User user)  throws IOException {

        Map<String,Object> result = new HashMap<String, Object>();
       
      boolean token  =  TokenUtils.verify(user.getToken());

 
        //成功了继续访问登录
        if(token) {
 
        	JSONObject json = TokenUtils.verifyUser(user.getToken());
        	
        	//后续获取token的时候存进redis里  和用户绑定 token改成从请求头里获取
        	if (user.getName().equals(json.get("username"))){
                result.put("code", 200);
                result.put("msg", "登录成功");
                return new ResponseEntity(result, HttpStatus.OK);
        	}
        	
            result.put("code", 500);
            result.put("msg", "登录失败.token错误");

            return new ResponseEntity(result, HttpStatus.OK);
 
        }


        result.put("code", 500);
        result.put("msg", "登录失败");

        return new ResponseEntity(result, HttpStatus.OK);

    }
    
    
    

}
