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

create schema ibtest authorization dba;

create table ibtest.names (
id int,
firstName varchar(20),
lastName varchar(20)
);

insert into ibtest.names (id, firstName, lastName) values(1, 'Fred', 'Flintstone');
insert into ibtest.names (id, firstName, lastName) values(2, 'Wilma', 'Flintstone');
insert into ibtest.names (id, firstName, lastName) values(3, 'Pebbles', 'Flintstone');
insert into ibtest.names (id, firstName, lastName) values(4, 'Barney', 'Rubble');
insert into ibtest.names (id, firstName, lastName) values(5, 'Betty', 'Rubble');
insert into ibtest.names (id, firstName, lastName) values(6, 'Bamm Bamm', 'Rubble');
