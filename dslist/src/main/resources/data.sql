TRUNCATE TABLE tb_belonging, tb_game, tb_game_list RESTART IDENTITY CASCADE;

INSERT INTO tb_game_list (name) VALUES

('Aventura e RPG'),
('Jogos de plataforma');

INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES
('The Witcher 3: Wild Hunt', 4.9, 2015, 'RPG', 'PC, PS4, Xbox One', 'https://example.com/witcher3.jpg', 'Caçador de monstros em mundo aberto', 'Descrição longa do The Witcher 3...'),                                                                                                  ('God of War', 4.8, 2018, 'Ação-Aventura', 'PS4, PS5', 'https://example.com/godofwar.jpg', 'Kratos na mitologia nórdica', 'Descrição longa de God of War...'),
('Hollow Knight', 4.7, 2017, 'Metroidvania', 'PC, Switch, PS4, Xbox One', 'https://example.com/hollowknight.jpg', 'Aventura em reino subterrâneo', 'Descrição longa de Hollow Knight...'),
('Celeste', 4.6, 2018, 'Plataforma', 'PC, Switch, PS4, Xbox One', 'https://example.com/celeste.jpg', 'Plataforma desafiador sobre ansiedade', 'Descrição longa de Celeste...'),
('Red Dead Redemption 2', 4.9, 2018, 'Ação-Aventura', 'PC, PS4, Xbox One', 'https://example.com/rdr2.jpg', 'Velho Oeste em mundo aberto', 'Descrição longa de RDR2...');

INSERT INTO tb_belonging (position, list_id, game_id) VALUES
 (0, 1, 1),
 (1, 1, 2),
 (2, 1, 3),
 (3, 1, 5),
 (0, 2, 3),
 (1, 2, 4);