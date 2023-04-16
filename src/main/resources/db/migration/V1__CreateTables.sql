CREATE TABLE `employee`
(
    id                  int                                             NOT NULL AUTO_INCREMENT,
    first_name_person   varchar(50)                                     NOT NULL,
    last_name_person    varchar(50)                                     NOT NULL,
    email_person        varchar(100)                                     NOT NULL,
    phone_number_person varchar(50)                                     NOT NULL,
    birthdate_person    date                                            NOT NULL,
    description         varchar(50)                                     NOT NULL,
    employee_status     varchar(25)                                     NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (email_person, phone_number_person)
);

CREATE TABLE `patient`
(
    id                  int                  NOT NULL AUTO_INCREMENT,
    first_name_person   varchar(50)          NOT NULL,
    last_name_person    varchar(50)          NOT NULL,
    email_person        varchar(100)         NOT NULL,
    phone_number_person varchar(50)          NOT NULL,
    birthdate_person    date                 NOT NULL,
    bsn                 int                  NOT NULL,
    health_ins_num      int                  NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (email_person, bsn, health_ins_num, phone_number_person)
);

CREATE TABLE `user`
(
    id              int              NOT NULL AUTO_INCREMENT,
    email           varchar(20)      NOT NULL,
    password        varchar(100)     NOT NULL,
    employee_id     int              NULL,
    patient_id      int              NULL,

    PRIMARY KEY (id),
    UNIQUE (email),
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patient (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `user_role`
(
    id        int         NOT NULL AUTO_INCREMENT,
    user_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `diagnose`
(
    id               int             NOT NULL AUTO_INCREMENT,
    patient_id       int             NOT NULL,
    emp_id           int             NOT NULL,
    name_diagnose    varchar(50)     NOT NULL,
    description      varchar(250)    NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (patient_id) REFERENCES patient (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (emp_id) REFERENCES employee (id)
);

CREATE TABLE `prescription`
(
    id               int            NOT NULL AUTO_INCREMENT,
    emp_id           int            NOT NULL,
    diagnose_id      int            NOT NULL,
    start_date       date           NOT NULL,
    end_date         date           NOT NULL,
    medication       varchar(50)    NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (emp_id) REFERENCES employee (id),
    FOREIGN KEY (diagnose_id) REFERENCES diagnose (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `appointment`
(
    id                      int            NOT NULL AUTO_INCREMENT,
    id_patient              int            NOT NULL,
    id_doctor               int            NOT NULL,
    date_appointment        datetime       NOT NULL,
    purpose                 varchar(250)   NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_patient) REFERENCES patient (id),
    FOREIGN KEY (id_doctor) REFERENCES employee (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `unavailability`
(
    id                         int            NOT NULL AUTO_INCREMENT,
    emp_id                     int            NOT NULL,
    date_unavailability        date           NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (emp_id) REFERENCES employee (id) ON UPDATE CASCADE ON DELETE CASCADE
);


