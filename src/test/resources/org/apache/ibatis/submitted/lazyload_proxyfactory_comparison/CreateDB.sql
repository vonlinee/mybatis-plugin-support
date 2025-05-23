--
--    Copyright 2009-2025 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       https://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

drop table users if exists;
drop table groups if exists;
drop table categories if exists;

create table users (
  id int,
  name varchar(20),
  owner_id int
);

create table groups (
  id int,
  name varchar(20)
);

insert into groups  (id, name) values(1, 'Group1');
insert into users (id, name, owner_id) values(1, 'User1', 1);
