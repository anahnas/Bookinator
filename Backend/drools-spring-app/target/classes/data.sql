insert into user_table(username, password, first_name, last_name, email) values('guest', 'guest', 'guest', 'guest','guest');

insert into tag(tag_name) values ("author");
insert into tag(tag_name) values ("name");
insert into tag(tag_name) values ("description");



insert into book(avaivable_no,rating,search_match) values(1,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (1,2,'Lovac na zmajeve');

insert into book(avaivable_no,rating,search_match) values(4,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (2,2,'Za kim zvono zvoni');
insert into book_tag(book_id,tag_key,tag_value) values (2,1,'Ernest Hemingvej');
insert into book_tag(book_id,tag_key,tag_value) values (2,3,'Roman napisan 1939. na Kubi a objavljen 1940.');

insert into book(avaivable_no,rating,search_match) values(2,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (3,2,'Ubiti pticu rugalicu');
insert into book_tag(book_id,tag_key,tag_value) values (3,1,'Harper Lee');
insert into book_tag(book_id,tag_key,tag_value) values (3,3,'Američki dramski roman.');


insert into book(avaivable_no,rating,,search_match) values(5,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (4,2,'Proces');
insert into book_tag(book_id,tag_key,tag_value) values (4,1,'Franz Kafka');
insert into book_tag(book_id,tag_key,tag_value) values (4,3,'Proces je roman Franza Kafke, napisan između 1914. i 1915. godine i objavljen 1925. godine. Jedan od njegovih najpoznatijih radova, to je priča o čoveku koji je uhapšen i optužen od strane dalekog i nedostupnog organa vlasti, za zločin čija priroda ostaje do kraja nepoznata i njemu i čitaocu.');
