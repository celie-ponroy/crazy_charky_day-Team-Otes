DROP TABLE IF EXISTS user_competence CASCADE;
DROP TABLE IF EXISTS besoin CASCADE;
DROP TABLE IF EXISTS competence CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       nom VARCHAR(100) NOT NULL
);

CREATE TABLE competence (
                            id SERIAL PRIMARY KEY,
                            libelle VARCHAR(100) NOT NULL,
                            description TEXT
);

CREATE TABLE besoin (
                        id SERIAL PRIMARY KEY,
                        nom_client VARCHAR(100) NOT NULL,
                        libelle VARCHAR(255) NOT NULL,
                        competence_id INT NOT NULL,
                        date TIMESTAMP NOT NULL,
                        CONSTRAINT fk_besoin_competence
                            FOREIGN KEY (competence_id)
                                REFERENCES competence (id)
);

CREATE TABLE user_competence (
                                 user_id UUID NOT NULL,
                                 competence_id INT NOT NULL,
                                 interet INT NOT NULL,
                                 PRIMARY KEY (user_id, competence_id),
                                 CONSTRAINT fk_user_competence_user
                                     FOREIGN KEY (user_id)
                                         REFERENCES users (id),
                                 CONSTRAINT fk_user_competence_competence
                                     FOREIGN KEY (competence_id)
                                         REFERENCES competence (id)
);

INSERT INTO users (nom) VALUES
                            ('Alice'),
                            ('Bob'),
                            ('Charlie');

INSERT INTO competence (libelle, description) VALUES
                                                  ('Jardinage', 'Entretien de jardins, plantations, tonte...'),
                                                  ('Plomberie', 'Installation et réparation de canalisations'),
                                                  ('Electricité', 'Raccordement électrique, dépannage');

INSERT INTO besoin (nom_client, libelle, competence_id, date) VALUES
                                                                  ('Alice', 'Besoin d\'un jardinier pour tailler les haies', 1, '2025-03-01 09:00:00'),
('Bob', 'Réparation de fuite sous l\'évier', 2, '2025-03-02 10:30:00'),
                                                                  ('Charlie', 'Problème électrique dans le salon', 3, '2025-03-03 14:00:00');

INSERT INTO user_competence (user_id, competence_id, interet)
SELECT id, 1, 9
FROM users
WHERE nom = 'Alice';

INSERT INTO user_competence (user_id, competence_id, interet)
SELECT id, 2, 7
FROM users
WHERE nom = 'Bob';

INSERT INTO user_competence (user_id, competence_id, interet)
SELECT id, 1, 5
FROM users
WHERE nom = 'Bob';

INSERT INTO user_competence (user_id, competence_id, interet)
SELECT id, 3, 8
FROM users
WHERE nom = 'Charlie';

