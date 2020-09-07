package com.xuao.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {
	public static void main(String[] args) throws IOException, ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Information> informations = new ArrayList<Information>();
		String str = "";
		while ((str = br.readLine()) != null) {
			try {
				int id = Integer.parseInt(str);
				
				if (id < 0 || id > informations.size()) {
					System.out.println("can not find " + id);
					continue;
				}
				System.out.println(informations.get(id-1).toString());
				continue;
			} catch (NumberFormatException e) {
				// is not a validate number
				
			}
			Information information = new Information();

			String[] informationStrings = str.split(" ");
			if (informationStrings.length == 4) {
				information.setId(informations.size());
				information.setStockCode(informationStrings[0]);
				information.setTrading_date(simpleDateFormat.parse(informationStrings[1]));
				information.setFactorId(informationStrings[2]);
				information.setExposure(informationStrings[3]);
				informations.add(information);
			}else if(informationStrings.length == 7) {
				information.setId(informations.size());
				information.setStockCode(informationStrings[0]);
				Date traningDate = simpleDateFormat.parse(informationStrings[1]);
				Long nextTraningDate = traningDate.getTime()+Long.parseLong(informationStrings[4])*60*60*24;
				information.setTrading_date(new Date(nextTraningDate));
				information.setFactorId(informationStrings[2]);
				Information informationLast = findLastInformation(information.getStockCode(), informations);
				if(informationLast==null) {
					information.setExposure(informationStrings[3]);
					information.setHasError(true);
				}
				else if (informationLast.isHasError()) {
					information.setExposure(informationLast.getExposure());
					information.setHasError(true);
				}else {
					if (informationLast.getExposure().equals(informationStrings[3])) {
						Double exposure = Double.parseDouble(informationStrings[3]) + Double.parseDouble(informationStrings[6]);
						information.setExposure(exposure.toString());
					}else {
						information.setExposure(informationLast.getExposure());
						information.setHasError(true);
					}
				}
				informations.add(information);
			}

		}

	}
	
	public static Information findLastInformation(String stockCode,List<Information> list) {
		Information information = null;
		for (Information information2 : list) {
			if (information2.getStockCode().equals(stockCode)) {
				information = information2;
			}
		}
		
		return information;
	}

}

class Information {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	int id;
	String stockCode;
	Date trading_date;
	String factorId;
	String exposure;
	int offset_t;
	double offset_e;
	boolean hasError;

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public int getOffset_t() {
		return offset_t;
	}

	public void setOffset_t(int offset_t) {
		this.offset_t = offset_t;
	}

	public double getOffset_e() {
		return offset_e;
	}

	public void setOffset_e(double offset_e) {
		this.offset_e = offset_e;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Date getTrading_date() {
		return trading_date;
	}

	public void setTrading_date(Date trading_date) {
		this.trading_date = trading_date;
	}

	public String getFactorId() {
		return factorId;
	}

	public void setFactorId(String factorId) {
		this.factorId = factorId;
	}

	public String getExposure() {
		return exposure;
	}

	public void setExposure(String exposure) {
		this.exposure = exposure;
	}

	@Override
	public String toString() {
		return stockCode+" "+simpleDateFormat.format(trading_date)+" "+factorId+" "+ (hasError? "error":exposure) ;
	}
	
	

}
