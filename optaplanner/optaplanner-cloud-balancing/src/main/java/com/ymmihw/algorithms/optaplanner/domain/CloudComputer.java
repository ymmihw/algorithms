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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XStreamAlias("CloudComputer")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CloudComputer extends AbstractPersistable {
  private static final long serialVersionUID = 1L;
  private int cpuPower; // in gigahertz
  private int memory; // in gigabyte RAM
  private int networkBandwidth; // in gigabyte per hour
  private int cost; // in euro per month

  public CloudComputer(long id, int cpuPower, int memory, int networkBandwidth, int cost) {
    super(id);
    this.cpuPower = cpuPower;
    this.memory = memory;
    this.networkBandwidth = networkBandwidth;
    this.cost = cost;
  }

  // ************************************************************************
  // Complex methods
  // ************************************************************************

  public int getMultiplicand() {
    return cpuPower * memory * networkBandwidth;
  }
}
