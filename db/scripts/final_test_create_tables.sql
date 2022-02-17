create table company (
id integer NOT NULL,
name character varying,
CONSTRAINT company_pkey PRIMARY KEY (id)
);

create table person (
id integer NOT NULL,
name character varying,
company_id integer references company(id),
CONSTRAINT person_pkey PRIMARY KEY(id))