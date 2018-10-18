package com.test.bank.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LaunchData {

    boolean status;

    List<Long> testCasesIds;

}
