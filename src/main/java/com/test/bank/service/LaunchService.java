package com.test.bank.service;

import com.test.bank.model.Launch;
import com.test.bank.model.LaunchData;
import com.test.bank.model.TestCase;
import com.test.bank.repository.LaunchRepository;
import com.test.bank.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    public Long add (Launch launch) {
        return launchRepository.save(launch).getId();
    }

    public Optional<Launch> findById (Long id) {
        return launchRepository.findById(id);
    }

    public Long add (LaunchData LaunchData) {
        List<TestCase> testCases = (List<TestCase>) testCaseRepository.findAllById(LaunchData.getTestCasesIds());
        Launch launch = new Launch()
                .setStatus(LaunchData.isStatus())
                .setTestCases(testCases);
        return add(launch);

    }
}
