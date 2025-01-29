CREATE TABLE SPEECH (
    ID UUID PRIMARY KEY,
    AUTHOR VARCHAR(255) NOT NULL,
    SPEECH_DATE DATE NOT NULL,
    CONTENT TEXT NOT NULL
);

CREATE TABLE KEYWORDS (
    ID UUID PRIMARY KEY,
    SPEECH_ID UUID NOT NULL,
    KEYWORD VARCHAR(255) NOT NULL,
    CONSTRAINT fk_speech FOREIGN KEY (SPEECH_ID) REFERENCES SPEECH(ID) ON DELETE CASCADE
);

CREATE INDEX idx_keyword_keyword ON KEYWORDS(KEYWORD);
CREATE INDEX idx_keyword_speech_id ON KEYWORDS(SPEECH_ID);

-- Insert data into the SPEECH table
INSERT INTO SPEECH (ID, AUTHOR, SPEECH_DATE, CONTENT)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Dr. Martin Luther King Jr.', '1963-08-28', 'I have a dream that one day every valley shall be exalted, every hill and mountain shall be made low.'),
    ('e0c8328e-99cc-44a5-b5a3-22227b2c5d5a', 'John F. Kennedy', '1961-01-20', 'Ask not what your country can do for you; ask what you can do for your country.'),
    ('f8c58b18-88ff-4518-aaf2-6e9eebd66d64', 'Barack Obama', '2008-11-04', 'If there is anyone out there who still doubts that America is a place where all things are possible, tonight is your answer.');

-- Insert data into the KEYWORDS table
INSERT INTO KEYWORDS (ID, SPEECH_ID, KEYWORD)
VALUES
    ('a3c7cb44-3f33-4001-b4c7-4822b7a7696d', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'dream'),
    ('b76f2d56-5ec4-40a5-a563-94d6b5e8b5c2', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'valley'),
    ('c2d2e8b9-9cbf-4b7e-bf3c-2e9571a5dc3c', 'e0c8328e-99cc-44a5-b5a3-22227b2c5d5a', 'country'),
    ('d1e43d6f-0e93-4963-98c8-cc6347d45cba', 'e0c8328e-99cc-44a5-b5a3-22227b2c5d5a', 'ask'),
    ('e9c5487f-2e91-4fa5-9ed3-5f36b45c6c21', 'f8c58b18-88ff-4518-aaf2-6e9eebd66d64', 'America'),
    ('f2b9e784-bdf7-404d-bff8-e3c734d4e6c3', 'f8c58b18-88ff-4518-aaf2-6e9eebd66d64', 'possible'),
    ('a4e7c85b-60c8-41b1-9c84-6d23768bcd45', 'f8c58b18-88ff-4518-aaf2-6e9eebd66d64', 'doubts');
