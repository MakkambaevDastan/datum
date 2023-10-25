create table if not exists endpoint
(
    endpoint varchar(255) not null,
    method   varchar(255) not null,
    name     varchar(255),
    primary key (endpoint, method)
);

create table if not exists icd10
(
    category           integer,
    deleted            boolean default false,
    dent               boolean,
    item               integer,
    subcategory        integer,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    block              varchar(255),
    chapter            varchar(255),
    code               varchar(255),
    description        varchar(255),
    name               varchar(255)
);

create table if not exists person
(
    birth_day          date,
    deleted            boolean default false,
    expiry             date,
    family             boolean,
    issue              date,
    male               boolean,
    passport_number    integer,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    pin                bigint,
    address            varchar(255),
    authority          varchar(255),
    country            varchar(255),
    email              varchar(255),
    firstname          varchar(255),
    passport_series    varchar(255),
    patronymic         varchar(255),
    phone              varchar(255),
    surname            varchar(255)
);

create table if not exists post
(
    deleted            boolean default false,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    code               varchar(255),
    description        text,
    en                 varchar(255),
    kg                 varchar(255),
    ru                 varchar(255)
);

create table if not exists privilege
(
    bool      boolean,
    clinic_id bigint       not null,
    post_id   bigint       not null,
    endpoint  varchar(255) not null,
    method    varchar(255) not null,
    primary key (clinic_id, post_id, endpoint, method)
);

create table if not exists service
(
    deleted            boolean default false,
    number             integer,
    section            integer,
    view               varchar(1),
    code               varchar(7),
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    kg                 varchar(255),
    name               varchar(255)
);

create table if not exists users
(
    code      integer,
    deleted   boolean,
    enabled   boolean,
    locked    boolean,
    id        bigint not null
        primary key,
    person_id bigint
        unique
        constraint fkd21kkcigxa21xuby5i3va9ncs
            references person,
    email     varchar(255)
        unique,
    password  varchar(255),
    role      varchar(255)
        constraint users_role_check
            check ((role)::text = ANY
                   ((ARRAY ['USER'::character varying, 'ADMIN'::character varying, 'ROOT'::character varying])::text[]))
);

create table if not exists clinic
(
    deleted            boolean default false,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    user_id            bigint
        constraint fk170twqkl3qdy9u4ind0fdo3bb
            references users,
    name               varchar(255)
);

create table if not exists clinic_person
(
    clinic_id bigint not null
        constraint fkcr2ssh01igxbohgwsv9ctm3ft
            references clinic,
    person_id bigint not null
        constraint fk55e1s99xhmoffxr2vksj3qir
            references person
);

create table if not exists department
(
    deleted            boolean default false,
    clinic_id          bigint
        constraint fk6io2v0mkg9sp6i0vgm9p1oem2
            references clinic,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    address            varchar(255),
    name               varchar(255),
    phone              varchar(255)
);

create table if not exists employee
(
    deleted            boolean default false,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    department_id      bigint
        constraint fkbejtwvg9bxus2mffsm3swj3u9
            references department,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    post_id            bigint
        constraint fkcm3b9d5fiw8s6co7lkw8c0lbs
            references post,
    user_id            bigint
        constraint fkhal2duyxxjtadykhxos7wd3wg
            references users,
    schedule           jsonb
);

create table if not exists anamnesis
(
    deleted            boolean default false,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    employee_id        bigint
        constraint fk1dmqsmgrc7eujqym5wen3sksl
            references employee,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    person_id          bigint,
    anamnesis_details  jsonb,
    tooth              jsonb
);

create table if not exists anamnesis_icd10
(
    anamnesis_id bigint not null
        constraint fkc1vk68c4t08lhrpjlqvmkhba0
            references anamnesis,
    icd10_id     bigint not null
        constraint fkmi0l63eyj6gd3hdu2he4suct4
            references icd10
);

create table if not exists appointment
(
    deleted            boolean default false,
    minute             integer,
    visible            boolean,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    employee_id        bigint
        constraint fk9daqcqq2nrtbcr5xqeivvkorq
            references employee,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    person_id          bigint
        constraint fkt8en2adfe6sjnw9eeo7m2bwcb
            references person,
    start              timestamp(6) with time zone,
    description        varchar(255)
);

create table if not exists preiskurant
(
    deleted            boolean default false,
    clinic_id          bigint
        constraint fk7sp8da6yt1bu8wk94mxxeso04
            references clinic,
    created_by         bigint,
    creation_date      timestamp(6) with time zone,
    id                 bigint not null
        primary key,
    last_modified_by   bigint,
    last_modified_date timestamp(6) with time zone,
    service            bigint,
    description        text,
    price              jsonb
);

create table if not exists token
(
    expired    boolean not null,
    revoked    boolean not null,
    id         bigint  not null
        primary key,
    user_id    bigint
        constraint fkj8rfw4x0wjjyibfqq566j4qng
            references users,
    token      varchar(255)
        unique,
    token_type varchar(255)
        constraint token_token_type_check
            check ((token_type)::text = 'BEARER'::text)
);

