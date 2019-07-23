package com.qxy.aspect;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qxy.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 如果程序报错统一返回系统异常信息
 */
//@ControllerAdvice //针对controller层生效
@RestControllerAdvice
@Slf4j
public class SysResultAspect {
	@ExceptionHandler(RuntimeException.class)//如果遇到指定的异常类型指定以下方法
	//@ResponseBody
	public SysResult sysResultFail( Exception e) {
		e.printStackTrace();
		log.error("服务器异常啦..."+e.getMessage());
		return SysResult.fail();
	}
}
