DELETE
FROM wrote;

DELETE
FROM bsection;

DELETE
FROM author;

DELETE
FROM book;

INSERT INTO bsection (sectionidid, name)
    VALUES(1, 'Fiction'),
          (2, 'Technology'),
          (3, 'Travel'),
          (4, 'Business'),
          (5, 'Religion');

INSERT INTO author (authorid, fname, lname)
    VALUES (1, 'John', 'Mitchell');
           (2, 'Dan', 'Brown');
           (3, 'Jerry', 'Poe');
           (4, 'Wells', 'Teague');
           (5, 'George', 'Gallinger');
           (6, 'Ian', 'Stewart');

INSERT INTO book (bookid, title, ISBN, copy, sectionid)
    VALUES (1, 'Flatterland', '9780738206752', 2001, 1),
           (2, 'Digital Fortess', '9788489367012', 2007, 1),
           (3, 'The Da Vinci Code', '9780307474278', 2009, 1),
           (4, 'Essentials of Finance', '1314241651234', NULL, 4),
           (5, 'Calling Texas Home', '1885171382134', 2000, 3);


INSERT INTO wrote (bookid, authorid)
    VALUES (1, 6);
           (2, 2);
           (3, 2);
           (4, 5);
           (4, 3);
           (5, 4);



alter sequence hibernate_sequence restart with 25;