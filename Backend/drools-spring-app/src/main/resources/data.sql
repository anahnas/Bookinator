insert into user_table(username, password, first_name, last_name, email, user_type) values('guest', 'guest', 'guest', 'guest','guest', 0);
insert into member(member_pkey, banned, membership_expired, can_rent, rented, discount, membership) values(1, false, true, true, 10, 0, 100);

insert into user_table(username, password, first_name, last_name, email, user_type) values('guest2', 'guest2', 'guest2', 'guest2','guest2', 0);
insert into member(member_pkey, banned, membership_expired, can_rent, rented, discount, membership) values(2, false, true, true, 1, 0, 150);

insert into user_table(username, password, first_name, last_name, email, user_type) values('guest3', 'guest3', 'guest3', 'guest3','guest3', 0);
insert into member(member_pkey, banned, membership_expired, can_rent, rented, discount, membership) values(3, false, true, true, 2, 0, 200);

insert into user_table(username, password, first_name, last_name, email, user_type) values('guest4', 'guest', 'guest', 'guest','guest', 0);
insert into member(member_pkey, banned, membership_expired, can_rent, rented, discount, membership) values(4, false, true, true, 3, 0, 250);

insert into user_table(username, password, first_name, last_name, email, user_type) values('guest5', 'guest', 'guest', 'guest','guest', 0);
insert into member(member_pkey, banned, membership_expired, can_rent, rented, discount, membership) values(5, false, true, true, 14, 0, 300);

insert into user_table(username, password, first_name, last_name, email, user_type) values('admin', 'admin', 'admin', 'admin','admin', 1);

insert into tag(tag_name, tag_approved) values ('author', true);
insert into tag(tag_name, tag_approved) values ('name', true);
insert into tag(tag_name, tag_approved) values ('description', true);
insert into tag(tag_name, tag_approved) values ('character', true);
insert into tag(tag_name, tag_approved) values ('targetAgeGroup', true);
insert into employee_table(employee_pkey) values (2);


insert into book(avaivable_no,rating,search_match) values(0,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (1,2,'The kite runner');
insert into book_tag(book_id,tag_key,tag_value) values (1,1,'Khaled Hosseini');
insert into book_tag(book_id,tag_key,tag_value) values (1,5,50);
insert into book_tag(book_id,tag_key,tag_value) values (1,3,'The Kite Runner is the first novel by Afghan-American author Khaled Hosseini.[1] Published in 2003 by Riverhead Books, it tells the story of Amir, a young boy from the Wazir Akbar Khan district of Kabul, whose closest friend is Hassan. The story is set against a backdrop of tumultuous events, from the fall of Afghanistans monarchy through the Soviet military intervention, the exodus of refugees to Pakistan and the United States, and the rise of the Taliban regime.');

insert into book(avaivable_no,rating,search_match) values(4,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (2,2,'For Whom the Bell Tolls');
insert into book_tag(book_id,tag_key,tag_value) values (2,1,'ErnestHemingway');
insert into book_tag(book_id,tag_key,tag_value) values (2,3,'For Whom the Bell Tolls is a novel by Ernest Hemingway published in 1940. It tells the story of Robert Jordan, a young American volunteer attached to a Republican guerrilla unit during the Spanish Civil War. As a dynamiter, he is assigned to blow up a bridge during an attack on the city of Segovia.');;


insert into book(avaivable_no,rating,search_match) values(2,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (3,2,'To Kill a Mockingbird ');
insert into book_tag(book_id,tag_key,tag_value) values (3,1,'Harper Lee');
insert into book_tag(book_id,tag_key,tag_value) values (3,3,'To Kill a Mockingbird is a novel by Harper Lee published in 1960. Instantly successful, widely read in high schools and middle schools in the United States, it has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lees observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.');


insert into book(avaivable_no,rating,search_match) values(5,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (4,2,'The Trial');
insert into book_tag(book_id,tag_key,tag_value) values (4,1,'Franz Kafka');
insert into book_tag(book_id,tag_key,tag_value) values (4,3,'The Trial is a novel written by Franz Kafka between 1914 and 1915 and published posthumously in 1925. One of his best-known works, it tells the story of Josef K., a man arrested and prosecuted by a remote, inaccessible authority, with the nature of his crime revealed neither to him nor to the reader.');


insert into book(avaivable_no,rating,search_match) values(0,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (5,2,'The Bridge on the Drina');
insert into book_tag(book_id,tag_key,tag_value) values (5,1,'Ivo Andric');
insert into book_tag(book_id,tag_key,tag_value) values (5,4,'turk');
insert into book_tag(book_id,tag_key,tag_value) values (5,3,'The Bridge on the Drina is a historical novel by the Yugoslav writer Ivo AndriÄ‡. It revolves around the Mehmed PaÅ¡a SokoloviÄ‡ Bridge in ViÅ¡egrad, which spans the Drina River and stands as a silent witness to history from its construction by the Ottomans in the mid-16th century until its partial destruction during World War I. The story spans about four centuries and covers the Ottoman and Austro-Hungarian occupations of the region, with a particular emphasis on the lives, destinies and relations of the local inhabitants, especially Serbs and Bosnian Muslims. .');

insert into book(avaivable_no,rating,search_match) values(3,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (6,2,'The unbearable lightness of being');
insert into book_tag(book_id,tag_key,tag_value) values (6,1,'Milan Kundera');
insert into book_tag(book_id,tag_key,tag_value) values (6,4,'Sabine');
insert into book_tag(book_id,tag_key,tag_value) values (6,3,'Successful surgeon Tomas (Daniel Day-Lewis) leaves Prague for an operation, meets a young photographer named Tereza (Juliette Binoche), and brings her back with him. Tereza is surprised to learn that Tomas is already having an affair with the bohemian Sabina (Lena Olin), but when the Soviet invasion occurs, all three flee to Switzerland. Sabina begins an affair, Tom continues womanizing, and Tereza, disgusted, returns to Czechoslovakia. Realizing his mistake, Tomas decides to chase after her.');

insert into book(avaivable_no,rating,search_match) values(3,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (7,2,'The Hitchhiker Guide to the Galaxy');
insert into book_tag(book_id,tag_key,tag_value) values (7,1,'Douglas Adams');
insert into book_tag(book_id,tag_key,tag_value) values (7,4,'Ford Prefect');
insert into book_tag(book_id,tag_key,tag_value) values (7,3,'The Hitchhiker Guide to the Galaxy is a comedy science fiction series created by Douglas Adams. Originally a radio comedy broadcast on BBC Radio 4 in 1978, it was later adapted to other formats, including stage shows, novels, comic books, a 1981 TV series, a 1984 video game, and 2005 feature film.');

insert into book(avaivable_no,rating,search_match) values(3,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (7,2,'The Hitchhiker Guide to the Galaxy');
insert into book_tag(book_id,tag_key,tag_value) values (7,1,'Douglas Adams');
insert into book_tag(book_id,tag_key,tag_value) values (7,4,'Ford Prefect');
insert into book_tag(book_id,tag_key,tag_value) values (7,3,'The Hitchhiker Guide to the Galaxy is a comedy science fiction series created by Douglas Adams. Originally a radio comedy broadcast on BBC Radio 4 in 1978, it was later adapted to other formats, including stage shows, novels, comic books, a 1981 TV series, a 1984 video game, and 2005 feature film.');

insert into book(avaivable_no,rating,search_match) values(3,0,0);
insert into book_tag(book_id,tag_key,tag_value) values (8,2,'Steppenwolf');
insert into book_tag(book_id,tag_key,tag_value) values (8,1,'Hermann Hesse');
insert into book_tag(book_id,tag_key,tag_value) values (8,4,'Harry Haller');
insert into book_tag(book_id,tag_key,tag_value) values (8,3,'Steppenwolf is the tenth novel by German-Swiss author Hermann Hesse. Originally published in Germany in 1927, it was first translated into English in 1929. The novel was named after the German name for the steppe wolf');


insert into member_wishlist(member_member_pkey, wishlist_id) values (1, 5);
insert into member_wishlist(member_member_pkey, wishlist_id) values (1, 4);
insert into member_wishlist(member_member_pkey, wishlist_id) values (1, 1);
insert into member_wishlist(member_member_pkey, wishlist_id) values (2, 1);
insert into member_wishlist(member_member_pkey, wishlist_id) values (2, 4);
insert into member_wishlist(member_member_pkey, wishlist_id) values (3, 5);
insert into member_wishlist(member_member_pkey, wishlist_id) values (3, 4);
insert into member_wishlist(member_member_pkey, wishlist_id) values (3, 1);

insert into tag(tag_name, tag_approved) values ('deaths', false);
insert into book_tag(book_id,tag_key,tag_value) values (5,6,'Main character');
insert into member_wrong_tags(member_member_pkey, wrong_tags_id) values (1, 6);


insert into book_rating values(1,1,1);
insert into book_rating values(2,1,2);
insert into book_rating values(6,1,5);
insert into book_rating values(7,1,4.5);
insert into book_rating values(8,1,1);
insert into book_rating values(9,1,5);

insert into book_rating values(1,2,1);
insert into book_rating values(2,2,2);
insert into book_rating values(3,2,5);
insert into book_rating values(4,2,5);
insert into book_rating values(5,2,5);
insert into book_rating values(6,2,4);
insert into book_rating values(7,2,5);
insert into book_rating values(8,2,1);
insert into book_rating values(9,2,5);


insert into book_rating values(2,3,2.5);
insert into book_rating values(3,3,4.5);
insert into book_rating values(4,3,4);
insert into book_rating values(5,3,3);
insert into book_rating values(6,3,3.5);
insert into book_rating values(7,3,4.5);
insert into book_rating values(8,3,4);
insert into book_rating values(9,3,5);

insert into book_rating values(1,4,5);
insert into book_rating values(2,4,5);
insert into book_rating values(3,4,5);
insert into book_rating values(4,4,1);
insert into book_rating values(5,4,2);
insert into book_rating values(6,4,3);
insert into book_rating values(7,4,1);
insert into book_rating values(8,4,4);
insert into book_rating values(9,4,1);
