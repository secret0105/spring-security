package com.xiaoshazhou.result;

/**
 * 异常枚举类
 * @author zhoujin
 */
public enum CustomExceptionType {

  /**
   * 用户输入错误
   */
  USER_INPUT_ERROR(400,"您输入的数据格式错误或您没有权限访问资源！"),
  /**
   * 系统错误
   */
  SYSTEM_ERROR (500,"系统出现异常，请您稍后再试或联系管理员！");

  private String description;//异常类型中文描述

  private int code; //code

  CustomExceptionType(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCode() {
    return code;
  }

}
