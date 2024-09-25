create table document (date_created timestamp(6) not null, date_signed timestamp(6), current_user VARCHAR(255), id VARCHAR(255) not null, body varchar(255) not null, name varchar(255) not null, type varchar(255) not null check (type in ('VACATION','JOB_APPLICATION')), primary key (id));
create table student (id VARCHAR(255) not null, first_name varchar(255) not null, last_name varchar(255) not null, primary key (id));
create table student_documents (documents_id VARCHAR(255) not null unique, student_entity_id VARCHAR(255) not null);
alter table if exists document add constraint FKs8x9h6t1voshuddm5a70mobma foreign key (current_user) references student;
alter table if exists student_documents add constraint FKoim4fcdgn6cfsxmc4nr91u69f foreign key (documents_id) references document;
alter table if exists student_documents add constraint FKe3a6lg7vsosk68156goo9cvrd foreign key (student_entity_id) references student;