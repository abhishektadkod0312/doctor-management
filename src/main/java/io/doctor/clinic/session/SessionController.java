package io.doctor.clinic.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionController {
    @Autowired
    SessionUseCase sessionUseCase;

    @PostMapping("/session")
    public void create(@RequestBody SessionRequest sessionRequest){
        sessionUseCase.createSession(sessionRequest.getPatient(), sessionRequest);
    }

    @GetMapping("/sessions")
    public List<Session> getAll(){
        return sessionUseCase.sessionList();
    }
}
