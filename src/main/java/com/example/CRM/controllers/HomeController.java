package com.example.CRM.controllers;

import com.example.CRM.models.Courses;
import com.example.CRM.models.Operators;
import com.example.CRM.models.Request;
import com.example.CRM.repos.CourseRepo;
import com.example.CRM.repos.OperatorRepo;
import com.example.CRM.repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private OperatorRepo operatorRepo;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<Request> requests = requestRepo.findAll();
        model.addAttribute("requests", requests);
        return "index";
    }

    @GetMapping(value = "/open")
    public String open(Model model) {
        List<Request> openrequests = requestRepo.limit();
        model.addAttribute("openrequests", openrequests);
        return "open";
    }

    @GetMapping(value = "/closed")
    public String closed(Model model){
        List<Request> closedRequests = requestRepo.getAllByHandledTrue();
        model.addAttribute("closedrequests", closedRequests);
        return "closed";
    }

    @GetMapping(value = "/newrequest")
    public String newRequest(Model model) {
        List<Courses> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "newrequest";
    }

    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id") long id,
                          Model model){
        Request request = requestRepo.getReferenceById(id);
        List<Operators> operators = operatorRepo.findAll();
        List<Operators> currentOperators = request.getOperators();
        model.addAttribute("currentoperators", currentOperators);
        model.addAttribute("operators", operators);
        model.addAttribute("request", request);
        return "details";
    }

    @PostMapping(value = "/addrequest")
    public String addRequest(@RequestParam(name = "full_name") String fullName,
                             @RequestParam(name = "course_id") Long course_id,
                             @RequestParam(name = "phone") String phone,
                             @RequestParam(name = "comment") String comment){
        Courses course = courseRepo.findById(course_id).orElse(null);

        if(course != null) {
            Request request = new Request();
            request.setUserName(fullName);
            request.setHandled(false);
            request.setPhone(phone);
            request.setComment(comment);
            request.setCourse(course);
            requestRepo.save(request);
            return "redirect:/";
        }

        return "redirect:/error404";
    }

    @GetMapping(value = "/error404")
    public String error(){
        return "error404";
    }

    @PostMapping(value = "/handle")
    public String handleRequest(@RequestParam(name = "request_id") Long id,
                                @RequestParam(name = "handler") List<Long> operator_id){
        List<Operators> operators = operatorRepo.findAllById(operator_id);
        Request request = requestRepo.getReferenceById(id);
        request.setOperators(operators);
        request.setHandled(true);
        requestRepo.save(request);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteoperatorfromrequest")
    public String deleteOperatorFromRequest(@RequestParam(name = "request_id") Long id,
                                            @RequestParam(name = "handler_id") Long handler_id){
        Request request = requestRepo.getReferenceById(id);
        Operators operator = operatorRepo.getReferenceById(handler_id);
        request.getOperators().remove(operator);
        requestRepo.save(request);
        return "redirect:/details/" + id;
    }

    @PostMapping(value = "/deleterequest")
    public String deleteRequest(@RequestParam(name = "id") Long id){
        Request request = requestRepo.getReferenceById(id);
        request.getOperators().clear();
        requestRepo.save(request);
        requestRepo.delete(request);
        return "redirect:/";
    }

}