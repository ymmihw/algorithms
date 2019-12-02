/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package com.ymmihw.algorithms.optaplanner.domain;

import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoft.HardSoftScoreXStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PlanningSolution
@XStreamAlias("CloudBalance")
@Setter
@NoArgsConstructor
public class CloudBalance extends AbstractPersistable {
  private List<CloudComputer> computerList;

  private List<CloudProcess> processList;

  @XStreamConverter(HardSoftScoreXStreamConverter.class)
  private HardSoftScore score;

  public CloudBalance(long id, List<CloudComputer> computerList, List<CloudProcess> processList) {
    super(id);
    this.computerList = computerList;
    this.processList = processList;
  }

  @ValueRangeProvider(id = "computerRange")
  @ProblemFactCollectionProperty
  public List<CloudComputer> getComputerList() {
    return computerList;
  }

  @PlanningEntityCollectionProperty
  public List<CloudProcess> getProcessList() {
    return processList;
  }

  @PlanningScore
  public HardSoftScore getScore() {
    return score;
  }

  // ************************************************************************
  // Complex methods
  // ************************************************************************

}
