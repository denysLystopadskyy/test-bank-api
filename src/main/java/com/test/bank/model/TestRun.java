package com.test.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class TestRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    boolean result;

    @OneToMany()
    @JoinColumn(name = "testRunId")
    List<TestCase> testCases;
}
