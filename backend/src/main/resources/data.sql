INSERT INTO tag
    (deleted, name)
VALUES
    (false, 'ReactJS');
INSERT INTO tag
    (deleted, name)
VALUES
    (false, 'Java');
INSERT INTO tag
    (deleted, name)
VALUES
    (false, 'Spring');

INSERT INTO department
    (deleted, name)
VALUES
    (false, 'Compro');
INSERT INTO department
    (deleted, name)
VALUES
    (false, 'MBA');


INSERT INTO address
    (city, state, zip, deleted)
VALUES
    ('Seattle', 'WS', '52557', false);
INSERT INTO address
    (city, state, zip, deleted)
VALUES
    ('San Francisco', 'CA', '52546', false);

INSERT INTO address
    (city, state, zip, deleted)
VALUES
    ('Fairfield', 'IA', '52556', false);

INSERT INTO address
    (city, state, zip, deleted)
VALUES
    ('Chicago', 'IL', '45766', false);

INSERT INTO faculty
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, department_id, deleted)
VALUES
    (11, true, 'abror', 'Abror', 'Khamidov', 'a.khamidov@miu.edu', null, 2, 1, false);

INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (1, true, 'ibrokhim', 'Ibrokhim', 'Shukrullaev', 'i.shukrullaev@miu.edu', null, 1, 3.85, 1, false);

INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (2, true, 'elbek', 'Elbek', 'Shaykulov', 'elbek.shaykulov@miu.edu', null, 2, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (3, true, 'alisher', 'Firstname3', 'Lastname3', 'name@miu.edu', null, 3, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (4, true, 'username4', 'Firstname4', 'Lastname4', 'name@miu.edu', null, 4, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (5, true, 'username5', 'Firstname5', 'Lastname5', 'name@miu.edu', null, 1, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (6, true, 'username6', 'Firstname6', 'Lastname6', 'name@miu.edu', null, 2, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (7, true, 'username7', 'Firstname7', 'Lastname7', 'name@miu.edu', null, 3, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (8, true, 'username8', 'Firstname8', 'Lastname8', 'name@miu.edu', null, 4, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (9, true, 'username9', 'Firstname9', 'Lastname9', 'name@miu.edu', null, 3, 3.85, 1, false);
INSERT INTO student
    (id, active, username, first_name, last_name, email, last_logged_in, address_id, gpa, major_id, deleted)
VALUES
    (10, true, 'username10', 'Firstname10', 'Lastname10', 'name@miu.edu', null, 2, 3.85, 1, false);

INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$50k sign in bonus', 'Microsoft Job Position', 'Seattle', 'WS', 'Microsoft', 1, false, DATE('2022-1-1'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$40k sign in bonus', 'Google Job Position', 'San Francisco', 'CA', 'Google', 2, false, DATE('2022-1-2'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$5k sign in bonus', 'Microsoft Job Position', 'Fairfield', 'IA', 'Microsoft', 3, false, DATE('2022-1-3'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$60k sign in bonus', 'Microsoft Job Position', 'Chicago', 'IL', 'Microsoft', 4, false, DATE('2022-1-4'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$70k sign in bonus', 'Microsoft Job Position', 'Seattle', 'WS', 'Microsoft', 5, false, DATE('2022-1-5'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$90k sign in bonus', 'Microsoft Job Position', 'Fairfield', 'IA', 'Microsoft', 6, false, DATE('2022-1-6'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$90k sign in bonus', 'Microsoft Job Position', 'Chicago', 'IL', 'Microsoft', 7, false, DATE('2022-1-7'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$51k sign in bonus', 'Microsoft Job Position', 'Seattle', 'WS', 'Microsoft', 8, false, DATE('2022-1-8'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$52k sign in bonus', 'Microsoft Job Position', 'Chicago', 'IL', 'Microsoft', 9, false, DATE('2022-1-9'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$53k sign in bonus', 'Microsoft Job Position', 'Seattle', 'WS', 'Microsoft', 10, false, DATE('2022-1-10'));
INSERT INTO job_advertisement
    (benefits, description, city, state, company_name, created_by_id, deleted, created_at)
VALUES
    ('$54k sign in bonus', 'Microsoft Job Position', 'Fairfield', 'IA', 'Microsoft', 10, false, DATE('2022-1-11'));

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (1, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (1, 2);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (2, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (2, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (3, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (3, 3);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (4, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (4, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (5, 3);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (5, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (6, 3);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (6, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (7, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (7, 3);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (8, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (8, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (9, 3);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (9, 2);


INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (10, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (10, 3);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (11, 1);

INSERT INTO job_advertisement_tags
    (job_advertisement_id, tags_id)
VALUES
    (11, 2);


INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 1, 3);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 1, 4);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 1, 5);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 2, 6);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 2, 7);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 2, 8);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 2, 9);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 2, 10);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 3, 1);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 3, 2);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 3, 3);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 3, 4);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 3, 5);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 4, 6);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 4, 7);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 4, 8);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 4, 9);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 4, 10);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 5, 1);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 5, 2);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 5, 3);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 5, 4);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 5, 5);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 6, 6);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 6, 7);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 6, 8);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 6, 9);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 6, 10);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 1);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 2);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 3);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 4);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 5);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 6);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 7, 7);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 8, 8);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 8, 9);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 8, 10);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 9, 1);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 9, 2);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 9, 3);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 9, 4);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 9, 5);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 10, 6);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 10, 7);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 10, 8);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 10, 9);

INSERT  INTO job_application
    (deleted, job_advertisement_id, student_id)
VALUES
    (false, 10, 10);

-- INSERT INTO subscriber
-- (subscriber_id, token) VALUES (1, 'dy6664oRPgXsA-iLNZn8Lb:APA91bHp4OfcGtkYFzJLXr7aDVPm3USoW6WdLYvsbQhntAWsXrpBd2VhBiU9_eKu-NTuktZFCe7-_AF0JRzcLLeWmyqgFEn3dDNSckW1kKoBJimMa1TIjsir7sJhDQwU-kL7uMP3SWDB');

--     INSERT INTO subscriber
-- (subscriber_id, token) VALUES (2, 'eGZGsQlStcY-BH5TURs8p4:APA91bGflvqydTXR20mQVLF8jOGmAO5gnxR-kv1mmiY4n0_Ymt-1exvih6KTGdxOxY18h4_oAP3dRqxBcfa-_vYkjNZzwATKoOR2coCXViVNM5hRymFskfLg4psk1YRzTPQAK9QEQ3K7');
