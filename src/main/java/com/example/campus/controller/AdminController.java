package com.example.campus.controller;

import com.example.campus.entity.Event;
import com.example.campus.entity.Student;
import com.example.campus.entity.EventJoin;
import com.example.campus.service.EventService;
import com.example.campus.service.StudentService;
import com.example.campus.service.EventJoinService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventService eventService;
    private final StudentService studentService;
    private final EventJoinService eventJoinService;
    private final String uploadDir;

    public AdminController(EventService eventService, StudentService studentService, EventJoinService eventJoinService,
                           @Value("${event.upload.dir}") String uploadDir) {
        this.eventService = eventService;
        this.studentService = studentService;
        this.eventJoinService = eventJoinService;
        this.uploadDir = uploadDir;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("events", eventService.getAllEvents());
        return "admin-dashboard";
    }

    @GetMapping("/add-event")
    public String addEventPage(HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        return "add-event";
    }

    @PostMapping("/add-event")
    public String addEvent(@ModelAttribute Event event,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           @RequestParam(value = "imageUrl", required = false) String imageUrl,
                           HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";

        String resolvedImage = resolveImagePath(imageFile, imageUrl, null);
        event.setImageUrl(resolvedImage);
        eventService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit-event/{id}")
    public String editEventPage(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "edit-event";
    }

    @PostMapping("/edit-event/{id}")
    public String editEvent(@PathVariable Long id,
                            @ModelAttribute Event eventDetails,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            @RequestParam(value = "imageUrl", required = false) String imageUrl,
                            HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        Event event = eventService.getEventById(id);
        if (event != null) {
            event.setTitle(eventDetails.getTitle());
            event.setDescription(eventDetails.getDescription());
            String resolvedImage = resolveImagePath(imageFile, imageUrl, event.getImageUrl());
            event.setImageUrl(resolvedImage);
            event.setDate(eventDetails.getDate());
            event.setTime(eventDetails.getTime());
            event.setDay(eventDetails.getDay());
            event.setLocationAddress(eventDetails.getLocationAddress());
            event.setMapIframe(eventDetails.getMapIframe());
            event.setPaymentAmount(eventDetails.getPaymentAmount());
            eventService.saveEvent(event);
        }
        return "redirect:/admin/dashboard";
    }

    private String resolveImagePath(MultipartFile imageFile, String imageUrl, String existingImageUrl) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String originalName = Path.of(imageFile.getOriginalFilename()).getFileName().toString();
                if (originalName == null || originalName.isBlank()) {
                    originalName = "uploaded-image";
                }
                String fileName = System.currentTimeMillis() + "_" + originalName;
                Path destination = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                return "/uploads/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            return imageUrl.trim();
        }
        return existingImageUrl;
    }

    @GetMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        
        eventJoinService.deleteAllByEventId(id);
        eventService.deleteEvent(id);
        
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/students")
    public String viewStudents(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        model.addAttribute("students", studentService.getAllStudents());
        return "admin-students";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        
        eventJoinService.deleteAllByStudentId(id);
        studentService.deleteStudent(id);
        
        return "redirect:/admin/students";
    }

    @GetMapping("/edit-student/{id}")
    public String editStudentPage(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }

    @PostMapping("/edit-student/{id}")
    public String editStudent(@PathVariable Long id, @ModelAttribute Student studentDetails, HttpSession session) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        Student student = studentService.getStudentById(id);
        if (student != null) {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setDepartment(studentDetails.getDepartment());
            student.setNumber(studentDetails.getNumber());
            student.setBasicDetails(studentDetails.getBasicDetails());
            student.setVerified(studentDetails.isVerified());
            studentService.updateStudent(student);
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/event-participants/{eventId}")
    public String viewParticipants(@PathVariable Long eventId, HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        List<EventJoin> joins = eventJoinService.getByEventId(eventId);
        Event event = eventService.getEventById(eventId);
        
        model.addAttribute("event", event);
        model.addAttribute("joins", joins);
        return "admin-participants";
    }

    @GetMapping("/participations")
    public String viewAllParticipants(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        List<EventJoin> joins = eventJoinService.getAll();
        model.addAttribute("joins", joins);
        return "admin-all-participations";
    }
}
