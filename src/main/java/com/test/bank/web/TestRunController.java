package com.test.bank.web;

import com.test.bank.service.ProjectsService;
import com.test.bank.service.TestCasesService;
import com.test.bank.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRunController {

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private TestCasesService testCasesService;

    @PostMapping(path = "/projects/{projectId}")
    public ResponseEntity createTestRun (@PathVariable Long projectId, @RequestBody List<Long> testCaseIds) {
        if (!projectsService.findProjectById(projectId).isPresent()) {
            return new ResponseEntity<>("Project is not present.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(testRunService.add(testCaseIds), HttpStatus.CREATED);
    }
}
