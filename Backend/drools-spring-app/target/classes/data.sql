insert into user_table(username, password, first_name, last_name, email, user_type) values('guest', 'guest', 'guest', 'guest','guest', 0);
insert into member(member_pkey, banned, membership_expired, wrong_tags) values(1, false, true, 0);

insert into user_table(username, password, first_name, last_name, email, user_type) values('admin', 'admin', 'admin', 'admin','admin', 1);

insert into tag(tag_name, tag_approved) values ('author', true);
insert into tag(tag_name, tag_approved) values ('name', true);
insert into tag(tag_name, tag_approved) values ('description', true);
insert into employee_table(employee_pkey) values (2);



insert into book(avaivable_no,rating,search_match) values(1,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (1,2,'Lovac na zmajeve');
insert into book_tag(book_id,tag_key,tag_value) values (1,1,'Khaled Hosseini');
insert into book_tag(book_id,tag_key,tag_value) values (1,3,'Emotivna prica o ljubavi i prijateljstvu smjestena u Afganistan za vrijeme okrutnog talibanskog rezima. Paralelno uz pricu o decackom prijateljstvu koje razaraju strah, ljubomora i okrutnost, tece prica o modernom Afganistanu, neverovatno nesretnoj zemlji koju je nacela americka okupacija a potom dokrajcio talibanski rezim.');

insert into book(avaivable_no,rating,search_match) values(4,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (2,2,'Za kim zvono zvoni');
insert into book_tag(book_id,tag_key,tag_value) values (2,1,'Ernest Hemingvej');
insert into book_tag(book_id,tag_key,tag_value) values (2,3,'Roman napisan 1939. na Kubi a objavljen 1940., jedno od piscevih najpopularnijih dela. To je istorijsko - ljubavni roman koji govori o dozivljajima americkog profesora spanskog jezika Roberta Jordana, koji ucestvuje u Spanskom gradjanskom ratu kao dobrovoljac protiv fasista Francisca Franca, a na strani komunista. Sam roman bazira se na Hemingwayevim iskustvima u Spaniji tokom rata.');;


insert into book(avaivable_no,rating,search_match) values(2,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (3,2,'Ubiti pticu rugalicu');
insert into book_tag(book_id,tag_key,tag_value) values (3,1,'Harper Lee');
insert into book_tag(book_id,tag_key,tag_value) values (3,3,'Americki dramski roman koji je 1960. napisala i objavila Harper Lee. Postigao je brzi uspeh i postao klasik americke knjizevnosti. Radnja se labavo zasniva na Leeinim opazanjima o svojoj porodici i komsijama, kao i na dogadjaju koji se je odigrao u blizini njenog grada 1936., kada je imala 10 godina. Roman krasi toplina i humor, uprkos tome sto se bavi ozbiljnim temama o silovanju i rasnoj diskriminaciji.');


insert into book(avaivable_no,rating,search_match) values(5,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (4,2,'Proces');
insert into book_tag(book_id,tag_key,tag_value) values (4,1,'Franz Kafka');
insert into book_tag(book_id,tag_key,tag_value) values (4,3,'Proces je roman Franza Kafke, napisan izmedju 1914. i 1915. godine i objavljen 1925. godine. Jedan od njegovih najpoznatijih radova, to je prica o coveku koji je uhapsen i optuzen od strane dalekog i nedostupnog organa vlasti, za zločin cija priroda ostaje do kraja nepoznata i njemu i citaocu.');


insert into book(avaivable_no,rating,search_match) values(3,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (5,2,'Na Drini Cuprija');
insert into book_tag(book_id,tag_key,tag_value) values (5,1,'Ivo Andric');
insert into book_tag(book_id,tag_key,tag_value) values (5,4,'Turcin');
insert into book_tag(book_id,tag_key,tag_value) values (5,3,'Na Drini cuprija je jedan od najpoznatijih romana domaceg knjizevnika i nobelovca Ive Andrica, a nakon Proklete avlije i jedan od knjizevno doradjenijih, hronoloski prati cetiri veka zbivanja oko velikog mosta preko reke Drine u Visegradu, koji je izgradio veliki vezir Mehmed paša Sokolovic, poreklom iz tih krajeva.');