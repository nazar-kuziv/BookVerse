-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS activities CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS library CASCADE;
DROP TABLE IF EXISTS liked_reviews CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS saved_reviews CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_friends CASCADE;

DROP SEQUENCE IF EXISTS activities_seq;
DROP SEQUENCE IF EXISTS books_seq;
DROP SEQUENCE IF EXISTS comments_seq;
DROP SEQUENCE IF EXISTS library_seq;
DROP SEQUENCE IF EXISTS liked_reviews_seq;
DROP SEQUENCE IF EXISTS reviews_seq;
DROP SEQUENCE IF EXISTS saved_reviews_seq;
DROP SEQUENCE IF EXISTS users_seq;

CREATE SEQUENCE activities_seq START WITH 45 INCREMENT BY 1;
CREATE SEQUENCE books_seq START WITH 22 INCREMENT BY 1;
CREATE SEQUENCE comments_seq START WITH 45 INCREMENT BY 1;
CREATE SEQUENCE library_seq START WITH 37 INCREMENT BY 1;
CREATE SEQUENCE liked_reviews_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reviews_seq START WITH 23 INCREMENT BY 1;
CREATE SEQUENCE saved_reviews_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE users_seq START WITH 10 INCREMENT BY 1;

CREATE TABLE activities
(
    id                 bigint NOT NULL,
    user_id            bigint,
    book_id            bigint,
    date_of_reading    timestamp(6),
    minutes_of_reading bigint,
    PRIMARY KEY (id)
);

CREATE TABLE books
(
    id           bigint NOT NULL,
    title        varchar(255),
    author       varchar(255),
    genre        varchar(255),
    pages_amount integer,
    description  varchar(10000),
    cover_path   varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id                bigint NOT NULL,
    user_id           bigint,
    review_id         bigint,
    text              varchar(255),
    date              timestamp(6),
    parent_comment_id bigint,
    PRIMARY KEY (id)
);

CREATE TABLE library
(
    id                   bigint NOT NULL,
    user_id              bigint,
    book_id              bigint,
    number_of_pages_read integer,
    PRIMARY KEY (id)
);

CREATE TABLE liked_reviews
(
    id        bigint NOT NULL,
    user_id   bigint,
    review_id bigint,
    PRIMARY KEY (id)
);

CREATE TABLE reviews
(
    id              bigint NOT NULL,
    user_id         bigint,
    book_id         bigint,
    review          varchar(10000),
    likes_amount    integer,
    comments_amount integer,
    share_amount    integer,
    date            timestamp(6),
    PRIMARY KEY (id)
);

CREATE TABLE saved_reviews
(
    id        bigint NOT NULL,
    review_id bigint,
    user_id   bigint,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 bigint NOT NULL,
    login              varchar(255),
    password           varchar(255),
    email              varchar(255),
    name               varchar(255),
    last_name          varchar(255),
    picture_cover_path varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE users_friends
(
    user_id   bigint NOT NULL,
    friend_id bigint NOT NULL
);

ALTER TABLE IF EXISTS activities ADD CONSTRAINT FKlafx35fue8kwd1wewuwtydfsb FOREIGN KEY (book_id) REFERENCES books;
ALTER TABLE IF EXISTS activities ADD CONSTRAINT FKq6cjukylkgxdjkm9npk9va2f2 FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS comments ADD CONSTRAINT FK7h839m3lkvhbyv3bcdv7sm4fj FOREIGN KEY (parent_comment_id) REFERENCES comments;
ALTER TABLE IF EXISTS comments ADD CONSTRAINT FKdpo60i7auk5cudv7kkny8jrqb FOREIGN KEY (review_id) REFERENCES reviews;
ALTER TABLE IF EXISTS comments ADD CONSTRAINT FK8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS library ADD CONSTRAINT FKo40djui3rupdnn1y6ygspjcsf FOREIGN KEY (book_id) REFERENCES books;
ALTER TABLE IF EXISTS library ADD CONSTRAINT FKs4b8fprhj7nsfbs2x3do0edh4 FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS liked_reviews ADD CONSTRAINT FK9hi9jxhr01t5us30xu9gw3g35 FOREIGN KEY (review_id) REFERENCES reviews;
ALTER TABLE IF EXISTS liked_reviews ADD CONSTRAINT FKij14bvr6hg98pjyumf0fo18l3 FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS reviews ADD CONSTRAINT FK6a9k6xvev80se5rreqvuqr7f9 FOREIGN KEY (book_id) REFERENCES books;
ALTER TABLE IF EXISTS reviews ADD CONSTRAINT FKcgy7qjc1r99dp117y9en6lxye FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS saved_reviews ADD CONSTRAINT FK2grxe95cg4p65uwubtlnmtihk FOREIGN KEY (review_id) REFERENCES reviews;
ALTER TABLE IF EXISTS saved_reviews ADD CONSTRAINT FKdc1o41371hipo260cnk6b8wtv FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE IF EXISTS users_friends ADD CONSTRAINT FKetin2ga6w0oln69xfef2wwjqw FOREIGN KEY (friend_id) REFERENCES users;
ALTER TABLE IF EXISTS users_friends ADD CONSTRAINT FKry5pun2eg852sbl2l50p236bo FOREIGN KEY (user_id) REFERENCES users;