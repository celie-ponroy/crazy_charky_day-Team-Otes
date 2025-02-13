CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS user_competence CASCADE;
DROP TABLE IF EXISTS besoin CASCADE;
DROP TABLE IF EXISTS competence CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       nom VARCHAR(255) NOT NULL,
                       role INT NOT NULL
);

CREATE TABLE competence (
                            id SERIAL PRIMARY KEY,
                            libelle VARCHAR(255),
                            description TEXT
);

CREATE TABLE besoin (
                        id SERIAL PRIMARY KEY,
                        client_id UUID NOT NULL,
                        libelle VARCHAR(255),
                        competence_id INT NOT NULL,
                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE,
                        FOREIGN KEY (competence_id) REFERENCES competence(id) ON DELETE CASCADE
);

CREATE TABLE user_competence (
                                 user_id UUID NOT NULL,
                                 competence_id INT NOT NULL,
                                 interet INT NOT NULL CHECK (interet BETWEEN 1 AND 10),
                                 PRIMARY KEY (user_id, competence_id),
                                 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                 FOREIGN KEY (competence_id) REFERENCES competence(id) ON DELETE CASCADE
);

CREATE TABLE service (
                         id SERIAL PRIMARY KEY,
                         client_id UUID NOT NULL,
                         salarie_id UUID NOT NULL,
                         id_besoin INT NOT NULL,
                         FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE,
                         FOREIGN KEY (salarie_id) REFERENCES users(id) ON DELETE CASCADE,
                         FOREIGN KEY (id_besoin) REFERENCES besoin(id) ON DELETE CASCADE
);

INSERT INTO users (id, nom, role) VALUES
                                      ('b32c67e6-4328-4a8b-8d9d-23260b8d28a3', 'Client Un', 0),
                                      ('7a40e88f-1111-4e6f-a90e-1234567890ab', 'Client Deux', 0),
                                      ('8b9e2fca-2222-4ad9-9fcd-0987654321cd', 'Client Trois', 0),
                                      ('4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a', 'Employé Un', 1),
                                      ('9f7c2e3b-3333-4abc-8a7d-112233445566', 'Employé Deux', 1),
                                      ('a6e8d10a-4444-4cba-b123-6677889900aa', 'Employé Trois', 1),
                                      ('6d8f8b10-d1b9-45c8-b843-5c2c64f0afc1', 'Admin Un', 2),
                                      ('b7f9d20b-5555-4dcd-c234-778899001122', 'Admin Deux', 2);

INSERT INTO competence (libelle, description) VALUES
                                                  ('Jardinage', 'Service de jardinage'),
                                                  ('Cuisine', 'Préparation culinaire'),
                                                  ('Plomberie', 'Installation et réparation de plomberie'),
                                                  ('Nettoyage', 'Service de nettoyage général'),
                                                  ('Bricolage', 'Travaux manuels et réparations'),
                                                  ('Informatique', 'Maintenance et support informatique');

INSERT INTO besoin (client_id, libelle, competence_id, date) VALUES
                                                                 ('b32c67e6-4328-4a8b-8d9d-23260b8d28a3', 'Besoin d''un spécialiste en jardinage pour demain', 1, CURRENT_DATE + INTERVAL '1 day'),
                                                                 ('7a40e88f-1111-4e6f-a90e-1234567890ab', 'Besoin d''un chef pour une soirée', 2, CURRENT_DATE + INTERVAL '2 day'),
                                                                 ('8b9e2fca-2222-4ad9-9fcd-0987654321cd', 'Besoin d''un plombier pour urgence', 3, CURRENT_DATE + INTERVAL '3 day'),
                                                                 ('b32c67e6-4328-4a8b-8d9d-23260b8d28a3', 'Besoin d''un nettoyeur pour le bureau', 4, CURRENT_DATE + INTERVAL '4 day'),
                                                                 ('7a40e88f-1111-4e6f-a90e-1234567890ab', 'Besoin d''un bricoleur pour des réparations', 5, CURRENT_DATE + INTERVAL '5 day'),
                                                                 ('8b9e2fca-2222-4ad9-9fcd-0987654321cd', 'Besoin d''un technicien en informatique', 6, CURRENT_DATE + INTERVAL '6 day');

INSERT INTO user_competence (user_id, competence_id, interet) VALUES
                                                                  ('4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a', 1, 8),
                                                                  ('4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a', 2, 5),
                                                                  ('9f7c2e3b-3333-4abc-8a7d-112233445566', 3, 7),
                                                                  ('9f7c2e3b-3333-4abc-8a7d-112233445566', 4, 9),
                                                                  ('a6e8d10a-4444-4cba-b123-6677889900aa', 5, 6),
                                                                  ('a6e8d10a-4444-4cba-b123-6677889900aa', 6, 8);

INSERT INTO service (client_id, salarie_id, id_besoin) VALUES
                                                           ('b32c67e6-4328-4a8b-8d9d-23260b8d28a3', '4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a', 1),
                                                           ('7a40e88f-1111-4e6f-a90e-1234567890ab', '4f7a4cd1-9c47-4a64-9f55-813fc60a4c3a', 2),
                                                           ('8b9e2fca-2222-4ad9-9fcd-0987654321cd', '9f7c2e3b-3333-4abc-8a7d-112233445566', 3),
                                                           ('b32c67e6-4328-4a8b-8d9d-23260b8d28a3', '9f7c2e3b-3333-4abc-8a7d-112233445566', 4),
                                                           ('7a40e88f-1111-4e6f-a90e-1234567890ab', 'a6e8d10a-4444-4cba-b123-6677889900aa', 5),
                                                           ('8b9e2fca-2222-4ad9-9fcd-0987654321cd', 'a6e8d10a-4444-4cba-b123-6677889900aa', 6);
