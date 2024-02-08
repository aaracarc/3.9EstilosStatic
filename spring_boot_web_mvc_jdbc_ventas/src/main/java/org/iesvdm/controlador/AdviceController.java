package org.iesvdm.controlador;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AdviceController {

    //Excepciones personalizadas o concretas
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception exception) {

        model.addAttribute("traza", exception.getMessage());

        return "error";
    }

    //Excepciones que se escapen
    @ExceptionHandler(RuntimeException.class)
    public String handleAllUncaughtException(Model model, RuntimeException exception){

        model.addAttribute("traza", exception.getMessage());

        return "error";
    }
}