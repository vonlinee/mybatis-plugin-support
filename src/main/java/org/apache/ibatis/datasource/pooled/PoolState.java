/*
 *    Copyright 2009-2024 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.datasource.pooled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Clinton Begin
 */
public class PoolState {

  // This lock does not guarantee consistency.
  // Field values can be modified in PooledDataSource
  // after the instance is returned from
  // PooledDataSource#getPoolState().
  // A possible fix is to create and return a 'snapshot'.
  private final ReentrantLock lock = new ReentrantLock();

  protected PooledDataSource dataSource;

  protected final List<PooledConnection> idleConnections = new ArrayList<>();
  protected final List<PooledConnection> activeConnections = new ArrayList<>();
  protected long requestCount;
  protected long accumulatedRequestTime;
  protected long accumulatedCheckoutTime;
  protected long claimedOverdueConnectionCount;
  protected long accumulatedCheckoutTimeOfOverdueConnections;
  protected long accumulatedWaitTime;
  protected long hadToWaitCount;
  protected long badConnectionCount;

  public PoolState(PooledDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public long getRequestCount() {
    lock.lock();
    try {
      return requestCount;
    } finally {
      lock.unlock();
    }
  }

  public long getAverageRequestTime() {
    lock.lock();
    try {
      return requestCount == 0 ? 0 : accumulatedRequestTime / requestCount;
    } finally {
      lock.unlock();
    }
  }

  public long getAverageWaitTime() {
    lock.lock();
    try {
      return hadToWaitCount == 0 ? 0 : accumulatedWaitTime / hadToWaitCount;
    } finally {
      lock.unlock();
    }
  }

  public long getHadToWaitCount() {
    lock.lock();
    try {
      return hadToWaitCount;
    } finally {
      lock.unlock();
    }
  }

  public long getBadConnectionCount() {
    lock.lock();
    try {
      return badConnectionCount;
    } finally {
      lock.unlock();
    }
  }

  public long getClaimedOverdueConnectionCount() {
    lock.lock();
    try {
      return claimedOverdueConnectionCount;
    } finally {
      lock.unlock();
    }
  }

  public long getAverageOverdueCheckoutTime() {
    lock.lock();
    try {
      return claimedOverdueConnectionCount == 0 ? 0
          : accumulatedCheckoutTimeOfOverdueConnections / claimedOverdueConnectionCount;
    } finally {
      lock.unlock();
    }
  }

  public long getAverageCheckoutTime() {
    lock.lock();
    try {
      return requestCount == 0 ? 0 : accumulatedCheckoutTime / requestCount;
    } finally {
      lock.unlock();
    }
  }

  public int getIdleConnectionCount() {
    lock.lock();
    try {
      return idleConnections.size();
    } finally {
      lock.unlock();
    }
  }

  public int getActiveConnectionCount() {
    lock.lock();
    try {
      return activeConnections.size();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public String toString() {
    lock.lock();
    try {
      String builder = "\n===CONFIGURATION==============================================" +
        "\n jdbcDriver                     " + dataSource.getDriver() +
        "\n jdbcUrl                        " + dataSource.getUrl() +
        "\n jdbcUsername                   " + dataSource.getUsername() +
        "\n jdbcPassword                   " +
        (dataSource.getPassword() == null ? "NULL" : "************") +
        "\n poolMaxActiveConnections       " + dataSource.poolMaximumActiveConnections +
        "\n poolMaxIdleConnections         " + dataSource.poolMaximumIdleConnections +
        "\n poolMaxCheckoutTime            " + dataSource.poolMaximumCheckoutTime +
        "\n poolTimeToWait                 " + dataSource.poolTimeToWait +
        "\n poolPingEnabled                " + dataSource.poolPingEnabled +
        "\n poolPingQuery                  " + dataSource.poolPingQuery +
        "\n poolPingConnectionsNotUsedFor  " + dataSource.poolPingConnectionsNotUsedFor +
        "\n ---STATUS-----------------------------------------------------" +
        "\n activeConnections              " + getActiveConnectionCount() +
        "\n idleConnections                " + getIdleConnectionCount() +
        "\n requestCount                   " + getRequestCount() +
        "\n averageRequestTime             " + getAverageRequestTime() +
        "\n averageCheckoutTime            " + getAverageCheckoutTime() +
        "\n claimedOverdue                 " + getClaimedOverdueConnectionCount() +
        "\n averageOverdueCheckoutTime     " + getAverageOverdueCheckoutTime() +
        "\n hadToWait                      " + getHadToWaitCount() +
        "\n averageWaitTime                " + getAverageWaitTime() +
        "\n badConnectionCount             " + getBadConnectionCount() +
        "\n===============================================================";
      return builder;
    } finally {
      lock.unlock();
    }
  }

}
