INSERT INTO users (
    public_id,
    username,
    password,
    first_name,
    last_name,
    phone_number,
    district
)
VALUES (
           '100001',
           'ilnur',
            -- hash, generated using generator WebSite for password 'ilnur089'
           '{bcrypt}$2a$10$6UJu1JgbUMSrcRQ4bDV5LOoZMjcLsKKsahmTTpg8TV60HrbjTrFzy',
           'ilnur',
           'yamaletdinov',
           '+998900000000',
           'SHAYHANTAHUR'
       );

INSERT INTO users (
    public_id,
    username,
    password,
    first_name,
    last_name,
    phone_number,
    district
)
VALUES (
           '111111',
           'runli',
           -- hash, generated using generator WebSite for password 'ilnur089'
           '{noop}ilnur',
           'ilnur',
           'yamaletdinov',
           '+998900000000',
           'SHAYHANTAHUR'
       );