create sequence seq_users;
create sequence seq_task;
create sequence seq_attachment;
create table users (
                       id bigint not null,
                       login varchar(100) not null,
                       password varchar(50) not null,
                       name varchar(100) not null,
                       email text not null,
---------------------------------------------------
                       constraint pk_users_id primary key (id)
);

create table attachment (
                            id bigint not null,
                            task_id bigint not null,
                            url varchar(500),
                            title varchar(500),
----------------------------------------------
                            constraint pk_attachment_id primary key (id)
);

create table task (
                      id bigint not null,
                      users_id bigint not null,
                      attachments_id bigint not null,
                      title varchar(150) not null,
                      description text,
                      date_create timestamp default current_date,
                      date_end date,
                      priority int not null default 5,
-------------------------------------
                      constraint pk_task_id primary key (id),
                      constraint fk_user_id_for_task foreign key (users_id) references users(id) on delete cascade,
                      constraint fk_attachments_id_for_task foreign key (attachments_id) references attachment(id) on delete cascade
)

alter table attachment add constraint fk_task_id_for_attachment foreign key (task_id) references task(id) on delete cascade

alter table task add status text ;

create table task_attachment (
                                 task_id bigint not null,
                                 attachment bigint not null
)
