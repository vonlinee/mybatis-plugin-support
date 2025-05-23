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
package org.apache.ibatis.submitted.manyanno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;

class ManyAnnoTest extends BaseDataTest {

  @Test
  void getMessageForEmptyDatabase() throws Exception {
    final Environment environment = new Environment("test", new JdbcTransactionFactory(),
        BaseDataTest.createBlogDataSource());
    final Configuration config = new Configuration(environment);
    config.addMapper(PostMapper.class);
    final SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
    try (SqlSession session = factory.openSession()) {

      PostMapper mapper = session.getMapper(PostMapper.class);
      List<AnnoPost> posts = mapper.getPosts(101);

      assertEquals(3, posts.size());
      assertEquals(3, posts.get(0).getTags().size());
      assertEquals(1, posts.get(1).getTags().size());
      assertEquals(0, posts.get(2).getTags().size());
    }
  }

}
