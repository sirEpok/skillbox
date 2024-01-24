DROP SCHEMA IF EXISTS users_scheme cascade;

CREATE SCHEMA IF NOT EXISTS users_scheme;

create sequence IF NOT EXISTS users_scheme.comments_id_seq
start 1
increment 1
cache 1;

create table IF NOT EXISTS users_scheme.users(
                                   id serial PRIMARY KEY,
                                   email character varying,
                                   login character varying,
                                   password character varying,
                                   first_name character varying,
                                   last_name character varying,
                                   birthday date,
                                   deleted boolean
);

create table IF NOT EXISTS users_scheme.posts(
                                   id serial PRIMARY KEY,
                                   title_post character varying,
                                   text_post text,
                                   date_created date,
                                   likes integer,
                                   user_id integer,
                                   FOREIGN KEY (user_id) REFERENCES users_scheme.users(id)
);

create table IF NOT EXISTS users_scheme.comments(
                                      id integer PRIMARY KEY default nextval('users_scheme.comments_id_seq'),
                                      text_comment character varying,
                                      likes integer,
                                      post_id integer,
                                      user_id integer,
                                      FOREIGN KEY (post_id) REFERENCES users_scheme.posts(id),
                                      FOREIGN KEY (user_id) REFERENCES users_scheme.users(id)
);

create table IF NOT EXISTS users_scheme.subscription(
                                          source_user_id integer,
                                          target_user_id integer,
                                          PRIMARY KEY (source_user_id, target_user_id),
                                          FOREIGN KEY (source_user_id) REFERENCES users_scheme.users(id),
                                          FOREIGN KEY (target_user_id) REFERENCES users_scheme.users(id)
);