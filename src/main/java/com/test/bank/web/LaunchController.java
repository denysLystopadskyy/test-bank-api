package com.test.bank.web;

import com.test.bank.model.LaunchData;
import com.test.bank.service.LaunchService;
import com.test.bank.service.ProjectsService;
import com.test.bank.service.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class LaunchController {

    @Autowired
    private LaunchService LaunchService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private TestCasesService testCasesService;

    @PostMapping(path = "/projects/{projectId}/launch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTestRun (@PathVariable Long projectId, @RequestBody LaunchData launchData) {
        if (!isProjectPresent(projectId)) {
            return projectIsNotPresent();
        }
        return new ResponseEntity<>(Collections.singletonMap("id", LaunchService.add(launchData)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/projects/{projectId}/launch/{launchId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestRun (@PathVariable Long projectId, @PathVariable Long launchId) {
        if (!isProjectPresent(projectId)) {
            return projectIsNotPresent();
        }
        return new ResponseEntity<>(Collections.singletonMap("launch", LaunchService.findById(launchId)), HttpStatus.OK);
    }

    private ResponseEntity projectIsNotPresent () {
        return new ResponseEntity<>(Collections.singletonMap("error", "Project is not present."), HttpStatus.NOT_FOUND);
    }


    private boolean isProjectPresent (Long projectId) {
        return projectsService.findProjectById(projectId).isPresent();
    }

}
