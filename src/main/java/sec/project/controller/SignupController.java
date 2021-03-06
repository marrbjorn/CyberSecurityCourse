package sec.project.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @PostConstruct
    public void init() {
        signupRepository.save(new Signup("Bjorn Arnleifsson", "28-388"));
        signupRepository.save(new Signup("Torvald Algeirsson", "55-221"));
        signupRepository.save(new Signup("Dagbjort og FlokR", "99-111"));
        signupRepository.save(new Signup("Teppa Pekkanen", "49-10-221-3"));
        Signup signup = new Signup();
        signup.setName("Donald");
        signup.setPhone("Duck");
        signupRepository.save(signup);
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    // We able to remove this "debug" mapping for get access to this page;
    // Or just will add there more protection layers for possibility get page..
    // Or re-change settings under the SecurityConfiguration.java :
    @RequestMapping(value = "/hidden", method = RequestMethod.GET)
    public String loadHidden() {
        return "hidden";
    }

    // We able to do protection for "pages-not-for-all" with more proper design;
    // There is main protection just the "string" under the GET as parameter;
    // And just string, which can be transferred from user's browser.
    // There is enough to be logged 
    //    (but page designed to be visible not for all logged users);    
    @RequestMapping(value = "/fylkr", method = RequestMethod.GET)
    public String loadFylkr(Model model, @RequestParam(required = false) String trick) {
        if (trick.equals("doTheTrick")) {
            model.addAttribute("listeners", signupRepository.findAll());
            return "fylkr";
        } else {
            return "redirect:/form";
        }
    }

    @RequestMapping(value = "/formsrc", method = RequestMethod.GET)
    public String loadSRC(@RequestParam String review) {
        if (review.equals("on")) {
            return "formsrc";
        } else {
            return "redirect:/form";
        }
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String phone) {
        signupRepository.save(new Signup(name, phone));
        return "done";
    }
}