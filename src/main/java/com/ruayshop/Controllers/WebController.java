package com.ruayshop.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ruayshop.Entities.Admin;
import com.ruayshop.Entities.Bill;
import com.ruayshop.Entities.Customer;
import com.ruayshop.Entities.Motorcycle;
import com.ruayshop.Entities.Sell;
import com.ruayshop.Repositories.AdminRepository;
import com.ruayshop.Repositories.BillRepository;
import com.ruayshop.Repositories.CustomerRepository;
import com.ruayshop.Repositories.MotorcycleRepository;
import com.ruayshop.Repositories.SellRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class WebController {
    
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MotorcycleRepository motorcycleRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    SellRepository sellRepository;
    
    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        List<Admin> allAdmin = adminRepository.findAll();
        model.addAttribute("allAdmin", allAdmin);
        return "adminview";
    }
    
    @GetMapping("/editadmin/{id}")
    public String editAdmin(@PathVariable int id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Admin currentAdmin = adminRepository.findById(id).orElse(null);
        model.addAttribute("currentAdmin", currentAdmin);
        return "editadminview";
    }

    @PostMapping("/saveadmin")
    public String saveAdmin(@ModelAttribute("currentAdmin") Admin admin) {
        adminRepository.save(admin);
        //System.out.println(admin.getId());
        return "redirect:/admin";
    }

    @PostMapping("/deleteadmin/{id}")
    public String deleteAdmin(@PathVariable int id) {
        adminRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/newadmin")
    public String newAdmin(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Admin currentAdmin = new Admin();
        model.addAttribute("currentAdmin", currentAdmin);
        return "newadminview";
    }

    @GetMapping("/customer")
    public String viewCustomer(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        List<Customer> allCustomers = customerRepository.findAll();
        model.addAttribute("allCustomers", allCustomers);
        return "customerview";
    }

    @GetMapping("/newcustomer")
    public String newCustomer(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Customer currentCustomer = new Customer();
        model.addAttribute("currentCustomer", currentCustomer);
        return "newcustomerview";
    }

    @PostMapping("/savecustomer")
    public String saveCustomer(@ModelAttribute("currentCustomer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/editcustomer/{id}")
    public String editCustomer(@PathVariable int id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Customer currentCustomer = customerRepository.findById(id).orElse(null);
        model.addAttribute("currentCustomer", currentCustomer);
        return "editcustomerview";
    }

    @PostMapping("/deletecustomer/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerRepository.deleteById(id);
        return "redirect:/customer";
    }

    @GetMapping("/motorcycle")
    public String viewMotorcycle(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        List<Motorcycle> allMotorcycles = motorcycleRepository.findAll();
        model.addAttribute("allMotorcycles", allMotorcycles);
        return "motorcycleview";
    }

    @GetMapping("/newmotorcycle")
    public String newMotorcycle(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Motorcycle currenMotorcycle = new Motorcycle();
        model.addAttribute("currentMotorcycle", currenMotorcycle);
        return "newmotorcycleview";
    }
    
    @PostMapping("/savemotorcycle")
    public String saveMotorcycle(@ModelAttribute("currentMotorcycle") Motorcycle motorcycle) {
        motorcycleRepository.save(motorcycle);
        return "redirect:/motorcycle";
    }

    @GetMapping("/editmotorcycle/{id}")
    public String editMotorcycle(@PathVariable int id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Motorcycle currentMotorcycle = motorcycleRepository.findById(id).orElse(null);
        model.addAttribute("currentMotorcycle", currentMotorcycle);
        return "editmotorcycleview";
    }

    @PostMapping("/deletemotorcycle/{id}")
    public String deleteMotorcycle(@PathVariable int id) {
        motorcycleRepository.deleteById(id);
        return "redirect:/motorcycle";
    }

    @GetMapping("/sell")
    public String sellMotor(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        return "sellview";
    }

    @PostMapping("/createbill")
    public String createBill(
            @RequestParam("customerId") Integer customerId,
            @RequestParam("motorcycleId") List<Integer> motorcycleIds,
            @RequestParam("amount") List<Integer> amounts,
            HttpSession session) {

        // Check if the motorcycleIds and amounts lists are the same size
        if (motorcycleIds.size() != amounts.size()) {
            throw new IllegalArgumentException("Motorcycle IDs and amounts must have the same number of entries.");
        }

        // Assuming an admin with ID 1, you may want to handle admin retrieval more dynamically.
        Admin admin = (Admin) session.getAttribute("admin");
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        Bill bill = new Bill();
        double totalPrice = 0.0;
        bill.setCustomer(customer);
        bill.setAdmin(admin);
        bill.setBillDate(new Date());

        for (int i = 0; i < motorcycleIds.size(); i++) {
            Integer motorcycleId = motorcycleIds.get(i);
            int amount = amounts.get(i);

            // Retrieve the motorcycle entity
            Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).orElseThrow(() -> 
                new IllegalArgumentException("Motorcycle with ID " + motorcycleId + " not found.")
            );

            // Check stock
            if (motorcycle.getStock() < amount) {
                throw new IllegalArgumentException("Not enough stock for motorcycle: " + motorcycle.getModel() + ". Available: " + motorcycle.getStock() + ", Requested: " + amount);
            }

            // Update stock and calculate total price
            motorcycle.decreaseStock(amount);
            totalPrice += motorcycle.getPrice() * amount;
            
            // Save the motorcycle with updated stock
            motorcycleRepository.save(motorcycle);

            // Create a new Sell entry
            Sell sell = new Sell();
            sell.setMotorcycle(motorcycle);
            sell.setAmount(amount);
            bill.addSell(sell);
        }

        // Set total price in the bill
        bill.setTotalPrice(totalPrice);

        // Save the bill
        billRepository.save(bill);
        
        // Redirect to the bills list
        return "redirect:/bills"; 
    }

    @GetMapping("/bills")
    public String viewBills(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        List<Bill> allBills = billRepository.findAll();
        model.addAttribute("bills", allBills);
        return "billsview";
    }

    @PostMapping("/bills/delete")
    public String deleteBill(@RequestParam("id") Integer id) {
        billRepository.deleteById(id); // Call the service to delete the bill
        return "redirect:/bills"; // Redirect back to the bills view
    }

    @GetMapping("/bills/details/{id}")
    public String viewBillDetails(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Bill bill = billRepository.findById(id).orElse(null);
        model.addAttribute("bill", bill);
        return "billDetail"; // Name of the Thymeleaf template for bill details
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login"; // Return the name of your login template (e.g., login.html)
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model, HttpSession session) {
        Admin admin = adminRepository.findByUsername(username);
        //System.out.println(user.getUsername());
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("admin", admin);
            return "redirect:/sell";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return the login template with an error message
        }
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login";
    }
}
