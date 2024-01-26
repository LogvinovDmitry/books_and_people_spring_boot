--
-- PostgreSQL database dump
--

-- Dumped from database version 13.11
-- Dumped by pg_dump version 14.8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: book; Type: TABLE; Schema: public; Owner: springboot_user
--

CREATE TABLE public.book (
    id integer NOT NULL,
    person_id integer,
    author character varying NOT NULL,
    name character varying NOT NULL,
    publication_year integer NOT NULL,
    date_when_book_was_taken timestamp without time zone,
    CONSTRAINT book_publication_year_check CHECK ((publication_year > 0))
);


ALTER TABLE public.book OWNER TO springboot_user;

--
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: springboot_user
--

ALTER TABLE public.book ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: person; Type: TABLE; Schema: public; Owner: springboot_user
--

CREATE TABLE public.person (
    id integer NOT NULL,
    name character varying NOT NULL,
    age integer,
    email character varying,
    address character varying(455) NOT NULL,
    data_birth_day date,
    date_of_registration timestamp without time zone,
    CONSTRAINT person_age_check CHECK ((age > 0))
);


ALTER TABLE public.person OWNER TO springboot_user;

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: springboot_user
--

ALTER TABLE public.person ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: springboot_user
--

COPY public.book (id, person_id, author, name, publication_year, date_when_book_was_taken) FROM stdin;
5	84	Михаил Булгаков	Мастер и Маргарита	1970	\N
3	\N	Антон Чехов	Три сестры	1968	2023-11-20 18:54:18.619
2	93	Лев Толстой	Война и мир	1871	2023-11-22 00:50:53.731
4	\N	Александр Пушкин	Евгений Онегин	1833	2023-11-20 18:53:40.124
38	\N	апапапапа	Мастер	1968	\N
7	79	Алексей Толстой	Анна Каренина2	1978	2024-01-25 16:58:02.829907
1	79	Фёдор Достоевский	Преступление и наказание	1890	2024-01-25 16:58:57.392765
6	\N	Иван Тургенев	Отцы и дети	1962	2023-11-20 18:54:13.613
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: springboot_user
--

COPY public.person (id, name, age, email, address, data_birth_day, date_of_registration) FROM stdin;
81	Коваленко Ольга Владимировна	22	kovalenko@gmail.com	Украина, Харьков, 987123	2001-11-30	2023-11-09 19:15:00
82	Михайлов Сергей Анатольевич	35	mihailov@gmail.com	Россия, Санкт-Петербург, 321987	1988-07-05	2023-11-09 19:20:00
83	Новикова Екатерина Игоревна	27	novikova@gmail.com	Украина, Львов, 654789	1996-04-18	2023-11-09 19:25:00
84	Захаров Дмитрий Васильевич	29	zaharov@gmail.com	Россия, Екатеринбург, 789456	1994-09-22	2023-11-09 19:30:00
88	Соколова Юлия Сергеевна	24	sokolova@gmail.com	Украина, Днепр, 321654	1999-10-03	2023-11-09 19:50:00
91	Васнецов Артур Николаевич	34	vasnetsov@gmail.com	Россия, Воронеж, 789123	1986-11-09	2023-11-09 20:05:00
92	Шевцова Мария Алексеевна	25	shevtsova@gmail.com	Беларусь, Брест, 456987	1998-04-22	2023-11-09 20:10:00
93	Кузьмин Денис Игоревич	28	kuzmin@gmail.com	Украина, Луганск, 987456	1995-07-15	2023-11-09 20:15:00
94	Романов Аркадий Васильевич	30	romanov@gmail.com	Россия, Самара, 321789	1993-03-18	2023-11-09 20:20:00
96	Лебедев Илья Владимирович	35	lebedev@gmail.com	Беларусь, Могилев, 123654	1988-06-24	2023-11-09 20:30:00
97	Калинина Анастасия Ивановна	27	kalinina@gmail.com	Украина, Чернигов, 987789	1996-05-07	2023-11-09 20:35:00
79	Петров Петр Петрович	30	petrov@gmail.com	Украина, Киев, 987654	1995-08-20	\N
98	Васильев Петр Сергеевич	66	builder14143990@gmail.com	Украина, Днепр, 456236	2014-06-23	2023-11-15 18:44:37.061
90	Федорова Анастасия Валерьевна	33	fedorova@gmail.com	Украина, Запорожье, 123987	2000-08-14	\N
86	Тимофеев Артем Владимирович	44	timofeev@gmail.com	Россия, Новосибирск, 987321	1997-03-25	\N
95	Беляева Елена Петровна	44	belyaeva@gmail.com	Украина, Херсон, 654123	2001-12-01	\N
80	Сидоров Алексей Николаевич	25	sidorov@gmail.com	Беларусь, Минск, 456789	1993-02-10	\N
87	Козлов Максим Алексеевич	33	kozlov@gmail.com	Беларусь, Гродно, 456123	1989-06-12	\N
\.


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: springboot_user
--

SELECT pg_catalog.setval('public.book_id_seq', 41, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: springboot_user
--

SELECT pg_catalog.setval('public.person_id_seq', 99, true);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: springboot_user
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: person person_email_key; Type: CONSTRAINT; Schema: public; Owner: springboot_user
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_email_key UNIQUE (email);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: springboot_user
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: person unique_name_constraint; Type: CONSTRAINT; Schema: public; Owner: springboot_user
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT unique_name_constraint UNIQUE (name);


--
-- Name: book book_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: springboot_user
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.person(id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

