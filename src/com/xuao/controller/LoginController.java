package com.xuao.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuao.bean.ResultVO;
import com.xuao.bean.UserEntity;
import com.xuao.jwt.JwtTokenUtil;
import com.xuao.redis.RedisUtils;
import com.xuao.service.JwtUserDetailsService;


@RestController
public class LoginController {

	Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	RedisUtils redisUtils;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResultVO login(@RequestBody UserEntity sysUser, HttpServletRequest request) {
		ResultVO resultVO = new ResultVO();
		List<String> list = new ArrayList<String>();
		try {
			log.info(new ObjectMapper().writeValueAsString(sysUser));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean validateUserSucc = jwtUserDetailsService.validateUser(sysUser);

		if (validateUserSucc) {
			final UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			resultVO.setResult(token);
			resultVO.setCode("200");
			return resultVO;
		} else {
			resultVO.setResult("user validate failed");
			resultVO.setCode("401");
			return resultVO;
		}
	}

	@PostMapping("/saveUser")
	public void saveUser(@RequestBody UserEntity userEntity) {
		log.info("save user");
		try {
			log.info(new ObjectMapper().writeValueAsString(userEntity));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redisUtils.set(userEntity.getUsername(), userEntity.getPassword());
	}

	@CrossOrigin
	@PostMapping("/haha")
	public void haha(String sdpDocId, HttpServletResponse response) {
		OutputStream os = null;
		try {
//		            AttachmentDataDO attachment = fileAttachmentService.getAttachment(hdr);

//		            byte[] pdfBytes = OpUtils.getEsmActivity().getDocument(sdpDocId).getData();

			try {
				File file = new File("D:\\Doc\\bill_base64_bytes_array.txt");
//		    		FileInputStream in = new FileInputStream(file);
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				byte[] b = new byte[1024];
				int len = 0;

				String bytesString;

				bytesString = bufferedReader.readLine();
				System.out.println(bytesString);

				byte[] bytes = Base64.getDecoder().decode(bytesString);

//						byte[] bytes = bytesString.getBytes();
				os = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + sdpDocId);
				response.setHeader("Access-Control-Allow-Origin", "*");
				os.write(bytes);
				os.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO return value wait for check
			String msgArray[] = { "error getAttachment! pls try again!" };
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error("close os error while getAttachment");
				}
			}
		}}

	public static void main(String[] args) {
		try {
			File file = new File("D:\\Doc\\bill_base64_bytes_array.txt");
//		FileInputStream in = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			byte[] b = new byte[1024];
			int len = 0;

			String bytesString;

			bytesString = bufferedReader.readLine();
			System.out.println(bytesString);

//			byte[] bytes = bytesString.getBytes();

//			System.out.println(new BigInteger(1,bytes).toString(2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		InputStream in;
//		try {
//			InputStream fls = new FileInputStream(file);
//			byte[] bytes = FileCopyUtils.copyToByteArray(fls);
//			System.out.println(new String(bytes,"UTF-8"));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

	}

	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);
	}

}