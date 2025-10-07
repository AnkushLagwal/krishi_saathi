package com.example.krishisaathi.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.krishisaathi.Repository.DriversRepository;
import com.example.krishisaathi.Repository.FarmerRepository;
import com.example.krishisaathi.model.Drivers;
import com.example.krishisaathi.model.Farmers;
import com.example.krishisaathi.service.DriverService;
import com.example.krishisaathi.service.FarmerService;
import com.example.krishisaathi.service.FileUploadService;
import com.example.krishisaathi.service.ImageStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private FarmerRepository farmersRepo;
	
	@Autowired
	private DriversRepository driversRepo;
	
	@Autowired
    private ImageStorageService imageStorageService;
	
	@Autowired
    private FileUploadService fileUploadService;
	
	@Autowired
    private FarmerService farmerService;
	
	@Autowired
    private DriverService driverService;
	
	
	    // Home Page
	    @GetMapping({"/", "/index"})
	    public String home() {
	        return "index";  // loads index.html (templates/index.html)
	    }

	    // About Page
	    @GetMapping("/about")
	    public String about() {
	        return "about"; // loads about.html
	    }

	    // Contact Page
	    @GetMapping("/contact")
	    public String contact() {
	        return "contact"; // loads contact.html
	    }
	    

	    // Farmer Registration Page
	    // Show Registration Form
	    @GetMapping("/farmer-registration")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("farmer", new Farmers());
	        return "farmer-registration"; // farmer-registration.html
	    }

	    // Handle Registration Submit
	    @PostMapping("/farmer-registration")
	    public String registerFarmer(@ModelAttribute("farmer") Farmers farmer,
	                                 @RequestParam("photoFile") MultipartFile file) {
	        try {
	            if (!file.isEmpty()) {
	                // Use the service to save image and get path
	                String imagePath = imageStorageService.saveImage(file);
	                farmer.setPhoto(imagePath);
	            }
	            farmersRepo.save(farmer);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "error"; // you can create an error page
	        }
	        return "redirect:/farmer-login";
	    }
	    
	  //for image uploading
	    @PostMapping("/upload")
	    public String uploadDriverImage(@RequestParam("image") MultipartFile image) {
	        try {
	            String imagePath = imageStorageService.saveImage(image);
	            return "Image uploaded successfully: " + imagePath;
	        } catch (Exception e) {
	            return "Error uploading image: " + e.getMessage();
	        }
	    }
	    
	    
	    

	    // Farmer Login Page
	    // Show login page
	    @GetMapping("/farmer-login")
	    public String showLoginPage() {
	        return "farmer-login"; // thymeleaf template
	    }

	    // Handle login form submission
	    @PostMapping("/farmer-login")
	    public String loginFarmer(@RequestParam("mobileNo") String mobileNo,
	                              @RequestParam("password") String password,
	                              Model model) {

	        // Fetch farmer by mobile number
	        Farmers farmer = farmersRepo.findByMobileNo(mobileNo);

	        if (farmer != null && farmer.getPassword().equals(password)) {
	            // Login successful, redirect to services page
	            return "redirect:/services"; 
	        } else {
	            // Login failed, send error message
	            model.addAttribute("error", "Invalid mobile number or password");
	            return "farmer-login";
	        }
	    }
	    
	    
	    

	    // Driver Registration Page
	    
	    
	    // Show Registration Form
	    @GetMapping("/driver-registration")
	    public String showDriverRegistrationForm(Model model) {
	        model.addAttribute("driver", new Drivers());
	        return "driver-registration";
	    }

	    // Handle Driver Registration Submit
	    @PostMapping("/driver-registration")
	    public String registerDriver(
	            @ModelAttribute Drivers driver,                  // only binds text fields like name, phone
	            @RequestParam("photoFile") MultipartFile photoFile,
	            @RequestParam("rcDocFile") MultipartFile rcDocFile,
	            @RequestParam("insuranceDocFile") MultipartFile insuranceDocFile
	    ) {
	        try {
	            // Save Photo
	            if (!photoFile.isEmpty()) {
	                String photoPath = fileUploadService.saveFile(photoFile);
	                driver.setPhoto(photoPath);
	            }

	            // Save RC Document
	            if (!rcDocFile.isEmpty()) {
	                String rcPath = fileUploadService.saveFile(rcDocFile);
	                driver.setRcDoc(rcPath);
	            }

	            // Save Insurance Document
	            if (!insuranceDocFile.isEmpty()) {
	                String insurancePath = fileUploadService.saveFile(insuranceDocFile);
	                driver.setInsuranceDoc(insurancePath);
	            }

	            // Save Driver entity
	            driverService.saveDriver(driver);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return "error"; // create error.html page for errors
	        }

	        return "redirect:/driver-login"; // redirect after successful registration
	    }

	    // Driver Login Page
	 // Show login page
	    @GetMapping("/driver-login")
	    public String showDriverLoginPage() {
	        return "driver-login"; // thymeleaf template
	    }

	    // Handle login form submission
	    @PostMapping("/driver-login")
	    public String loginDriver(@RequestParam("mobile") String mobile,
	                              @RequestParam("password") String password,
	                              HttpSession session,
	                              Model model) {

	        Drivers driver = driversRepo.findByMobile(mobile);

	        if (driver != null && driver.getPassword().equals(password)) {
	            // Save driver in session
	            session.setAttribute("loggedDriver", driver);

	            return "redirect:/driver-dashboard";
	        } else {
	            model.addAttribute("error", "Invalid mobile number or password");
	            return "driver-login";
	        }
	    }


	    
	    
	    // Driver Dashboard Page
	    @GetMapping("/driver-dashboard")
	    public String driverDashboard(HttpSession session, Model model) {
	        Drivers driver = (Drivers) session.getAttribute("loggedDriver");

	        if (driver == null) {
	            return "redirect:/driver-login";
	        }

	        model.addAttribute("driver", driver);
	        model.addAttribute("totalDrivers", driverService.countAllDrivers());
	        model.addAttribute("totalVehicles", driverService.countUniqueVehicles());
	        model.addAttribute("totalLocations", driverService.countUniqueDistricts());
	        model.addAttribute("availableDrivers", driverService.countAvailableDrivers());
	        model.addAttribute("drivers", driverService.getAllDrivers());

	        return "driver-dashboard";
	    }



	    // Services Page
	    @GetMapping("/services")
	    public String services(Model model, Principal principal) {

	        // 1. Add logged-in farmer for navbar/profile dropdown
	        if (principal != null) {
	            Farmers farmer = farmersRepo.findByEmail(principal.getName());
	            if (farmer != null) {
	                model.addAttribute("farmer", farmer);
	            }
	        }

	        // 2. Add all registered farmers for the section
	        List<Farmers> farmersList = farmerService.getAllFarmers();
	        model.addAttribute("farmers", farmersList);

	        return "services"; // loads services.html
	    }
	    @PostMapping("/services") //for showing the farmer list in service page in our farmers section
	    public String showServicesPage(Model model) {
	        List<Farmers> farmers = farmerService.getAllFarmers(); // fetch from DB
	        model.addAttribute("farmers", farmers);
	        return "services"; // your services.html template
	    }




	    // More Page (if you need)
	    @GetMapping("/more")
	    public String more() {
	        return "more"; // loads more.html
	    }
	    
	    
	    @GetMapping("/crop-guidance")
	    public String cropGuidance() {
	        return "crop-guidance"; // loads crop-guidance
	    }
	    @GetMapping("/market-price-analysis")
	    public String marketPriceAnalysis() {
	        return "market-price-analysis"; // loads market-price-analysis
	    }
	    @GetMapping("/transport-facilities")
	    public String transportFacilities() {
	        return "transport-facilities"; // loads transport-facilities
	    }
	    @GetMapping("/profit-calculator")
	    public String profitCalculator() {
	        return "profit-calculator"; // loads profit-calculator
	    }
	


}
