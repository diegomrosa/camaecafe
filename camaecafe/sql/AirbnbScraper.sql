DROP DATABASE IF EXISTS airbnbscraper;

CREATE DATABASE airbnbscraper;
USE airbnbscraper;

CREATE TABLE lang (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lang_639_1 VARCHAR(2) NOT NULL,
    lang_639_2T VARCHAR(3) NOT NULL,
    lang_639_2B VARCHAR(3) NOT NULL,
    lang_name VARCHAR(80) NOT NULL,
    native_name VARCHAR(80) NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE country (
    code CHAR(2) NOT NULL COMMENT 'Two-letter country code (ISO 3166-1 alpha-2)',
    name VARCHAR(255) NOT NULL COMMENT 'English country name',
    full_name VARCHAR(255) NOT NULL COMMENT 'Full English country name',
    iso3 CHAR(3) NOT NULL COMMENT 'Three-letter country code (ISO 3166-1 alpha-3)',
    number CHAR(3) NOT NULL COMMENT 'Three-digit country number (ISO 3166-1 numeric)',
    continent_code CHAR(2) NOT NULL,
    PRIMARY KEY (code));

CREATE TABLE location (
    id BIGINT NOT NULL AUTO_INCREMENT,
    raw_text VARCHAR(200) NOT NULL,
    city VARCHAR(40),
    region VARCHAR(40),
    country VARCHAR(40),
    PRIMARY KEY (id)); 

CREATE TABLE scrape (
    id BIGINT NOT NULL,
    scrape_date DATE NOT NULL,
    location_id BIGINT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE airbnb_user (
    id BIGINT NOT NULL,
    is_host BOOLEAN NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    location_id BIGINT,
    PRIMARY KEY (id));

CREATE TABLE listing (
    id BIGINT NOT NULL,
    listing_url VARCHAR(40) NOT NULL,
    scrape_id BIGINT NOT NULL,
    host_id BIGINT NOT NULL,
    listing_name TEXT NOT NULL,
    listing_description TEXT NOT NULL,
    description_lang_id BIGINT,
    PRIMARY KEY (id));

CREATE TABLE review (
    id BIGINT NOT NULL,
    send_date DATE,
    comments TEXT,
    comments_lang_id BIGINT,
    reviewer_id BIGINT,
    listing_id BIGINT NOT NULL,
    PRIMARY KEY (id));

ALTER TABLE scrape ADD CONSTRAINT scrape_location_fk FOREIGN KEY (location_id) REFERENCES location (id);
ALTER TABLE airbnb_user ADD CONSTRAINT user_location_fk FOREIGN KEY (location_id) REFERENCES location (id);
ALTER TABLE listing ADD CONSTRAINT listing_scrape_fk FOREIGN KEY (scrape_id) REFERENCES scrape(id);
ALTER TABLE listing ADD CONSTRAINT listing_host_fk FOREIGN KEY (host_id) REFERENCES airbnb_user(id);
ALTER TABLE listing ADD CONSTRAINT listing_lang_fk FOREIGN KEY (description_lang_id) REFERENCES lang(id);
ALTER TABLE review ADD CONSTRAINT review_reviewer_fk FOREIGN KEY (reviewer_id) REFERENCES airbnb_user(id);
ALTER TABLE review ADD CONSTRAINT review_listing_fk FOREIGN KEY (listing_id) REFERENCES listing(id);
ALTER TABLE review ADD CONSTRAINT review_lang_fk FOREIGN KEY (comments_lang_id) REFERENCES lang(id);

INSERT INTO lang VALUES (0, "aa", "aar", "aar", "Afar", "Afaraf");
INSERT INTO lang VALUES (NULL, "ab", "abk", "abk", "Abkhaz", "аҧсуа бызшәа, аҧсшәа");
INSERT INTO lang VALUES (NULL, "ae", "ave", "ave", "Avestan", "avesta");
INSERT INTO lang VALUES (NULL, "af", "afr", "afr", "Afrikaans", "Afrikaans");
INSERT INTO lang VALUES (NULL, "ak", "aka", "aka", "Akan", "Akan");
INSERT INTO lang VALUES (NULL, "am", "amh", "amh", "Amharic", "አማርኛ");
INSERT INTO lang VALUES (NULL, "an", "arg", "arg", "Aragonese", "aragonés");
INSERT INTO lang VALUES (NULL, "ar", "ara", "ara", "Arabic", "العربية");
INSERT INTO lang VALUES (NULL, "as", "asm", "asm", "Assamese", "অসমীয়া");
INSERT INTO lang VALUES (NULL, "av", "ava", "ava", "Avaric", "авар мацӀ, магӀарул мацӀ");
INSERT INTO lang VALUES (NULL, "ay", "aym", "aym", "Aymara", "aymar aru");
INSERT INTO lang VALUES (NULL, "az", "aze", "aze", "Azerbaijani", "azərbaycan dili");
INSERT INTO lang VALUES (NULL, "az", "azb", "azb", "South Azerbaijani", "تورکجه‎");
INSERT INTO lang VALUES (NULL, "ba", "bak", "bak", "Bashkir", "башҡорт теле");
INSERT INTO lang VALUES (NULL, "be", "bel", "bel", "Belarusian", "беларуская мова");
INSERT INTO lang VALUES (NULL, "bg", "bul", "bul", "Bulgarian", "български език");
INSERT INTO lang VALUES (NULL, "bh", "bih", "bih", "Bihari", "भोजपुरी");
INSERT INTO lang VALUES (NULL, "bi", "bis", "bis", "Bislama", "Bislama");
INSERT INTO lang VALUES (NULL, "bm", "bam", "bam", "Bambara", "bamanankan");
INSERT INTO lang VALUES (NULL, "bn", "ben", "ben", "Bengali; Bangla", "বাংলা");
INSERT INTO lang VALUES (NULL, "bo", "bod", "tib", "Tibetan Standard, Tibetan, Central", "བོད་ཡིག");
INSERT INTO lang VALUES (NULL, "br", "bre", "bre", "Breton", "brezhoneg");
INSERT INTO lang VALUES (NULL, "bs", "bos", "bos", "Bosnian", "bosanski jezik");
INSERT INTO lang VALUES (NULL, "ca", "cat", "cat", "Catalan; Valencian", "català, valencià");
INSERT INTO lang VALUES (NULL, "ce", "che", "che", "Chechen", "нохчийн мотт");
INSERT INTO lang VALUES (NULL, "ch", "cha", "cha", "Chamorro", "Chamoru");
INSERT INTO lang VALUES (NULL, "co", "cos", "cos", "Corsican", "corsu, lingua corsa");
INSERT INTO lang VALUES (NULL, "cr", "cre", "cre", "Cree", "ᓀᐦᐃᔭᐍᐏᐣ");
INSERT INTO lang VALUES (NULL, "cs", "ces", "cze", "Czech", "čeština, český jazyk");
INSERT INTO lang VALUES (NULL, "cu", "chu", "chu", "Old Church Slavonic, Church Slavonic, Old Bulgarian ", "ѩзыкъ словѣньскъ");
INSERT INTO lang VALUES (NULL, "cv", "chv", "chv", "Chuvash", "чӑваш чӗлхи");
INSERT INTO lang VALUES (NULL, "cy", "cym", "wel", "Welsh", "Cymraeg");
INSERT INTO lang VALUES (NULL, "da", "dan", "dan", "Danish", "dansk");
INSERT INTO lang VALUES (NULL, "de", "deu", "ger", "German", "Deutsch");
INSERT INTO lang VALUES (NULL, "dv", "div", "div", "Divehi; Dhivehi; Maldivian;", "ދިވެހި");
INSERT INTO lang VALUES (NULL, "dz", "dzo", "dzo", "Dzongkha", "རྫོང་ཁ");
INSERT INTO lang VALUES (NULL, "ee", "ewe", "ewe", "Ewe", "Eʋegbe");
INSERT INTO lang VALUES (NULL, "el", "ell", "gre", "Greek, Modern", "ελληνικά");
INSERT INTO lang VALUES (NULL, "en", "eng", "eng", "English", "English");
INSERT INTO lang VALUES (NULL, "eo", "epo", "epo", "Esperanto", "Esperanto");
INSERT INTO lang VALUES (NULL, "es", "spa", "spa", "Spanish; Castilian", "español, castellano");
INSERT INTO lang VALUES (NULL, "et", "est", "est", "Estonian", "eesti, eesti keel");
INSERT INTO lang VALUES (NULL, "eu", "eus", "baq", "Basque", "euskara, euskera");
INSERT INTO lang VALUES (NULL, "fa", "fas", "per", "Persian (Farsi)", "فارسی");
INSERT INTO lang VALUES (NULL, "ff", "ful", "ful", "Fula; Fulah; Pulaar; Pular", "Fulfulde, Pulaar, Pular");
INSERT INTO lang VALUES (NULL, "fi", "fin", "fin", "Finnish", "suomi, suomen kieli");
INSERT INTO lang VALUES (NULL, "fj", "fij", "fij", "Fijian", "vosa Vakaviti");
INSERT INTO lang VALUES (NULL, "fo", "fao", "fao", "Faroese", "føroyskt");
INSERT INTO lang VALUES (NULL, "fr", "fra", "fre", "French", "français, langue française");
INSERT INTO lang VALUES (NULL, "fy", "fry", "fry", "Western Frisian", "Frysk");
INSERT INTO lang VALUES (NULL, "ga", "gle", "gle", "Irish", "Gaeilge");
INSERT INTO lang VALUES (NULL, "gd", "gla", "gla", "Scottish Gaelic; Gaelic", "Gàidhlig");
INSERT INTO lang VALUES (NULL, "gl", "glg", "glg", "Galician", "galego");
INSERT INTO lang VALUES (NULL, "gn", "grn", "grn", "Guaraní", "Avañe'ẽ");
INSERT INTO lang VALUES (NULL, "gu", "guj", "guj", "Gujarati", "ગુજરાતી");
INSERT INTO lang VALUES (NULL, "gv", "glv", "glv", "Manx", "Gaelg, Gailck");
INSERT INTO lang VALUES (NULL, "ha", "hau", "hau", "Hausa", "Hausa, هَوُسَ");
INSERT INTO lang VALUES (NULL, "he", "heb", "heb", "Hebrew (modern)", "עברית");
INSERT INTO lang VALUES (NULL, "hi", "hin", "hin", "Hindi", "हिन्दी, हिंदी");
INSERT INTO lang VALUES (NULL, "ho", "hmo", "hmo", "Hiri Motu", "Hiri Motu");
INSERT INTO lang VALUES (NULL, "hr", "hrv", "hrv", "Croatian", "hrvatski jezik");
INSERT INTO lang VALUES (NULL, "ht", "hat", "hat", "Haitian; Haitian Creole", "Kreyòl ayisyen");
INSERT INTO lang VALUES (NULL, "hu", "hun", "hun", "Hungarian", "magyar");
INSERT INTO lang VALUES (NULL, "hy", "hye", "arm", "Armenian", "Հայերեն");
INSERT INTO lang VALUES (NULL, "hz", "her", "her", "Herero", "Otjiherero");
INSERT INTO lang VALUES (NULL, "ia", "ina", "ina", "Interlingua", "Interlingua");
INSERT INTO lang VALUES (NULL, "id", "ind", "ind", "Indonesian", "Bahasa Indonesia");
INSERT INTO lang VALUES (NULL, "ie", "ile", "ile", "Interlingue", "Originally called Occidental; then Interlingue after WWII");
INSERT INTO lang VALUES (NULL, "ig", "ibo", "ibo", "Igbo", "Asụsụ Igbo");
INSERT INTO lang VALUES (NULL, "ii", "iii", "iii", "Nuosu", "ꆈꌠ꒿ Nuosuhxop");
INSERT INTO lang VALUES (NULL, "ik", "ipk", "ipk", "Inupiaq", "Iñupiaq, Iñupiatun");
INSERT INTO lang VALUES (NULL, "io", "ido", "ido", "Ido", "Ido");
INSERT INTO lang VALUES (NULL, "is", "isl", "ice", "Icelandic", "Íslenska");
INSERT INTO lang VALUES (NULL, "it", "ita", "ita", "Italian", "italiano");
INSERT INTO lang VALUES (NULL, "iu", "iku", "iku", "Inuktitut", "ᐃᓄᒃᑎᑐᑦ");
INSERT INTO lang VALUES (NULL, "ja", "jpn", "jpn", "Japanese", "日本語 (にほんご)");
INSERT INTO lang VALUES (NULL, "jv", "jav", "jav", "Javanese", "basa Jawa");
INSERT INTO lang VALUES (NULL, "ka", "kat", "geo", "Georgian", "ქართული");
INSERT INTO lang VALUES (NULL, "kg", "kon", "kon", "Kongo", "KiKongo");
INSERT INTO lang VALUES (NULL, "ki", "kik", "kik", "Kikuyu, Gikuyu", "Gĩkũyũ");
INSERT INTO lang VALUES (NULL, "kj", "kua", "kua", "Kwanyama, Kuanyama", "Kuanyama");
INSERT INTO lang VALUES (NULL, "kk", "kaz", "kaz", "Kazakh", "қазақ тілі");
INSERT INTO lang VALUES (NULL, "kl", "kal", "kal", "Kalaallisut, Greenlandic", "kalaallisut, kalaallit oqaasii");
INSERT INTO lang VALUES (NULL, "km", "khm", "khm", "Khmer", "ខ្មែរ, ខេមរភាសា, ភាសាខ្មែរ");
INSERT INTO lang VALUES (NULL, "kn", "kan", "kan", "Kannada", "ಕನ್ನಡ");
INSERT INTO lang VALUES (NULL, "ko", "kor", "kor", "Korean", "한국어 (韓國語), 조선어 (朝鮮語)");
INSERT INTO lang VALUES (NULL, "kr", "kau", "kau", "Kanuri", "Kanuri");
INSERT INTO lang VALUES (NULL, "ks", "kas", "kas", "Kashmiri", "कश्मीरी, كشميري‎");
INSERT INTO lang VALUES (NULL, "ku", "kur", "kur", "Kurdish", "Kurdî, كوردی‎");
INSERT INTO lang VALUES (NULL, "kv", "kom", "kom", "Komi", "коми кыв");
INSERT INTO lang VALUES (NULL, "kw", "cor", "cor", "Cornish", "Kernewek");
INSERT INTO lang VALUES (NULL, "ky", "kir", "kir", "Kyrgyz", "Кыргызча, Кыргыз тили");
INSERT INTO lang VALUES (NULL, "la", "lat", "lat", "Latin", "latine, lingua latina");
INSERT INTO lang VALUES (NULL, "lb", "ltz", "ltz", "Luxembourgish, Letzeburgesch", "Lëtzebuergesch");
INSERT INTO lang VALUES (NULL, "lg", "lug", "lug", "Ganda", "Luganda");
INSERT INTO lang VALUES (NULL, "li", "lim", "lim", "Limburgish, Limburgan, Limburger", "Limburgs");
INSERT INTO lang VALUES (NULL, "ln", "lin", "lin", "Lingala", "Lingála");
INSERT INTO lang VALUES (NULL, "lo", "lao", "lao", "Lao", "ພາສາລາວ");
INSERT INTO lang VALUES (NULL, "lt", "lit", "lit", "Lithuanian", "lietuvių kalba");
INSERT INTO lang VALUES (NULL, "lu", "lub", "lub", "Luba-Katanga", "Tshiluba");
INSERT INTO lang VALUES (NULL, "lv", "lav", "lav", "Latvian", "latviešu valoda");
INSERT INTO lang VALUES (NULL, "mg", "mlg", "mlg", "Malagasy", "fiteny malagasy");
INSERT INTO lang VALUES (NULL, "mh", "mah", "mah", "Marshallese", "Kajin M̧ajeļ");
INSERT INTO lang VALUES (NULL, "mi", "mri", "mao", "Māori", "te reo Māori");
INSERT INTO lang VALUES (NULL, "mk", "mkd", "mac", "Macedonian", "македонски јазик");
INSERT INTO lang VALUES (NULL, "ml", "mal", "mal", "Malayalam", "മലയാളം");
INSERT INTO lang VALUES (NULL, "mn", "mon", "mon", "Mongolian", "монгол");
INSERT INTO lang VALUES (NULL, "mr", "mar", "mar", "Marathi (Marāṭhī)", "मराठी");
INSERT INTO lang VALUES (NULL, "ms", "msa", "may", "Malay", "bahasa Melayu, بهاس ملايو‎");
INSERT INTO lang VALUES (NULL, "mt", "mlt", "mlt", "Maltese", "Malti");
INSERT INTO lang VALUES (NULL, "my", "mya", "bur", "Burmese", "ဗမာစာ");
INSERT INTO lang VALUES (NULL, "na", "nau", "nau", "Nauru", "Ekakairũ Naoero");
INSERT INTO lang VALUES (NULL, "nb", "nob", "nob", "Norwegian Bokmål", "Norsk bokmål");
INSERT INTO lang VALUES (NULL, "nd", "nde", "nde", "North Ndebele", "isiNdebele");
INSERT INTO lang VALUES (NULL, "ne", "nep", "nep", "Nepali", "नेपाली");
INSERT INTO lang VALUES (NULL, "ng", "ndo", "ndo", "Ndonga", "Owambo");
INSERT INTO lang VALUES (NULL, "nl", "nld", "dut", "Dutch", "Nederlands, Vlaams");
INSERT INTO lang VALUES (NULL, "nn", "nno", "nno", "Norwegian Nynorsk", "Norsk nynorsk");
INSERT INTO lang VALUES (NULL, "no", "nor", "nor", "Norwegian", "Norsk");
INSERT INTO lang VALUES (NULL, "nr", "nbl", "nbl", "South Ndebele", "isiNdebele");
INSERT INTO lang VALUES (NULL, "nv", "nav", "nav", "Navajo, Navaho", "Diné bizaad, Dinékʼehǰí");
INSERT INTO lang VALUES (NULL, "ny", "nya", "nya", "Chichewa; Chewa; Nyanja", "chiCheŵa, chinyanja");
INSERT INTO lang VALUES (NULL, "oc", "oci", "oci", "Occitan", "occitan, lenga d'òc");
INSERT INTO lang VALUES (NULL, "oj", "oji", "oji", "Ojibwe, Ojibwa", "ᐊᓂᔑᓈᐯᒧᐎᓐ");
INSERT INTO lang VALUES (NULL, "om", "orm", "orm", "Oromo", "Afaan Oromoo");
INSERT INTO lang VALUES (NULL, "or", "ori", "ori", "Oriya", "ଓଡ଼ିଆ");
INSERT INTO lang VALUES (NULL, "os", "oss", "oss", "Ossetian, Ossetic", "ирон æвзаг");
INSERT INTO lang VALUES (NULL, "pa", "pan", "pan", "Panjabi, Punjabi", "ਪੰਜਾਬੀ, پنجابی");
INSERT INTO lang VALUES (NULL, "pi", "pli", "pli", "Pāli", "पाऴि");
INSERT INTO lang VALUES (NULL, "pl", "pol", "pol", "Polish", "język polski, polszczyzna");
INSERT INTO lang VALUES (NULL, "ps", "pus", "pus", "Pashto, Pushto", "پښتو");
INSERT INTO lang VALUES (NULL, "pt", "por", "por", "Portuguese", "português");
INSERT INTO lang VALUES (NULL, "qu", "que", "que", "Quechua", "Runa Simi, Kichwa");
INSERT INTO lang VALUES (NULL, "rm", "roh", "roh", "Romansh", "rumantsch grischun");
INSERT INTO lang VALUES (NULL, "rn", "run", "run", "Kirundi", "Ikirundi");
INSERT INTO lang VALUES (NULL, "ro", "ron", "rum", "Romanian", "limba română");
INSERT INTO lang VALUES (NULL, "ru", "rus", "rus", "Russian", "русский язык");
INSERT INTO lang VALUES (NULL, "rw", "kin", "kin", "Kinyarwanda", "Ikinyarwanda");
INSERT INTO lang VALUES (NULL, "sa", "san", "san", "Sanskrit (Saṁskṛta)", "संस्कृतम्");
INSERT INTO lang VALUES (NULL, "sc", "srd", "srd", "Sardinian", "sardu");
INSERT INTO lang VALUES (NULL, "sd", "snd", "snd", "Sindhi", "सिन्धी, سنڌي، سندھی‎");
INSERT INTO lang VALUES (NULL, "se", "sme", "sme", "Northern Sami", "Davvisámegiella");
INSERT INTO lang VALUES (NULL, "sg", "sag", "sag", "Sango", "yângâ tî sängö");
INSERT INTO lang VALUES (NULL, "si", "sin", "sin", "Sinhala, Sinhalese", "සිංහල");
INSERT INTO lang VALUES (NULL, "sk", "slk", "slo", "Slovak", "slovenčina, slovenský jazyk");
INSERT INTO lang VALUES (NULL, "sl", "slv", "slv", "Slovene", "slovenski jezik, slovenščina");
INSERT INTO lang VALUES (NULL, "sm", "smo", "smo", "Samoan", "gagana fa'a Samoa");
INSERT INTO lang VALUES (NULL, "sn", "sna", "sna", "Shona", "chiShona");
INSERT INTO lang VALUES (NULL, "so", "som", "som", "Somali", "Soomaaliga, af Soomaali");
INSERT INTO lang VALUES (NULL, "sr", "srp", "srp", "Serbian", "српски језик");
INSERT INTO lang VALUES (NULL, "ss", "ssw", "ssw", "Swati", "SiSwati");
INSERT INTO lang VALUES (NULL, "st", "sot", "sot", "Southern Sotho", "Sesotho");
INSERT INTO lang VALUES (NULL, "su", "sun", "sun", "Sundanese", "Basa Sunda");
INSERT INTO lang VALUES (NULL, "sv", "swe", "swe", "Swedish", "Svenska");
INSERT INTO lang VALUES (NULL, "sw", "swa", "swa", "Swahili", "Kiswahili");
INSERT INTO lang VALUES (NULL, "ta", "tam", "tam", "Tamil", "தமிழ்");
INSERT INTO lang VALUES (NULL, "te", "tel", "tel", "Telugu", "తెలుగు");
INSERT INTO lang VALUES (NULL, "tg", "tgk", "tgk", "Tajik", "тоҷикӣ, toğikī, تاجیکی‎");
INSERT INTO lang VALUES (NULL, "th", "tha", "tha", "Thai", "ไทย");
INSERT INTO lang VALUES (NULL, "ti", "tir", "tir", "Tigrinya", "ትግርኛ");
INSERT INTO lang VALUES (NULL, "tk", "tuk", "tuk", "Turkmen", "Türkmen, Түркмен");
INSERT INTO lang VALUES (NULL, "tl", "tgl", "tgl", "Tagalog", "Wikang Tagalog, ᜏᜒᜃᜅ᜔ ᜆᜄᜎᜓᜄ᜔");
INSERT INTO lang VALUES (NULL, "tn", "tsn", "tsn", "Tswana", "Setswana");
INSERT INTO lang VALUES (NULL, "to", "ton", "ton", "Tonga (Tonga Islands)", "faka Tonga");
INSERT INTO lang VALUES (NULL, "tr", "tur", "tur", "Turkish", "Türkçe");
INSERT INTO lang VALUES (NULL, "ts", "tso", "tso", "Tsonga", "Xitsonga");
INSERT INTO lang VALUES (NULL, "tt", "tat", "tat", "Tatar", "татар теле, tatar tele");
INSERT INTO lang VALUES (NULL, "tw", "twi", "twi", "Twi", "Twi");
INSERT INTO lang VALUES (NULL, "ty", "tah", "tah", "Tahitian", "Reo Tahiti");
INSERT INTO lang VALUES (NULL, "ug", "uig", "uig", "Uyghur, Uighur","Uyƣurqə, ئۇيغۇرچە‎");
INSERT INTO lang VALUES (NULL, "uk", "ukr", "ukr", "Ukrainian", "українська мова");
INSERT INTO lang VALUES (NULL, "ur", "urd", "urd", "Urdu", "اردو");
INSERT INTO lang VALUES (NULL, "uz", "uzb", "uzb", "Uzbek", "O‘zbek, Ўзбек, أۇزبېك‎");
INSERT INTO lang VALUES (NULL, "ve", "ven", "ven", "Venda", "Tshivenḓa");
INSERT INTO lang VALUES (NULL, "vi", "vie", "vie", "Vietnamese", "Tiếng Việt");
INSERT INTO lang VALUES (NULL, "vo", "vol", "vol", "Volapük", "Volapük");
INSERT INTO lang VALUES (NULL, "wa", "wln", "wln", "Walloon", "walon");
INSERT INTO lang VALUES (NULL, "wo", "wol", "wol", "Wolof", "Wollof");
INSERT INTO lang VALUES (NULL, "xh", "xho", "xho", "Xhosa", "isiXhosa");
INSERT INTO lang VALUES (NULL, "yi", "yid", "yid", "Yiddish", "ייִדיש");
INSERT INTO lang VALUES (NULL, "yo", "yor", "yor", "Yoruba", "Yorùbá");
INSERT INTO lang VALUES (NULL, "za", "zha", "zha", "Zhuang, Chuang", "Saɯ cueŋƅ, Saw cuengh");
INSERT INTO lang VALUES (NULL, "zh", "zho", "chi", "Chinese", "中文 (Zhōngwén), 汉语, 漢語");
INSERT INTO lang VALUES (NULL, "zu", "zul", "zul", "Zulu", "isiZulu");

INSERT INTO country (`code`, `continent_code`, `name`, `iso3`, `number`, `full_name`) VALUES
  ('AF', 'AS', "Afghanistan", 'AFG', '004', "Islamic Republic of Afghanistan"),
  ('AX', 'EU', "Åland Islands", 'ALA', '248', "Åland Islands"),
  ('AL', 'EU', "Albania", 'ALB', '008', "Republic of Albania"),
  ('DZ', 'AF', "Algeria", 'DZA', '012', "People's Democratic Republic of Algeria"),
  ('AS', 'OC', "American Samoa", 'ASM', '016', "American Samoa"),
  ('AD', 'EU', "Andorra", 'AND', '020', "Principality of Andorra"),
  ('AO', 'AF', "Angola", 'AGO', '024', "Republic of Angola"),
  ('AI', 'NA', "Anguilla", 'AIA', '660', "Anguilla"),
  ('AQ', 'AN', "Antarctica", 'ATA', '010', "Antarctica (the territory South of 60 deg S)"),
  ('AG', 'NA', "Antigua and Barbuda", 'ATG', '028', "Antigua and Barbuda"),
  ('AR', 'SA', "Argentina", 'ARG', '032', "Argentine Republic"),
  ('AM', 'AS', "Armenia", 'ARM', '051', "Republic of Armenia"),
  ('AW', 'NA', "Aruba", 'ABW', '533', "Aruba"),
  ('AU', 'OC', "Australia", 'AUS', '036', "Commonwealth of Australia"),
  ('AT', 'EU', "Austria", 'AUT', '040', "Republic of Austria"),
  ('AZ', 'AS', "Azerbaijan", 'AZE', '031', "Republic of Azerbaijan"),
  ('BS', 'NA', "Bahamas", 'BHS', '044', "Commonwealth of the Bahamas"),
  ('BH', 'AS', "Bahrain", 'BHR', '048', "Kingdom of Bahrain"),
  ('BD', 'AS', "Bangladesh", 'BGD', '050', "People's Republic of Bangladesh"),
  ('BB', 'NA', "Barbados", 'BRB', '052', "Barbados"),
  ('BY', 'EU', "Belarus", 'BLR', '112', "Republic of Belarus"),
  ('BE', 'EU', "Belgium", 'BEL', '056', "Kingdom of Belgium"),
  ('BZ', 'NA', "Belize", 'BLZ', '084', "Belize"),
  ('BJ', 'AF', "Benin", 'BEN', '204', "Republic of Benin"),
  ('BM', 'NA', "Bermuda", 'BMU', '060', "Bermuda"),
  ('BT', 'AS', "Bhutan", 'BTN', '064', "Kingdom of Bhutan"),
  ('BO', 'SA', "Bolivia", 'BOL', '068', "Plurinational State of Bolivia"),
  ('BQ', 'NA', "Bonaire, Sint Eustatius and Saba", 'BES', '535', "Bonaire, Sint Eustatius and Saba"),
  ('BA', 'EU', "Bosnia and Herzegovina", 'BIH', '070', "Bosnia and Herzegovina"),
  ('BW', 'AF', "Botswana", 'BWA', '072', "Republic of Botswana"),
  ('BV', 'AN', "Bouvet Island (Bouvetøya)", 'BVT', '074', "Bouvet Island (Bouvetøya)"),
  ('BR', 'SA', "Brazil", 'BRA', '076', "Federative Republic of Brazil"),
  ('IO', 'AS', "British Indian Ocean Territory (Chagos Archipelago)", 'IOT', '086', "British Indian Ocean Territory (Chagos Archipelago)"),
  ('VG', 'NA', "British Virgin Islands", 'VGB', '092', "British Virgin Islands"),
  ('BN', 'AS', "Brunei Darussalam", 'BRN', '096', "Brunei Darussalam"),
  ('BG', 'EU', "Bulgaria", 'BGR', '100', "Republic of Bulgaria"),
  ('BF', 'AF', "Burkina Faso", 'BFA', '854', "Burkina Faso"),
  ('BI', 'AF', "Burundi", 'BDI', '108', "Republic of Burundi"),
  ('KH', 'AS', "Cambodia", 'KHM', '116', "Kingdom of Cambodia"),
  ('CM', 'AF', "Cameroon", 'CMR', '120', "Republic of Cameroon"),
  ('CA', 'NA', "Canada", 'CAN', '124', "Canada"),
  ('CV', 'AF', "Cabo Verde", 'CPV', '132', "Republic of Cabo Verde"),
  ('KY', 'NA', "Cayman Islands", 'CYM', '136', "Cayman Islands"),
  ('CF', 'AF', "Central African Republic", 'CAF', '140', "Central African Republic"),
  ('TD', 'AF', "Chad", 'TCD', '148', "Republic of Chad"),
  ('CL', 'SA', "Chile", 'CHL', '152', "Republic of Chile"),
  ('CN', 'AS', "China", 'CHN', '156', "People's Republic of China"),
  ('CX', 'AS', "Christmas Island", 'CXR', '162', "Christmas Island"),
  ('CC', 'AS', "Cocos (Keeling) Islands", 'CCK', '166', "Cocos (Keeling) Islands"),
  ('CO', 'SA', "Colombia", 'COL', '170', "Republic of Colombia"),
  ('KM', 'AF', "Comoros", 'COM', '174', "Union of the Comoros"),
  ('CD', 'AF', "Congo", 'COD', '180', "Democratic Republic of the Congo"),
  ('CG', 'AF', "Congo", 'COG', '178', "Republic of the Congo"),
  ('CK', 'OC', "Cook Islands", 'COK', '184', "Cook Islands"),
  ('CR', 'NA', "Costa Rica", 'CRI', '188', "Republic of Costa Rica"),
  ('CI', 'AF', "Cote d'Ivoire", 'CIV', '384', "Republic of Cote d'Ivoire"),
  ('HR', 'EU', "Croatia", 'HRV', '191', "Republic of Croatia"),
  ('CU', 'NA', "Cuba", 'CUB', '192', "Republic of Cuba"),
  ('CW', 'NA', "Curaçao", 'CUW', '531', "Curaçao"),
  ('CY', 'AS', "Cyprus", 'CYP', '196', "Republic of Cyprus"),
  ('CZ', 'EU', "Czechia", 'CZE', '203', "Czech Republic"),
  ('DK', 'EU', "Denmark", 'DNK', '208', "Kingdom of Denmark"),
  ('DJ', 'AF', "Djibouti", 'DJI', '262', "Republic of Djibouti"),
  ('DM', 'NA', "Dominica", 'DMA', '212', "Commonwealth of Dominica"),
  ('DO', 'NA', "Dominican Republic", 'DOM', '214', "Dominican Republic"),
  ('EC', 'SA', "Ecuador", 'ECU', '218', "Republic of Ecuador"),
  ('EG', 'AF', "Egypt", 'EGY', '818', "Arab Republic of Egypt"),
  ('SV', 'NA', "El Salvador", 'SLV', '222', "Republic of El Salvador"),
  ('GQ', 'AF', "Equatorial Guinea", 'GNQ', '226', "Republic of Equatorial Guinea"),
  ('ER', 'AF', "Eritrea", 'ERI', '232', "State of Eritrea"),
  ('EE', 'EU', "Estonia", 'EST', '233', "Republic of Estonia"),
  ('ET', 'AF', "Ethiopia", 'ETH', '231', "Federal Democratic Republic of Ethiopia"),
  ('FO', 'EU', "Faroe Islands", 'FRO', '234', "Faroe Islands"),
  ('FK', 'SA', "Falkland Islands (Malvinas)", 'FLK', '238', "Falkland Islands (Malvinas)"),
  ('FJ', 'OC', "Fiji", 'FJI', '242', "Republic of Fiji"),
  ('FI', 'EU', "Finland", 'FIN', '246', "Republic of Finland"),
  ('FR', 'EU', "France", 'FRA', '250', "French Republic"),
  ('GF', 'SA', "French Guiana", 'GUF', '254', "French Guiana"),
  ('PF', 'OC', "French Polynesia", 'PYF', '258', "French Polynesia"),
  ('TF', 'AN', "French Southern Territories", 'ATF', '260', "French Southern Territories"),
  ('GA', 'AF', "Gabon", 'GAB', '266', "Gabonese Republic"),
  ('GM', 'AF', "Gambia", 'GMB', '270', "Republic of the Gambia"),
  ('GE', 'AS', "Georgia", 'GEO', '268', "Georgia"),
  ('DE', 'EU', "Germany", 'DEU', '276', "Federal Republic of Germany"),
  ('GH', 'AF', "Ghana", 'GHA', '288', "Republic of Ghana"),
  ('GI', 'EU', "Gibraltar", 'GIB', '292', "Gibraltar"),
  ('GR', 'EU', "Greece", 'GRC', '300', "Hellenic Republic of Greece"),
  ('GL', 'NA', "Greenland", 'GRL', '304', "Greenland"),
  ('GD', 'NA', "Grenada", 'GRD', '308', "Grenada"),
  ('GP', 'NA', "Guadeloupe", 'GLP', '312', "Guadeloupe"),
  ('GU', 'OC', "Guam", 'GUM', '316', "Guam"),
  ('GT', 'NA', "Guatemala", 'GTM', '320', "Republic of Guatemala"),
  ('GG', 'EU', "Guernsey", 'GGY', '831', "Bailiwick of Guernsey"),
  ('GN', 'AF', "Guinea", 'GIN', '324', "Republic of Guinea"),
  ('GW', 'AF', "Guinea-Bissau", 'GNB', '624', "Republic of Guinea-Bissau"),
  ('GY', 'SA', "Guyana", 'GUY', '328', "Co-operative Republic of Guyana"),
  ('HT', 'NA', "Haiti", 'HTI', '332', "Republic of Haiti"),
  ('HM', 'AN', "Heard Island and McDonald Islands", 'HMD', '334', "Heard Island and McDonald Islands"),
  ('VA', 'EU', "Holy See (Vatican City State)", 'VAT', '336', "Holy See (Vatican City State)"),
  ('HN', 'NA', "Honduras", 'HND', '340', "Republic of Honduras"),
  ('HK', 'AS', "Hong Kong", 'HKG', '344', "Hong Kong Special Administrative Region of China"),
  ('HU', 'EU', "Hungary", 'HUN', '348', "Hungary"),
  ('IS', 'EU', "Iceland", 'ISL', '352', "Republic of Iceland"),
  ('IN', 'AS', "India", 'IND', '356', "Republic of India"),
  ('ID', 'AS', "Indonesia", 'IDN', '360', "Republic of Indonesia"),
  ('IR', 'AS', "Iran", 'IRN', '364', "Islamic Republic of Iran"),
  ('IQ', 'AS', "Iraq", 'IRQ', '368', "Republic of Iraq"),
  ('IE', 'EU', "Ireland", 'IRL', '372', "Ireland"),
  ('IM', 'EU', "Isle of Man", 'IMN', '833', "Isle of Man"),
  ('IL', 'AS', "Israel", 'ISR', '376', "State of Israel"),
  ('IT', 'EU', "Italy", 'ITA', '380', "Republic of Italy"),
  ('JM', 'NA', "Jamaica", 'JAM', '388', "Jamaica"),
  ('JP', 'AS', "Japan", 'JPN', '392', "Japan"),
  ('JE', 'EU', "Jersey", 'JEY', '832', "Bailiwick of Jersey"),
  ('JO', 'AS', "Jordan", 'JOR', '400', "Hashemite Kingdom of Jordan"),
  ('KZ', 'AS', "Kazakhstan", 'KAZ', '398', "Republic of Kazakhstan"),
  ('KE', 'AF', "Kenya", 'KEN', '404', "Republic of Kenya"),
  ('KI', 'OC', "Kiribati", 'KIR', '296', "Republic of Kiribati"),
  ('KP', 'AS', "Korea", 'PRK', '408', "Democratic People's Republic of Korea"),
  ('KR', 'AS', "Korea", 'KOR', '410', "Republic of Korea"),
  ('KW', 'AS', "Kuwait", 'KWT', '414', "State of Kuwait"),
  ('KG', 'AS', "Kyrgyz Republic", 'KGZ', '417', "Kyrgyz Republic"),
  ('LA', 'AS', "Lao People's Democratic Republic", 'LAO', '418', "Lao People's Democratic Republic"),
  ('LV', 'EU', "Latvia", 'LVA', '428', "Republic of Latvia"),
  ('LB', 'AS', "Lebanon", 'LBN', '422', "Lebanese Republic"),
  ('LS', 'AF', "Lesotho", 'LSO', '426', "Kingdom of Lesotho"),
  ('LR', 'AF', "Liberia", 'LBR', '430', "Republic of Liberia"),
  ('LY', 'AF', "Libya", 'LBY', '434', "State of Libya"),
  ('LI', 'EU', "Liechtenstein", 'LIE', '438', "Principality of Liechtenstein"),
  ('LT', 'EU', "Lithuania", 'LTU', '440', "Republic of Lithuania"),
  ('LU', 'EU', "Luxembourg", 'LUX', '442', "Grand Duchy of Luxembourg"),
  ('MO', 'AS', "Macao", 'MAC', '446', "Macao Special Administrative Region of China"),
  ('MG', 'AF', "Madagascar", 'MDG', '450', "Republic of Madagascar"),
  ('MW', 'AF', "Malawi", 'MWI', '454', "Republic of Malawi"),
  ('MY', 'AS', "Malaysia", 'MYS', '458', "Malaysia"),
  ('MV', 'AS', "Maldives", 'MDV', '462', "Republic of Maldives"),
  ('ML', 'AF', "Mali", 'MLI', '466', "Republic of Mali"),
  ('MT', 'EU', "Malta", 'MLT', '470', "Republic of Malta"),
  ('MH', 'OC', "Marshall Islands", 'MHL', '584', "Republic of the Marshall Islands"),
  ('MQ', 'NA', "Martinique", 'MTQ', '474', "Martinique"),
  ('MR', 'AF', "Mauritania", 'MRT', '478', "Islamic Republic of Mauritania"),
  ('MU', 'AF', "Mauritius", 'MUS', '480', "Republic of Mauritius"),
  ('YT', 'AF', "Mayotte", 'MYT', '175', "Mayotte"),
  ('MX', 'NA', "Mexico", 'MEX', '484', "United Mexican States"),
  ('FM', 'OC', "Micronesia", 'FSM', '583', "Federated States of Micronesia"),
  ('MD', 'EU', "Moldova", 'MDA', '498', "Republic of Moldova"),
  ('MC', 'EU', "Monaco", 'MCO', '492', "Principality of Monaco"),
  ('MN', 'AS', "Mongolia", 'MNG', '496', "Mongolia"),
  ('ME', 'EU', "Montenegro", 'MNE', '499', "Montenegro"),
  ('MS', 'NA', "Montserrat", 'MSR', '500', "Montserrat"),
  ('MA', 'AF', "Morocco", 'MAR', '504', "Kingdom of Morocco"),
  ('MZ', 'AF', "Mozambique", 'MOZ', '508', "Republic of Mozambique"),
  ('MM', 'AS', "Myanmar", 'MMR', '104', "Republic of the Union of Myanmar"),
  ('NA', 'AF', "Namibia", 'NAM', '516', "Republic of Namibia"),
  ('NR', 'OC', "Nauru", 'NRU', '520', "Republic of Nauru"),
  ('NP', 'AS', "Nepal", 'NPL', '524', "Nepal"),
  ('NL', 'EU', "Netherlands", 'NLD', '528', "Kingdom of the Netherlands"),
  ('NC', 'OC', "New Caledonia", 'NCL', '540', "New Caledonia"),
  ('NZ', 'OC', "New Zealand", 'NZL', '554', "New Zealand"),
  ('NI', 'NA', "Nicaragua", 'NIC', '558', "Republic of Nicaragua"),
  ('NE', 'AF', "Niger", 'NER', '562', "Republic of Niger"),
  ('NG', 'AF', "Nigeria", 'NGA', '566', "Federal Republic of Nigeria"),
  ('NU', 'OC', "Niue", 'NIU', '570', "Niue"),
  ('NF', 'OC', "Norfolk Island", 'NFK', '574', "Norfolk Island"),
  ('MK', 'EU', "North Macedonia", 'MKD', '807', "Republic of North Macedonia"),
  ('MP', 'OC', "Northern Mariana Islands", 'MNP', '580', "Commonwealth of the Northern Mariana Islands"),
  ('NO', 'EU', "Norway", 'NOR', '578', "Kingdom of Norway"),
  ('OM', 'AS', "Oman", 'OMN', '512', "Sultanate of Oman"),
  ('PK', 'AS', "Pakistan", 'PAK', '586', "Islamic Republic of Pakistan"),
  ('PW', 'OC', "Palau", 'PLW', '585', "Republic of Palau"),
  ('PS', 'AS', "Palestine", 'PSE', '275', "State of Palestine"),
  ('PA', 'NA', "Panama", 'PAN', '591', "Republic of Panama"),
  ('PG', 'OC', "Papua New Guinea", 'PNG', '598', "Independent State of Papua New Guinea"),
  ('PY', 'SA', "Paraguay", 'PRY', '600', "Republic of Paraguay"),
  ('PE', 'SA', "Peru", 'PER', '604', "Republic of Peru"),
  ('PH', 'AS', "Philippines", 'PHL', '608', "Republic of the Philippines"),
  ('PN', 'OC', "Pitcairn Islands", 'PCN', '612', "Pitcairn Islands"),
  ('PL', 'EU', "Poland", 'POL', '616', "Republic of Poland"),
  ('PT', 'EU', "Portugal", 'PRT', '620', "Portuguese Republic"),
  ('PR', 'NA', "Puerto Rico", 'PRI', '630', "Commonwealth of Puerto Rico"),
  ('QA', 'AS', "Qatar", 'QAT', '634', "State of Qatar"),
  ('RE', 'AF', "Réunion", 'REU', '638', "Réunion"),
  ('RO', 'EU', "Romania", 'ROU', '642', "Romania"),
  ('RU', 'EU', "Russian Federation", 'RUS', '643', "Russian Federation"),
  ('RW', 'AF', "Rwanda", 'RWA', '646', "Republic of Rwanda"),
  ('BL', 'NA', "Saint Barthélemy", 'BLM', '652', "Saint Barthélemy"),
  ('SH', 'AF', "Saint Helena, Ascension and Tristan da Cunha", 'SHN', '654', "Saint Helena, Ascension and Tristan da Cunha"),
  ('KN', 'NA', "Saint Kitts and Nevis", 'KNA', '659', "Federation of Saint Kitts and Nevis"),
  ('LC', 'NA', "Saint Lucia", 'LCA', '662', "Saint Lucia"),
  ('MF', 'NA', "Saint Martin", 'MAF', '663', "Saint Martin (French part)"),
  ('PM', 'NA', "Saint Pierre and Miquelon", 'SPM', '666', "Saint Pierre and Miquelon"),
  ('VC', 'NA', "Saint Vincent and the Grenadines", 'VCT', '670', "Saint Vincent and the Grenadines"),
  ('WS', 'OC', "Samoa", 'WSM', '882', "Independent State of Samoa"),
  ('SM', 'EU', "San Marino", 'SMR', '674', "Republic of San Marino"),
  ('ST', 'AF', "Sao Tome and Principe", 'STP', '678', "Democratic Republic of Sao Tome and Principe"),
  ('SA', 'AS', "Saudi Arabia", 'SAU', '682', "Kingdom of Saudi Arabia"),
  ('SN', 'AF', "Senegal", 'SEN', '686', "Republic of Senegal"),
  ('RS', 'EU', "Serbia", 'SRB', '688', "Republic of Serbia"),
  ('SC', 'AF', "Seychelles", 'SYC', '690', "Republic of Seychelles"),
  ('SL', 'AF', "Sierra Leone", 'SLE', '694', "Republic of Sierra Leone"),
  ('SG', 'AS', "Singapore", 'SGP', '702', "Republic of Singapore"),
  ('SX', 'NA', "Sint Maarten (Dutch part)", 'SXM', '534', "Sint Maarten (Dutch part)"),
  ('SK', 'EU', "Slovakia (Slovak Republic)", 'SVK', '703', "Slovakia (Slovak Republic)"),
  ('SI', 'EU', "Slovenia", 'SVN', '705', "Republic of Slovenia"),
  ('SB', 'OC', "Solomon Islands", 'SLB', '090', "Solomon Islands"),
  ('SO', 'AF', "Somalia", 'SOM', '706', "Federal Republic of Somalia"),
  ('ZA', 'AF', "South Africa", 'ZAF', '710', "Republic of South Africa"),
  ('GS', 'AN', "South Georgia and the South Sandwich Islands", 'SGS', '239', "South Georgia and the South Sandwich Islands"),
  ('SS', 'AF', "South Sudan", 'SSD', '728', "Republic of South Sudan"),
  ('ES', 'EU', "Spain", 'ESP', '724', "Kingdom of Spain"),
  ('LK', 'AS', "Sri Lanka", 'LKA', '144', "Democratic Socialist Republic of Sri Lanka"),
  ('SD', 'AF', "Sudan", 'SDN', '729', "Republic of Sudan"),
  ('SR', 'SA', "Suriname", 'SUR', '740', "Republic of Suriname"),
  ('SJ', 'EU', "Svalbard & Jan Mayen Islands", 'SJM', '744', "Svalbard & Jan Mayen Islands"),
  ('SZ', 'AF', "Eswatini", 'SWZ', '748', "Kingdom of Eswatini"),
  ('SE', 'EU', "Sweden", 'SWE', '752', "Kingdom of Sweden"),
  ('CH', 'EU', "Switzerland", 'CHE', '756', "Swiss Confederation"),
  ('SY', 'AS', "Syrian Arab Republic", 'SYR', '760', "Syrian Arab Republic"),
  ('TW', 'AS', "Taiwan", 'TWN', '158', "Taiwan, Province of China"),
  ('TJ', 'AS', "Tajikistan", 'TJK', '762', "Republic of Tajikistan"),
  ('TZ', 'AF', "Tanzania", 'TZA', '834', "United Republic of Tanzania"),
  ('TH', 'AS', "Thailand", 'THA', '764', "Kingdom of Thailand"),
  ('TL', 'AS', "Timor-Leste", 'TLS', '626', "Democratic Republic of Timor-Leste"),
  ('TG', 'AF', "Togo", 'TGO', '768', "Togolese Republic"),
  ('TK', 'OC', "Tokelau", 'TKL', '772', "Tokelau"),
  ('TO', 'OC', "Tonga", 'TON', '776', "Kingdom of Tonga"),
  ('TT', 'NA', "Trinidad and Tobago", 'TTO', '780', "Republic of Trinidad and Tobago"),
  ('TN', 'AF', "Tunisia", 'TUN', '788', "Tunisian Republic"),
  ('TR', 'AS', "Turkey", 'TUR', '792', "Republic of Turkey"),
  ('TM', 'AS', "Turkmenistan", 'TKM', '795', "Turkmenistan"),
  ('TC', 'NA', "Turks and Caicos Islands", 'TCA', '796', "Turks and Caicos Islands"),
  ('TV', 'OC', "Tuvalu", 'TUV', '798', "Tuvalu"),
  ('UG', 'AF', "Uganda", 'UGA', '800', "Republic of Uganda"),
  ('UA', 'EU', "Ukraine", 'UKR', '804', "Ukraine"),
  ('AE', 'AS', "United Arab Emirates", 'ARE', '784', "United Arab Emirates"),
  ('GB', 'EU', "United Kingdom of Great Britain and Northern Ireland", 'GBR', '826', "United Kingdom of Great Britain & Northern Ireland"),
  ('US', 'NA', "United States of America", 'USA', '840', "United States of America"),
  ('UM', 'OC', "United States Minor Outlying Islands", 'UMI', '581', "United States Minor Outlying Islands"),
  ('VI', 'NA', "United States Virgin Islands", 'VIR', '850', "United States Virgin Islands"),
  ('UY', 'SA', "Uruguay", 'URY', '858', "Eastern Republic of Uruguay"),
  ('UZ', 'AS', "Uzbekistan", 'UZB', '860', "Republic of Uzbekistan"),
  ('VU', 'OC', "Vanuatu", 'VUT', '548', "Republic of Vanuatu"),
  ('VE', 'SA', "Venezuela", 'VEN', '862', "Bolivarian Republic of Venezuela"),
  ('VN', 'AS', "Vietnam", 'VNM', '704', "Socialist Republic of Vietnam"),
  ('WF', 'OC', "Wallis and Futuna", 'WLF', '876', "Wallis and Futuna"),
  ('EH', 'AF', "Western Sahara", 'ESH', '732', "Western Sahara"),
  ('YE', 'AS', "Yemen", 'YEM', '887', "Yemen"),
  ('ZM', 'AF', "Zambia", 'ZMB', '894', "Republic of Zambia"),
  ('ZW', 'AF', "Zimbabwe", 'ZWE', '716', "Republic of Zimbabwe");

INSERT INTO location VALUES (1, 'BEIJING, BEIJING, CHINA', 'Beijing', 'Beijing', 'China');
INSERT INTO location VALUES (2, 'NEW YORK, NEW YORK, UNITED STATES', 'New York', 'New York', 'United States');
INSERT INTO location VALUES (3, 'PARIS, ÎLE-DE-FRANCE, FRANCE', 'Paris', 'Île-de-France', 'France');
INSERT INTO location VALUES (4, 'RIO DE JANEIRO, RIO DE JANEIRO, BRAZIL', 'Rio de Janeiro', 'Rio de Janeiro', 'Brazil');

INSERT INTO scrape VALUES (20210706215602, '2021-07-06', 3);
INSERT INTO scrape VALUES (20210717121617, '2021-07-17', 4);
INSERT INTO scrape VALUES (20210717121634, '2021-07-17', 1);
INSERT INTO scrape VALUES (20210804005827, '2021-08-04', 2);
