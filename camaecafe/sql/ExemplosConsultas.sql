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
SELECT count(1) FROM scrape scr
        inner join listing lis
        on scr.id = lis.scrape_id and scr.location_id = 4
        inner join review rev
        on lis.id = rev.listing_id and (rev.send_date BETWEEN '2021-01-01'and '2021-01-31');

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