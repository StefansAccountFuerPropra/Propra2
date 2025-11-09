package return_werte;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ReturnTypeController {


  @GetMapping("/1")
  public @ResponseBody String direkteRückgabe() {
    return HTML_DOKUMENT;
  }

  @GetMapping("/1b")
  public ResponseEntity<String> responseEntity() {
    return new ResponseEntity<>(HTML_DOKUMENT, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
  }

  @GetMapping("/2")
  public String templateDatei(Model model) {
    model.addAttribute("seite", "2");
    return "main";
  }

  @GetMapping("/2b")
  @ResponseStatus(HttpStatus.CREATED)
  public String statusCode(Model model) {
    model.addAttribute("seite", "2b");
    return "main";
  }

  @GetMapping("/3")
  public RedirectView redirectView() {
    System.out.println("Redirect 3");
    return new RedirectView("/2");
  }

  @GetMapping("/3b")
  public String redirectViewString() {
    System.out.println("Redirect 3b");
    return "redirect:/2";
  }

  @GetMapping("/3c")
  public String forwardString() {
    System.out.println("Redirect 3c");
    return "forward:/2";
  }

  @GetMapping("/4")
  public ModelAndView modelAndView() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("main");
    modelAndView.setStatus(HttpStatus.ACCEPTED);
    modelAndView.addObject("seite","4");
    return modelAndView;
  }




  public static final String HTML_DOKUMENT = """
      <!DOCTYPE html>
      <html lang="en">
      <head>
          <meta charset="UTF-8">
          <title>Hauptseite</title>
      </head>
      <body>
             
      <h1>Rückgabetypen</h1>
             
      <p>Direkte Rückgabe</p>
             
      </body>
      </html>
      """;

}