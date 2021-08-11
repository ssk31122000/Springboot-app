package com.exproject.conferencedemo.controllers;

import com.exproject.conferencedemo.models.Session;
import com.exproject.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        var session = sessionRepository.findById(id).get();
        return session;
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        // check children records before delete
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.findById(id).get();
        BeanUtils.copyProperties(session,existingSession,"session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
