package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("${api_version}")
@RolesAllowed("ADMIN")
public class SentenceController
{
    private final SentenceService sentenceService;

    @Autowired
    public SentenceController(SentenceService sentenceService)
    {
        this.sentenceService = sentenceService;
    }
}
