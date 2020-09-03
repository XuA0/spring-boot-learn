package com.xuao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ResultVO implements Serializable{
	private static Logger log = LoggerFactory.getLogger(ResultVO.class);
	/* MODIFICATION HISTORY
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Date Modified	Modified by		Comments
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * 
	 *** =========when code is=================
	 *** empty/null:  no error, show info messages in list infoMessages
	 *** else value:  error, show question message or show error message
	 *****
	 ***** 
	 ***** ======if code is else value, when questionMsgFlag is=====
	 ***** true       : show question message
	 ***** else value : show error message
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -57852277669715387L;
	
	private transient Object result;
	
	//if code is not null, mean validation fail
	//used for show message title&content
	private String code;

	private transient Object msg;
	private String title;

	//if code is not null and below is "true"
	//mean show question message for current fail-validation 
	private boolean questionMsgFlag = false;

	//used to show info messages when validation&save succ
	private transient List<ResultVO> infoMessages = null;


    public List<ResultVO> getInfoMessages() {
		return infoMessages;
	}

	public void setInfoMessages(List<ResultVO> infoMessages) {
		this.infoMessages = infoMessages;
	}

	public ResultVO() {}
	
	public ResultVO(Object  result) {
		this("", "", result);
	}

	public ResultVO(String code,Object msg) {
		this(code, msg, "");
	}

	public ResultVO(String code,Object msg, boolean questionMsgFlag) {
		this.code = code;
		this.msg = msg;
		this.setQuestionMsgFlag(questionMsgFlag);
	}

	public ResultVO(String code,Object msg,Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}


	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultVO [result=" + result + ", code=" + code + ", msg=" + msg + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//add ln str
	public void showInfoMessage(Object notUse, String errorCd, String[] msgArray, String title) {
		log.info("showInfoMessage notUse is {}", notUse);
		//put info message into ResultVO.result field
		if (this.infoMessages == null)
			this.infoMessages = new ArrayList<>();


	}

	public void showInfoMessage(Object notUse, String errorCd) {
		log.info("showInfoMessage2 notUse is {}", notUse);
		//put info message into ResultVO.result field
		if (this.infoMessages == null)
			this.infoMessages = new ArrayList<>();


	}
	//add ln end

	public HttpStatus getHttpStatus() {
		if(code == null || "".equals(code)) {
			return HttpStatus.OK;
		}

		return HttpStatus.BAD_REQUEST;
	}


	public static ResultVO showErrorMessage(Object notUse, String erroCd) {
		log.info("showErrorMessage3 notUse is {}", notUse);
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(erroCd);

		return resultVO;
	}

	public boolean isQuestionMsgFlag() {
		return questionMsgFlag;
	}

	public void setQuestionMsgFlag(boolean questionMsgFlag) {
		this.questionMsgFlag = questionMsgFlag;
	}


}
