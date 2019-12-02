/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ymmihw.algorithms.optaplanner.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import com.ymmihw.algorithms.optaplanner.domain.CloudBalance;
import com.ymmihw.algorithms.optaplanner.domain.CloudComputer;
import com.ymmihw.algorithms.optaplanner.domain.CloudProcess;
import com.ymmihw.algorithms.optaplanner.persistence.CloudBalancingGenerator;

/**
 * To benchmark this solver config, run {@link CloudBalancingBenchmarkHelloWorld} instead.
 */
public class CloudBalancingHelloWorld {

  public static void main(String[] args) {
    // Build the Solver
    SolverFactory<CloudBalance> solverFactory = SolverFactory.createFromXmlResource(
        "com/ymmihw/algorithms/optaplanner/solver/cloudBalancingSolverConfig.xml");
    Solver<CloudBalance> solver = solverFactory.buildSolver();

    // Load a problem with 400 computers and 1200 processes
    CloudBalance unsolvedCloudBalance = new CloudBalancingGenerator().createCloudBalance(400, 1200);

    // Solve the problem
    CloudBalance solvedCloudBalance = solver.solve(unsolvedCloudBalance);

    // Display the result
    System.out.println("\nSolved cloudBalance with 400 computers and 1200 processes:\n"
        + toDisplayString(solvedCloudBalance));
  }

  public static String toDisplayString(CloudBalance cloudBalance) {
    StringBuilder displayString = new StringBuilder();
    for (CloudProcess process : cloudBalance.getProcessList()) {
      CloudComputer computer = process.getComputer();
      displayString.append("  ").append(process.toString()).append(" -> ")
          .append(computer == null ? null : computer.toString()).append("\n");
    }
    return displayString.toString();
  }

}
