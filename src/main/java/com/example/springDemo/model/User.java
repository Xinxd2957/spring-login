package com.example.springDemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class User {

    private Long id;
    

    private String userId;
    
    private String roleId;
    
    private String userNo;
    
    private String userName;
    
    private String userFlag;
    
    private String userTel;
    
    private String loginName;
    
    private String loginPwd;
    
    private String loginLastDate;
    
    private String inputDate;
    
    private String userToken;
    
    private String expireDate;
    
    private String isMobileLogin;

    @ApiModelProperty(value = "用户号",example = "123456", required = true)
    private String name;

    @ApiModelProperty(value = "密码",example = "123456", required = true)
    private String passWord;

    @ApiModelProperty(value = "昵称",example = "小小", required = true)
    private String nickName;

    @ApiModelProperty(value = "状态 0 :正常 1 ：禁止",example = "0", required = true)
    private Integer status;
    
    @ApiModelProperty(value = "token",example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMyIsImV4cCI6MTYwNjMwMzYzMywidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.Vy3yAqBNGIfnxOK9HzdV9KDgonmOMT3eSXBD9eYJP_g", required = true)
    private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", roleId=" + roleId + ", userNo=" + userNo + ", userName="
				+ userName + ", userFlag=" + userFlag + ", userTel=" + userTel + ", loginName=" + loginName
				+ ", loginPwd=" + loginPwd + ", loginLastDate=" + loginLastDate + ", inputDate=" + inputDate
				+ ", userToken=" + userToken + ", expireDate=" + expireDate + ", isMobileLogin=" + isMobileLogin
				+ ", name=" + name + ", passWord=" + passWord + ", nickName=" + nickName + ", status=" + status
				+ ", token=" + token + ", getId()=" + getId() + ", getName()=" + getName() + ", getPassWord()="
				+ getPassWord() + ", getNickName()=" + getNickName() + ", getStatus()=" + getStatus() + ", getToken()="
				+ getToken() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}



    

}
