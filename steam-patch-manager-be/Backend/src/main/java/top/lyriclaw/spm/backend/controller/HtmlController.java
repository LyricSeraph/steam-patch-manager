package top.lyriclaw.spm.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@RequestMapping("")
@Slf4j
public class HtmlController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/index.html").forward(request, response);
    }

    @RequestMapping(value = "/html/**", method = RequestMethod.GET)
    void all(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        request.getRequestDispatcher(uri.replace("/html", "/dist")).forward(request, response);
    }

}
