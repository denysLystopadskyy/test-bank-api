package com.test.bank.service;

import com.test.bank.model.TestCase;
import com.test.bank.model.TestRun;
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.repository.TestRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    public Long add (TestRun testRun) {
        return testRunRepository.save(testRun).getId();
    }

    public Iterable<TestRun> getAllRuns () {
        return testRunRepository.findAll();
    }

    public Optional<TestRun> findRunById (Long id) {
        return testRunRepository.findById(id);
    }

    public Long add (List<Long> testCaseIds) {
        List<TestCase> testCases = (List<TestCase>) testCaseRepository.findAllById(testCaseIds);
        TestRun testRun = new TestRun().setTestCases(testCases);
        return testRunRepository.save(testRun).getId();

    }
}
