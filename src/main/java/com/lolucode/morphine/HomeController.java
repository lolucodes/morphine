package com.lolucode.morphine;

import com.lolucode.morphine.appuser.AppUser;
import com.lolucode.morphine.appuser.AppUserService;
import com.lolucode.morphine.appuser.AuthenticationService;
import com.lolucode.morphine.reservation.model.Reservation;
import com.lolucode.morphine.reservation.service.ReservationService;
import com.lolucode.morphine.reservation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;

@Controller
@AllArgsConstructor
public class HomeController {

    final UserService userService;

    final AppUserService appUserService;
    final ReservationService reservationService;

    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("pageName", "HomePage");
        return "homepage";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        if(appUser != null) {
            session.setAttribute("appUser", appUser);

            // Empty reservation object in case the user creates a new reservation
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }

        return "index";

    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation, Model model, @SessionAttribute("appUser") AppUser appUser) {
        // Save to DB after updating
        assert appUser != null;
        reservation.setAppUser(appUser);
        reservationService.create(reservation);
        Set<Reservation> userReservations = appUser.getReservations();
        userReservations.add(reservation);
       appUser.setReservations(userReservations);
        userService.update(appUser.getId(), appUser);
        return "redirect:/reservations";

    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return authenticationService.showLoginError(model);
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        return authenticationService.showRegistrationForm(model);
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid AppUser user) {
        return authenticationService.registerNewUser(user);
    }

}
