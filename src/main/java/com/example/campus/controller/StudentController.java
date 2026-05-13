package com.example.campus.controller;

import com.example.campus.entity.Event;
import com.example.campus.entity.EventJoin;
import com.example.campus.entity.Student;
import com.example.campus.service.EventJoinService;
import com.example.campus.service.EventService;
import com.example.campus.service.StudentService;
import com.example.campus.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final EventService eventService;
    private final EventJoinService eventJoinService;
    private final StudentService studentService;
private final EmailService emailService;
    
    @org.springframework.beans.factory.annotation.Value("${event.upload.dir}")
    private String uploadDir;

    public StudentController(EventService eventService, EventJoinService eventJoinService, StudentService studentService, EmailService emailService) {
    this.eventService = eventService;
    this.eventJoinService = eventJoinService;
    this.studentService = studentService;
    this.emailService = emailService;
}


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        List<Event> events = eventService.getAllEvents();
        List<EventJoin> joinedEvents = eventJoinService.getByStudentId(studentId);
        List<Long> joinedEventIds = joinedEvents.stream().map(ej -> ej.getEvent().getId()).collect(Collectors.toList());

        model.addAttribute("events", events);
        model.addAttribute("joinedEventIds", joinedEventIds);
        return "student-dashboard";
    }

    @GetMapping("/event-payment/{eventId}")
    public String paymentPage(@PathVariable Long eventId, HttpSession session, Model model, RedirectAttributes redirectAttrs) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        if (eventJoinService.exists(studentId, eventId)) {
            redirectAttrs.addFlashAttribute("msg", "You have already joined this event!");
            return "redirect:/student/dashboard";
        }

        Event event = eventService.getEventById(eventId);
        Student student = studentService.getStudentById(studentId);

        model.addAttribute("event", event);
        model.addAttribute("student", student);
        return "student-payment";
    }

    @PostMapping("/join/{eventId}")
    public String joinEvent(@PathVariable Long eventId, HttpSession session, RedirectAttributes redirectAttrs) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";

        if (!eventJoinService.exists(studentId, eventId)) {
            Student student = studentService.getStudentById(studentId);
            Event event = eventService.getEventById(eventId);

            if (student != null && event != null) {
                EventJoin join = new EventJoin();
join.setStudent(student);
join.setEvent(event);
join.setPaymentStatus("Paid"); // Automatically mark as paid since they came from payment page
eventJoinService.save(join);
// Send payment confirmation email
emailService.sendPaymentConfirmation(student.getEmail(), event);
redirectAttrs.addFlashAttribute("msg", "Successfully registered for " + event.getTitle() + "!");

            }
        } else {
            redirectAttrs.addFlashAttribute("msg", "You have already joined this event!");
        }
        return "redirect:/student/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";
        model.addAttribute("student", studentService.getStudentById(studentId));
        return "student-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute Student studentDetails,
                                @RequestParam(name = "profileImage", required = false) MultipartFile profileImage,
                                HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setDepartment(studentDetails.getDepartment());
            student.setNumber(studentDetails.getNumber());
            student.setBasicDetails(studentDetails.getBasicDetails());
            if (profileImage != null && !profileImage.isEmpty()) {
                try {
                    java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir).toAbsolutePath();
                    if (!java.nio.file.Files.exists(uploadPath)) {
                        java.nio.file.Files.createDirectories(uploadPath);
                    }
                    String originalName = java.nio.file.Path.of(profileImage.getOriginalFilename()).getFileName().toString();
                    if (originalName == null || originalName.isBlank()) {
                        originalName = "profile-image";
                    }
                    String fileName = System.currentTimeMillis() + "_profile_" + originalName;
                    java.nio.file.Path destination = uploadPath.resolve(fileName);
                    java.nio.file.Files.copy(profileImage.getInputStream(), destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    student.setImageDp("/uploads/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (studentDetails.getImageDp() != null && !studentDetails.getImageDp().trim().isEmpty()) {
                student.setImageDp(studentDetails.getImageDp());
            }
            session.setAttribute("studentName", student.getName());
            studentService.updateStudent(student);
        }
        return "redirect:/student/profile";
    }

    @GetMapping("/profile/delete")
    public String deleteProfile(HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/student/login";
        eventJoinService.deleteAllByStudentId(studentId);
        studentService.deleteStudent(studentId);
        session.invalidate();
        return "redirect:/";
    }
}
