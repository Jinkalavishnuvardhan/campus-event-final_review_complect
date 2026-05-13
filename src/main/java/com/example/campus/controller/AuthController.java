package com.example.campus.controller;

import com.example.campus.entity.Admin;
import com.example.campus.entity.Student;
import com.example.campus.service.AdminService;
import com.example.campus.service.StudentService;
import com.example.campus.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
public class AuthController {
    
    private final StudentService studentService;
    private final AdminService adminService;
    private final EmailService emailService;
    
    public AuthController(StudentService studentService, AdminService adminService, EmailService emailService) {
        this.studentService = studentService;
        this.adminService = adminService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/help")
    public String helpPage() { return "help"; }

    @GetMapping("/contact")
    public String contactPage() { return "contact"; }

    // STUDENT AUTH
    @GetMapping("/student/login")
    public String studentLoginPage() {
        return "student-login";
    }

    @PostMapping("/student/login")
    public String studentLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Student student = studentService.login(email, password);
        if (student != null) {
            if (!student.isVerified()) {
                model.addAttribute("error", "Please verify your email before logging in.");
                return "student-login";
            }
            session.setAttribute("studentId", student.getId());
            session.setAttribute("studentName", student.getName());
            return "redirect:/student/dashboard";
        }
        model.addAttribute("error", "Invalid email or password");
        return "student-login";
    }

    @GetMapping("/student/register")
    public String studentRegisterPage() {
        return "student-register";
    }

    @PostMapping("/student/register")
    public String studentRegister(@ModelAttribute Student student, @RequestParam("otp") String otp, HttpSession session, Model model) {
        // Check if email already exists
        Student existingStudent = studentService.findByEmail(student.getEmail());
        if(existingStudent != null) {
            model.addAttribute("error", "Email already registered. Please login.");
            return "student-register";
        }

        // Validate email verification from session
        Boolean isVerified = (Boolean) session.getAttribute("verified_" + student.getEmail());
        if (isVerified == null || !isVerified) {
            model.addAttribute("error", "Please verify your email before registering.");
            return "student-register";
        }

        // Email is verified, register the student
        student.setVerified(true);
        studentService.register(student);
        
        // Clear session attributes
        session.removeAttribute("otp_" + student.getEmail());
        session.removeAttribute("verified_" + student.getEmail());
        
        model.addAttribute("msg", "Registration successful! You can now login.");
        return "student-login";
    }

    @PostMapping("/student/verify-otp-ajax")
    @ResponseBody
    public ResponseEntity<String> verifyOtpAjax(@RequestParam String email, @RequestParam String otp, HttpSession session) {
        String sessionOtp = (String) session.getAttribute("otp_" + email);
        
        if (sessionOtp != null && sessionOtp.equals(otp)) {
            session.setAttribute("verified_" + email, true);
            return ResponseEntity.ok("Verified");
        }
        return ResponseEntity.badRequest().body("Invalid OTP");
    }

    @PostMapping("/student/send-otp-ajax")
    @ResponseBody
    public ResponseEntity<String> sendOtpAjax(@RequestParam String email, HttpSession session) {
        // Check if email already exists
        Student existingStudent = studentService.findByEmail(email);
        if(existingStudent != null) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        String otp = emailService.generateOtp();
        session.setAttribute("otp_" + email, otp);
        
        try {
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            // Because we added the fallback in EmailService, this shouldn't throw,
            // but just in case, we return an error message
            return ResponseEntity.status(500).body("Failed to send OTP email.");
        }
    }

    @GetMapping("/student/verify-otp")
    public String verifyOtpPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/student/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        Student student = studentService.findByEmail(email);
        if (student != null && otp.equals(student.getOtp())) {
            student.setVerified(true);
            student.setOtp(null); // Clear OTP after verification
            studentService.updateStudent(student);
            model.addAttribute("msg", "Email verified successfully! You can now login.");
            return "student-login";
        }
        model.addAttribute("error", "Invalid OTP. Please try again.");
        model.addAttribute("email", email);
        return "verify-otp";
    }

    @GetMapping("/student/logout")
    public String studentLogout(HttpSession session) {
        session.removeAttribute("studentId");
        session.removeAttribute("studentName");
        return "redirect:/";
    }

    // ADMIN AUTH
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("adminId", admin.getId());
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Invalid username or password");
        return "admin-login";
    }
    


    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("adminId");
        return "redirect:/";
    }
}
