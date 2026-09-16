package dev.Tridip.HomeService.service;

import org.springframework.stereotype.Service;
import dev.Tridip.HomeService.repository.UserRepo;
import dev.Tridip.HomeService.dto.auth.LoginReqDto;
import dev.Tridip.HomeService.dto.auth.LoginResDto;
import dev.Tridip.HomeService.dto.auth.SignUpReqDto;
import dev.Tridip.HomeService.model.User;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final AdtLogService adt_log_srvc;
    public AuthService(UserRepo userRepo, AdtLogService adt_log_srvc){
        this.userRepo = userRepo;
        this.adt_log_srvc = adt_log_srvc;
    }
    public Boolean signUp(SignUpReqDto req){
       User user = userRepo.findByMail(req.getEmail());
       if(user!=null){
        return false;
       }
       
       User newUser = new User(
        null,
        req.getName(),
        req.getUsername(),
        req.getPassword(),
        req.getEmail(),
        req.getPhone_number(),
        "USER",
        req.getAddress(),
        null,
        true
       );
       System.out.println(newUser.toString());
       userRepo.create(newUser);
       adt_log_srvc.createAuditLog("users", "CREATE", newUser.getId(), newUser.getId(), null, newUser);
       return true;
    }

    public LoginResDto logIn(LoginReqDto req){
        User user = userRepo.findByMail(req.getEmail());
        if(user == null || !user.getPassword().equals(req.getPassword())){
            return null;
        }
        // create jwt token and store in cookie
        return new LoginResDto(user.getId(), user.getName(), user.getRole(), user.getUsername());
    }
}
