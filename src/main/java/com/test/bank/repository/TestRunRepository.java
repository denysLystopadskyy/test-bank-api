package com.test.bank.repository;

import com.test.bank.model.TestRun;
import org.springframework.data.repository.CrudRepository;

public interface TestRunRepository extends CrudRepository<TestRun, Long> {
}
