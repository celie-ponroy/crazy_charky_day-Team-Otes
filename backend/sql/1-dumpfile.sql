--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: besoin; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.besoin (
    id integer NOT NULL,
    client_id uuid NOT NULL,
    libelle character varying(255),
    competence_id integer NOT NULL,
    date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.besoin OWNER TO "user";

--
-- Name: besoin_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.besoin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.besoin_id_seq OWNER TO "user";

--
-- Name: besoin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.besoin_id_seq OWNED BY public.besoin.id;


--
-- Name: competence; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.competence (
    id integer NOT NULL,
    libelle character varying(255),
    description text
);


ALTER TABLE public.competence OWNER TO "user";

--
-- Name: competence_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.competence_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.competence_id_seq OWNER TO "user";

--
-- Name: competence_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.competence_id_seq OWNED BY public.competence.id;


--
-- Name: service; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.service (
    id integer NOT NULL,
    client_id uuid NOT NULL,
    salarie_id uuid NOT NULL,
    id_besoin integer NOT NULL
);


ALTER TABLE public.service OWNER TO "user";

--
-- Name: service_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.service_id_seq OWNER TO "user";

--
-- Name: service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.service_id_seq OWNED BY public.service.id;


--
-- Name: user_competence; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.user_competence (
    user_id uuid NOT NULL,
    competence_id integer NOT NULL,
    interet integer NOT NULL,
    CONSTRAINT user_competence_interet_check CHECK (((interet >= 1) AND (interet <= 10)))
);


ALTER TABLE public.user_competence OWNER TO "user";

--
-- Name: users; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    nom character varying(255) NOT NULL,
    role integer NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO "user";

--
-- Name: besoin id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.besoin ALTER COLUMN id SET DEFAULT nextval('public.besoin_id_seq'::regclass);


--
-- Name: competence id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.competence ALTER COLUMN id SET DEFAULT nextval('public.competence_id_seq'::regclass);


--
-- Name: service id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.service ALTER COLUMN id SET DEFAULT nextval('public.service_id_seq'::regclass);


--
-- Data for Name: besoin; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.besoin (id, client_id, libelle, competence_id, date) FROM stdin;
1	b32c67e6-4328-4a8b-8d9d-23260b8d28a3	Besoin d'un spécialiste en jardinage pour demain	1	2025-02-14 00:00:00
2	7a40e88f-1111-4e6f-a90e-1234567890ab	Besoin d'un chef pour une soirée	2	2025-02-15 00:00:00
3	8b9e2fca-2222-4ad9-9fcd-0987654321cd	Besoin d'un plombier pour urgence	3	2025-02-16 00:00:00
4	b32c67e6-4328-4a8b-8d9d-23260b8d28a3	Besoin d'un nettoyeur pour le bureau	4	2025-02-17 00:00:00
5	7a40e88f-1111-4e6f-a90e-1234567890ab	Besoin d'un bricoleur pour des réparations	5	2025-02-18 00:00:00
6	8b9e2fca-2222-4ad9-9fcd-0987654321cd	Besoin d'un technicien en informatique	6	2025-02-19 00:00:00
\.


--
-- Data for Name: competence; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.competence (id, libelle, description) FROM stdin;
1	Jardinage	Service de jardinage
2	Cuisine	Préparation culinaire
3	Plomberie	Installation et réparation de plomberie
4	Nettoyage	Service de nettoyage général
5	Bricolage	Travaux manuels et réparations
6	Electricien	Faire de l'electricite
\.


--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.service (id, client_id, salarie_id, id_besoin) FROM stdin;
1	b32c67e6-4328-4a8b-8d9d-23260b8d28a3	4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a	1
2	7a40e88f-1111-4e6f-a90e-1234567890ab	4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a	2
3	8b9e2fca-2222-4ad9-9fcd-0987654321cd	9f7c2e3b-3333-4abc-8a7d-112233445566	3
4	b32c67e6-4328-4a8b-8d9d-23260b8d28a3	9f7c2e3b-3333-4abc-8a7d-112233445566	4
5	7a40e88f-1111-4e6f-a90e-1234567890ab	a6e8d10a-4444-4cba-b123-6677889900aa	5
6	8b9e2fca-2222-4ad9-9fcd-0987654321cd	a6e8d10a-4444-4cba-b123-6677889900aa	6
\.


--
-- Data for Name: user_competence; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.user_competence (user_id, competence_id, interet) FROM stdin;
4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a	1	8
4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a	2	5
9f7c2e3b-3333-4abc-8a7d-112233445566	3	7
9f7c2e3b-3333-4abc-8a7d-112233445566	4	9
a6e8d10a-4444-4cba-b123-6677889900aa	5	6
a6e8d10a-4444-4cba-b123-6677889900aa	6	8
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users (id, nom, role, password) FROM stdin;
b32c67e6-4328-4a8b-8d9d-23260b8d28a3	Client Un	0	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
7a40e88f-1111-4e6f-a90e-1234567890ab	Client Deux	0	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
8b9e2fca-2222-4ad9-9fcd-0987654321cd	Client Trois	0	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a	Employé Un	1	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
9f7c2e3b-3333-4abc-8a7d-112233445566	Employé Deux	1	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
a6e8d10a-4444-4cba-b123-6677889900aa	Employé Trois	1	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
6d8f8b10-d1b9-45c8-b843-5c2c64f0afc1	Admin Un	2	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
b7f9d20b-5555-4dcd-c234-778899001122	Admin Deux	2	$2y$10$p4XTteDAhVb0uotLyG7vRuCBm3GyUOT/PDa53i4TQpC0KQxYXA1Y6
add98e0d-508b-4390-90c2-1cd3eea8fc59	victor	2	$2y$10$5h5raal.Q7MTG/L.yU6J4.h.6VRgQQ.Ogly96cKJQvCJa.bpB8meG
a8ec5427-1cb1-461a-b76d-07db17c80a81	odin	1	$2y$10$Z0R3ovILOvceMlB53/wNg.hWXy6qAziDtbC1oY5gyhljJNejCLHI6
\.


--
-- Name: besoin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.besoin_id_seq', 6, true);


--
-- Name: competence_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.competence_id_seq', 11, true);


--
-- Name: service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.service_id_seq', 6, true);


--
-- Name: besoin besoin_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.besoin
    ADD CONSTRAINT besoin_pkey PRIMARY KEY (id);


--
-- Name: competence competence_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.competence
    ADD CONSTRAINT competence_pkey PRIMARY KEY (id);


--
-- Name: service service_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);


--
-- Name: user_competence user_competence_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.user_competence
    ADD CONSTRAINT user_competence_pkey PRIMARY KEY (user_id, competence_id);


--
-- Name: users users_nom_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_nom_key UNIQUE (nom);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: besoin besoin_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.besoin
    ADD CONSTRAINT besoin_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: besoin besoin_competence_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.besoin
    ADD CONSTRAINT besoin_competence_id_fkey FOREIGN KEY (competence_id) REFERENCES public.competence(id) ON DELETE CASCADE;


--
-- Name: service service_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: service service_id_besoin_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_id_besoin_fkey FOREIGN KEY (id_besoin) REFERENCES public.besoin(id) ON DELETE CASCADE;


--
-- Name: service service_salarie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_salarie_id_fkey FOREIGN KEY (salarie_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: user_competence user_competence_competence_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.user_competence
    ADD CONSTRAINT user_competence_competence_id_fkey FOREIGN KEY (competence_id) REFERENCES public.competence(id) ON DELETE CASCADE;


--
-- Name: user_competence user_competence_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.user_competence
    ADD CONSTRAINT user_competence_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

