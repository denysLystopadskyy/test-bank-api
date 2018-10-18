package com.test.bank.tests.controller;

import com.test.bank.TestCaseStatus;
import com.test.bank.model.Launch;
import com.test.bank.model.LaunchData;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.service.LaunchService;
import com.test.bank.service.ProjectsService;
import com.test.bank.service.TestCasesService;
import com.test.bank.tests.web.IntegrationTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LaunchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectsService projectsService;

    @MockBean
    private TestCasesService testCasesService;

    @MockBean
    private TestCaseRepository testCaseRepository;

    @MockBean
    private LaunchService launchService;

    private Project mockedProject;
    private Launch mockedLaunch;

    private TestCase testCase;
    private TestCase testCase2;
    private List<TestCase> testCases;
    private List<Long> testCaseIds;

    @Before
    public void setUp () {
        mockedProject = new Project();
        mockedProject.setId(41L);
        mockedProject.setName("Test Project");
        mockedProject.setDeleted(false);

        when(projectsService.findProjectById(mockedProject.getId())).thenReturn(Optional.of(mockedProject));

        testCase = new TestCase();
        testCase.setId(51L);
        testCase.setName("Mocked Test Case 1.");
        testCase.setStatus(TestCaseStatus.PASSED.name());

        testCase2 = new TestCase();
        testCase2.setId(52L);
        testCase2.setName("Mocked Test Case 2.");
        testCase2.setStatus(TestCaseStatus.PASSED.name());

        testCaseIds = Arrays.asList(testCase.getId(), testCase2.getId());
        testCases = Arrays.asList(testCase, testCase2);

        when(testCasesService.findTestCaseById(testCase.getId())).thenReturn(Optional.of(testCase));
        when(testCasesService.findTestCaseById(testCase2.getId())).thenReturn(Optional.of(testCase2));
        when(testCaseRepository.findAllById(testCaseIds))
                .thenReturn(testCases);

        mockedLaunch = new Launch()
                .setId(61L)
                .setStatus(true)
                .setTestCases(Collections.singletonList(testCase2));
    }

    @Test
    public void testCanCreateLaunch () throws Exception {

        LaunchData launchData = new LaunchData()
                .setStatus(true)
                .setTestCasesIds(testCaseIds);

        when(launchService.add(launchData)).thenReturn(mockedLaunch.getId());

        mockMvc.perform(
                post("/projects/{projectId}/launch", mockedProject.getId(), launchData)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(IntegrationTestUtils.toJson(launchData))
        )
                .andExpect(status().isCreated())
                .andExpect(content().json(String.format("{\"id\":%d}", mockedLaunch.getId())))
                .andDo(print());
    }
}
