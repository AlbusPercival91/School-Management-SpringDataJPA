CREATE SCHEMA IF NOT EXISTS school;
    
CREATE TABLE IF NOT EXISTS school.groups
(
    group_id SERIAL NOT NULL,
    group_name character varying(10) NOT NULL,
    CONSTRAINT group_pkey PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS school.students
(
    student_id SERIAL NOT NULL,
    group_id integer,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);
    
CREATE TABLE IF NOT EXISTS school.course
(
    course_id SERIAL NOT NULL,
    course_name character varying(50) NOT NULL,
    course_description character varying,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS school.students_courses_checkouts
(
    checkouts_id SERIAL,
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT students_courses_checkouts_pkey PRIMARY KEY (checkouts_id),
    CONSTRAINT course_id FOREIGN KEY (course_id) REFERENCES school.course (course_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES school.students (student_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);
