/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.ymmihw.algorithms.optaplanner.optional.domain.CloudComputerStrengthComparator;
import com.ymmihw.algorithms.optaplanner.optional.domain.CloudProcessDifficultyComparator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PlanningEntity(difficultyComparatorClass = CloudProcessDifficultyComparator.class)
@XStreamAlias("CloudProcess")
@Getter
@Setter
@NoArgsConstructor
public class CloudProcess extends AbstractPersistable {
  private int requiredCpuPower; // in gigahertz
  private int requiredMemory; // in gigabyte RAM
  private int requiredNetworkBandwidth; // in gigabyte per hour

  // Planning variables: changes during planning, between score calculations.
  private CloudComputer computer;

  @PlanningVariable(valueRangeProviderRefs = {"computerRange"},
      strengthComparatorClass = CloudComputerStrengthComparator.class)
  public CloudComputer getComputer() {
    return computer;
  }

  public String details() {
    return "CloudProcess [id=" + id + ", requiredMemory=" + requiredMemory
        + ", requiredNetworkBandwidth=" + requiredNetworkBandwidth + ", computer=" + computer
        + ", requiredCpuPower=" + requiredCpuPower + "]";
  }

  // ************************************************************************
  // Complex methods
  // ************************************************************************

  public int getRequiredMultiplicand() {
    return requiredCpuPower * requiredMemory * requiredNetworkBandwidth;
  }

}
