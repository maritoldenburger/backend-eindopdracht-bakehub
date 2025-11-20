INSERT INTO users (username, email, password, role)
VALUES ('annie_ananas', 'annieananas@bakehub.com', 'wachtwoord123', 'USER'),
       ('bobbybanaan', 'bobbiebanaan@bakehub.com', 'wachtwoord123', 'USER'),
       ('wendywatermeloen', 'wendywatermeloen@bakehub.com', 'wachtwoord123', 'ADMIN'),
       ('lolalimoen95', 'lolalimoen@bakehub.com', 'wachtwoord123', 'USER'),
       ('fredframb00s', 'fredframboos@bakehub.com', 'wachtwoord123', 'ADMIN'),
       ('brambrownie', 'brambrownie@bakehub.com', 'wachtwoord123', 'ADMIN');

INSERT INTO categories (name, description, image_url)
VALUES ('taart',
        'Of je nu aan de slag wilt met een klassieke appeltaart, een frisse citroenkwarktaart of gewoon een simpele chocoladetaart, hier vind je volop inspiratie. Perfect voor verjaardagen, een high tea of gewoon omdat je er zin in hebt!',
        NULL),
       ('cake',
        'Van een luchtige vanillecake tot een smeuïge kruidcake: hier ontdek je een hele verzameling cakes die altijd in de smaak vallen. Handig voor verjaardagen, onverwacht bezoek of gewoon een gezellige middag thuis.',
        NULL),
       ('koekjes',
        'Een koek bij de koffie vindt iedereen lekker. Je vind hier de lekkerste koekjesrecepten, van knapperige speculaasjes en klassieke stroopwafels tot simpele zandkoekjes.',
        NULL),
       ('brood',
        'Zin in knapperig volkorenbrood, ambachtelijke bolletjes of zoete kaneelbroodjes? Je vindt hier vast het juiste recept. Niets gaat boven de geur van zelfgebakken brood in huis!',
        NULL),
       ('hartig',
        'Baksels hoeven niet altijd zoet te zijn. Denk aan hartige taarten, saucijzenbroodjes en quiches - ideaal voor de lunch, een borrel of als hartig tussendoortje!',
        NULL);

INSERT INTO recipes (name, image_url, description, instructions, servings, preparation_time, rating, category_id)
VALUES ('Speculoos cheesecake',
        NULL,
        'Een romige no-bake cheesecake met een kruidige speculoosbodem en speculoospasta.',
        '1. Verkruimel speculooskoekjes en meng met gesmolten boter. 2. Druk de kruimels stevig in een vorm en laat opstijven. 3. Klop roomkaas, slagroom en speculoospasta luchtig. 4. Giet het mengsel op de bodem en strijk glad. 5. Laat de cheesecake minimaal 4 uur opstijven in de koelkast.',
        10,
        30,
        4.5,
        1);

INSERT INTO ingredients (name, quantity, unit, recipe_id)
VALUES ('speculooskoekjes', 300, 'GRAM', 1),
       ('boter', 125, 'GRAM', 1),
       ('roomkaas', 400, 'GRAM', 1),
       ('slagroom', 250, 'MILLILITER', 1),
       ('speculoospasta', 150, 'GRAM', 1);

INSERT INTO recipes (name, image_url, description, instructions, servings, preparation_time, rating, category_id)
VALUES ('Speculaascake',
        NULL,
        'Een zachte kruidige cake, perfect voor herfst en winter.',
        '1. Klop boter en suiker romig. 2. Voeg eieren toe en mix tot een glad beslag. 3. Zeef bloem en speculaaskruiden erdoor. 4. Giet het beslag in een cakeblik. 5. Bak 55 minuten op 160°C tot de cake goudbruin is.',
        18,
        80,
        5,
        2);

INSERT INTO ingredients (name, quantity, unit, recipe_id)
VALUES ('boter', 200, 'GRAM', 2),
       ('lichtbruine basterdsuiker', 150, 'GRAM', 2),
       ('eieren', 3, NULL, 2),
       ('bloem', 250, 'GRAM', 2),
       ('speculaaskruiden', 2, 'TEASPOON', 2);

INSERT INTO recipes (name, image_url, description, instructions, servings, preparation_time, rating, category_id)
VALUES ('Gevuld Speculaas',
        NULL,
        'Een klassiek Sinterklaasgebak met zachte amandelspijs',
        '1. Maak een deeg van bloem, boter, suiker en speculaaskruiden. 2. Laat het deeg een uur rusten in de koelkast. 3. Meng amandelspijs met ei voor de vulling. 4. Rol het deeg uit, verdeel de spijs ertussen en leg er een tweede deeglaag op. 5. Bak 35 minuten op 175°C.',
        16,
        90,
        4,
        3);

INSERT INTO ingredients (name, quantity, unit, recipe_id)
VALUES ('bloem', 300, 'GRAM', 3),
       ('boter', 200, 'GRAM', 3),
       ('donkere basterdsuiker', 150, 'GRAM', 3),
       ('speculaaskruiden', 2, 'TEASPOON', 3),
       ('amandelspijs', 300, 'GRAM', 3),
       ('ei', 1, NULL, 3);

INSERT INTO recipes (name, image_url, description, instructions, servings, preparation_time, rating, category_id)
VALUES ('Suikerbrood',
        NULL,
        'Een zoet, zacht brood met parelsuiker en een vleugje kaneel.',
        '1. Meng bloem, gist, melk en boter tot een soepel deeg. 2. Laat het deeg 1 uur rijzen. 3. Kneed parelsuiker en kaneel voorzichtig door het deeg. 4. Vorm een brood en leg in een ingevette vorm. 5. Laat nog 30 minuten rijzen en bak 35 minuten op 180°C.',
        15,
        120,
        4,
        4);

INSERT INTO ingredients (name, quantity, unit, recipe_id)
VALUES ('volkorenmeel', 500, 'GRAM', 4),
       ('melk', 250, 'MILLILITER', 4),
       ('boter', 50, 'GRAM', 4),
       ('gist', 7, 'GRAM', 4),
       ('parelsuiker', 150, 'GRAM', 4),
       ('kaneel', 1, 'TEASPOON', 4);

INSERT INTO recipes (name, image_url, description, instructions, servings, preparation_time, rating, category_id)
VALUES ('Kerstboom Plaattaart',
        NULL,
        'Een feestelijke bladerdeeghap in kerstboomvorm, gevuld met roomkaas, pesto en gegrilde groenten.',
        '1. Snijd bladerdeeg in een kerstboomvorm. 2. Meng roomkaas met groene pesto. 3. Bestrijk de plak met het mengsel. 4. Verdeel gegrilde paprika en courgette erover. 5. Dek af met een tweede laag bladerdeeg en bak 20 minuten op 200°C tot goudbruin.',
        8,
        40,
        4.5,
        5);

INSERT INTO ingredients (name, quantity, unit, recipe_id)
VALUES ('bladerdeeg', 2, NULL, 5),
       ('roomkaas', 150, 'GRAM', 5),
       ('groene pesto', 2, 'TABLESPOON', 5),
       ('gegrilde paprika', 1, NULL, 5),
       ('gegrilde courgette', 1, NULL, 5);

INSERT INTO reviews (rating, comment, image_url, user_id, recipe_id)
VALUES (5, 'Superromig en precies zoet genoeg. Echt een favoriet!', NULL, 1, 1),
       (4, 'Heel lekker! De bodem mocht iets steviger.', NULL, 3, 1),
       (5, 'Een heerlijke kruidige cake, perfect bij een kop thee.', NULL, 2, 2),
       (5, 'Mooi gerezen en lekker luchtig.', NULL, 4, 2),
       (5, 'Precies zoals van de bakker! Heerlijk zacht van binnen.', NULL, 1, 3),
       (3, 'Lekker, maar mijn deeg werd iets te droog.', NULL, 5, 3),
       (4,
        'Goed recept, maar het rijzen duurde bij mij iets langer dan aangegeven. Misschien was mijn keuken te koud. Eindresultaat was wel super!',
        NULL, 3, 4),
       (4, 'Goed gelukt, maar iets zoeter dan verwacht.', NULL, 2, 4),
       (4,
        'Leuk idee en makkelijk om te maken. Wel jammer dat de vorm niet helemaal lukte bij mij, maar smaak was top!',
        NULL, 4, 5),
       (5, 'Heel feestelijk én lekker. Gaat vaker op tafel komen!', NULL, 4, 5);

INSERT INTO favorites (user_id, recipe_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 5),
       (4, 1),
       (4, 4),
       (5, 4),
       (6, 2);
