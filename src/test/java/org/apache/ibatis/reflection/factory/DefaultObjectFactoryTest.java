/*
 *    Copyright 2009-2025 the original author or authors.
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
package org.apache.ibatis.reflection.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.ibatis.reflection.ReflectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * DefaultObjectFactoryTest
 *
 * @author Ryan Lamore
 */
class DefaultObjectFactoryTest {

  @Test
  void createClass() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    TestClass testClass = defaultObjectFactory.create(TestClass.class, Arrays.asList(String.class, Integer.class),
        Arrays.asList("foo", 0));

    Assertions.assertEquals((Integer) 0, testClass.myInteger, "myInteger didn't match expected");
    Assertions.assertEquals("foo", testClass.myString, "myString didn't match expected");
  }

  @Test
  void createClassThrowsProperErrorMsg() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    try {
      defaultObjectFactory.create(TestClass.class, List.of(String.class), List.of("foo"));
      Assertions.fail("Should have thrown ReflectionException");
    } catch (Exception e) {
      Assertions.assertInstanceOf(ReflectionException.class, e, "Should be ReflectionException");
      Assertions.assertTrue(e.getMessage().contains("(String)"), "Should not have trailing commas in types list");
      Assertions.assertTrue(e.getMessage().contains("(foo)"), "Should not have trailing commas in values list");
    }
  }

  @Test
  void createHashMap() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    Map map = defaultObjectFactory.create(Map.class, null, null);
    Assertions.assertInstanceOf(HashMap.class, map, "Should be HashMap");
  }

  @Test
  void createArrayList() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    List list = defaultObjectFactory.create(List.class);
    Assertions.assertInstanceOf(ArrayList.class, list, " list should be ArrayList");

    Collection collection = defaultObjectFactory.create(Collection.class);
    Assertions.assertInstanceOf(ArrayList.class, collection, " collection should be ArrayList");

    Iterable iterable = defaultObjectFactory.create(Iterable.class);
    Assertions.assertInstanceOf(ArrayList.class, iterable, " iterable should be ArrayList");
  }

  @Test
  void createTreeSet() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    SortedSet sortedSet = defaultObjectFactory.create(SortedSet.class);
    Assertions.assertInstanceOf(TreeSet.class, sortedSet, " sortedSet should be TreeSet");
  }

  @Test
  void createHashSet() {
    DefaultObjectFactory defaultObjectFactory = new DefaultObjectFactory();
    Set set = defaultObjectFactory.create(Set.class);
    Assertions.assertInstanceOf(HashSet.class, set, " set should be HashSet");
  }
}
