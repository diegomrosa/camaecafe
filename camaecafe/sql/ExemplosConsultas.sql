USE airbnbscraper;

-- Amount of reviews by city.
SELECT loc.city, count(rev.id) FROM review rev
    inner join listing lis
    on rev.listing_id = lis.id
    inner join scrape scr
    on lis.scrape_id = scr.id
    inner join location loc
    on scr.location_id = loc.id
    group by loc.city;

-- Amount of reviews by city by year (1).
SELECT loc.city, count(rev.id) FROM review rev
    inner join listing lis
    on (rev.listing_id = lis.id) and (rev.send_date BETWEEN '2019-01-01'and '2019-12-31')
    inner join scrape scr
    on lis.scrape_id = scr.id
    inner join location loc
    on scr.location_id = loc.id and loc.city = 'Rio de Janeiro';
    
-- Amount of reviews by city by year (2).
SELECT rev.send_date FROM scrape scr
        inner join listing lis
        on scr.id = lis.scrape_id and scr.location_id = 4
        inner join review rev
        on lis.id = rev.listing_id and (rev.send_date BETWEEN '2016-01-01'and '2016-01-31');

-- Amount of listings by city.
SELECT loc.city, count(lis.id) FROM listing lis
    inner join scrape scr
    on lis.scrape_id = scr.id
    inner join location loc
    on scr.location_id = loc.id
    group by loc.city;

SELECT COUNT(1) FROM airbnb_user;

SELECT COUNT(1) FROM review;

SELECT COUNT(1) FROM listing;

-- The first reviews from Beijing.
SELECT rev.send_date FROM scrape scr
        inner join listing lis
        on scr.id = lis.scrape_id and scr.location_id = 1
        inner join review rev
        on lis.id = rev.listing_id and (rev.send_date BETWEEN '2014-01-01'and '2014-12-31');

DELETE FROM review WHERE send_date < '2016-07-01';

DELETE FROM review WHERE send_date >= '2021-07-01';

SELECT COUNT(1) FROM review;

SELECT usr.user_name, COUNT(rev.id) as nrevs FROM airbnb_user usr
    LEFT JOIN review rev
    ON usr.id = rev.reviewer_id AND usr.location_id IS NULL AND (rev.send_date BETWEEN '2016-01-01'and '2016-06-01')
    GROUP BY usr.id;

SELECT COUNT(1) FROM airbnb_user usr WHERE usr.id NOT IN (SELECT rev.reviewer_id FROM review rev UNION SELECT lis.host_id FROM listing lis);

DELETE FROM airbnb_user WHERE id NOT IN (SELECT rev.reviewer_id FROM review rev UNION SELECT lis.host_id FROM listing lis);

SELECT COUNT(1) FROM airbnb_user;

SELECT COUNT(1) FROM review;

SELECT usr.id, usr.is_host, usr.user_name, rev.send_date FROM airbnb_user usr
    INNER JOIN review rev
    ON usr.is_host = 1 AND usr.id = rev.reviewer_id;

SELECT COUNT(1) FROM airbnb_user usr
    INNER JOIN review rev
    ON usr.is_host = 1 AND usr.id = rev.reviewer_id;

SELECT COUNT(reviewer_id) FROM review WHERE send_date BETWEEN '2016-07-01' AND '2016-07-31';

ALTER TABLE review ADD COLUMN comments_lang_confidence VARCHAR(6);

ALTER TABLE review DROP CONSTRAINT review_lang_fk;
ALTER TABLE review DROP COLUMN comments_lang_id;
ALTER TABLE review ADD COLUMN comments_lang_iso VARCHAR(6);

