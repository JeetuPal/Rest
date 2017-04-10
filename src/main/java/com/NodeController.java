package com;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.res.NodeResponse;

@RestController
public class NodeController {
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
	@RequestMapping(value = "/greeting", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

	@RequestMapping(value = "/otp", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponse getOtp(@RequestParam(value="mobile", defaultValue="World") String name) {
        return new NodeResponse();
    }
	
	@RequestMapping(value = "/payment-modes", method=RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponse updatePreferredMode(@RequestParam(value="preferred_mode", defaultValue="xyz") String name) {
		System.out.println("Payment-modes: "+ name);
        return new NodeResponse();
    }
	
	@RequestMapping(value = "/{userId}/**", method = RequestMethod.PUT)
	public void serveFile(@PathVariable("userId") long userId, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("userid" + userId);
		System.out.println(request.getParameter("first_name"));
		System.out.println(request.getParameter("last_name"));
		System.out.println(request.getParameter("phone"));
		System.out.println(request.getParameter("emergency_contact"));
		System.out.println(request.getParameter("referral_code_used"));
	}
	
	@RequestMapping(value="/drivers/init", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String init() {
		String res = "{\"code\":1001,\"error\":false,\"message\":\"Successful\",\"appVersionChecks\":{\"code\":9900,\"error\":false,\"message\":\"Perfecto!\",\"appUrl\":\"https://play.google.com/store/apps/details?id=taxi.baxi.driverapp\"},\"initData\":{\"balance\":0,\"dailyEarning\":0,\"dailyTripCount\":0,\"dailyBonus\":0,\"driverId\":400,\"bike\":null,\"appointmentTimeout\":23,\"showTopupOptions\":false,\"allowedTopups\":[100],\"overdraftLimit\":200,\"showTripStartOtp\":false,\"vehicle\":{},\"appointments\":[]},\"flags\":{\"requireProfile\":false,\"requireDocuments\":false,\"requireVehicle\":false}}";
		return res;
	}
//	GET /vehicles/init - Expected response JSON
	@RequestMapping(value="/vehicles/init", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getVehicle() {
		return "{ \"code\": 1001, \"error\": false, \"message\": \"Successful\", \"vehicles\": [{ \"type\": \"Bike\", \"list\": [{ \"make\": \"BAJAJ\", \"models\": [\"CT 100\", \"PLATINA\", \"DISCOVER\", \"PULSAR\"] }, { \"make\": \"TVS\", \"models\": [\"STAR SPORTS\", \"WEGO\", \"JUPITER\", \"Sport E S\", \"Star City\", \"Apache\", \"SPORT 100\", \"SPORT 100\", \"PHOENIX\"] }, { \"make\": \"MAHINDRA\", \"models\": [\"CENTURO\", \"ROCKSTAR\"] }, { \"make\": \"HERO\", \"models\": [\"HF DELUXE\", \"SPLENDOR PRO\", \"iSMART\"] }, { \"make\": \"HONDA\", \"models\": [\"CD 110 DREAM\", \"LIVO\", \"Activa 3G\", \"Dream Yuga\", \"DREAM NEO\", \"SHINE\"] }, { \"make\": \"YAMAHA\", \"models\": [\"FASCINO\"] }, { \"make\": \"ROYAL ENFIELD\", \"models\": [\"BULLET\"] }] }, { \"type\": \"Car\", \"list\": [{ \"make\": \"Maruti\", \"models\": [\"Swift Dezire\", \"Alto\", \"Activa 3G\", \"Dream Yuga\", \"DREAM NEO\", \"SHINE\"] }, { \"make\": \"Tata\", \"models\": [\"Indica\", \"Qualis\"] }, { \"make\": \"Mercedes\", \"models\": [\"Benz\"] }] }] }";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String post(@RequestHeader(value ="employee") String employee,
			@RequestHeader(value="employeeid") String id, @RequestBody String json) {
		System.out.println("employee: " + employee);
		System.out.println("employeeId: " + id);
	    System.out.println("Json data: " + json);
//	    pj = mapper.readValue(json, POJO.class);

	    //do some things with json, put some header information in json
	    return "{\"name\": \"tina\"}";
	}

	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				
				File dir = new File("~/uploadFile" + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}
	
	@RequestMapping(value="/sandip", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public NodeResponse sandip() {
		return new NodeResponse();
	}
}

	