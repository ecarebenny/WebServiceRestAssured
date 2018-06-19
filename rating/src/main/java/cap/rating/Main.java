package cap.rating;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import ExcelUtilities.ExcelUtils;
import WebServiceUtilities.GetVINdetails;
import WebServiceUtilities.Webservices;

public class Main {
	
	static String inputexcelfile;
	static List vininput = new ArrayList();
	static Response response;

	static Map<String,List> hm = new HashMap<String,List>();
	

	public static void main(String[] args) throws Exception {
		
		inputexcelfile = System.getProperty ("user.dir") + File.separator + "InputExcelFile\\SafeAuto_GW_Rating_Accelerator %28Autosaved%29.xlsm";
		
		for(int i = 3 ; i<13 ; i++)
		{
			String l_value = ExcelUtils.ExcelRead(inputexcelfile, 40, i, 1);
			if(!l_value.isEmpty())
			{
				System.out.println(l_value);
				vininput.add(l_value);
				
			}
		}
		
		String url = "https://hqgwpcodevb01.safeautonet.net/pc/service/edge/custom/vinvalidation";
		
		System.out.println(url);
		for(int j=0;j<vininput.size();j++)
		{
	//	Gson gson = new GsonBuilder().create();	
	//	GetVINdetails getvindetail ;
		response = Webservices.PostCallWithHeader(url,"{\"id\":\"16\",\"method\":\"getVinDetailsByVin\",\"params\":[{\"vin\" : \""    +vininput.get(j)+    "\"}],\"jsonrpc\":\"2.0\"}");
		if(response.getStatusCode() == 200){
	//		getvindetail = gson.fromJson(response.getBody().asString(), GetVINdetails.class);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List vinvalues = new ArrayList();
			vinvalues.add(jsonPathEvaluator.get("result.vehicleResponse.securityTypeCode"));
			vinvalues.add(jsonPathEvaluator.get("result.vehicleResponse.securityTypeDescription"));
			
			String  symbol = (jsonPathEvaluator.get("result.rapaGroup.rapa.bodilyInjury")).toString() +"|" + jsonPathEvaluator.get("result.rapaGroup.rapa.collision") +"|" + jsonPathEvaluator.get("result.rapaGroup.rapa.comprehensive")+"|"  + jsonPathEvaluator.get("result.rapaGroup.rapa.medicalPay")+"|"  + jsonPathEvaluator.get("result.rapaGroup.rapa.personalInjuryProtection")+"|"  + jsonPathEvaluator.get("result.rapaGroup.rapa.propertyDamage") ;
			vinvalues.add(symbol);
			
			hm.put((String) vininput.get(j), vinvalues);
						
		System.out.println(response.asString());

		}
		}
		for(int k=0 ; k<hm.size(); k++)
		{
			for (int l = 0; l< hm.entrySet().size();l++)
			{
				if(l == 0) {
			ExcelUtils.ExcelWrite(inputexcelfile, 88, k+3, hm.get(vininput.get(k)).get(l), 1);
			        }
				if(l == 1) {
					ExcelUtils.ExcelWrite(inputexcelfile, 87, k+3, hm.get(vininput.get(k)).get(l), 1);
					}
				if(l == 2) {
					ExcelUtils.ExcelWrite(inputexcelfile, 83, k+3, hm.get(vininput.get(k)).get(l), 1);
					}
			}
		}
	}

	}

